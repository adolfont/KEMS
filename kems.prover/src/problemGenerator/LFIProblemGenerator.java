/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * [CLASS_DESSCRIPTION]
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class LFIProblemGenerator extends ProblemGenerator {

	private String atomName = "A";

	private String conclusionName = "C";

	/**
	 * @return Returns the atomName.
	 */
	public String getAtomName() {
		return atomName;
	}

	/**
	 * @return Returns the conclusionName.
	 */
	public String getConclusionName() {
		return conclusionName;
	}

	/**
	 */
	protected LFIProblemGenerator(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see problemGenerator.ProblemGenerator#generate(logic.problem.Problem,
	 *      int)
	 */
	protected Problem generate(Problem p, int index) {
		return generate(p, 1, index);
	}

	protected Problem generate(Problem p, int begin, int end) {
		FormulaFactory ff = new FormulaFactory();
		SignedFormulaFactory sff = new SignedFormulaFactory();

		p.setFormulaFactory(ff);
		p.setSignedFormulaFactory(sff);

		SignedFormulaList sfl = new SignedFormulaList();

		sfl.add(generateFirstSignedFormula(ff, sff, begin, end));
		sfl.add(generateSecondSignedFormula(ff, sff, begin, end));
		sfl.add(generateThirdSignedFormula(ff, sff, begin, end));

		sfl.add(generateFourthSignedFormula(ff, sff, end));
		p.setSignedFormulaList(sfl);

		return p;
	}

	/**
	 * @param ff
	 * @param sff
	 * @param size
	 * @return
	 */
	abstract protected SignedFormula generateFourthSignedFormula(
			FormulaFactory ff, SignedFormulaFactory sff, int size);

	/**
	 * @param ff
	 * @param sff
	 * @param begin
	 * @param end
	 * @return
	 */
	abstract protected SignedFormula generateThirdSignedFormula(
			FormulaFactory ff, SignedFormulaFactory sff, int begin, int end);

	/**
	 * @param ff
	 * @param sff
	 * @param begin
	 * @param end
	 * @return
	 */
	abstract protected SignedFormula generateSecondSignedFormula(
			FormulaFactory ff, SignedFormulaFactory sff, int begin, int end);

	/**
	 * @param ff
	 * @param sff
	 * @param begin
	 * @param end
	 * @return
	 */
	abstract protected SignedFormula generateFirstSignedFormula(
			FormulaFactory ff, SignedFormulaFactory sff, int begin, int end);

}
