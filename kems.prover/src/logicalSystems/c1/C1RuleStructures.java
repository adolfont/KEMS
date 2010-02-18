/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.c1;

import logic.formulas.Connective;
import logic.logicalSystem.ISignature;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.Rule;
import rules.ThreePremisesOneConclusionRule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;
import rules.structures.ConnectiveRuleStructureFactory;
import rules.structures.OnePremiseRuleList;
import rules.structures.PBRuleList;
import rules.structures.RuleList;
import rules.structures.RuleType;
import rules.structures.RulesStructure;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * 
 */
public class C1RuleStructures {

	// some structures
	private ISignature signature;
	private RulesStructure _rules;
	private ConnectiveRuleStructureFactory crsf;
	// structures for rules
	protected OnePremiseRuleList onePremiseRules;
	private ConnectiveRoleSignRuleList twoPremiseRules;
	private ConnectiveRoleSignRuleList threePremiseRules;
	private ConnectiveRoleSignRuleList specialTwoPremiseRules;
	private PBRuleList PBRules;
	private PBRuleList specialPBRules;
	private PBRuleList thirdPBRules;
	// public constants for lists of rules
	public static final String ONE_PREMISE_RULES = "onePremiseRules";
	public static final String TWO_PREMISE_RULES = "twoPremiseRules";
	public static final String THREE_PREMISE_RULES = "threePremiseRules";
	public static final String SPECIAL_TWO_PREMISE_RULES = "specialTwoPremiseRules";
	public static final String PB_RULES = "PBRules";
	public static final String SPECIAL_PB_RULES = "specialPBRules";
	public static final String THIRD_PB_RULES = "thirdPBRules";

	public C1RuleStructures(ISignature signature) {
		this.signature = signature;
		crsf = new ConnectiveRuleStructureFactory();

		/** mandatory one premise rules */
		onePremiseRules = initializeOnePremiseRuleList();

		/** mandatory two premise rules */
		twoPremiseRules = initializeTwoPremiseRuleList();

		/** mandatory two premise rules */
		threePremiseRules = initializeThreePremiseRuleList();

		/** mandatory special two premise rules */
		specialTwoPremiseRules = initializeSpecialTwoPremiseRuleList();

		/** mandatory rules for applying PB */
		PBRules = initializePBRuleList();

		/** mandatory special rules for applying PB */
		specialPBRules = initializeSpecialPBRuleList();

		/** mandatory special rules for applying PB */
		thirdPBRules = initializeThirdPBRuleList();

		/** Order of the sets of rules */
		// the order is important!
		_rules = new RulesStructure();
		_rules.add(ONE_PREMISE_RULES, onePremiseRules);
		_rules.add(TWO_PREMISE_RULES, twoPremiseRules);
		_rules.add(THREE_PREMISE_RULES, threePremiseRules);
		// _rules.add(SPECIAL_TWO_PREMISE_RULES, specialTwoPremiseRules);
		_rules.add(PB_RULES, PBRules);
		_rules.add(SPECIAL_PB_RULES, specialPBRules);
		_rules.add(THIRD_PB_RULES, thirdPBRules);
	}

	public RulesStructure getRuleStructure() {
		return _rules;
	}

	public RuleList getRules(Connective conn) {
		return crsf.createCRS(conn).getRules();
	}

	protected OnePremiseRuleList initializeOnePremiseRuleList() {
		onePremiseRules = new OnePremiseRuleList();

		addToOnePremiseRules(C1Signs.FALSE, C1Connectives.NOT, C1Rules.F_NOT);
		addToOnePremiseRules(C1Signs.TRUE, C1Connectives.AND, C1Rules.T_AND);
		addToOnePremiseRules(C1Signs.FALSE, C1Connectives.OR, C1Rules.F_OR);
		addToOnePremiseRules(C1Signs.FALSE, C1Connectives.IMPLIES, C1Rules.F_IMPLIES);
		addToOnePremiseRules(C1Signs.TRUE, C1Connectives.NOT, C1Rules.T_NOT_NOT);

		return onePremiseRules;
	}

