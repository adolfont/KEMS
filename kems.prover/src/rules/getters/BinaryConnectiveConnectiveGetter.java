/*
 * Created on 05/08/2005
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
public class BinaryConnectiveConnectiveGetter implements KESignedFormulaGetter {

    private FormulaSign _sign;

    private Connective _connective;

    private KERuleRole _role;

    /**
     * @param _sign
     * @param _connective
     * @param _role
     */
    public BinaryConnectiveConnectiveGetter(FormulaSign _sign,
            Connective _connective, KERuleRole _role) {
        this._sign = _sign;
        this._connective = _connective;
        this._role = _role;
    }


    /*
     * (non-Javadoc)
     * 
     * @see rulesGetters.KESignedFormulaGetter#getSignedFormula(signedFormulasNew.SignedFormulaFactory,
     *           formulasNew.FormulaFactory, signedFormulasNew.SignedFormulaList)
     */
    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
        return sff.createSignedFormula(_sign, ff.createCompositeFormula(
                _connective, ((Formula) (_role.getFormulas(sfl.get(0)
                        .getFormula())).get(0))));
    }
}
