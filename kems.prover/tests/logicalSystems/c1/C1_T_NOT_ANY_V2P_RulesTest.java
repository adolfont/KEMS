package logicalSystems.c1;

import static org.junit.Assert.assertTrue;
import logic.formulas.Connective;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;

import org.junit.Before;
import org.junit.Test;

import rules.ActionType;
import rules.KEAction;
import rules.KERuleRole;
import rules.TwoPremisesOneConclusionRule;
import rules.getters.C1_F_CONS_ANY_Getter;
import rules.patterns.C1_T_NOT_ANY_LEFT_V2P_Pattern;

public class C1_T_NOT_ANY_V2P_RulesTest {

	private SignedFormulaCreator sfc;
	private SignedFormulaList sfl;

	@Before
	public void setUp() {
		sfc = new SignedFormulaCreator("sats5");

	}

	@Test
	public void testAllPossibleApllications() {
		testApplication(C1Connectives.AND, "&");
		testApplication(C1Connectives.OR, "|");
		testApplication(C1Connectives.IMPLIES, "->");

		testApplicationV2(C1Connectives.AND, "&", C1Rules.T_NOT_AND_LEFT_V2P);
		testApplicationV2(C1Connectives.OR, "|", C1Rules.T_NOT_OR_LEFT_V2P);
		testApplicationV2(C1Connectives.IMPLIES, "->", C1Rules.T_NOT_IMPLIES_LEFT_V2P);
	}

	public void testApplication(Connective c, String cs) {
		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A1" + cs + "B1)"));
		sfl.add(sfc.parseString("T (A1" + cs + "B1)&(!(A1&!A1))"));

		// assertTrue(C1Rules.T_NOT_AND_LEFT_V2P.matchesMain(sfl.get(0)));

		// PADRAO baseado em interface IBinarySignedFormulaPattern
		// T!(A%B)
		// T (A%B)&!(A&!A)
		// USANDO PADROES JA FEITOS
		C1_T_NOT_ANY_LEFT_V2P_Pattern pattern = new C1_T_NOT_ANY_LEFT_V2P_Pattern(c);
		assertTrue(pattern.matchesMain(sfl.get(0)));
		assertTrue(pattern.matches(sfl.get(0), sfl.get(1)));
		assertTrue(pattern.getAuxiliaryCandidates(sfc.getSignedFormulaFactory(),
				sfc.getFormulaFactory(), sfl.get(0)).toString().equals("[T ((A1" + cs + "B1)&!(A1&!A1))]"));

		C1_F_CONS_ANY_Getter getter = new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT);
		// System.out.println(getter.getSignedFormula(
		// sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(), sfl));
		assertTrue(getter.getSignedFormula(sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(), sfl)
				.toString().equals("F !(B1&!B1)"));

		TwoPremisesOneConclusionRule rule = new TwoPremisesOneConclusionRule("TESTE", pattern,
				new KEAction(ActionType.ADD_NODE, getter));
		// System.out.println(rule.getPossibleConclusions(sfc.getSignedFormulaFactory(),
		// sfc
		// .getFormulaFactory(), sfl));
		assertTrue(rule.getPossibleConclusions(sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(),
				sfl).toString().equals("[F !(B1&!B1)]"));

	}

	public void testApplicationV2(Connective c, String cs, TwoPremisesOneConclusionRule rule) {
		sfl = new SignedFormulaList();
		sfl.add(sfc.parseString("T !(A1" + cs + "B1)"));
		sfl.add(sfc.parseString("T (A1" + cs + "B1)&(!(A1&!A1))"));

		// assertTrue(C1Rules.T_NOT_AND_LEFT_V2P.matchesMain(sfl.get(0)));

		// PADRAO baseado em interface IBinarySignedFormulaPattern
		// T!(A%B)
		// T (A%B)&!(A&!A)
		// USANDO PADROES JA FEITOS
		assertTrue(rule.matchesMain(sfl.get(0)));
		assertTrue(rule.getAuxiliaryCandidates(sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(),
				sfl.get(0)).toString().equals("[T ((A1" + cs + "B1)&!(A1&!A1))]"));

		C1_F_CONS_ANY_Getter getter = (C1_F_CONS_ANY_Getter) rule.getConclusion().getContent();
		// System.out.println(getter.getSignedFormula(
		// sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(), sfl));
		assertTrue(getter.getSignedFormula(sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(), sfl)
				.toString().equals("F !(B1&!B1)"));

		assertTrue(rule.getPossibleConclusions(sfc.getSignedFormulaFactory(), sfc.getFormulaFactory(),
				sfl).toString().equals("[F !(B1&!B1)]"));

	}

}