	protected void addToOnePremiseRules(FormulaSign sign, Connective conn, Rule r) {
		if (signature.contains(conn)) {
			addConnectiveRuleType(conn, r, RuleType.SIMPLE_1P);
			onePremiseRules.add(sign, conn, r);
		}
	}

	private void addConnectiveRuleType(Connective conn, Rule r, RuleType rt) {
		crsf.createCRS(conn).add(r, rt);
	}

	/**
	 * @return
	 */
	private ConnectiveRoleSignRuleList initializeTwoPremiseRuleList() {
		twoPremiseRules = new ConnectiveRoleSignRuleList();

		addToTwoPremiseRules(C1Signs.FALSE, C1Connectives.AND, KERuleRole.LEFT, C1Rules.F_AND_LEFT);
		addToTwoPremiseRules(C1Signs.FALSE, C1Connectives.AND, KERuleRole.RIGHT, C1Rules.F_AND_RIGHT);
		addToTwoPremiseRules(C1Signs.TRUE, C1Connectives.OR, KERuleRole.LEFT, C1Rules.T_OR_LEFT);
		addToTwoPremiseRules(C1Signs.TRUE, C1Connectives.OR, KERuleRole.RIGHT, C1Rules.T_OR_RIGHT);
		addToTwoPremiseRules(C1Signs.TRUE, C1Connectives.IMPLIES, KERuleRole.LEFT,
				C1Rules.T_IMPLIES_LEFT);
		addToTwoPremiseRules(C1Signs.TRUE, C1Connectives.IMPLIES, KERuleRole.RIGHT,
				C1Rules.T_IMPLIES_RIGHT);
		addToTwoPremiseRules(C1Signs.TRUE, C1Connectives.NOT, KERuleRole.LEFT, C1Rules.T_NOT_2);
		// TODO: how to differentiate T_NOT_2B from T_NOT_2 here?
//		addToTwoPremiseRules(C1Signs.TRUE, C1Connectives.NOT, KERuleRole.LEFT, C1Rules.T_NOT_2B);
		return twoPremiseRules;
	}

	/**
	 * @return
	 */
	private ConnectiveRoleSignRuleList initializeThreePremiseRuleList() {
		threePremiseRules = new ConnectiveRoleSignRuleList();

		addToThreePremiseRules(C1Signs.TRUE, C1Connectives.AND, KERuleRole.LEFT, C1Rules.T_NOT_AND_LEFT);
		addToThreePremiseRules(C1Signs.TRUE, C1Connectives.AND, KERuleRole.RIGHT,
				C1Rules.T_NOT_AND_RIGHT);
		addToThreePremiseRules(C1Signs.TRUE, C1Connectives.OR, KERuleRole.LEFT, C1Rules.T_NOT_OR_LEFT);
		addToThreePremiseRules(C1Signs.TRUE, C1Connectives.OR, KERuleRole.RIGHT, C1Rules.T_NOT_OR_RIGHT);
		addToThreePremiseRules(C1Signs.TRUE, C1Connectives.IMPLIES, KERuleRole.LEFT,
				C1Rules.T_NOT_IMPLIES_LEFT);
		addToThreePremiseRules(C1Signs.TRUE, C1Connectives.IMPLIES, KERuleRole.RIGHT,
				C1Rules.T_NOT_IMPLIES_RIGHT);
		return threePremiseRules;
	}

	/**
	 * @return
	 */
	private ConnectiveRoleSignRuleList initializeSpecialTwoPremiseRuleList() {
		specialTwoPremiseRules = new ConnectiveRoleSignRuleList();

		addToSpecialTwoPremiseRules(C1Signs.FALSE, C1Connectives.AND, KERuleRole.LEFT,
				C1Rules.F_CONS_AND_1);
		addToSpecialTwoPremiseRules(C1Signs.FALSE, C1Connectives.AND, KERuleRole.RIGHT,
				C1Rules.F_CONS_AND_2);
		addToSpecialTwoPremiseRules(C1Signs.FALSE, C1Connectives.OR, KERuleRole.LEFT,
				C1Rules.F_CONS_OR_1);
		addToSpecialTwoPremiseRules(C1Signs.FALSE, C1Connectives.OR, KERuleRole.RIGHT,
				C1Rules.F_CONS_OR_2);
		addToSpecialTwoPremiseRules(C1Signs.FALSE, C1Connectives.IMPLIES, KERuleRole.LEFT,
				C1Rules.F_CONS_IMPLIES_1);
		addToSpecialTwoPremiseRules(C1Signs.FALSE, C1Connectives.IMPLIES, KERuleRole.RIGHT,
				C1Rules.F_CONS_IMPLIES_2);
		return specialTwoPremiseRules;
	}

