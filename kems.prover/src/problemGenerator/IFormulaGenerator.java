/*
 * Created on 09/11/2005
 *
 */
package problemGenerator;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IFormulaGenerator {
	public Formula getInstance(FormulaFactory ff, int index);
}
