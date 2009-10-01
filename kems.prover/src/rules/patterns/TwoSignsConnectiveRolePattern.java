/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import java.util.List;

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
 * A class for representing binary signed formula patterns used when rules with
 * two premisses are analysed.
 * 
 * For instance, the T-> rule can be applied to T A->B (main formula) and T A
 * (auxiliary formula) because the two formulas match the T-> rule premiss
 * pattern (T, ->, T, LEFT).
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class TwoSignsConnectiveRolePattern implements
        IBinarySignedFormulaPattern {

    FormulaSign _mainSign, _auxiliarySign;

    Connective _mainConnective;

    KERuleRole _auxiliaryRole;

    public TwoSignsConnectiveRolePattern(FormulaSign mainSign,
            Connective mainConnective, FormulaSign auxiliarySign,
            KERuleRole auxiliaryRole) {
        _mainSign = mainSign;
        _mainConnective = mainConnective;
        _auxiliarySign = auxiliarySign;
        _auxiliaryRole = auxiliaryRole;
    }

    /**
     * Verifies if auxiliary formula is equal to the role in the main formula.
     * 
     * @param main
     * @param auxiliary
     * @return
     */
    public boolean roleMatches(Formula main, Formula auxiliary) {
        return _auxiliaryRole.getFormulas(main).contains(auxiliary);
    }

    /*
     * (non-Javadoc)
     * 
     * @see patterns.BinarySignedFormulaPattern#matches(null, null)
     */
    public boolean matches(SignedFormula main, SignedFormula auxiliary) {

        boolean mainMatch = matchesMain(main);

        boolean auxiliaryMatch = _auxiliarySign.equals(auxiliary.getSign())
                && roleMatches(main.getFormula(), auxiliary.getFormula());

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

    /*
     * (non-Javadoc)
     * 
     * @see patterns.BinarySignedFormulaPattern#getAuxiliaryCandidates(signedFormulasNew.SignedFormulaFactory,
     *           formulasNew.FormulaFactory, signedFormulasNew.SignedFormula)
     */
    public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormula sfMain) {
        SignedFormulaList sfl = new SignedFormulaList();

        List<Formula> l = _auxiliaryRole.getFormulas(sfMain.getFormula());
        for (int i = 0; i < l.size(); i++) {
            sfl
                    .add(sff.createSignedFormula(_auxiliarySign, (Formula) l
                            .get(i)));
        }

        return sfl;
    }

}