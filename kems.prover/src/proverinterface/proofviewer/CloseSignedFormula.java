/*
 * Created on 15/11/2005
 *
 */
package proverinterface.proofviewer;

import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class CloseSignedFormula extends SignedFormula {

    /**
     * @param sign
     * @param formula
     */
    public CloseSignedFormula(FormulaSign sign, Formula formula) {
        super(sign, formula);
    }
    
    /* (non-Javadoc)
     * @see logic.signedFormulas.SignedFormula#toString()
     */
    public String toString() {
        return "x";
    }

}
