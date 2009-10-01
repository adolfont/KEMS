/*
 * Created on 26/10/2005
 *
 */
package logic.valuation;

import logic.formulas.AtomicFormula;
import logic.signedFormulas.SignedFormula;


/**
 * Class that represents classical propositional logic valuations.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class CPLValuation extends AbstractValuation{

    /* (non-Javadoc)
     * @see logic.valuation.AbstractValuation#satisfiesCriteria(logic.signedFormulas.SignedFormula)
     */
    protected boolean satisfiesCriteria(SignedFormula sf) {
        return sf.getFormula() instanceof AtomicFormula;
    }


}
