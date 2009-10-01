/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignConnectivePattern implements IUnarySignedFormulaPattern{

    FormulaSign _sign;
    Connective _conn;
    
    public SignConnectivePattern(FormulaSign s, Connective c) {
        this._sign = s;
        this._conn = c;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see patterns.SignedFormulaPattern#matches(signedFormulas.SignedFormula)
     */
    public boolean matches(SignedFormula sf) {
        return sf.getSign().equals(_sign) && matchesConnective(sf.getFormula());
    }
    
    private boolean matchesConnective(Formula f){
        if (f instanceof AtomicFormula){
            return false;
        }
        else return ((CompositeFormula)f).getConnective().equals(_conn);
        
    }
}
