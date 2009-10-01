/*
 * Created on 10/11/2004
 *
 */
package rules.getters;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class BinaryTwoPremisesAddConnectiveGetter implements
        KESignedFormulaGetter {

    private FormulaSign _sign;

    private Connective _connective;

    /**
     * @param _sign
     * @param _connective
     */
    public BinaryTwoPremisesAddConnectiveGetter(FormulaSign _sign,
            Connective _connective) {
        super();
        this._sign = _sign;
        this._connective = _connective;
    }

    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
        SignedFormula sfMain = sfl.get(0);
//        sfAux = sfl.get(1);
        Formula left = (Formula) (KERuleRole.LEFT.getFormulas(sfMain
                .getFormula()).get(0));

        return sff.createSignedFormula(_sign, ff.createCompositeFormula(
                _connective, left));

    }

}