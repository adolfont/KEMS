/*
 * Created on 05/04/2005
 *
 */
package logicalSystems.classicalLogic;

import logic.signedFormulas.FormulaSign;

/**
 * Signs for classical logic.
 * @author Adolfo Gustavo Serra Seca Neto
 */
public class ClassicalSigns {
    public static final FormulaSign FALSE = new FormulaSign(0);

    public static final FormulaSign TRUE = new FormulaSign(1);
    
    static{
        FALSE.setOpposite(TRUE);
        TRUE.setOpposite(FALSE);
    }
}
