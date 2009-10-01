package logicalSystems.c1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

import org.junit.Before;
import org.junit.Test;

import rules.TwoPremisesOneConclusionRule;
import rules.patterns.C1ConsistencyAnyBinaryConnectivePattern;
import rules.patterns.C1ConsistencyPattern;
import rules.patterns.C1SignConsistencyAnyBinaryConnectivePattern;
import rules.patterns.C1SignConsistencyPattern;
import rules.patterns.C1_Sign_T_NOT_1_Pattern;

public class C1_T_NOT_1_RuleTest {

	SignedFormulaFactory sff;
	FormulaFactory ff;

	Formula x;
	Formula not_x;
	Formula CX;
	Formula not_CX;
	SignedFormula auxiliaryPremise;

	Formula x_and_not_x;
	Formula CX_and_not_CX;
	Formula not__x_and_not_x;
	Formula not__CX_and_not_CX;

	@Before
	public void setUp() {
		sff = new SignedFormulaFactory();
		ff = new FormulaFactory();

		x = ff.createAtomicFormula("X");
		CX = ff.createCompositeFormula(C1Connectives.AND, ff
				.createAtomicFormula("Y"), ff.createAtomicFormula("Z"));
		not_x = ff.createCompositeFormula(C1Connectives.NOT, x);
		not_CX = ff.createCompositeFormula(C1Connectives.NOT, CX);
		auxiliaryPremise = sff.createSignedFormula(C1Signs.TRUE, not_x);

		x_and_not_x = ff.createCompositeFormula(C1Connectives.AND, x, not_x);
		CX_and_not_CX = ff
				.createCompositeFormula(C1Connectives.AND, CX, not_CX);
		not__x_and_not_x = ff.createCompositeFormula(C1Connectives.NOT,
				x_and_not_x);
		not__CX_and_not_CX = ff.createCompositeFormula(C1Connectives.NOT,
				CX_and_not_CX);

	}

	@Test
	public void testPatterns_C1ConsistencyPattern() {

		C1ConsistencyPattern pattern = new C1ConsistencyPattern(
				C1Connectives.NOT, C1Connectives.AND);
		assertFalse(pattern.matches(x_and_not_x));
		// System.out.println(not__x_and_not_x);
		assertTrue(pattern.matches(not__x_and_not_x));
		assertFalse(pattern.matches(CX_and_not_CX));
		assertTrue(pattern.matches(not__CX_and_not_CX));
	}

	@Test
	public void testPatterns_T_C1ConsistencyPattern() {

		C1SignConsistencyPattern pattern = new C1SignConsistencyPattern(
				C1Signs.TRUE);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_and_not_x)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x)));

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				CX_and_not_CX)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__CX_and_not_CX)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__CX_and_not_CX)));
	}

	@Test
	public void testPatterns_Consistency_Any_Pattern() {

		C1ConsistencyAnyBinaryConnectivePattern pattern = new C1ConsistencyAnyBinaryConnectivePattern(
				C1Connectives.AND);

		assertFalse(pattern.matches(x_and_not_x));
		assertFalse(pattern.matches(not__x_and_not_x));
		assertFalse(pattern.matches(CX_and_not_CX));
		assertTrue(pattern.matches(not__CX_and_not_CX));
	}

	@Test
	public void testPatterns_Sign_Consistency_Any_Pattern() {

		C1SignConsistencyAnyBinaryConnectivePattern pattern = new C1SignConsistencyAnyBinaryConnectivePattern(
				C1Signs.TRUE, C1Connectives.AND);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_and_not_x)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x)));

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				CX_and_not_CX)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__CX_and_not_CX)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__CX_and_not_CX)));

	}

	@Test
	public void testPatterns_C1_T_Not_Consistency_Pattern() {
		C1_Sign_T_NOT_1_Pattern pattern = new C1_Sign_T_NOT_1_Pattern(
				C1Signs.TRUE);

		SignedFormula sf1 = sff.createSignedFormula(C1Signs.TRUE, not_x);
		SignedFormula sf2 = sff.createSignedFormula(C1Signs.FALSE, not_x);
		SignedFormula sf3 = sff.createSignedFormula(C1Signs.TRUE, not_CX);
		SignedFormula sf4 = sff.createSignedFormula(C1Signs.FALSE, not_CX);
		SignedFormula sf5 = sff.createSignedFormula(C1Signs.FALSE, CX);
		SignedFormula sf6 = sff.createSignedFormula(C1Signs.TRUE, x_and_not_x);
		SignedFormula sf7 = sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x);
		SignedFormula sf8 = sff
				.createSignedFormula(C1Signs.TRUE, CX_and_not_CX);
		SignedFormula sf9 = sff.createSignedFormula(C1Signs.TRUE,
				not__CX_and_not_CX);

		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sf1).toString()
				.equals("[]"));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sf2).toString()
				.equals("[]"));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sf3).toString()
				.equals("[]"));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sf4).toString()
				.equals("[]"));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sf7).toString()
				.equals("[T !X]"));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sf9).toString()
				.equals("[T !(Y&Z)]"));
