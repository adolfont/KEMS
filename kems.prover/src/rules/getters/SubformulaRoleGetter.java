/*
 * Created on 17/11/2004
 *
 */
package rules.getters;

import java.util.List;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;
import rules.patterns.ISubformulaPattern;

/**
 * 
 * Getter used for X_AND_2 rule.
 * 
 * @author adolfo
 *  
 */
public class SubformulaRoleGetter implements KESignedFormulaGetter,
        SubformulaGetter {

    ISubformulaPattern _pattern;

    KERuleRole _role;

    /**
     * @param _pattern
     * @param _role
     */
    public SubformulaRoleGetter(ISubformulaPattern _pattern, KERuleRole _role) {
        this._pattern = _pattern;
        this._role = _role;
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {

        //        Formula f = _pattern.getMatchedSubformula(sfl);
        //        List l = f.getImmediateSubformulas();
        //// System.out.println(f + " " + l);
        //        
        //        
        //
        //        if (_role.equals(KERuleRole.OTHER)) {
        //            Formula auxFormula = sfl.get(1).getFormula();
        //            Formula left = (Formula) l.get(0);
        //            Formula right = (Formula) l.get(1);
        //
        //            if (auxFormula.equals(left)) {
        //                return substitute(sff, ff, sfl, f, right);
        //            } else {
        //                if (auxFormula.equals(right)) {
        //                    return substitute(sff, ff, sfl, f, left);
        //                }
        //            }
        //
        //        } else {
        //// System.err.println("LR");
        //           
        //            Formula substitution = (Formula) _role.getFormulas(f).get(0);
        //// System.out.println(substitution + " " + f + " " + sfl.get(0) + " "
        // + substitute(sff, ff, sfl, f, substitution));
        //            return substitute(sff, ff, sfl, f, substitution);
        //        }
        //
        //        return null;

        return getSignedFormula(sff, ff, sfl, _pattern
                .getMatchedSubformula(sfl));
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl, Formula f) {

    	List<Formula> l = f.getImmediateSubformulas();
        //        System.out.println(f + " " + l);

        if (_role.equals(KERuleRole.OTHER)) {
            Formula auxFormula = sfl.get(1).getFormula();
            Formula left = (Formula) l.get(0);
            Formula right = (Formula) l.get(1);

            if (auxFormula.equals(left)) {
                return substitute(sff, ff, sfl, f, right);
            } else {
                if (auxFormula.equals(right)) {
                    return substitute(sff, ff, sfl, f, left);
                }
            }

        } else {
            //            System.err.println("LR");

            Formula substitution = (Formula) _role.getFormulas(f).get(0);
            //            System.out.println(substitution + " " + f + " " + sfl.get(0) + "
            // " + substitute(sff, ff, sfl, f, substitution));
            return substitute(sff, ff, sfl, f, substitution);
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