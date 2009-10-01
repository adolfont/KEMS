/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;

/**
 * TODO MODIFY * A class for representing binary signed formula patterns used
 * when rules with two premisses are analysed.
 * 
 * For instance, the T-> rule can be applied to T A->B (main formula) and T A
 * (auxiliary formula) because the two formulas match the T-> rule premiss
 * pattern (T, ->, T, LEFT).
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ThreeSignsConnectiveTwoRolesPattern implements
        IBinarySignedFormulaPattern {

    FormulaSign _mainSign, _auxiliarySign1, _auxiliarySign2;

    Connective _mainConnective;

    KERuleRole _auxiliaryRole1, _auxiliaryRole2;

    public ThreeSignsConnectiveTwoRolesPattern(FormulaSign mainSign,
            Connective mainConnective, FormulaSign auxiliarySign1,
            KERuleRole auxiliaryRole1, FormulaSign auxiliarySign2,
            KERuleRole auxiliaryRole2) {
        _mainSign = mainSign;
        _mainConnective = mainConnective;
        _auxiliarySign1 = auxiliarySign1;
        _auxiliarySign2 = auxiliarySign2;
        _auxiliaryRole1 = auxiliaryRole1;
        _auxiliaryRole2 = auxiliaryRole2;
    }

    /**
     * Verifies if auxiliary formula is equal to the role in the main formula.
     * 
     * @param main
     * @param auxiliary
     * @return
     */
    public boolean roleMatches(KERuleRole role, Formula main, Formula auxiliary) {
        return role.getFormulas(main).contains(auxiliary);
    }

    /*
     * (non-Javadoc)
     * 
     * @see patterns.BinarySignedFormulaPattern#matches(signedFormulasNew.SignedFormula,
     *      signedFormulasNew.SignedFormula)
     */
    public boolean matches(SignedFormula main, SignedFormula auxiliary) {

        boolean mainMatch = matchesMain(main);

        boolean auxiliaryMatch = (_auxiliarySign1.equals(auxiliary.getSign()) && roleMatches(
                _auxiliaryRole1, main.getFormula(), auxiliary.getFormula()))
                || (_auxiliarySign2.equals(auxiliary.getSign()) && roleMatches(
                        _auxiliaryRole2, main.getFormula(), auxiliary
                                .getFormula()));

        return mainMatch && auxiliaryMatch;
    }

    /**
     * @param main
     * @return
     */
    public boolean matchesMain(SignedFormula main) {
        boolean mainMatch = _mainSign.equals(main.getSign())
                && matchesConnective(main.getFormula());
        return mainMatch;
    }

    private boolean matchesConnective(Formula f) {
        if (f instanceof AtomicFormula) {
            return false;
        } else
            return ((CompositeFormula) f).getConnective().equals(
                    _mainConnective);
    }

    /* (non-Javadoc)
     * @see patterns.BinarySignedFormulaPattern#getAuxiliaryCandidates(signedFormulasNew.SignedFormulaFactory, formulasNew.FormulaFactory, signedFormulasNew.SignedFormula)
     */
    public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff, FormulaFactory ff, SignedFormula sfMain) {
        SignedFormulaList sfl = new SignedFormulaList();
        
        sfl.add(sff.createSignedFormula(_auxiliarySign1, (Formula)
                _auxiliaryRole1.getFormulas(sfMain.getFormula()).get(0) ) );
        sfl.add(sff.createSignedFormula(_auxiliarySign2, (Formula)
                _auxiliaryRole2.getFormulas(sfMain.getFormula()).get(0) ) );
        
        return sfl;
    }

}