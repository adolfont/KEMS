package logicalSystems.c1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

import org.junit.Before;
import org.junit.Test;

import rules.KERuleRole;
import rules.TwoPremisesOneConclusionRule;
import rules.getters.C1_F_CONS_ANY_Getter;
import rules.patterns.C1ConsistencyConnectivePattern;

public class C1_F_CONS_ANY_RulesTest {

	private SignedFormulaFactory sff;
	private FormulaFactory ff;

	private Formula x;
	private Formula not_x;

	private Formula x_and_not_x;
	private Formula not__x_and_not_x;
	private AtomicFormula y;
	private Formula x_and_y;
	private Formula not__x_and_y;
	private Formula not_y;
	private Formula xcy_and_notxcy;
	private Formula not__xcy_and_notxcy;
	private Formula y_and_not_y;
	private Formula not__y_and_not_y;

	private TwoPremisesOneConclusionRule F_CONS_AND_1, F_CONS_AND_2;
	private Formula x_or_y;
	private Formula not__x_or_y;
	private Formula xdy_and_notxdy;
	private Formula not__xdy_and_notxdy;
	private Formula x_i_y;
	private Formula not__x_i_y;
	private Formula not__xiy_and_notxiy;
	private Formula xiy_and_notxiy;

	@Before
	public void setUp() {
		sff = new SignedFormulaFactory();
		ff = new FormulaFactory();

		x = ff.createAtomicFormula("X");
		not_x = ff.createCompositeFormula(C1Connectives.NOT, x);
		y = ff.createAtomicFormula("Y");
		not_y = ff.createCompositeFormula(C1Connectives.NOT, y);

		x_and_y = ff.createCompositeFormula(C1Connectives.AND, ff
				.createAtomicFormula("X"), ff.createAtomicFormula("Y"));
		not__x_and_y = ff.createCompositeFormula(C1Connectives.NOT, x_and_y);
		x_or_y = ff.createCompositeFormula(C1Connectives.OR, ff
				.createAtomicFormula("X"), ff.createAtomicFormula("Y"));
		not__x_or_y = ff.createCompositeFormula(C1Connectives.NOT, x_or_y);
		x_i_y = ff.createCompositeFormula(C1Connectives.IMPLIES, ff
				.createAtomicFormula("X"), ff.createAtomicFormula("Y"));
		not__x_i_y = ff.createCompositeFormula(C1Connectives.NOT, x_i_y);

		x_and_not_x = ff.createCompositeFormula(C1Connectives.AND, x, not_x);
		y_and_not_y = ff.createCompositeFormula(C1Connectives.AND, y, not_y);
		xcy_and_notxcy = ff.createCompositeFormula(C1Connectives.AND, x_and_y,
				not__x_and_y);
		xdy_and_notxdy = ff.createCompositeFormula(C1Connectives.AND, x_or_y,
				not__x_or_y);
		xiy_and_notxiy = ff.createCompositeFormula(C1Connectives.AND, x_i_y,
				not__x_i_y);
		not__x_and_not_x = ff.createCompositeFormula(C1Connectives.NOT,
				x_and_not_x);
		not__y_and_not_y = ff.createCompositeFormula(C1Connectives.NOT,
				y_and_not_y);
		not__xcy_and_notxcy = ff.createCompositeFormula(C1Connectives.NOT,
				xcy_and_notxcy);
		not__xdy_and_notxdy = ff.createCompositeFormula(C1Connectives.NOT,
				xdy_and_notxdy);
		not__xiy_and_notxiy = ff.createCompositeFormula(C1Connectives.NOT,
				xiy_and_notxiy);
	}

	@Test
	public void test_C1ConsistencyConnectivePattern() {
		C1ConsistencyConnectivePattern pattern = new C1ConsistencyConnectivePattern(
				C1Signs.FALSE, C1Connectives.AND, C1Signs.TRUE,
				KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.LEFT);
		// Input:
		// Main: F !( X%Y & !(X%Y))
		// Auxiliary: T !(X & !X) (that is, T o X)
		// Output:
		// true or false (for matches* methods)
		// auxiliary candidates

		// matches Main
		assertTrue(pattern.matchesMain(sff.createSignedFormula(C1Signs.FALSE,
				not__xcy_and_notxcy)));
		assertFalse(pattern.matchesMain(sff.createSignedFormula(C1Signs.FALSE,
				xcy_and_notxcy)));
		assertFalse(pattern.matchesMain(sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x)));
		assertFalse(pattern.matchesMain(sff.createSignedFormula(C1Signs.TRUE,
				not__xcy_and_notxcy)));

