/*
 * Created on 17/11/2004
 *
 */
package rules.getters;

import java.util.List;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;
import rules.patterns.ISubformulaPattern;

/**
 * 
 * Getter used for X_TOP_AND rule.
 * <p>
 * For instance, if X \phi(\top \and A) then X \phi(A), where the _role is OTHER
 * because A is the other formula and the _replacementAuxiliary connective is
 * the connective we use to create a formula. The pattern gives us the formula
 * to be substituted.
 * 
 * <p>
 * TODO This works only with binary connectives.
 * 
 * @author adolfo
 *  
 */
public class SubformulaConnectiveForTestRoleGetter implements KESignedFormulaGetter, SubformulaGetter {

    ISubformulaPattern _pattern;

    Connective _replacementAuxiliary;

    KERuleRole _role;

    /**
     * @param _pattern
     * @param auxiliary
     * @param _role
     */
    public SubformulaConnectiveForTestRoleGetter(ISubformulaPattern _pattern,
            Connective auxiliary, KERuleRole _role) {
        this._pattern = _pattern;
        _replacementAuxiliary = auxiliary;
        this._role = _role;
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {

        Formula f = _pattern.getMatchedSubformula(sfl);

//        if (_role.equals(KERuleRole.OTHER)) {
//            Formula conn_formula = ff
//                    .createCompositeFormula(_replacementAuxiliary);
//
//            List l = f.getImmediateSubformulas();
//            Formula left = (Formula) l.get(0);
//            Formula right = (Formula) l.get(1);
//
//            if (conn_formula.equals(left)) {
//                return substitute(sff, ff, sfl, f, right);
//            } else {
//                if (conn_formula.equals(right)) {
//                    return substitute(sff, ff, sfl, f, left);
//                }
//            }
//
//        }
//        
//
//        return null;
        
        return getSignedFormula(sff, ff, sfl, f);

    }

    private SignedFormula substitute(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl, Formula substituted,
            Formula replacement) {
        return sff.createSignedFormula(sfl.get(0).getSign(), ff
                .createFormulaBySubstitution(sfl.get(0).getFormula(),
                        substituted, replacement));
    }
    
    public SignedFormula getSignedFormula(SignedFormulaFactory sff, FormulaFactory ff, SignedFormulaList sfl, Formula substituted){
        
        
        if (_role.equals(KERuleRole.OTHER)) {
            Formula conn_formula = ff
                    .createCompositeFormula(_replacementAuxiliary);

            List<Formula> l = substituted.getImmediateSubformulas();
            Formula left = (Formula) l.get(0);
            Formula right = (Formula) l.get(1);

            if (conn_formula.equals(left)) {
                return substitute(sff, ff, sfl, substituted, right);
            } else {
                if (conn_formula.equals(right)) {
                    return substitute(sff, ff, sfl, substituted, left);
                }
            }

        }
        

        return null;

        
    }


}