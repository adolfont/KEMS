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
public class BinaryConnectiveGetter implements  KESignedFormulaGetter {
    FormulaSign _sign;

    KERuleRole _role;

    public static final BinaryConnectiveGetter FALSE_LEFT = new BinaryConnectiveGetter(
            ClassicalSigns.FALSE, KERuleRole.LEFT);

    public static final BinaryConnectiveGetter FALSE_RIGHT = new BinaryConnectiveGetter(
            ClassicalSigns.FALSE, KERuleRole.RIGHT);

    public static final BinaryConnectiveGetter TRUE_LEFT = new BinaryConnectiveGetter(
            ClassicalSigns.TRUE, KERuleRole.LEFT);

    public static final BinaryConnectiveGetter TRUE_RIGHT = new BinaryConnectiveGetter(
            ClassicalSigns.TRUE, KERuleRole.RIGHT);

    private BinaryConnectiveGetter(FormulaSign sign, KERuleRole role) {
        _sign = sign;
        _role = role;
    };

    public SignedFormula getSignedFormula(SignedFormulaFactory sff, FormulaFactory ff, 
            SignedFormulaList sfl) {
        return sff.createSignedFormula(_sign, ((Formula) (_role.getFormulas(sfl.get(0).getFormula())).get(0)));
    }    
}