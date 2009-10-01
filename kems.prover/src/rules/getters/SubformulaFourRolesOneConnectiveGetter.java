/*
 * Created on 01/12/2004
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
 * @author adolfo
 *  
 */
public class SubformulaFourRolesOneConnectiveGetter implements
        KESignedFormulaGetter {

    ISubformulaPattern _pattern;

    KERuleRole _role1, _role2;

    Connective _conn;

    /**
     *  
     */
    public SubformulaFourRolesOneConnectiveGetter(ISubformulaPattern patt,
            KERuleRole role1, KERuleRole role2, Connective conn) {
        _pattern = patt;
        _role1 = role1;
        _role2 = role2;
        _conn = conn;
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {

        Formula f = _pattern.getMatchedSubformula(sfl);

        Formula firstRoleFormula = (Formula) _role1.getFormulas(f).get(0);
        Formula secondRoleFormula = (Formula) _role2.getFormulas(f).get(0);

        if (firstRoleFormula == sfl.get(1).getFormula()) {
            Formula replacement_formula = secondRoleFormula;
            return substitute(sff, ff, sfl, f, replacement_formula);
        } else if (secondRoleFormula == sfl.get(1).getFormula()) {
            Formula replacement_formula = ff.createCompositeFormula(_conn,
                    firstRoleFormula);
            return substitute(sff, ff, sfl, f, replacement_formula);

        }

        return null;
    }

    /**
     * @param sff
     * @param ff
     * @param sfl
     * @param f
     * @param replacement_formula
     * @return
     */
    private SignedFormula substitute(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl, Formula substituted,
            Formula replacement) {
        return sff.createSignedFormula(sfl.get(0).getSign(), ff
                .createFormulaBySubstitution(sfl.get(0).getFormula(),
                        substituted, replacement));
    }

}