/*
 * Created on 17/11/2004
 *
 */
package rules.getters;


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
 * 
 * @author adolfo
 *  
 */
public class SubformulaConnectiveRoleGetter implements KESignedFormulaGetter, SubformulaGetter {

    ISubformulaPattern _pattern;

    Connective _replacementAuxiliary;

    KERuleRole _role;

    /**
     * @param _pattern
     * @param auxiliary
     * @param _role
     */
    public SubformulaConnectiveRoleGetter(ISubformulaPattern _pattern,
            Connective auxiliary, KERuleRole _role) {
        this._pattern = _pattern;
        _replacementAuxiliary = auxiliary;
        this._role = _role;
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {

//        Formula f = _pattern.getMatchedSubformula(sfl);
//
//        if (_role.equals(KERuleRole.LEFT) || _role.equals(KERuleRole.RIGHT)){
//            Formula conn_formula = ff
//            .createCompositeFormula(_replacementAuxiliary, (Formula) _role.getFormulas(f).get(0));
//            return substitute(sff, ff, sfl, f, conn_formula);
//            
//        }
        // return null;

        return getSignedFormula(sff, ff, sfl, _pattern.getMatchedSubformula(sfl));

    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl, Formula f) {

//        Formula f = _pattern.getMatchedSubformula(sfl);

        if (_role.equals(KERuleRole.LEFT) || _role.equals(KERuleRole.RIGHT)){
            Formula conn_formula = ff
            .createCompositeFormula(_replacementAuxiliary, (Formula) _role.getFormulas(f).get(0));
            return substitute(sff, ff, sfl, f, conn_formula);
            
        }

        return null;

    }

    private SignedFormula substitute(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl, Formula substituted,
            Formula replacement) {
        return sff.createSignedFormula(sfl.get(0).getSign(), ff
                .createFormulaBySubstitution(sfl.get(0).getFormula(),
                        substituted, replacement));
    }

}