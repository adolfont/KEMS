/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.mbc;

import logicalSystems.classicalLogic.ClassicalRules;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.ActionType;
import rules.KEAction;
import rules.KERuleRole;
import rules.NamedRule;
import rules.OnePremiseOneConclusionRule;
import rules.OnePremissTwoConclusionsRule;
import rules.ThreePremisesOneConclusionRule;
import rules.TwoPremisesOneConclusionRule;
import rules.getters.BinaryConnectiveConnectiveGetter;
import rules.getters.BinaryTwoPremisesAddConnectiveGetter;
import rules.getters.BinaryTwoPremisesAuxiliaryGetter;
import rules.getters.UnaryConnectiveGetter;
import rules.getters.UnarySignConnectiveGetter;
import rules.patterns.SignPattern;
import rules.patterns.SignThreeConnectivesThreeRolesPattern;
import rules.patterns.TwoSignsConnectiveRolePattern;
import rules.patterns.TwoSignsTwoConnectivesRolePattern;

/**
 * Rules for mbC. The rules are public static members for efficiency
 * reasons. The releaseMemory() method allows us to save some memory when
 * necessary.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MBCRules {

	// PB or Rb rule
	public static NamedRule PB = new NamedRule("PB");

	// simple rules with NOT
	public static OnePremiseOneConclusionRule F_NOT = ClassicalRules.F_NOT;

	// simple rules with AND
	public static OnePremissTwoConclusionsRule T_AND = ClassicalRules.T_AND;

	public static TwoPremisesOneConclusionRule F_AND_LEFT = ClassicalRules.F_AND_LEFT;

	public static TwoPremisesOneConclusionRule F_AND_RIGHT = ClassicalRules.F_AND_RIGHT;
	
	// simple rules with OR
	public static OnePremissTwoConclusionsRule F_OR = ClassicalRules.F_OR;

	public static TwoPremisesOneConclusionRule T_OR_LEFT = ClassicalRules.T_OR_LEFT;

	public static TwoPremisesOneConclusionRule T_OR_RIGHT = ClassicalRules.T_OR_RIGHT;
	
	// simple rules with IMPLIES
	public static OnePremissTwoConclusionsRule F_IMPLIES = ClassicalRules.F_IMPLIES;

	public static TwoPremisesOneConclusionRule T_IMPLIES_LEFT = ClassicalRules.T_IMPLIES_LEFT;

	public static TwoPremisesOneConclusionRule T_IMPLIES_RIGHT = ClassicalRules.T_IMPLIES_RIGHT;

	// rules with NEGATION AND CONSISTENCY

	// T_NOT_1
	// T !A
	// T oA
	// ---
	// F A
	public static TwoPremisesOneConclusionRule T_NOT_1 = new TwoPremisesOneConclusionRule(
			"T_NOT_1", new TwoSignsTwoConnectivesRolePattern(MBCSigns.TRUE,
					MBCConnectives.NOT, MBCSigns.TRUE,
					MBCConnectives.CONSISTENCY, KERuleRole.LEFT), new KEAction(
					ActionType.ADD_NODE, UnaryConnectiveGetter.FALSE));

	// derived (optional) rules

	// F_FORMULA - to be used with care
	// F A
	//-----
	// T !A
	public static OnePremiseOneConclusionRule F_FORMULA = new OnePremiseOneConclusionRule(
			"F_FORMULA", new SignPattern(ClassicalSigns.TRUE), new KEAction(
					ActionType.ADD_NODE, new UnarySignConnectiveGetter(
							MBCSigns.FALSE, MBCConnectives.NOT)));

	// T_NOT_2 rule - where CONSISTENCY appears in the conclusion
	// T !A - main
	// T A - auxiliary
	//------
	// F oA
	public static TwoPremisesOneConclusionRule T_NOT_2 = new TwoPremisesOneConclusionRule(
			"T_NOT_2",

			new TwoSignsConnectiveRolePattern(MBCSigns.TRUE,
					MBCConnectives.NOT, MBCSigns.TRUE, KERuleRole.LEFT),
			new KEAction(ActionType.ADD_NODE,
					new BinaryConnectiveConnectiveGetter(MBCSigns.FALSE,
							MBCConnectives.CONSISTENCY, KERuleRole.LEFT)));

	// rules with CONSISTENCY

	// T_CONS_1
	// T oA
	// T A
	// --
	// F !A
	public static TwoPremisesOneConclusionRule T_CONS_1 = new TwoPremisesOneConclusionRule(
			"T_CONS_1",
			new TwoSignsConnectiveRolePattern(MBCSigns.TRUE,
					MBCConnectives.CONSISTENCY, MBCSigns.TRUE, KERuleRole.LEFT),
			new KEAction(ActionType.ADD_NODE,
					new BinaryTwoPremisesAddConnectiveGetter(MBCSigns.FALSE,
							MBCConnectives.NOT)));

	// T_CONS_2
	// T oA
	// T B -> A
	// T B -> !A
	// --
	// F B
	public static ThreePremisesOneConclusionRule T_CONS_2 = new ThreePremisesOneConclusionRule(
			"T_CONS_2", new SignThreeConnectivesThreeRolesPattern(
					MBCSigns.TRUE, MBCConnectives.CONSISTENCY,
					MBCConnectives.IMPLIES, MBCConnectives.NOT,
					KERuleRole.LEFT, KERuleRole.RIGHT, KERuleRole.RIGHT,
					KERuleRole.LEFT),

			new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesAuxiliaryGetter.FALSE_LEFT));

	public static void releaseMemory() {
		PB = null;

		F_NOT = null;

		T_AND = null;
		F_AND_LEFT = null;
		F_AND_RIGHT = null;

		F_OR = null;
		T_OR_LEFT = null;
		T_OR_RIGHT = null;

		F_IMPLIES = null;
		T_IMPLIES_LEFT = null;
		T_IMPLIES_RIGHT = null;

		T_NOT_1 = null;
		T_NOT_2 = null;

		F_FORMULA = null;
		T_CONS_1 = null;
		T_CONS_2 = null;
	}

}