//		System.out.println(pattern.getAuxiliaryCandidates(sff, ff, sf7));
//		System.out.println(pattern.getAuxiliaryCandidates(sff, ff, sf9));

		assertFalse(pattern.matchesMain(sf1));
		assertFalse(pattern.matchesMain(sf2));
		assertFalse(pattern.matchesMain(sf3));
		assertFalse(pattern.matchesMain(sf4));
		assertFalse(pattern.matchesMain(sf5));
		assertFalse(pattern.matchesMain(sf6));
		assertTrue(pattern.matchesMain(sf7));
		assertFalse(pattern.matchesMain(sf8));
		assertTrue(pattern.matchesMain(sf9));

		assertFalse(pattern.matches(sf1, sf6));
		assertTrue(pattern.matches(sf7, sf1));
		assertFalse(pattern.matches(sf3, sf8));
		assertTrue(pattern.matches(sf9, sf3));

	}

	@Test
	public void test_T_NOT_1() {

		// Variation of MBCRules.T_NOT_1
		TwoPremisesOneConclusionRule T_NOT_1 = C1Rules.T_NOT_2;
			
//			new TwoPremisesOneConclusionRule(
//				"T_NOT_1", new C1_Sign_T_NOT_1_Pattern(MBCSigns.TRUE),
//				new KEAction(ActionType.ADD_NODE, UnaryConnectiveGetter.FALSE));

		// Input:
		// Main: T !X
		// Auxiliary: T !(X & !X) (that is, T o X)

		// Output: F X

		// PRIMEIRO TESTE
		SignedFormula mainPremise = sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x);
		SignedFormulaList sfl;
		sfl = new SignedFormulaList();
		sfl.add(mainPremise);
		sfl.add(auxiliaryPremise);
		SignedFormulaList conclusions;
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		SignedFormula conc = sff.createSignedFormula(C1Signs.FALSE, x);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(conc));

		// SEGUNDO TESTE
		sfl = new SignedFormulaList();
		sfl.add(auxiliaryPremise);
		sfl.add(mainPremise);
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		assertTrue(conclusions == null);

		// TERCEIRO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__CX_and_not_CX));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not_CX));
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(sff.createSignedFormula(C1Signs.FALSE,
				CX)));

		// QUARTO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__CX_and_not_CX));
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not_CX));
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		assertTrue(conclusions==null);

		// QUINTO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__CX_and_not_CX));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not_CX));
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		assertTrue(conclusions==null);

		// SEXTO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__CX_and_not_CX));
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not_CX));
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		assertTrue(conclusions==null);

		// SETIMO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__CX_and_not_CX));
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not_x));
		conclusions = T_NOT_1.getPossibleConclusions(sff, ff, sfl);
		assertTrue(conclusions==null);

	}

}
