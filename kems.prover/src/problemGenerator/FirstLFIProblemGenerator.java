/*
 * Created on 06/10/2005
 *
 */
package problemGenerator;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCSigns;

/**
 * Class that generates the first family of problems for evaluating mbC and C1
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FirstLFIProblemGenerator extends LFIProblemGenerator{


	/**
	 */
	protected FirstLFIProblemGenerator() {
		super("FirstLFIFamily");
	}

	/**
	 */
	protected FirstLFIProblemGenerator(String name) {
		super(name);
	}


	protected SignedFormula generateFirstSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int begin, int end) {
		//		Formula f = getInstance(ff, end);
		//		for (int i = end - 1; i >= begin; i--) {
		//			f = ff.createCompositeFormula(MBCConnectives.AND,
		//					getInstance(ff, i), f);
		//		}
		//		return (sff.createSignedFormula(ClassicalSigns.TRUE, f));
		FormulaGenerator fg = new FormulaGenerator(getAtomName(), MBCConnectives.NOT);
		SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE,
				MBCConnectives.AND);

		return sfg.generate(ff, sff, begin, end,
				FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg);
	}

	protected SignedFormula generateSecondSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int begin, int end) {

		FormulaGenerator fg2 = new FirstLFIFamilySpecialFormulaGenerator(getAtomName(),
				MBCConnectives.CONSISTENCY, MBCConnectives.IMPLIES);
		SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE,
				MBCConnectives.AND);

		return sfg.generate(ff, sff, begin, end,
				FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg2);

	}

	/**
	 * @param ff
	 * @param sff
	 * @param begin
	 * @param end
	 * @return
	 */
	protected SignedFormula generateThirdSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int begin, int end) {
		FormulaGenerator fg = new FormulaGenerator(getAtomName(),
				MBCConnectives.CONSISTENCY);
		SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE,
				MBCConnectives.OR);

		// creates (!A_n) ->C
		Formula f_end = ff.createCompositeFormula(MBCConnectives.IMPLIES, ff
				.createCompositeFormula(MBCConnectives.NOT, ff
						.createAtomicFormula(getAtomName() + end)), ff
				.createAtomicFormula(getConclusionName()));

		SignedFormula sf_begin = sfg.generate(ff, sff, begin, end,
			FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg);

		SignedFormula sf_result = sff.createSignedFormula(sf_begin.getSign(),
				ff.createCompositeFormula(MBCConnectives.OR, sf_begin
						.getFormula(), f_end));

		return sf_result;
	}

	// comentado em 22-06-2006
//	private Formula getInstance(FormulaFactory ff, int i) {
//		String auxLetter1 = getAtomName();
//		return ff.createCompositeFormula(MBCConnectives.NOT, ff
//				.createAtomicFormula(auxLetter1 + i));
//	}

	/**
	 * @param ff
	 * @param sff
	 * @return
	 */
	protected SignedFormula generateFourthSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int size) {
		return sff.createSignedFormula(MBCSigns.FALSE, ff
				.createAtomicFormula(getConclusionName()));
	}

}
