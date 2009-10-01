/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCSigns;

/**
 * Generates the third family of problems for mbC anbd C1. 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ThirdLFIProblemGenerator extends FirstLFIProblemGenerator{

	private static final String UL="Ul";
	private static final String UR="Ur";
	private static final String C1="C1";
	private static final String C2="C2";

	protected ThirdLFIProblemGenerator() {
		super("ThirdLFIFamily");
	}

	protected SignedFormula generateSecondSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int begin, int end) {
		
		ThirdLFIFamilySpecialFormulaGenerator fg2 = new ThirdLFIFamilySpecialFormulaGenerator(getAtomName(),
				"C",
				MBCConnectives.CONSISTENCY, MBCConnectives.IMPLIES, MBCConnectives.OR, end, UL);
		SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE,
				MBCConnectives.AND);

		return sfg.generate(ff, sff, begin, end,
				FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg2);

	}
	
	protected Problem generate(Problem p, int begin, int end) {
		FormulaFactory ff = new FormulaFactory();
		SignedFormulaFactory sff = new SignedFormulaFactory();

		p.setFormulaFactory(ff);
		p.setSignedFormulaFactory(sff);

		SignedFormulaList sfl = new SignedFormulaList();

		sfl.add(generateInitialSignedFormula(ff,sff));
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
     * @return
     */
    private SignedFormula generateInitialSignedFormula(FormulaFactory ff, SignedFormulaFactory sff) {
        AtomicFormula ul = ff.createAtomicFormula(UL);
        AtomicFormula ur = ff.createAtomicFormula(UR);
        Formula ulAndUr = ff.createCompositeFormula(MBCConnectives.AND, ul, ur);
        
        return sff.createSignedFormula(MBCSigns.TRUE, ulAndUr);
    }
    
	protected SignedFormula generateFourthSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int size) {
	    AtomicFormula c1 = ff.createAtomicFormula(C1);
	    AtomicFormula c2 = ff.createAtomicFormula(C2);
	    Formula c2OrC = ff.createCompositeFormula(MBCConnectives.OR, c2, ff
				.createAtomicFormula(getConclusionName()));
	    Formula c1Implies = ff.createCompositeFormula(MBCConnectives.IMPLIES, c1, c2OrC);
	    
		return sff.createSignedFormula(MBCSigns.FALSE, c1Implies);
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

		
		// creates (!A_n&Ul) ->C
		AtomicFormula ur = ff.createAtomicFormula(UR);
		Formula notAn = ff
		.createCompositeFormula(MBCConnectives.NOT, ff
				.createAtomicFormula(getAtomName() + end));
		Formula urAndNotAn= ff.createCompositeFormula(MBCConnectives.AND, ur, notAn);
		Formula f_end = ff.createCompositeFormula(MBCConnectives.IMPLIES, urAndNotAn, ff
				.createAtomicFormula(getConclusionName()));


		SignedFormula sf_begin = sfg.generate(ff, sff, begin, end,
			FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg);

		SignedFormula sf_result = sff.createSignedFormula(sf_begin.getSign(),
				ff.createCompositeFormula(MBCConnectives.OR, sf_begin
						.getFormula(), f_end));

		return sf_result;
	}


}
