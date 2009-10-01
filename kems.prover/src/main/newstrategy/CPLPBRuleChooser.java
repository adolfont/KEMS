/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import rules.Rule;

/**
 * PB Rule Chooser for CPL
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class CPLPBRuleChooser implements IPBRuleChooser {

	
	public CPLPBRuleChooser() {
		super();
	}

	/* (non-Javadoc)
	 * @see main.newstrategy.IPBRuleChooser#canApply(rules.Rule, logic.signedFormulas.SignedFormula, logic.signedFormulas.SignedFormulaBuilder)
	 */
	public boolean canApply(Rule r, SignedFormula sf, SignedFormulaBuilder sfb) {
		return true;
	}

}
