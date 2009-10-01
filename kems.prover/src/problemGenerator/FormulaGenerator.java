/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;

/**
 * Basic class for generating a formula that iterates over a range.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FormulaGenerator implements IFormulaGenerator{

	private String atom;

	private Connective conn;

	/**
	 * @param atom
	 * @param conn
	 */
	public FormulaGenerator(String atom, Connective conn) {
		super();
		this.atom = atom;
		this.conn = conn;
	}

	/**
	 * @return Returns the atom.
	 */
	public String getAtom() {
		return atom;
	}

	/**
	 * @return Returns the conn.
	 */
	public Connective getConn() {
		return conn;
	}

	/**
	 * @param ff
	 * @param end
	 * @return
	 */
	public Formula getInstance(FormulaFactory ff, int index) {
		return ff.createCompositeFormula(conn, getAtomicFormula(ff, index));
	}

	/**
	 * @param index
	 * @return
	 */
	protected Formula getAtomicFormula(FormulaFactory ff, int index) {
		return ff.createAtomicFormula(atom + index);
	}

}
