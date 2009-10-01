/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import rules.Rule;

/**
 * Interface for PB rules choosers.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IPBRuleChooser {
    
    /**
     * @param r
     * @param sf
     * @param sfb
     * @return true if the rule r can be applied to the signed formula sf
     */
    public boolean canApply(Rule r, SignedFormula sf, SignedFormulaBuilder sfb);

}
