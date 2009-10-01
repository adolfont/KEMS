/*
 * Created on 26/10/2005
 *
 */
package logic.valuation;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.signedFormulas.SignedFormula;
import logicalSystems.c1.C1Connectives;
import logicalSystems.c1.C1Signs;

/**
 * Class that represents C1 valuations.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class C1Valuation extends AbstractValuation {

    /*
     * (non-Javadoc)
     * 
     * @see logic.valuation.AbstractValuation#satisfiesCriteria(logic.signedFormulas.SignedFormula)
     */
    protected boolean satisfiesCriteria(SignedFormula sf) {
        return (sf.getFormula() instanceof AtomicFormula)
                || (sf.getSign().equals(C1Signs.TRUE)
                        && sf.getFormula() instanceof CompositeFormula && ((CompositeFormula) sf
                        .getFormula()).getConnective().equals(
                        C1Connectives.CONSISTENCY));
    }

}
