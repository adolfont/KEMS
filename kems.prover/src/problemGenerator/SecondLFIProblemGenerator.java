/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCSigns;

/**
 * Generates the second family of problems for mbC anbd C1. 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class SecondLFIProblemGenerator extends FirstLFIProblemGenerator{

	
	protected SecondLFIProblemGenerator() {
		super("SecondLFIFamily");
	}

	protected SignedFormula generateSecondSignedFormula(FormulaFactory ff,
			SignedFormulaFactory sff, int begin, int end) {
		
		SecondLFIFamilySpecialFormulaGenerator fg2 = new SecondLFIFamilySpecialFormulaGenerator(getAtomName(),
				"C",
				MBCConnectives.CONSISTENCY, MBCConnectives.IMPLIES, MBCConnectives.OR, end);
		SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE,
				MBCConnectives.AND);

		return sfg.generate(ff, sff, begin, end,
				FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg2);

	}

}
