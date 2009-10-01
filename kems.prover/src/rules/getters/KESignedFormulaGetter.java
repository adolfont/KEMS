/*
 * Created on 22/10/2004
 *
 */
package rules.getters;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface KESignedFormulaGetter {
	
	public SignedFormula getSignedFormula (SignedFormulaFactory sff, FormulaFactory ff, SignedFormulaList sfl);


}
