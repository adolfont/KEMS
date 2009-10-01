/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.Connective;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;

/**
 * Generates a signed formula following some pattern.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignedFormulaGenerator {

	private FormulaSign sign;

	private Connective conn;

	/**
	 * @param sign
	 * @param conn
	 */
	public SignedFormulaGenerator(FormulaSign sign, Connective conn) {
		this.sign = sign;
		this.conn = conn;
	}

	public SignedFormula generate(FormulaFactory ff, SignedFormulaFactory sff,
			int begin, int end, int association, IFormulaGenerator fg) {

		return (sff.createSignedFormula(sign, new FormulaIteratingGenerator(
				conn).generate(ff, begin, end, association, fg)));
	}

}
