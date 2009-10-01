/*
 * Created on 12/11/2004
 *
 */
package rules.getters;

import logic.formulas.Arity;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.patterns.ISubformulaPattern;

/**
 * @author adolfo
 *  
 */
public class SimpleSubformulaGetter implements KESignedFormulaGetter, SubformulaGetter {

    ISubformulaPattern _pattern;

    Connective _replacement;

    /**
     * @param _pattern
     * @param _replacement
     */
    public SimpleSubformulaGetter(ISubformulaPattern _pattern, Connective _replacement) {
        this._pattern = _pattern;
        this._replacement = _replacement;

        if (_replacement.getArity() != Arity.ZEROARY) {
            throw new RuntimeException(
                    "Error: Arity must be ZEROARY in SubformulaGetter constructor.");
        }
    }

//    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
//            FormulaFactory ff, SignedFormulaList sfl) {
//
//        Formula substituted = _pattern.getMatchedSubformula(sfl);
//
//        return sff.createSignedFormula(sfl.get(0).getSign(), ff
//                .createFormulaBySubstitution(sfl.get(0).getFormula(),
//                        substituted, ff.createCompositeFormula(_replacement)));
//    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {

        Formula substituted = _pattern.getMatchedSubformula(sfl);

        return sff.createSignedFormula(sfl.get(0).getSign(), ff
                .createFormulaBySubstitution(sfl.get(0).getFormula(),
                        substituted, ff.createCompositeFormula(_replacement)));
    }

    //    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
    //            FormulaFactory ff, SignedFormula sfMain,
    //            SignedFormula sfAux ) {
    //
    //        Formula substituted = _pattern.getMatchedSubformula(sfMain, sfAux
    //                );
    //
    //        return sff.createSignedFormula(sfMain.getSign(), ff
    //                .createFormulaBySubstitution(sfMain.getFormula(),
    //                        substituted, ff.createCompositeFormula(_replacement)));
    //    }
    
    /**
     * @param sff
     * @param ff
     * @param sfl
     * @param f
     * @return
     */
    public SignedFormula getSignedFormula(SignedFormulaFactory sff, FormulaFactory ff, SignedFormulaList sfl, Formula substituted){
        
        return sff.createSignedFormula(sfl.get(0).getSign(), ff
                .createFormulaBySubstitution(sfl.get(0).getFormula(),
                        substituted, ff.createCompositeFormula(_replacement)));
        
    }


}