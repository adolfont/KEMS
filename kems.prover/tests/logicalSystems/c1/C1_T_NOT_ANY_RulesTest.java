package logicalSystems.c1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import logic.formulas.AtomicFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalSigns;

import org.junit.Before;
import org.junit.Test;

import rules.ActionType;
import rules.C1_F_CONS_ANY_Rule;
import rules.KEAction;
import rules.KERuleRole;
import rules.ThreePremisesOneConclusionRule;
import rules.TwoPremisesOneConclusionRule;
import rules.getters.C1_F_CONS_ANY_Getter;
import rules.patterns.C1SignConsistencyPattern;
import rules.patterns.C1_T_NOT_ANY_Pattern;
import rules.patterns.SignConnectiveConnectivePattern;
import rules.patterns.SignConnectivePattern;

public class C1_T_NOT_ANY_RulesTest {

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

	SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");

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

	// RULES T_NOT_ANY_SIDE
	// Input:
	// Main: T !(X%Y)
	// Auxiliary 1: T X%Y
	// Auxiliary 2: T !(X & !X) (that is, T o X)
	// Output: F !(Y & !Y)

	// FIRST TEST: test pattern for main formula
	// T !(A%B)
	@Test
	public void test_C1_T_NOT_ANY_MainPattern() {

		SignConnectiveConnectivePattern pattern = new SignConnectiveConnectivePattern(
				ClassicalSigns.TRUE, C1Connectives.NOT, C1Connectives.AND);

		pattern = new SignConnectiveConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.NOT, C1Connectives.AND);

		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_y)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__x_and_y)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				xcy_and_notxcy)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__xcy_and_notxcy)));

		pattern = new SignConnectiveConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.NOT, C1Connectives.OR);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_y)));

		Formula x_or_y = ff.createCompositeFormula(C1Connectives.OR, x, y);
		Formula not__x_or_y = ff.createCompositeFormula(C1Connectives.NOT,
				x_or_y);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__x_or_y)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_or_y)));

		pattern = new SignConnectiveConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.NOT, C1Connectives.IMPLIES);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_y)));

		Formula x_imp_y = ff
				.createCompositeFormula(C1Connectives.IMPLIES, x, y);
		Formula not__x_imp_y = ff.createCompositeFormula(C1Connectives.NOT,
				x_imp_y);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				not__x_imp_y)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_imp_y)));

	}

	// SECOND TEST: test pattern for first auxiliary formula
	// T (A%B)
	@Test
	public void test_C1_T_NOT_ANY_Aux1Pattern() {

		SignConnectivePattern pattern;

		pattern = new SignConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.AND);

		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_and_y)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				x_and_y)));
		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_y)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_and_not_x)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				xcy_and_notxcy)));

		pattern = new SignConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.OR);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_and_y)));

		Formula x_or_y = ff.createCompositeFormula(C1Connectives.OR, x, y);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				x_or_y)));
		assertTrue(pattern.matches(sff
				.createSignedFormula(C1Signs.TRUE, x_or_y)));

		pattern = new SignConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.IMPLIES);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_and_y)));

		Formula x_imp_y = ff
				.createCompositeFormula(C1Connectives.IMPLIES, x, y);

		assertFalse(pattern.matches(sff.createSignedFormula(C1Signs.FALSE,
				x_imp_y)));
		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				x_imp_y)));

	}

	// SECOND TEST: test pattern for third auxiliary formula
	// T !(A&!A)
	@Test
	public void test_C1_T_NOT_ANY_Aux2Pattern() {

		C1SignConsistencyPattern pattern;

		pattern = new C1SignConsistencyPattern(ClassicalSigns.TRUE);

		assertTrue(pattern.matches(sff.createSignedFormula(C1Signs.TRUE,
				not__x_and_not_x)));
	}

	@Test
	public void test_Three_Signed_Formula_Pattern_All_Connectives() {
		test_Three_Signed_Formula_Pattern(C1Connectives.AND, "&");
		test_Three_Signed_Formula_Pattern(C1Connectives.OR, "|");
		test_Three_Signed_Formula_Pattern(C1Connectives.IMPLIES, "->");
	}

	// Pattern combining three sub-patterns
	public void test_Three_Signed_Formula_Pattern(Connective c, String cs) {
		C1_T_NOT_ANY_Pattern pattern;
		SignedFormulaList sfl;

		// BEGIN PART OF TEST
		pattern = new C1_T_NOT_ANY_Pattern(c, KERuleRole.LEFT);

		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A" + cs + "B)"));
		sfl.add(sfc.parseString("T (A" + cs + "B)"));
		sfl.add(sfc.parseString("T !(A&!A)"));

		assertTrue(pattern.matchesMain(sfl.get(0)));
		assertFalse(pattern.matchesMain(sfl.get(1)));

		assertTrue(pattern.matchesFirstAuxiliary(sfl.get(0), sfl.get(1)));
		assertFalse(pattern.matchesFirstAuxiliary(sfl.get(0), sfl.get(2)));

		assertTrue(pattern.matchesSecondAuxiliary(sfl.get(0), sfl.get(2)));
		assertFalse(pattern.matchesSecondAuxiliary(sfl.get(0), sfl.get(1)));
		assertFalse(pattern.matchesSecondAuxiliary(sfl.get(0), sfc
				.parseString("T !(B&!B)")));

		assertTrue(pattern.matches(sfl.get(0), sfl.get(1), sfl.get(2)));
		assertFalse(pattern.matches(sfl.get(0), sfl.get(2), sfl.get(1)));

		// System.out.println(pattern.getAuxiliaryCandidates(sff, ff,
		// sfl.get(0), sfl.get(1)).get(0));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sfl.get(0),
				sfl.get(1)).get(0).toString().equals("T !(A&!A)"));

		// BEGIN PART OF TEST
		pattern = new C1_T_NOT_ANY_Pattern(c, KERuleRole.RIGHT);

		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A" + cs + "B)"));
		sfl.add(sfc.parseString("T (A" + cs + "B)"));
		sfl.add(sfc.parseString("T !(B&!B)"));

		assertTrue(pattern.matchesMain(sfl.get(0)));
		assertFalse(pattern.matchesMain(sfl.get(1)));

		assertTrue(pattern.matchesFirstAuxiliary(sfl.get(0), sfl.get(1)));
		assertFalse(pattern.matchesFirstAuxiliary(sfl.get(0), sfl.get(2)));

		assertTrue(pattern.matchesSecondAuxiliary(sfl.get(0), sfl.get(2)));
		assertFalse(pattern.matchesSecondAuxiliary(sfl.get(0), sfl.get(1)));
		assertFalse(pattern.matchesSecondAuxiliary(sfl.get(0), sfc
				.parseString("T !(A&!A)")));

		assertTrue(pattern.matches(sfl.get(0), sfl.get(1), sfl.get(2)));
		assertFalse(pattern.matches(sfl.get(0), sfl.get(2), sfl.get(1)));

		// System.out.println(pattern.getAuxiliaryCandidates(sff, ff,
		// sfl.get(0), sfl.get(1)).get(0));
		assertTrue(pattern.getAuxiliaryCandidates(sff, ff, sfl.get(0),
				sfl.get(1)).get(0).toString().equals("T !(B&!B)"));
	}

	@Test
	public void testGetter() {
		testGetter("&", KERuleRole.RIGHT, "T !(A&!A)", "F !(B&!B)");
		testGetter("|", KERuleRole.RIGHT, "T !(A&!A)", "F !(B&!B)");
		testGetter("->", KERuleRole.RIGHT, "T !(A&!A)", "F !(B&!B)");

		testGetter("&", KERuleRole.LEFT, "T !(B&!B)", "F !(A&!A)");
		testGetter("|", KERuleRole.LEFT, "T !(B&!B)", "F !(A&!A)");
		testGetter("->", KERuleRole.LEFT, "T !(B&!B)", "F !(A&!A)");
	}

	public void testGetter(String cs, KERuleRole role, String secondAux,
			String expected) {
		C1_F_CONS_ANY_Getter getter = new C1_F_CONS_ANY_Getter(C1Signs.FALSE,
				role);

		SignedFormulaList sfl;

		// BEGIN PART OF TEST

		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A" + cs + "B)"));
		sfl.add(sfc.parseString("T (A" + cs + "B)"));
		sfl.add(sfc.parseString(secondAux));

		// System.out.println(getter.getSignedFormula(sff, ff, sfl));
		assertEquals(expected, getter.getSignedFormula(sff, ff, sfl).toString());
	}

	@Test
	public void testRuleApplication() {
		testRuleApplication("T_NOT_AND_LEFT", C1Connectives.AND,
				KERuleRole.LEFT, KERuleRole.RIGHT, "&", "T !(A&!A)",
				"F !(B&!B)");
		testRuleApplication("T_NOT_AND_RIGHT", C1Connectives.AND,
				KERuleRole.RIGHT, KERuleRole.LEFT, "&", "T !(B&!B)",
				"F !(A&!A)");
		testRuleApplication("T_NOT_OR_LEFT", C1Connectives.OR, KERuleRole.LEFT,
				KERuleRole.RIGHT, "|", "T !(A&!A)", "F !(B&!B)");
		testRuleApplication("T_NOT_OR_RIGHT", C1Connectives.OR,
				KERuleRole.RIGHT, KERuleRole.LEFT, "|", "T !(B&!B)",
				"F !(A&!A)");
		testRuleApplication("T_NOT_IMPLIES_LEFT", C1Connectives.IMPLIES,
				KERuleRole.LEFT, KERuleRole.RIGHT, "->", "T !(A&!A)",
				"F !(B&!B)");
		testRuleApplication("T_NOT_IMPLIES_RIGHT", C1Connectives.IMPLIES,
				KERuleRole.RIGHT, KERuleRole.LEFT, "->", "T !(B&!B)",
				"F !(A&!A)");
	}

	private void testRuleApplication(String name, Connective c,
			KERuleRole premRole, KERuleRole concRole, String cs,
			String secondAux, String expected) {

		C1_F_CONS_ANY_Rule rule = new C1_F_CONS_ANY_Rule(name,
				new C1_T_NOT_ANY_Pattern(c, premRole), new KEAction(
						ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(
								C1Signs.FALSE, concRole)));

		SignedFormulaList sfl;

		// BEGIN PART OF TEST

		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A" + cs + "B)"));
		sfl.add(sfc.parseString("T (A" + cs + "B)"));
		sfl.add(sfc.parseString(secondAux));

		assertTrue(rule.getPossibleConclusions(sff, ff, sfl).size() == 1);
		// System.out.println(rule.getPossibleConclusions(sff, ff,
		// sfl).get(0).toString());
		assertEquals(expected, rule.getPossibleConclusions(sff, ff, sfl).get(0)
				.toString());
	}
	
	@Test
	public void testRuleApplication_static() {
		testRuleApplication_static(C1Rules.T_NOT_AND_LEFT, "&", "T !(A&!A)",
		"F !(B&!B)");
		testRuleApplication_static(C1Rules.T_NOT_AND_RIGHT, "&", "T !(B&!B)",
		"F !(A&!A)");
		testRuleApplication_static(C1Rules.T_NOT_OR_LEFT, "|", "T !(A&!A)",
		"F !(B&!B)");
		testRuleApplication_static(C1Rules.T_NOT_OR_RIGHT, "|", "T !(B&!B)",
		"F !(A&!A)");
		testRuleApplication_static(C1Rules.T_NOT_IMPLIES_LEFT, "->", "T !(A&!A)",
		"F !(B&!B)");
		testRuleApplication_static(C1Rules.T_NOT_IMPLIES_RIGHT, "->", "T !(B&!B)",
		"F !(A&!A)");
	}


	private void testRuleApplication_static(ThreePremisesOneConclusionRule rule, String cs,
			String secondAux, String expected) {


		SignedFormulaList sfl;

		// BEGIN PART OF TEST

		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A" + cs + "B)"));
		sfl.add(sfc.parseString("T (A" + cs + "B)"));
		sfl.add(sfc.parseString(secondAux));

		assertTrue(rule.getPossibleConclusions(sff, ff, sfl).size() == 1);
		// System.out.println(rule.getPossibleConclusions(sff, ff,
		// sfl).get(0).toString());
		assertEquals(expected, rule.getPossibleConclusions(sff, ff, sfl).get(0)
				.toString());
	}

}
