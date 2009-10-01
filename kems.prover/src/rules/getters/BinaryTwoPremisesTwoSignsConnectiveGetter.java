/*
 * Created on 10/11/2004
 *
 */
package rules.getters;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.KERuleRole;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class BinaryTwoPremisesTwoSignsConnectiveGetter implements
        KESignedFormulaGetter {
    FormulaSign _sign1, _sign2;

    KERuleRole _role;

    public static final BinaryTwoPremisesTwoSignsConnectiveGetter AUX_SIGN__OTHER = new BinaryTwoPremisesTwoSignsConnectiveGetter(
            ClassicalSigns.TRUE, ClassicalSigns.FALSE, KERuleRole.OTHER);

    private BinaryTwoPremisesTwoSignsConnectiveGetter(FormulaSign sign1,
            FormulaSign sign2, KERuleRole role) {
        _sign1 = sign1;
        _sign2 = sign2;
        _role = role;
    };

    /**
     * @param sff
     * @param sfMain
     * @param sfAux
     * @return
     */
    public SignedFormula getSignedFormula(SignedFormulaFactory sff, FormulaFactory ff,
            SignedFormulaList sfl
            ) {

        SignedFormula sfMain = sfl.get(0);
        SignedFormula sfAux = sfl.get(1);
        FormulaSign sign;
        if (sfAux.getSign().equals(_sign1)) {
            sign = _sign1;
        } else {
            sign = _sign2;
        }

        Formula left = (Formula) (KERuleRole.LEFT.getFormulas(sfMain
                .getFormula()).get(0));
        Formula right = (Formula) (KERuleRole.RIGHT.getFormulas(sfMain
                .getFormula()).get(0));

        if (sfAux.getFormula() == left) {
            return sff.createSignedFormula(sign, right);
        } else {
            return sff.createSignedFormula(sign, left);
        }

    }

//    /*
//     * (non-Javadoc)
//     * 
//     * @see rulesNew.KESignedFormulaGetter#getSignedFormula(signedFormulasNew.SignedFormulaFactory,
//     *      signedFormulasNew.SignedFormula)
//     */
//    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
//            SignedFormula sf) {
//        // TODO corrigir essa interface KESignedFormulaGetter
//        return null;
//    }

}