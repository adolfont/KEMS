/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;

/**
 * A specialized version of FormulaGenerator.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class FirstLFIFamilySpecialFormulaGenerator extends FormulaGenerator {
	

	private Connective conn2;

	/**
	 * @param atom
	 * @param conn
	 */
	public FirstLFIFamilySpecialFormulaGenerator(String atom, Connective conn, Connective conn2) {
		super(atom, conn);
		this.conn2 = conn2;
	}

	/* (non-Javadoc)
	 * @see problemGenerator.FormulaGenerator#getInstance(logic.formulas.FormulaFactory, int)
	 */
	public Formula getInstance(FormulaFactory ff, int index) {
		return ff.createCompositeFormula(conn2, super.getInstance(ff,index), 
				super.getAtomicFormula(ff,index));
	}

}
