package logicalSystems.c1;

import static org.junit.Assert.assertEquals;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

import org.junit.Before;
import org.junit.Test;

import rules.IRule;

public class C1_T_NOT_NOT_RuleTest {

	private SignedFormulaFactory sff;
	private FormulaFactory ff;

	private Formula x;
	private Formula not_x;
	private Formula not_not_x;
	private Formula not_not_not_x;

	@Before
	public void setUp() {
		sff = new SignedFormulaFactory();
		ff = new FormulaFactory();

		x = ff.createAtomicFormula("X");
		not_x = ff.createCompositeFormula(C1Connectives.NOT, x);
		not_not_x = ff.createCompositeFormula(C1Connectives.NOT, not_x);
		not_not_not_x = ff.createCompositeFormula(C1Connectives.NOT, not_not_x);
	}

	@Test
	public void test_C1_T_NOT_NOT_Rule() {
		IRule r = C1Rules.T_NOT_NOT;
		SignedFormulaList sfl;
		sfl=new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not_not_x));
//		System.out.println(r.getPossibleConclusions(sff, ff, sfl));
		assertEquals(r.getPossibleConclusions(sff, ff, sfl).toString(),"[T X]");

		sfl=new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not_not_not_x));
//		System.out.println(r.getPossibleConclusions(sff, ff, sfl));
		assertEquals(r.getPossibleConclusions(sff, ff, sfl).toString(),"[T !X]");

		sfl=new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not_not_not_x));
//		System.out.println(r.getPossibleConclusions(sff, ff, sfl));
		assertEquals(r.getPossibleConclusions(sff, ff, sfl),null);
}


}
