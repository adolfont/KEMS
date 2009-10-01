/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import rules.Rule;

/**
 * PB Rule chooser that accepts any rule. A singleton.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class AcceptAllRuleChooser implements IPBRuleChooser {

    private static AcceptAllRuleChooser __INSTANCE = null;

    /**
     * @return
     */
    public static IPBRuleChooser getInstance() {
        if (__INSTANCE == null) {
            __INSTANCE = new AcceptAllRuleChooser();
        }

        return __INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.newstrategy.IPBRuleChooser#canApply(rules.Rule,
     *      logic.signedFormulas.SignedFormula,
     *      logic.signedFormulas.SignedFormulaBuilder)
     */
    public boolean canApply(Rule r, SignedFormula sf, SignedFormulaBuilder sfb) {
        return true;
    }

}
