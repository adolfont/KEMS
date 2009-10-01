/*
 * Created on 18/01/2005
 *  
 */
package logic.aspects;

import java.util.Iterator;
import java.util.Map;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logic.structures.SignedFormula_FormulaMultimap;

/**
 * Aspect that introduces structures (attributes) in the Formula class to deal
 * with parents and signed counterparts. This aspect complements
 * FormulaParentsAspect.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public aspect FormulaParentsAspectIntroductions {

    // new attributes for Formula instances
    FormulaList Formula._immediateParents;

    FormulaList Formula._parents;

    SignedFormulaList Formula._immediateSignedParents;

    SignedFormulaList Formula._signedCounterparts;

    SignedFormula_FormulaMultimap Formula._signedParents;

    // initilization of the new attributes

    after(Formula f): target(f) && execution(Formula+.new()) {
        f._immediateParents = new FormulaList();
        f._parents = new FormulaList();
        f._immediateSignedParents = new SignedFormulaList();
        f._signedParents = new SignedFormula_FormulaMultimap();
        f._signedCounterparts = new SignedFormulaList();
    };

    // getXXX(), addXX(), isXXX() methods for Formula instances
    /**
     * returns the list of immediate parents of this formula.
     * 
     * @return the list of immediate parents of this formula
     */
    public FormulaList Formula.getImmediateParents() {
        return _immediateParents;
    }

    /**
     * returns the list of all parents of this formula.
     * 
     * @return the list of all parents of this formula
     */
    public FormulaList Formula.getParents() {
        return _parents;
    }

    /**
     * returns the list of immediate signed parents of this formula.
     * 
     * @return the list of immediate signed parents of this formula
     */
    public SignedFormulaList Formula.getImmediateSignedParents() {
        return _immediateSignedParents;
    }

    /**
     * adds a new parent to this formula.
     * 
     * @param f -
     *            the new parent
     */
    public void Formula.addParent(Formula f) {
        if (!(_parents.contains(f))) {
            _parents.add(f);
        }
    }

    /**
     * adds a new immediate parent to this formula.
     * 
     * @param f -
     *            the new immediate parent
     */
    public void Formula.addImmediateParent(Formula f) {
        if (!(_immediateParents.contains(f))) {
            _immediateParents.add(f);
        }
    }

    /**
     * adds a new immediate signed parent to this formula.
     * 
     * @param f -
     *            the new immediate signed parent
     */
    public void Formula.addImmediateSignedParent(SignedFormula sf) {
        if (!(_immediateSignedParents.contains(sf))) {
            _immediateSignedParents.add(sf);
        }
    }

    // /**
    // * adds many parents to a formula
    // *
    // * @param fl -
    // * the list of new parents
    // */
    // public void addAllParents(FormulaList fl) {
    // _immediateParents.addAll(fl);
    // }

    /**
     * verifies if this formula is a child of other formula
     * 
     * @param f -
     *            the possible parent
     * @return true if this formula is a child of f
     */
    public boolean Formula.isChild(Formula f) {
        if (_immediateParents.contains(f)) {
            return true;
        } else {
            for (int i = 0; i < _immediateParents.size(); i++) {
                if (_immediateParents.get(i).isChild(f)) {
                    return true;
                }
            }
        }

        return false;

    }

    public SignedFormulaList Formula.getSignedCounterparts() {
        return _signedCounterparts;
    }

    public SignedFormulaList Formula.getSignedParents() {
        return _signedParents.keySet();
    }

    public FormulaList Formula.getSubformulasInSignedParent(SignedFormula sf) {
        return _signedParents.get(sf);
    }

    public void Formula.addSignedCounterpart(SignedFormula sf) {
        if (!_signedCounterparts.contains(sf)) {
            _signedCounterparts.add(sf);
        }
    }

    /**
     * @param sf
     */
    public void Formula.addSignedParent(SignedFormula sf, Formula f) {
        _signedParents.put(sf, f);
    }

    long around(FormulaFactory ff):
        target(ff) &&
        execution (public long FormulaFactory+.getStructuresSize())
        {

        long size = 0;

        size += getSize(ff.getAtomicFormulas());
        size += getSize(ff.getCompositeFormulas());
        size += ff.getConnectivesSize();

        return size;
    }

    private long getSize(Map<String,Formula> m) {
        Iterator<Formula> it = m.values().iterator();
        long size = 0;
        while (it.hasNext()) {
            Formula f = (Formula) it.next();

            size += f.getImmediateParents().size();
            size += f.getParents().size();
            size += f.getImmediateSignedParents().size();
            size += f.getSignedCounterparts().size();
            size += f.getSignedParents().size();
        }

        return size;
    }

}