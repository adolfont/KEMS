/*
 * Created on 26/10/2005
 *
 */
package logic.valuation;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.signedFormulas.SignedFormula;
import logicalSystems.mbc.MBCConnectives;

/**
 * Class that represents mbC valuations.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MBCValuation extends AbstractValuation {
    
    /*
     * (non-Javadoc)
     * 
     * @see logic.valuation.AbstractValuation#satisfiesCriteria(logic.signedFormulas.SignedFormula)
     */
    protected boolean satisfiesCriteria(SignedFormula sf) {
        return (sf.getFormula() instanceof AtomicFormula)
        ||
        (sf.getFormula() instanceof CompositeFormula && ((CompositeFormula) sf
                .getFormula()).getConnective().equals(
                MBCConnectives.CONSISTENCY))
        ||
        (sf.getFormula() instanceof CompositeFormula && ((CompositeFormula) sf
                .getFormula()).getConnective().equals(
                MBCConnectives.NOT));
    }

}
