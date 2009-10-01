/*
 * Created on 15/10/2004
 *
 */
package logic.formulas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.EqualsUtil;

/**
 * Represents a composite formula. Every composite formula has a connecetive and
 * a list of subformulas (possibly empty).
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class CompositeFormula extends Formula {

    private Connective _connective;

    private List<Formula> _subformulas;

    private int _hashCode;

    /**
     * Creates a composite formula.
     * 
     * @param _connective -
     *            a connective
     * @param _subformulas -
     *            a list of subformulas
     */
    protected CompositeFormula(Connective _connective, List<Formula> _subformulas) {
        super();
        this._connective = _connective;
        this._subformulas = _subformulas;

        // hashCode aspect
        this._hashCode = calculateHashCode();
    }

    /**
     * Creates a zeroary composite formula.
     * 
     * @param _connective -
     *            a zeroary connecive.
     */
    protected CompositeFormula(Connective _connective) {
        super();
        if (_connective.getArity() == Arity.ZEROARY) {
            this._connective = _connective;
            this._subformulas = new ArrayList<Formula>();

            // hashCode aspect
            this._hashCode = calculateHashCode();
        } else
            throw new RuntimeException("Wrong arity!");
    }

    /**
     * Creates a unary composite formula.
     * 
     * @param _connective -
     *            a unary connective
     * @param f -
     *            a formula
     */
    protected CompositeFormula(Connective _connective, Formula f) {
        super();
        if (_connective.getArity() == Arity.UNARY) {
            this._connective = _connective;
            this._subformulas = new ArrayList<Formula>();
            this._subformulas.add(f);

            // hashCode aspect
            this._hashCode = calculateHashCode();
        } else
            throw new RuntimeException("Wrong arity!");
    }

    /**
     * Creates a binary connective.
     * 
     * @param _connective -
     *            a connective
     * @param f1 -
     *            the left formula
     * @param f2 -
     *            the right formula
     */
    protected CompositeFormula(Connective _connective, Formula f1, Formula f2) {
        super();
        if (_connective.getArity() == Arity.BINARY) {
            this._connective = _connective;
            this._subformulas = new ArrayList<Formula>();
            this._subformulas.add(f1);
            this._subformulas.add(f2);

            // hashCode aspect
            this._hashCode = calculateHashCode();

        } else
            throw new RuntimeException("Wrong arity!");
    }

    public boolean equals(Object aThat) {
        if (this._hashCode == aThat.hashCode())
            return true;
        // use instanceof instead of getClass here for two reasons
        // 1. if need be, it can match any supertype, and not just one class;
        // 2. it renders an explict check for "that == null" redundant, since
        // it does the check for null already - "null instanceof [type]" always
        // returns false. (See Effective Java by Joshua Bloch.)
        if (!(aThat instanceof CompositeFormula))
            return false;
        // Alternative to the above line :
        // if ( aThat == null || aThat.getClass() != this.getClass() ) return
        // false;

        // cast to native object is now safe
        CompositeFormula that = (CompositeFormula) aThat;

        // now a proper field-by-field evaluation can be made
        return EqualsUtil.areEqual(this._connective, that._connective)
                && EqualsUtil.areEqual(this._subformulas, that._subformulas);
    }

    //    static Map m = new HashMap();
    //
    //    static int size = 0;

    public int hashCode() {

        //        if (m.get(this.toString())==null){
        //                    m.put(this.toString(),new Integer(result));
        //                }
        //                else{
        //                    Integer i = (Integer)m.get(this.toString());
        //                    if (i.intValue()!=result){
        //                        System.err.println("Erro: "+ this.toString());
        //                    }
        //                }
        //                if (m.size()>size+10){
        //                    System.out.println(m.toString());
        //                    System.out.println();
        //                    size = m.size();
        //                }

        return _hashCode;
    }

    // hashCode aspect
    /**
     * @return calculated hashCode
     */
    private int calculateHashCode() {
        
        int primes[]={2,3,5,7,11,13,17};
        
        int result = 0;
        result = _connective.hashCode()*primes[0];
        Iterator<Formula> it = _subformulas.iterator();
        
        int i = 1;
        while (it.hasNext()){
            Object o = it.next();
            o.hashCode();
            result = result+o.hashCode()*primes[i];
            i++;
        }
        
//        int result = HashCodeUtil.SEED;
//        result = HashCodeUtil.hash(result, _connective);
//        result = HashCodeUtil.hash(result, _subformulas);
        //        System.out.println(this + " " + result);

        //                System.out.println(result + " " + this);
        //                if (m.get(this.toString())==null){
        //                    m.put(this.toString(),new Integer(result));
        //                }
        //                else{
        //                    Integer i = (Integer)m.get(this.toString());
        //                    if (i.intValue()!=result){
        //                        System.err.println("Erro: "+ this.toString());
        //                    }
        //                }
        //                if (m.size()>size+10){
        //                    System.out.println(m.toString());
        //                    System.out.println();
        //                    size = m.size();
        //                }

        return result;

    }

    /*
     * (non-Javadoc)
     * 
     * @see formulasNew.Formula#getImmediateSubformulas()
     */
    public List<Formula> getImmediateSubformulas() {
        return _subformulas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see formulasNew.Formula#toString()
     */
    public String toString() {
        String result = "";
        if (_connective.getArity() == Arity.ZEROARY) {
            return _connective.getSymbol();
        } else if (_connective.getArity() == Arity.UNARY) {
            return _connective.getSymbol() + "" + _subformulas.get(0) + "";
        } else if (_connective.getArity() == Arity.BINARY) {
            return "(" + _subformulas.get(0) + _connective.getSymbol()
                    + _subformulas.get(1) + ")";
        } else if (_connective.getArity() == Arity.NARY) {
            result = "(";
            for (int i = 0; i < _subformulas.size() - 1; i++) {
                result = result + _subformulas.get(i) + _connective.getSymbol();
            }
            result = result + _subformulas.get(_subformulas.size() - 1) + ")";
        }

        return result;
    }

    /**
     * Returns a string similar to the one that would be generated for a formula
     * with the same arguments.
     * 
     * @param connective -
     *            a connective
     * @param subformulas -
     *            a list of subformulas
     * @return a string similar to the one that would be generated for a formula
     *         with the same arguments.
     */
    public static String toString(Connective connective, List<Formula> subformulas) {
        String result = "";
        if (connective.getArity() == Arity.ZEROARY) {
            return connective.getSymbol();
        } else if (connective.getArity() == Arity.UNARY) {
            return connective.getSymbol() + "(" + subformulas.get(0) + ")";
        } else if (connective.getArity() == Arity.BINARY) {
            return "(" + subformulas.get(0) + connective.getSymbol()
                    + subformulas.get(1) + ")";
        } else if (connective.getArity() == Arity.NARY) {
            result = "(";
            for (int i = 0; i < subformulas.size() - 1; i++) {
                result = result + subformulas.get(i) + connective.getSymbol();
            }
            result = result + subformulas.get(subformulas.size() - 1) + ")";
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see formulasNew.Formula#clone(formulasNew.FormulaFactory)
     */
    public Formula clone(FormulaFactory ff) {
        List<Formula> l = this._subformulas;
        List<Formula> newL = new ArrayList<Formula>();

        for (int i = 0; i < l.size(); i++) {
            newL.add(((Formula) l.get(i)).clone(ff));
        }

        return ff.createCompositeFormula((Connective) this.getConnective(),
                newL);
    }

    /**
     * returns the connective of the composite formula.
     * 
     * @return the connective
     */
    public Connective getConnective() {
        return _connective;
    }

}