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
 * Getter used with  X_TOP_BOTTOM rule.
 * 
 * <p>
 * TODO This works only with binary connectives.
 * 
 * @author adolfo
 *  
 */
public class SubformulaThreeRolesThreeConnectivesGetter implements KESignedFormulaGetter , SubformulaGetter{

    ISubformulaPattern _pattern;

    Connective _replaced, _replacement1, _replacement2;

    KERuleRole _role1, _role2;

    public SubformulaThreeRolesThreeConnectivesGetter(ISubformulaPattern _pattern,
            Connective _replaced, KERuleRole _role1, Connective _replacement1,
            KERuleRole _role2, Connective _replacement2) {
        super();
        this._pattern = _pattern;
        this._replaced = _replaced;
        this._role1 = _role1;
        this._replacement1 = _replacement1;
        this._role2 = _role2;
        this._replacement2 = _replacement2;
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
//        Formula f = _pattern.getMatchedSubformula(sfl);
//                
//        Formula firstRoleFormula = (Formula) _role1.getFormulas(f).get(0);
//        Formula secondRoleFormula = (Formula) _role2.getFormulas(f).get(0);
//        Formula replaced_formula = ff
//        .createCompositeFormula(_replaced);
//        Formula replacement_formula = ff
//        .createCompositeFormula(_replacement1);
//        
//        if (firstRoleFormula == replaced_formula){
//            return substitute (sff, ff, sfl, f, replacement_formula);
//        }
//        else if (secondRoleFormula == replaced_formula){
//            return substitute (sff, ff, sfl, f, ff.createCompositeFormula(_replacement2, firstRoleFormula));
//
//        }
//        
//
//        return null;

        return getSignedFormula(sff, ff, sfl, _pattern.getMatchedSubformula(sfl));
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl, Formula f) {
                
        Formula firstRoleFormula = (Formula) _role1.getFormulas(f).get(0);
        Formula secondRoleFormula = (Formula) _role2.getFormulas(f).get(0);
        Formula replaced_formula = ff
        .createCompositeFormula(_replaced);
        Formula replacement_formula = ff
        .createCompositeFormula(_replacement1);
        
        if (firstRoleFormula == replaced_formula){
            return substitute (sff, ff, sfl, f, replacement_formula);
        }
        else if (secondRoleFormula == replaced_formula){
            return substitute (sff, ff, sfl, f, ff.createCompositeFormula(_replacement2, firstRoleFormula));

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