/*
 * Created on 27/09/2005
 *
 */
package logicalSystems.c1;

import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalRules;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.ActionType;
import rules.C1_F_CONS_ANY_Rule;
import rules.KEAction;
import rules.KERuleRole;
import rules.NamedRule;
import rules.OnePremiseOneConclusionRule;
import rules.OnePremissTwoConclusionsRule;
import rules.TwoPremisesOneConclusionRule;
import rules.getters.C1_F_CONS_ANY_Getter;
import rules.getters.C1_T_NOT_1_Getter;
import rules.getters.UnaryConnectiveConnectiveGetter;
import rules.patterns.C1ConsistencyConnectivePattern;
import rules.patterns.C1_Sign_T_NOT_1_Pattern;
import rules.patterns.C1_T_NOT_ANY_LEFT_V2P_Pattern;
import rules.patterns.C1_T_NOT_ANY_Pattern;
import rules.patterns.SignConnectiveConnectivePattern;

/**
 * Class that contains C1 specific rules. Most C1 Rules come from mbC.
 * All C1 connectives are the same as those from mbC.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */

/**
 * Rules for mbC. The rules are public static members for efficiency reasons.
 * The releaseMemory() method allows us to save some memory when necessary.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */

public class C1Rules {

	// T_NOT_ANY_ROLE RULES
	// ANY=AND or OR or IMPLIES
	// ROLE=LEFT or RIGHT
	// Note: these are three premise rules!!!

