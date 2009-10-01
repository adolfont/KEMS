/*
 * Created on 26/10/2005
 *
 */
package logic.valuation;

import logic.formulas.Formula;
import logic.signedFormulas.SignedFormulaList;

/**
 * Interface for logic valuations.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IValuation {
    
    /**  Creates a valuation from a signed formula list.
     * @param signedFormulas
     * @return
     */
    public IValuation create (SignedFormulaList signedFormulas);
    
    /** Gets the value provided by a valuation for a formula
     * @param formula
     * @return true or false, depending on the valuation
     */
    public boolean getValue (Formula formula);

}