		// matches
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__xcy_and_notxcy), sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__xcy_and_notxcy), sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__xcy_and_notxcy), sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__xcy_and_notxcy), sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x)));

		// System.out.println(pattern.getAuxiliaryCandidates(sff, ff, sff
		// .createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy)));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff,
				sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy))
				.size() == 1);
		assertEquals(pattern.getAuxiliaryCandidates(sff, ff,
				sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy))
				.get(0).toString(), "T !(X&!X)");

		pattern = new C1ConsistencyConnectivePattern(C1Signs.FALSE,
				C1Connectives.AND, C1Signs.TRUE, KERuleRole.LEFT,
				KERuleRole.LEFT, KERuleRole.RIGHT);

		assertTrue(pattern.getAuxiliaryCandidates(sff, ff,
				sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy))
				.size() == 1);
		assertEquals(pattern.getAuxiliaryCandidates(sff, ff,
				sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy))
				.get(0).toString(), "T !(Y&!Y)");

		// System.out.println(pattern.getAuxiliaryCandidates(sff, ff, sff
		// .createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy)));
	}

	@Test
	public void test_F_CONS_ANY_GETTER() {
		C1_F_CONS_ANY_Getter getter;
		SignedFormulaList sfl;

		getter = new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT);
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy));
		// System.out.println(sfl);
		// System.out.println(getter.getSignedFormula(sff, ff, sfl));
		assertEquals(getter.getSignedFormula(sff, ff, sfl).toString(),
				"F !(X&!X)");

		getter = new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT);
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy));
		// System.out.println(sfl);
		// System.out.println(getter.getSignedFormula(sff, ff, sfl));
		assertEquals(getter.getSignedFormula(sff, ff, sfl).toString(),
				"F !(Y&!Y)");
	}

	@Test
	public void test_F_CONS_AND() {

		// Input:
		// Main: F !( X%Y & !(X%Y))
		// Auxiliary: T !(X & !X) (that is, T o X)

		// Output: F !(Y&!Y) (that is, F o Y)

		F_CONS_AND_1 = C1Rules.F_CONS_AND_1;

		SignedFormulaList sfl;
		SignedFormulaList conclusions;
		SignedFormula expectedConclusion;

		// PRIMEIRO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__x_and_not_x));
		conclusions = F_CONS_AND_1.getPossibleConclusions(sff, ff, sfl);
		expectedConclusion = sff.createSignedFormula(C1Signs.FALSE,
				not__y_and_not_y);
		assertTrue(conclusions.size() == 1);
		// System.out.println(expectedConclusion);
		// System.out.println(conclusions);
		assertTrue(conclusions.contains(expectedConclusion));

		// SEGUNDO TESTE
		F_CONS_AND_2 = C1Rules.F_CONS_AND_2;
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xcy_and_notxcy));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__y_and_not_y));
		conclusions = F_CONS_AND_2.getPossibleConclusions(sff, ff, sfl);
		expectedConclusion = sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x);
		// System.out.println(expectedConclusion);
		// System.out.println(conclusions);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(expectedConclusion));

		// conectivos diferentes, resto igual
		// sinais diferentes, retso igual
		// mesmo sinal
		// sinais trocados
	}

	@Test
	public void test_F_CONS_OR() {

		// Input:
		// Main: F !( X%Y & !(X%Y))
		// Auxiliary: T !(X & !X) (that is, T o X)

		// Output: F !(Y&!Y) (that is, F o Y)

		SignedFormulaList sfl;
		SignedFormulaList conclusions;
		SignedFormula expectedConclusion;

		// PRIMEIRO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xdy_and_notxdy));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__x_and_not_x));
		conclusions = C1Rules.F_CONS_OR_1.getPossibleConclusions(sff, ff, sfl);
		expectedConclusion = sff.createSignedFormula(C1Signs.FALSE,
				not__y_and_not_y);
//		System.out.println(sfl);
//		System.out.println(expectedConclusion);
//		System.out.println(conclusions);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(expectedConclusion));

		// SEGUNDO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xdy_and_notxdy));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__y_and_not_y));
		conclusions = C1Rules.F_CONS_OR_2.getPossibleConclusions(sff, ff, sfl);
		expectedConclusion = sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x);
		// System.out.println(expectedConclusion);
		// System.out.println(conclusions);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(expectedConclusion));

		// conectivos diferentes, resto igual
		// sinais diferentes, retso igual
		// mesmo sinal
		// sinais trocados
	}


	@Test
	public void test_F_CONS_IMPLIES() {

		// Input:
		// Main: F !( X%Y & !(X%Y))
		// Auxiliary: T !(X & !X) (that is, T o X)

		// Output: F !(Y&!Y) (that is, F o Y)

		SignedFormulaList sfl;
		SignedFormulaList conclusions;
		SignedFormula expectedConclusion;

		// PRIMEIRO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xiy_and_notxiy));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__x_and_not_x));
		conclusions = C1Rules.F_CONS_IMPLIES_1.getPossibleConclusions(sff, ff, sfl);
		expectedConclusion = sff.createSignedFormula(C1Signs.FALSE,
				not__y_and_not_y);
//		System.out.println(sfl);
//		System.out.println(expectedConclusion);
//		System.out.println(conclusions);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(expectedConclusion));

		// SEGUNDO TESTE
		sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.FALSE, not__xiy_and_notxiy));
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, not__y_and_not_y));
		conclusions = C1Rules.F_CONS_IMPLIES_2.getPossibleConclusions(sff, ff, sfl);
		expectedConclusion = sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_not_x);
		// System.out.println(expectedConclusion);
		// System.out.println(conclusions);
		assertTrue(conclusions.size() == 1);
		assertTrue(conclusions.contains(expectedConclusion));

		// conectivos diferentes, resto igual
		// sinais diferentes, retso igual
		// mesmo sinal
		// sinais trocados
	}

}
