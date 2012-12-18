/*
 * Created on 15/10/2004
 *
 */
package logic.formulas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Represents a factory of formulas. Uses Flyweight design pattern.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FormulaFactory {

    private Map<String, Formula> _atomicFormulas;

    private Map<String, Formula> _compositeFormulas;
    
    private Set<Connective> _connectives;
    
    /**
     * creates a formula factory
     */
    public FormulaFactory() {
        _atomicFormulas = new TreeMap<String, Formula>(); // ordered
        _compositeFormulas = new HashMap<String, Formula>();
        _connectives = new HashSet<Connective>();
    }

    // getters and setters

    // methods for creating formulas (without substitution)

    /**
     * creates an atomic formula
     * 
     * @param atom -
     *                   an atom name
     * @return the atomic formula created
     */
    public AtomicFormula createAtomicFormula(String atom) {
        
//        System.err.println("FF " + this.hashCode() +  " "+ atom);

        if (_atomicFormulas.containsKey(atom)) {
            return (AtomicFormula) _atomicFormulas.get(atom);
        }

        AtomicFormula af;
        _atomicFormulas.put(atom, af = new AtomicFormula(atom));

        return af;
    }

    // for zeroary formulas
    /**
     * creates a zeorary formula.
     * 
     * @param connective -
     *                   a zeroary connective
     * @return the zeorary formula created
     */
    public Formula createCompositeFormula(Connective connective) {

        _connectives.add(connective);

        if (connective.getArity().equals(Arity.ZEROARY))
            return createCompositeFormula(connective, new ArrayList<Formula>());
        else
            throw new RuntimeException("Wrong arity!");
    }

    // for unary formulas
    /**
     * creates a unary formula.
     * 
     * @param connective -
     *                   a unary connective
     * @param f -
     *                   a formula
     * @return the unary formula created
     */
    public Formula createCompositeFormula(Connective connective, Formula f) {
        if (connective.getArity().equals(Arity.UNARY)) {
            List<Formula> l = new ArrayList<Formula>();
            l.add(f);
            Formula f2 = createCompositeFormula(connective, l);

            _connectives.add(connective);

            return f2;
        } else
            throw new RuntimeException("Wrong arity!");
    }

    // for binary formulas
    /**
     * creates a binary formula.
     * 
     * @param connective
     *                   -a binary connective
     * @param left -
     *                   the left formula
     * @param right -
     *                   the right formula
     * @return the binary formula created
     */
    public Formula createCompositeFormula(Connective connective, Formula left,
            Formula right) {
        if (connective.getArity().equals(Arity.BINARY)) {

            List<Formula> formulas = new ArrayList<Formula>();
            formulas.add(left);
            formulas.add(right);

            if (_compositeFormulas.containsKey(CompositeFormula.toString(
                    connective, formulas))) {
                return (CompositeFormula) _compositeFormulas
                        .get(CompositeFormula.toString(connective, formulas));
            }

            CompositeFormula cf;
            _compositeFormulas.put(CompositeFormula.toString(connective,
                    formulas), cf = new CompositeFormula(connective, formulas));

            _connectives.add(connective);
            return cf;
        } else
            throw new RuntimeException("Wrong arity!");
    }

    // for nary formulas
    /**
     * creates a nary formula.
     * 
     * @param connective -
     *                   a nary connective
     * @param formulas -
     *                   a list of formulas
     * @return the nary formula created.
     */
    public Formula createCompositeFormula(Connective connective, List<Formula> formulas) {

        if (_compositeFormulas.containsKey(CompositeFormula.toString(
                connective, formulas))) {
            return (CompositeFormula) _compositeFormulas.get(CompositeFormula
                    .toString(connective, formulas));
        }
//        else{
//            Object[] cf = _compositeFormulas.keySet().toArray();
//            List l = Arrays.asList(cf);
//            for (int i=0; i<l.size(); i++){
//                if (((String)l.get(i)).equals (CompositeFormula.toString(connective,formulas))){
//                    System.err.println("ERROR");
//                    
//                }
//            }
            
//        }

        CompositeFormula cf;
        _compositeFormulas.put(CompositeFormula.toString(connective, formulas),
                cf = new CompositeFormula(connective, formulas));

        _connectives.add(connective);

        return cf;
    }

    // methods for creating formulas with substitution

    /**
     * createas a formula by substitution.
     * 
     * @param base -
     *                   the base formula
     * @param substituted -
     *                   the subformula of the base formula to be substituted by the
     *                   replacement
     * @param replacement -
     *                   the replacement formula
     * @return the formula created by substitution
     */
    public Formula createFormulaBySubstitution(Formula base,
            Formula substituted, Formula replacement) {

        Formula f = recursivelyCreateFormulaBySubstitution(base, substituted,
                replacement);

        return f;
    }

    private Formula recursivelyCreateFormulaBySubstitution(Formula base,
            Formula substituted, Formula replacement) {
        if (base == substituted) {
            return replaceSubformula(base, replacement);
        } else {
            if (base instanceof AtomicFormula) {
                return base;
            } else if (base instanceof CompositeFormula) {
                CompositeFormula cf = (CompositeFormula) base;
                Connective conn = cf.getConnective();
                if (conn.getArity() == Arity.ZEROARY) {
                    return base;
                } else if (conn.getArity() == Arity.UNARY) {
                    return replaceRecursivelyUnary(substituted, replacement,
                            cf, conn);
                } else if (conn.getArity() == Arity.BINARY) {
                    return replaceRecursivelyBinary(substituted, replacement,
                            cf, conn);
                } else if (conn.getArity() == Arity.NARY) {
                    return replaceRecursivelyNary(substituted, replacement, cf,
                            conn);
                }
            }
        }
        return null;
    }

    private Formula replaceRecursivelyUnary(Formula substituted,
            Formula replacement, CompositeFormula cf, Connective conn) {
        return createCompositeFormula(conn,
                recursivelyCreateFormulaBySubstitution((Formula) cf
                        .getImmediateSubformulas().get(0), substituted,
                        replacement));
    }

    private Formula replaceRecursivelyBinary(Formula substituted,
            Formula replacement, CompositeFormula cf, Connective conn) {
        return createCompositeFormula(conn,
                recursivelyCreateFormulaBySubstitution((Formula) cf
                        .getImmediateSubformulas().get(0), substituted,
                        replacement), recursivelyCreateFormulaBySubstitution(
                        (Formula) cf.getImmediateSubformulas().get(1),
                        substituted, replacement));
    }

    private Formula replaceRecursivelyNary(Formula substituted,
            Formula replacement, CompositeFormula cf, Connective conn) {
        List<Formula> l = new ArrayList<Formula>();
        // for each subformula, replace
        for (int i = 0; i < cf.getImmediateSubformulas().size(); i++) {
            l
                    .add(recursivelyCreateFormulaBySubstitution((Formula) cf
                            .getImmediateSubformulas().get(i), substituted,
                            replacement));

        }
        return createCompositeFormula(conn, l);
    }

    private Formula replaceSubformula(Formula base, Formula replacement) {
        if (replacement instanceof AtomicFormula) {
            return cloneAtomicFormula((AtomicFormula) replacement);
        } else if (replacement instanceof CompositeFormula) {
            return cloneCompositeFormula((CompositeFormula) replacement);
        } else
            return base;
    }

    // methods for cloning

    /**
     * clones an atomic formula
     * 
     * @param af
     * @return
     */
    public AtomicFormula cloneAtomicFormula(AtomicFormula af) {
        return (AtomicFormula) af.clone(this);
    }

    /**
     * clones a composite formula
     * 
     * @param nf
     * @return
     */
    public CompositeFormula cloneCompositeFormula(CompositeFormula nf) {
        return (CompositeFormula) nf.clone(this);
    }

    // methods for calculating the complexity of the formula factory
    /**
     * returns the size of a formula factory.
     * 
     * @return the size of a formula factory
     */
    public int getSize() {
        return _atomicFormulas.size() + _compositeFormulas.size();
    }

    /**
     * returns the size of the atomic formulas map.
     * 
     * @return the size of the atomic formulas map.
     */
    public int getAtomicFormulasSize() {
        return _atomicFormulas.size();
    }

    /**
     * returns the size of the composite formulas map.
     * 
     * @return the size of the composite formulas map.
     */
    public int getCompositeFormulasSize() {
        return _compositeFormulas.size();
    }

    /**
     * returns the size of the connectives map.
     * 
     * @return the size of the connectives map
     */
    public int getConnectivesSize() {
        return _connectives.size();
    }

    /**
     * @return the size of main structures used in the formula factory.
     */
    public long getStructuresSize() {
        long size = 0;

        size += getAtomicFormulas().size();
        size += getCompositeFormulas().size();
        size += getConnectivesSize();

        return size;
    }

    /**
     * @return Returns the _atomicFormulas.
     */
    public Map<String, Formula> getAtomicFormulas() {
        return _atomicFormulas;
    }

    /**
     * @return Returns the _compositeFormulas.
     */
    public Map<String, Formula> getCompositeFormulas() {
        return _compositeFormulas;
    }

    // methods for displaying the formula factory
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Atomic formulas:" + _atomicFormulas.toString()
                + " Composite formulas: " + _compositeFormulas.toString();
    }

}