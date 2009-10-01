/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.AtomicFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logicalSystems.mbc.MBCConnectives;

/**
 * A specialized version of FormulaGenerator for the second family of problems.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ThirdLFIFamilySpecialFormulaGenerator extends FormulaGenerator {

	private Connective conn2, conn3;

	private String conclusion;
	private int end;

    private String ul;

	/**
	 * @param atom
	 * @param conn
	 */
	public ThirdLFIFamilySpecialFormulaGenerator(String atom,
			String conclusion, Connective conn, Connective conn2,
			Connective conn3, int end, String ul) {
		super(atom, conn);
		this.conn2 = conn2;
		this.conn3 = conn3;
		this.conclusion = conclusion;
		this.end = end;
		this.ul=ul;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see problemGenerator.FormulaGenerator#getInstance(logic.formulas.FormulaFactory,
	 *      int)
	 */
	public Formula getInstance(FormulaFactory ff, int index) {

		Formula secondTail;

		// creates (!A_n&Ul) ->C
		AtomicFormula Ul = ff.createAtomicFormula(ul);
		Formula notAn = ff
		.createCompositeFormula(MBCConnectives.NOT, ff
				.createAtomicFormula(getAtom() + end));
		Formula notAnAndUl= ff.createCompositeFormula(MBCConnectives.AND, notAn, Ul);
		Formula f_end = ff.createCompositeFormula(MBCConnectives.IMPLIES, notAnAndUl, ff
				.createAtomicFormula(conclusion));

		FormulaIteratingGenerator fig = new FormulaIteratingGenerator(
				conn3);
		
		FormulaGenerator fg1 = new FormulaGenerator(getAtom(), getConn());
		
		Formula f1=null;
		if (index < end) {
			f1 = fig.generate(ff, index+1, end,
					FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg1);
		}
		
		if(f1!=null){
			secondTail = ff.createCompositeFormula(conn3, f_end, f1);
		}
		else secondTail = f_end;
		
		return ff.createCompositeFormula(conn2, super.getInstance(ff, index),
				secondTail);
	}

}
