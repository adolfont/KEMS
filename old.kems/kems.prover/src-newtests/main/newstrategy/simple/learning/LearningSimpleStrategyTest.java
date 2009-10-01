package main.newstrategy.simple.learning;

import junit.framework.TestCase;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;

public class LearningSimpleStrategyTest extends TestCase {

	public void testFormulaCreator() throws Exception {

		SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
		SignedFormula tf1 = sfc.parseString("T A1");
		SignedFormula tf2 = sfc.parseString("T A2");
		SignedFormula tf3 = sfc.parseString("T A3");
		SignedFormula tf4 = sfc.parseString("T A4");
		SignedFormula ff1 = sfc.parseString("F B1");
		SignedFormula ff2 = sfc.parseString("F B2");
		SignedFormula ff3 = sfc.parseString("F B3");

		SignedFormulaList sfl = new SignedFormulaList();
		sfl.add(tf1);
		sfl.add(tf2);
		sfl.add(ff1);
		sfl.add(ff2);
		sfl.add(ff3);
		sfl.add(tf3);
		sfl.add(tf4);
//		System.err.println(LearningSimpleStrategy.createLearnedFormula(sfl, sfc.getFormulaFactory()));

	}


}
