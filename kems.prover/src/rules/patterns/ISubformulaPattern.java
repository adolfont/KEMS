/*
 * Created on 17/11/2004
 *
 */
package rules.patterns;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author adolfo
 * 
 */
public interface ISubformulaPattern {

	public Formula getMatchedSubformula(SignedFormulaList sfl);

	/**
	 * @param sf
	 * @return
	 */
	public FormulaList getMainMatches(SignedFormula sf);

}