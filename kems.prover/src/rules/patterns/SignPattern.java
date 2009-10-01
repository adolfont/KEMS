/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignPattern implements IUnarySignedFormulaPattern {

    FormulaSign _sign;

    public SignPattern(FormulaSign s) {
        this._sign = s;
    }

    /*
     * (non-Javadoc)
     * 
     * @see patterns.SignedFormulaPattern#matches(signedFormulas.SignedFormula)
     */
    public boolean matches(SignedFormula sf) {
        return sf.getSign().equals(_sign);
    }

}