	public static C1_F_CONS_ANY_Rule T_NOT_AND_LEFT = new C1_F_CONS_ANY_Rule("T_NOT_AND_LEFT",
			new C1_T_NOT_ANY_Pattern(C1Connectives.AND, KERuleRole.LEFT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	public static C1_F_CONS_ANY_Rule T_NOT_AND_RIGHT = new C1_F_CONS_ANY_Rule("T_NOT_AND_RIGHT",
			new C1_T_NOT_ANY_Pattern(C1Connectives.AND, KERuleRole.RIGHT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT)));

	public static C1_F_CONS_ANY_Rule T_NOT_OR_LEFT = new C1_F_CONS_ANY_Rule("T_NOT_OR_LEFT",
			new C1_T_NOT_ANY_Pattern(C1Connectives.OR, KERuleRole.LEFT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	public static C1_F_CONS_ANY_Rule T_NOT_OR_RIGHT = new C1_F_CONS_ANY_Rule("T_NOT_OR_RIGHT",
			new C1_T_NOT_ANY_Pattern(C1Connectives.OR, KERuleRole.RIGHT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT)));

	public static C1_F_CONS_ANY_Rule T_NOT_IMPLIES_LEFT = new C1_F_CONS_ANY_Rule(
			"T_NOT_IMPLIES_LEFT", new C1_T_NOT_ANY_Pattern(C1Connectives.IMPLIES, KERuleRole.LEFT),
			new KEAction(ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	public static C1_F_CONS_ANY_Rule T_NOT_IMPLIES_RIGHT = new C1_F_CONS_ANY_Rule(
			"T_NOT_IMPLIES_RIGHT", new C1_T_NOT_ANY_Pattern(C1Connectives.IMPLIES, KERuleRole.RIGHT),
			new KEAction(ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT)));

	// T_NOT_ANY_RULES used only to apply PB
	// only three are necessary

	public static TwoPremisesOneConclusionRule T_NOT_AND_LEFT_V2P = new TwoPremisesOneConclusionRule(
			"T_NOT_AND_LEFT_V2P", new C1_T_NOT_ANY_LEFT_V2P_Pattern(C1Connectives.AND), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));
	public static TwoPremisesOneConclusionRule T_NOT_OR_LEFT_V2P = new TwoPremisesOneConclusionRule(
			"T_NOT_OR_LEFT_V2P", new C1_T_NOT_ANY_LEFT_V2P_Pattern(C1Connectives.OR), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));
	public static TwoPremisesOneConclusionRule T_NOT_IMPLIES_LEFT_V2P = new TwoPremisesOneConclusionRule(
			"T_NOT_IMPLIES_LEFT_V2P", new C1_T_NOT_ANY_LEFT_V2P_Pattern(C1Connectives.IMPLIES),
			new KEAction(ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	// F_CONS_ANY_RULES ANY=AND or OR or IMPLIES

	// Fo (A<conn>B) <conn>=ANY connective
	// ToA
	// ------
	// FoB
	// We only apply PB with this rule as objective if oA appears in the branch
	// as subformula of some formula.

	public static TwoPremisesOneConclusionRule F_CONS_AND_1 = new TwoPremisesOneConclusionRule(
			"F_CONS_AND_1", new C1ConsistencyConnectivePattern(C1Signs.FALSE, C1Connectives.AND,
					C1Signs.TRUE, KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.LEFT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	public static TwoPremisesOneConclusionRule F_CONS_OR_1 = new TwoPremisesOneConclusionRule(
			"F_CONS_OR_1", new C1ConsistencyConnectivePattern(C1Signs.FALSE, C1Connectives.OR,
					C1Signs.TRUE, KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.LEFT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	public static TwoPremisesOneConclusionRule F_CONS_IMPLIES_1 = new TwoPremisesOneConclusionRule(
			"F_CONS_IMPLIES_1", new C1ConsistencyConnectivePattern(C1Signs.FALSE, C1Connectives.IMPLIES,
					C1Signs.TRUE, KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.LEFT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.RIGHT)));

	// Fo (A<conn>B)
	// ToB
	// ------
	// FoA
	// This is a derived rule. PB need not be applied.

	public static TwoPremisesOneConclusionRule F_CONS_AND_2 = new TwoPremisesOneConclusionRule(
			"F_CONS_AND_2", new C1ConsistencyConnectivePattern(C1Signs.FALSE, C1Connectives.AND,
					C1Signs.TRUE, KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.RIGHT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT)));

	public static TwoPremisesOneConclusionRule F_CONS_OR_2 = new TwoPremisesOneConclusionRule(
			"F_CONS_OR_2", new C1ConsistencyConnectivePattern(C1Signs.FALSE, C1Connectives.OR,
					C1Signs.TRUE, KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.RIGHT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT)));

	public static TwoPremisesOneConclusionRule F_CONS_IMPLIES_2 = new TwoPremisesOneConclusionRule(
			"F_CONS_IMPLIES_2", new C1ConsistencyConnectivePattern(C1Signs.FALSE, C1Connectives.IMPLIES,
					C1Signs.TRUE, KERuleRole.LEFT, KERuleRole.LEFT, KERuleRole.RIGHT), new KEAction(
					ActionType.ADD_NODE, new C1_F_CONS_ANY_Getter(C1Signs.FALSE, KERuleRole.LEFT)));

	// T !!A
	// ------
	// T A

	public static final OnePremiseOneConclusionRule T_NOT_NOT = new OnePremiseOneConclusionRule(
			"T_NOT_NOT", new SignConnectiveConnectivePattern(ClassicalSigns.TRUE,
					ClassicalConnectives.NOT, ClassicalConnectives.NOT), new KEAction(ActionType.ADD_NODE,
					UnaryConnectiveConnectiveGetter.TRUE));

	// This one is different in C1
	// T oA
	// T !A
	// ------
	// F A

	public static TwoPremisesOneConclusionRule T_NOT_2 = new TwoPremisesOneConclusionRule("T_NOT_2",
			new C1_Sign_T_NOT_1_Pattern(C1Signs.TRUE), new KEAction(ActionType.ADD_NODE,
					new C1_T_NOT_1_Getter(C1Signs.FALSE)));
	// old version: T!A, ToA ---> FA
	// public static TwoPremisesOneConclusionRule T_NOT_1 = new
	// TwoPremisesOneConclusionRule(
	// "T_NOT_1", new C1_Sign_T_NOT_1_Pattern(C1Signs.TRUE), new KEAction(
	// ActionType.ADD_NODE, UnaryConnectiveGetter.FALSE));

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

	/*
	 * 
	 * // rules with NEGATION AND CONSISTENCY
	 * 
	 * // T_NOT_1 // T !A // T oA // --- // F A public static
	 * TwoPremisesOneConclusionRule T_NOT_1 = new TwoPremisesOneConclusionRule(
	 * "T_NOT_1", new TwoSignsTwoConnectivesRolePattern(MBCSigns.TRUE,
	 * MBCConnectives.NOT, MBCSigns.TRUE, MBCConnectives.CONSISTENCY,
	 * KERuleRole.LEFT), new KEAction( ActionType.ADD_NODE,
	 * UnaryConnectiveGetter.FALSE));
	 * 
	 * // derived (optional) rules
	 * 
	 * // F_FORMULA - to be used with care // F A //----- // T !A public static
	 * OnePremiseOneConclusionRule F_FORMULA = new OnePremiseOneConclusionRule(
	 * "F_FORMULA", new SignPattern(ClassicalSigns.TRUE), new KEAction(
	 * ActionType.ADD_NODE, new UnarySignConnectiveGetter( MBCSigns.FALSE,
	 * MBCConnectives.NOT)));
	 * 
	 * // T_NOT_2 rule - where CONSISTENCY appears in the conclusion // T !A -
	 * main // T A - auxiliary //------ // F oA public static
	 * TwoPremisesOneConclusionRule T_NOT_2 = new TwoPremisesOneConclusionRule(
	 * "T_NOT_2",
	 * 
	 * new TwoSignsConnectiveRolePattern(MBCSigns.TRUE, MBCConnectives.NOT,
	 * MBCSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE, new
	 * BinaryConnectiveConnectiveGetter(MBCSigns.FALSE,
	 * MBCConnectives.CONSISTENCY, KERuleRole.LEFT)));
	 * 
	 * // rules with CONSISTENCY
	 * 
	 * // T_CONS_1 // T oA // T A // -- // F !A public static
	 * TwoPremisesOneConclusionRule T_CONS_1 = new TwoPremisesOneConclusionRule(
	 * "T_CONS_1", new TwoSignsConnectiveRolePattern(MBCSigns.TRUE,
	 * MBCConnectives.CONSISTENCY, MBCSigns.TRUE, KERuleRole.LEFT), new
	 * KEAction(ActionType.ADD_NODE, new
	 * BinaryTwoPremisesAddConnectiveGetter(MBCSigns.FALSE, MBCConnectives.NOT)));
	 * 
	 * // T_CONS_2 // T oA // T B -> A // T B -> !A // -- // F B public static
	 * ThreePremisesOneConclusionRule T_CONS_2 = new
	 * ThreePremisesOneConclusionRule( "T_CONS_2", new
	 * SignThreeConnectivesThreeRolesPattern( MBCSigns.TRUE,
	 * MBCConnectives.CONSISTENCY, MBCConnectives.IMPLIES, MBCConnectives.NOT,
	 * KERuleRole.LEFT, KERuleRole.RIGHT, KERuleRole.RIGHT, KERuleRole.LEFT),
	 * 
	 * new KEAction(ActionType.ADD_NODE,
	 * BinaryTwoPremisesAuxiliaryGetter.FALSE_LEFT));
	 * 
	 * public static void releaseMemory() { PB = null;
	 * 
	 * F_NOT = null;
	 * 
	 * T_AND = null; F_AND_LEFT = null; F_AND_RIGHT = null;
	 * 
	 * F_OR = null; T_OR_LEFT = null; T_OR_RIGHT = null;
	 * 
	 * F_IMPLIES = null; T_IMPLIES_LEFT = null; T_IMPLIES_RIGHT = null;
	 * 
	 * T_NOT_1 = null; T_NOT_2 = null;
	 * 
	 * F_FORMULA = null; T_CONS_1 = null; T_CONS_2 = null; }
	 */

}
