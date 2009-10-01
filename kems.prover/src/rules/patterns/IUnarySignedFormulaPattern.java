/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import logic.signedFormulas.SignedFormula;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IUnarySignedFormulaPattern {
    
    /** Verifies if a given signed formula matches the pattern.
     * 
     * @param sf
     * @return
     */
    public boolean matches (SignedFormula sf);
}