	private void addToTwoPremiseRules(FormulaSign sign, Connective conn, KERuleRole role,
			TwoPremisesOneConclusionRule rule) {
		if (signature.contains(conn)) {
			addConnectiveRuleType(conn, rule, RuleType.SIMPLE_2P);
			twoPremiseRules.add(conn, role, sign, rule);
		}
	}

	private void addToThreePremiseRules(FormulaSign sign, Connective conn, KERuleRole role,
			ThreePremisesOneConclusionRule rule) {
		if (signature.contains(conn)) {
			addConnectiveRuleType(conn, rule, RuleType.SIMPLE_3P);
			threePremiseRules.add(conn, role, sign, rule);
		}
	}

	private void addToSpecialTwoPremiseRules(FormulaSign sign, Connective conn, KERuleRole role,
			TwoPremisesOneConclusionRule rule) {
		if (signature.contains(conn)) {
			addConnectiveRuleType(conn, rule, RuleType.SIMPLE_2P);
			specialTwoPremiseRules.add(conn, role, sign, rule);
		}
	}

	private PBRuleList initializePBRuleList() {
		PBRules = new PBRuleList();

		addToPBRules(PBRules, C1Signs.FALSE, C1Connectives.AND, C1Rules.F_AND_LEFT);
		addToPBRules(PBRules, C1Signs.TRUE, C1Connectives.OR, C1Rules.T_OR_LEFT);
		addToPBRules(PBRules, C1Signs.TRUE, C1Connectives.IMPLIES, C1Rules.T_IMPLIES_LEFT);
		// addToPBRules(PBRules, C1Signs.TRUE, C1Connectives.NOT, C1Rules.T_NOT_1);

		return PBRules;
	}

	private PBRuleList initializeSpecialPBRuleList() {
		// TODO maybe remove this OLD VERSION which was sABORTED...
		// specialPBRules = new C1SpecialPBRuleList();
		//		
		// addSpecialToPBRules(C1Signs.TRUE, C1Connectives.NOT, C1Rules.T_NOT_1);
		// addSpecialToPBRules(C1Signs.FALSE, C1Connectives.NOT, C1Connectives.AND,
		// C1Rules.F_CONS_AND_1);
		// addSpecialToPBRules(C1Signs.FALSE, C1Connectives.NOT, C1Connectives.OR,
		// C1Rules.F_CONS_OR_1);
		// addSpecialToPBRules(C1Signs.FALSE, C1Connectives.NOT,
		// C1Connectives.IMPLIES, C1Rules.F_CONS_IMPLIES_1);

		specialPBRules = new PBRuleList();

		addToPBRules(specialPBRules, C1Signs.TRUE, C1Connectives.AND, C1Rules.T_NOT_AND_LEFT_V2P);
		addToPBRules(specialPBRules, C1Signs.TRUE, C1Connectives.OR, C1Rules.T_NOT_OR_LEFT_V2P);
		addToPBRules(specialPBRules, C1Signs.TRUE, C1Connectives.IMPLIES,
				C1Rules.T_NOT_IMPLIES_LEFT_V2P);
		return specialPBRules;
	}

	private PBRuleList initializeThirdPBRuleList() {
		thirdPBRules = new PBRuleList();
		addToPBRules(thirdPBRules, C1Signs.TRUE, C1Connectives.NOT, C1Rules.T_NOT_2);
		return thirdPBRules;
	}

	/**
	 * @param false1
	 * @param and
	 * @param f_and_left
	 */
	private void addToPBRules(PBRuleList ruleList, FormulaSign sign, Connective conn, Rule r1) {
		if (signature.contains(conn)) {
			addConnectiveRuleType(conn, r1, RuleType.PB);
			ruleList.add(sign, conn, r1);
		}
	}

}
