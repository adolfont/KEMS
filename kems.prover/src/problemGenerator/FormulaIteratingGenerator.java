/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;

/**
 * Generates a formula by iterating
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FormulaIteratingGenerator {

	public static final int LEFT_ASSOCIATED = -1;

	public static final int RIGHT_ASSOCIATED = 1;

	private Connective conn;

	/**
	 * @param conn
	 */
	public FormulaIteratingGenerator(Connective conn) {
		super();
		this.conn = conn;
	}

	public Formula generate(FormulaFactory ff, int begin, int end,
			int association, IFormulaGenerator fg) {
		Formula f = null;
		if (association == LEFT_ASSOCIATED) {
			f = fg.getInstance(ff, begin);
			for (int i = begin + 1; i <= end; i++) {
				f = ff.createCompositeFormula(conn, f, fg.getInstance(ff, i));
			}
		} else {
			f = fg.getInstance(ff, end);
			for (int i = end - 1; i >= begin; i--) {
				f = ff.createCompositeFormula(conn, fg.getInstance(ff, i), f);
			}
		}
		return f;
	}

}
