/*
 * Created on 18/11/2004
 *
 */
package logic.signedFormulas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logic.formulas.AtomicFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.structures.FormulaSignMultimap;

/**
 * A factory of formulas and signed formulas.
 * 
 * @author Adolfo Gustavo Serra Seca neto
 *  
 */
public class SignedFormulaBuilder {

    SignedFormulaFactory _sff;

    FormulaFactory _ff;

    FormulaSignMultimap _fsmm;

    boolean _closed;

    /**
     * creates a signed formula builder
     */
    public SignedFormulaBuilder() {

        _ff = new FormulaFactory();
        _sff = new SignedFormulaFactory();
        initializeFields();
    }

    /**
     * creates a signed formula builder
     * 
     * @param _sff -
     *            a signed formula factory
     * @param _ff -
     *            a formula factory
     */
    public SignedFormulaBuilder(SignedFormulaFactory _sff, FormulaFactory _ff) {
        super();
        this._sff = _sff;
        this._ff = _ff;
        initializeFields();
        updateMultiMap();
    }

    private void initializeFields() {
        _fsmm = new FormulaSignMultimap();
        _closed = false;
    }

    private void updateMultiMap() {
        List<SignedFormula> l = new ArrayList<SignedFormula>();
        l.addAll(_sff.getSignedFormulas().values());
        for (int i = 0; i < l.size(); i++) {

            SignedFormula sf = (SignedFormula) l.get(i);
            // System.out.println(i + " " + sf);
            _fsmm.put(sf.getFormula(), sf.getSign());

            if (_fsmm.get(sf.getFormula()).size() == 2) {
                _closed = true;
            }
        }
    }

    // TODO This is specific for classical logic (see createSignedFormula)
    public boolean isClosed() {
        return _closed;

    }

    /**
     * @return the formula factory used
     */
    public FormulaFactory getFormulaFactory() {
        return _ff;
    }

    /**
     * @return the signed formula factory used
     *  
     */
    public SignedFormulaFactory getSignedFormulaFactory() {
        return _sff;
    }

    // some delegate methods from _sff and _ff

    /**
     * @param atom
     * @return
     */
    public AtomicFormula createAtomicFormula(String atom) {
        return _ff.createAtomicFormula(atom);
    }

    /**
     * @param connective
     * @return
     */
    public Formula createCompositeFormula(Connective connective) {
        return _ff.createCompositeFormula(connective);
    }

    /**
     * @param connective
     * @param f
     * @return
     */
    public Formula createCompositeFormula(Connective connective, Formula f) {
        return _ff.createCompositeFormula(connective, f);
    }

    /**
     * @param connective
     * @param left
     * @param right
     * @return
     */
    public Formula createCompositeFormula(Connective connective, Formula left,
            Formula right) {
        return _ff.createCompositeFormula(connective, left, right);
    }

    /**
     * @param connective
     * @param formulas
     * @return
     */
    public Formula createCompositeFormula(Connective connective, List<Formula> formulas) {
        return _ff.createCompositeFormula(connective, formulas);
    }

    /**
     * @param base
     * @param substituted
     * @param replacement
     * @return
     */
    public Formula createFormulaBySubstitution(Formula base,
            Formula substituted, Formula replacement) {
        return _ff.createFormulaBySubstitution(base, substituted, replacement);
    }

    /**
     * @return
     */
    public Map<String, Formula> getAtomicFormulas() {
        return _ff.getAtomicFormulas();
    }

    /**
     * @return
     */
    public Map<String, Formula> getCompositeFormulas() {
        return _ff.getCompositeFormulas();
    }

    /**
     * @param sign
     * @param formula
     * @return
     */
    public SignedFormula createSignedFormula(FormulaSign sign, Formula formula) {

        _fsmm.put(formula, sign);
        // TODO this is specific for classical logic
        if (_fsmm.get(formula).size() == 2) {
            _closed = true;
        }
        return _sff.createSignedFormula(sign, formula);
    }

    /**
     * @return
     */
    public SignedFormula getLastSignedFormulaAdded() {
        return _sff.getLastSignedFormulaAdded();
    }

    public SignedFormula createOppositeSignedFormula(SignedFormula sf) {
        return _sff.createOppositeSignedFormula(sf);
    }

    /**
     * @return
     */
    public Map<String, SignedFormula> getSignedFormulas() {
        return _sff.getSignedFormulas();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Signed Formulas: " + _sff.toString() + "\n" + "Formulas: "
                + _ff.toString() + "\n";
    }

    /**
     * @param true1
     * @param formula
     * @return
     */
    public SignedFormula createCloseSignedFormula(FormulaSign sign, Formula formula) {
        return _sff.createCloseSignedFormula(sign, formula);
    }

}