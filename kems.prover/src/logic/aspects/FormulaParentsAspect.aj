/*
 * Created on 29/12/2004
 *  
 */
package logic.aspects;

import java.util.ArrayList;
import java.util.List;

import logic.formulas.AtomicFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;

/**
 * Aspect for adding parents to formulas. This aspect is complemented by
 * FormulaParentsAspectInstroductions.
 * 
 * @author adolfo
 * 
 */
public aspect FormulaParentsAspect {

    // pointcuts for catching the creation of formulas and signed formulas

    pointcut createAtomicFormula():
        call (AtomicFormula FormulaFactory.createAtomicFormula(*));

    pointcut createZeroaryCompositeFormula(Connective conn):
        args(conn) &&
        call (Formula FormulaFactory.createCompositeFormula(*));

    pointcut createUnaryCompositeFormula(Connective conn, Formula f):
        args (conn,f) &&
        call (Formula FormulaFactory.createCompositeFormula(*,*));

    pointcut createBinaryCompositeFormula(Connective conn, Formula f_left,
            Formula f_right):
        args(conn, f_left, f_right) &&
        call (Formula FormulaFactory.createCompositeFormula(*,*,*));

    pointcut createNaryCompositeFormula(Connective conn, List<Formula> formulaList):
        args (conn, formulaList) &&
        call (Formula FormulaFactory.createCompositeFormula(*,List<Formula>));

    pointcut createFormulaBySubstitution(Formula base, Formula substituted,
            Formula replacement):
        args(base, substituted, replacement) &&
        call (Formula FormulaFactory.createFormulaBySubstitution(*,*,*));

    pointcut createSignedFormula(FormulaSign s, Formula formula):
        args(s, formula) &&
        call (SignedFormula SignedFormulaFactory.createSignedFormula(*,*));

    // around advices that add parents to formulas
    Formula around(Connective conn, Formula f): 
    	createUnaryCompositeFormula(conn, f){
        Formula cf = proceed(conn, f);

        f.addImmediateParent(cf);
        addParent(f, cf);

        return cf;
    }

    Formula around(Connective conn, Formula left, Formula right): 
        createBinaryCompositeFormula(conn, left, right){
        Formula cf = proceed(conn, left, right);

        left.addImmediateParent(cf);
        right.addImmediateParent(cf);
        addParent(left, cf);
        addParent(right, cf);

        return cf;
    }

    Formula around(Connective conn, List<Formula> formulas): 
        createNaryCompositeFormula(conn, formulas){
        Formula cf = proceed(conn, formulas);

        addParent(formulas, cf);
        addImmediateParent(formulas, cf);

        return cf;
    }

    Formula around(Formula base, Formula substituted, Formula replacement): 
        createFormulaBySubstitution(base, substituted, replacement){
        Formula f = proceed(base, substituted, replacement);

        if (replacement != f && !(f instanceof AtomicFormula)) {
            // System.err.println(base + " " + substituted + ' ' + "Adding
            // parents "+ replacement + ' ' + f);
            addParent(replacement, f);
            replacement.addImmediateParent(f);
        }

        return f;
    }

    SignedFormula around(FormulaSign s, Formula formula):
        createSignedFormula(s, formula){
        SignedFormula sf = proceed(s, formula);

        addImmediateSignedParents(formula, sf);
        addSignedParents(formula, sf);

        formula.addSignedCounterpart(sf);

        return sf;
    }

    // auxiliary methods

    private void addParent(Formula f, Formula cf) {
        List<Formula> l = new ArrayList<Formula>();
        l.add(f);
        addParent(l, cf);
    }

    private void addParent(List<Formula> formulas, Formula cf) {

        for (int i = 0; i < formulas.size(); i++) {
            addParentRecursively(((Formula) formulas.get(i)), cf);
        }

    }

    private void addParentRecursively(Formula formula, Formula cf) {

        formula.addParent(cf);
        for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
            addParentRecursively((Formula) formula.getImmediateSubformulas()
                    .get(i), cf);
        }
    }

    private void addImmediateParent(List<Formula> formulas, Formula cf) {
        for (int i = 0; i < formulas.size(); i++) {
            ((Formula) formulas.get(i)).addImmediateParent(cf);
        }
    }

    private void addImmediateSignedParents(Formula formula, SignedFormula sf) {
        for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
            ((Formula) formula.getImmediateSubformulas().get(i))
                    .addImmediateSignedParent(sf);
        }
    }

    private void addSignedParents(Formula formula, SignedFormula sf) {

        for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
            addParentRecursively(((Formula) formula.getImmediateSubformulas()
                    .get(i)), sf, formula);
        }

    }

    private void addParentRecursively(Formula formula, SignedFormula sf,
            Formula immediateParent) {

        formula.addSignedParent(sf, immediateParent);

        for (int i = 0; i < formula.getImmediateSubformulas().size(); i++) {
            addParentRecursively((Formula) formula.getImmediateSubformulas()
                    .get(i), sf, formula);
        }
    }

}