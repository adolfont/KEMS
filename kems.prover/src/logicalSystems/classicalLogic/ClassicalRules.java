/*
 * Created on 03/12/2004
 *
 */
package logicalSystems.classicalLogic;

import rules.ActionType;
import rules.KEAction;
import rules.KERuleRole;
import rules.NamedRule;
import rules.OnePremiseOneConclusionRule;
import rules.OnePremissTwoConclusionsRule;
import rules.TwoPremisesOneConclusionRule;
import rules.getters.BinaryConnectiveGetter;
import rules.getters.BinaryTwoPremisesConnectiveGetter;
import rules.getters.SimpleSubformulaGetter;
import rules.getters.SubformulaConnectiveRoleGetter;
import rules.getters.SubformulaRoleGetter;
import rules.getters.UnaryConnectiveGetter;
import rules.patterns.SignConnectivePattern;
import rules.patterns.SignConnectiveRoleSubformulaPattern;
import rules.patterns.TwoConnectivesRoleSubformulaPattern;
import rules.patterns.TwoSignsConnectiveRolePattern;

/**
 * Rules (and patterns for these rules) for classical propositional logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ClassicalRules {

	/** Rule for PB */
	public static final NamedRule PB = new NamedRule("PB");

	/** Rule for closing branches */
	public static final NamedRule CLOSE = new NamedRule("CLOSE");

	// rules with not

	public static final OnePremiseOneConclusionRule T_NOT = new OnePremiseOneConclusionRule("T_NOT",
			new SignConnectivePattern(ClassicalSigns.TRUE, ClassicalConnectives.NOT), new KEAction(
					ActionType.ADD_NODE, UnaryConnectiveGetter.FALSE));

	public static final OnePremiseOneConclusionRule F_NOT = new OnePremiseOneConclusionRule("F_NOT",
			new SignConnectivePattern(ClassicalSigns.FALSE, ClassicalConnectives.NOT), new KEAction(
					ActionType.ADD_NODE, UnaryConnectiveGetter.TRUE));

	static final SignConnectiveRoleSubformulaPattern pattern_X_NOT_T = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.NOT, ClassicalSigns.TRUE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_NOT_T = new TwoPremisesOneConclusionRule(
			"X_NOT_T", pattern_X_NOT_T, new KEAction(ActionType.ADD_NODE, new SimpleSubformulaGetter(
					pattern_X_NOT_T, ClassicalConnectives.BOTTOM)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_NOT_F = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.NOT, ClassicalSigns.FALSE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_NOT_F = new TwoPremisesOneConclusionRule(
			"X_NOT_F", pattern_X_NOT_F, new KEAction(ActionType.ADD_NODE, new SimpleSubformulaGetter(
					pattern_X_NOT_F, ClassicalConnectives.TOP)

			));

	// rules with and
	public static final OnePremissTwoConclusionsRule T_AND = new OnePremissTwoConclusionsRule(
			"T_AND", new SignConnectivePattern(ClassicalSigns.TRUE, ClassicalConnectives.AND),
			new KEAction(ActionType.ADD_NODE, BinaryConnectiveGetter.TRUE_LEFT), new KEAction(
					ActionType.ADD_NODE, BinaryConnectiveGetter.TRUE_RIGHT));

	public static final TwoPremisesOneConclusionRule F_AND_LEFT = new TwoPremisesOneConclusionRule(
			"F_AND_LEFT",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.AND,
					ClassicalSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	static final SignConnectiveRoleSubformulaPattern pattern_X_AND_T_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.AND, ClassicalSigns.TRUE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_AND_T_LEFT = new TwoPremisesOneConclusionRule(
			"X_AND_T_LEFT", pattern_X_AND_T_LEFT,

			new KEAction(ActionType.ADD_NODE,

			new SubformulaRoleGetter(pattern_X_AND_T_LEFT, KERuleRole.RIGHT)

			));

	static final SignConnectiveRoleSubformulaPattern pattern_X_AND_T_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.AND, ClassicalSigns.TRUE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_AND_T_RIGHT = new TwoPremisesOneConclusionRule(
			"X_AND_T_RIGHT", pattern_X_AND_T_RIGHT,

			new KEAction(ActionType.ADD_NODE,

			new SubformulaRoleGetter(pattern_X_AND_T_RIGHT, KERuleRole.LEFT)

			));

	static final SignConnectiveRoleSubformulaPattern pattern_X_AND_F_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.AND, ClassicalSigns.FALSE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_AND_F_LEFT = new TwoPremisesOneConclusionRule(
			"X_AND_F_LEFT", pattern_X_AND_F_LEFT,

			new KEAction(ActionType.ADD_NODE,

			new SimpleSubformulaGetter(pattern_X_AND_F_LEFT, ClassicalConnectives.BOTTOM)

			));

	static final SignConnectiveRoleSubformulaPattern pattern_X_AND_F_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.AND, ClassicalSigns.FALSE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_AND_F_RIGHT = new TwoPremisesOneConclusionRule(
			"X_AND_F_RIGHT", pattern_X_AND_F_RIGHT,

			new KEAction(ActionType.ADD_NODE,

			new SimpleSubformulaGetter(pattern_X_AND_F_RIGHT, ClassicalConnectives.BOTTOM)

			));

	// rules with or

	public static final OnePremissTwoConclusionsRule F_OR = new OnePremissTwoConclusionsRule("F_OR",
			new SignConnectivePattern(ClassicalSigns.FALSE, ClassicalConnectives.OR), new KEAction(
					ActionType.ADD_NODE, BinaryConnectiveGetter.FALSE_LEFT), new KEAction(
					ActionType.ADD_NODE, BinaryConnectiveGetter.FALSE_RIGHT));

	public static final TwoPremisesOneConclusionRule T_OR_LEFT = new TwoPremisesOneConclusionRule(
			"T_OR_LEFT",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.OR,
					ClassicalSigns.FALSE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	static final SignConnectiveRoleSubformulaPattern pattern_X_OR_T_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.OR, ClassicalSigns.TRUE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_OR_T_LEFT = new TwoPremisesOneConclusionRule(
			"X_OR_T_LEFT", pattern_X_OR_T_LEFT,

			new KEAction(ActionType.ADD_NODE, new SimpleSubformulaGetter(pattern_X_OR_T_LEFT,
					ClassicalConnectives.TOP)

			));

	static final SignConnectiveRoleSubformulaPattern pattern_X_OR_T_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.OR, ClassicalSigns.TRUE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_OR_T_RIGHT = new TwoPremisesOneConclusionRule(
			"X_OR_T_RIGHT", pattern_X_OR_T_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SimpleSubformulaGetter(pattern_X_OR_T_RIGHT,
					ClassicalConnectives.TOP)

			));

	static final SignConnectiveRoleSubformulaPattern pattern_X_OR_F_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.OR, ClassicalSigns.FALSE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_OR_F_LEFT = new TwoPremisesOneConclusionRule(
			"X_OR_F_LEFT", pattern_X_OR_F_LEFT,

			new KEAction(ActionType.ADD_NODE,

			new SubformulaRoleGetter(pattern_X_OR_F_LEFT, KERuleRole.RIGHT)

			));

	static final SignConnectiveRoleSubformulaPattern pattern_X_OR_F_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.OR, ClassicalSigns.FALSE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_OR_F_RIGHT = new TwoPremisesOneConclusionRule(
			"X_OR_F_RIGHT", pattern_X_OR_F_RIGHT,

			new KEAction(ActionType.ADD_NODE,

			new SubformulaRoleGetter(pattern_X_OR_F_RIGHT, KERuleRole.LEFT)

			));

	// rules with implies
	public static final OnePremissTwoConclusionsRule F_IMPLIES = new OnePremissTwoConclusionsRule(
			"F_IMPLIES", new SignConnectivePattern(ClassicalSigns.FALSE, ClassicalConnectives.IMPLIES),
			new KEAction(ActionType.ADD_NODE, BinaryConnectiveGetter.TRUE_LEFT), new KEAction(
					ActionType.ADD_NODE, BinaryConnectiveGetter.FALSE_RIGHT));

	public static final TwoPremisesOneConclusionRule T_IMPLIES_LEFT = new TwoPremisesOneConclusionRule(
			"T_IMPLIES_LEFT",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
					ClassicalSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	static final SignConnectiveRoleSubformulaPattern pattern_X_IMPLIES_T_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.IMPLIES, ClassicalSigns.TRUE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_IMPLIES_T_LEFT = new TwoPremisesOneConclusionRule(
			"X_IMPLIES_T_LEFT", pattern_X_IMPLIES_T_LEFT,

			new KEAction(ActionType.ADD_NODE, new SubformulaRoleGetter(pattern_X_IMPLIES_T_LEFT,
					KERuleRole.RIGHT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_IMPLIES_T_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.IMPLIES, ClassicalSigns.TRUE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_IMPLIES_T_RIGHT = new TwoPremisesOneConclusionRule(
			"X_IMPLIES_T_RIGHT", pattern_X_IMPLIES_T_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SimpleSubformulaGetter(pattern_X_IMPLIES_T_RIGHT,
					ClassicalConnectives.TOP))

	);

	static final SignConnectiveRoleSubformulaPattern pattern_X_IMPLIES_F_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.IMPLIES, ClassicalSigns.FALSE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_IMPLIES_F_LEFT = new TwoPremisesOneConclusionRule(
			"X_IMPLIES_F_LEFT", pattern_X_IMPLIES_F_LEFT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_IMPLIES_F_LEFT, ClassicalConnectives.TOP))

	);

	static final SignConnectiveRoleSubformulaPattern pattern_X_IMPLIES_F_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.IMPLIES, ClassicalSigns.FALSE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_IMPLIES_F_RIGHT = new TwoPremisesOneConclusionRule(
			"X_IMPLIES_F_RIGHT", pattern_X_IMPLIES_F_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SubformulaConnectiveRoleGetter(
					pattern_X_IMPLIES_F_RIGHT, ClassicalConnectives.NOT, KERuleRole.LEFT)));

	// rules with biimplies

	public static final TwoPremisesOneConclusionRule T_BIIMPLIES_LEFT_TRUE = new TwoPremisesOneConclusionRule(
			"T_BIIMPLIES_LEFT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static final TwoPremisesOneConclusionRule F_BIIMPLIES_LEFT_TRUE = new TwoPremisesOneConclusionRule(
			"F_BIIMPLIES_LEFT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule T_BIIMPLIES_LEFT_FALSE = new TwoPremisesOneConclusionRule(
			"T_BIIMPLIES_LEFT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.FALSE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule F_BIIMPLIES_LEFT_FALSE = new TwoPremisesOneConclusionRule(
			"F_BIIMPLIES_LEFT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.FALSE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	static final SignConnectiveRoleSubformulaPattern pattern_X_BIIMPLIES_T_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.BIIMPLIES, ClassicalSigns.TRUE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_BIIMPLIES_T_LEFT = new TwoPremisesOneConclusionRule(
			"X_BIIMPLIES_T_LEFT", pattern_X_BIIMPLIES_T_LEFT,

			new KEAction(ActionType.ADD_NODE, new SubformulaRoleGetter(pattern_X_BIIMPLIES_T_LEFT,
					KERuleRole.RIGHT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_BIIMPLIES_T_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.BIIMPLIES, ClassicalSigns.TRUE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_BIIMPLIES_T_RIGHT = new TwoPremisesOneConclusionRule(
			"X_BIIMPLIES_T_RIGHT", pattern_X_BIIMPLIES_T_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SubformulaRoleGetter(pattern_X_BIIMPLIES_T_RIGHT,
					KERuleRole.LEFT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_BIIMPLIES_F_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.BIIMPLIES, ClassicalSigns.FALSE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_BIIMPLIES_F_LEFT = new TwoPremisesOneConclusionRule(
			"X_BIIMPLIES_F_LEFT", pattern_X_BIIMPLIES_F_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaConnectiveRoleGetter(pattern_X_BIIMPLIES_F_LEFT, ClassicalConnectives.NOT,
							KERuleRole.RIGHT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_BIIMPLIES_F_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.BIIMPLIES, ClassicalSigns.FALSE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_BIIMPLIES_F_RIGHT = new TwoPremisesOneConclusionRule(
			"X_BIIMPLIES_F_RIGHT", pattern_X_BIIMPLIES_F_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SubformulaConnectiveRoleGetter(
					pattern_X_BIIMPLIES_F_RIGHT, ClassicalConnectives.NOT, KERuleRole.LEFT)));

	// rules with XOR

	public static final TwoPremisesOneConclusionRule T_XOR_LEFT_TRUE = new TwoPremisesOneConclusionRule(
			"T_XOR_LEFT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
					ClassicalSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule F_XOR_LEFT_TRUE = new TwoPremisesOneConclusionRule(
			"F_XOR_LEFT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
					ClassicalSigns.TRUE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static final TwoPremisesOneConclusionRule T_XOR_LEFT_FALSE = new TwoPremisesOneConclusionRule(
			"T_XOR_LEFT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
					ClassicalSigns.FALSE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static final TwoPremisesOneConclusionRule F_XOR_LEFT_FALSE = new TwoPremisesOneConclusionRule(
			"F_XOR_LEFT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
					ClassicalSigns.FALSE, KERuleRole.LEFT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule T_XOR_RIGHT_TRUE = new TwoPremisesOneConclusionRule(
			"T_XOR_RIGHT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
					ClassicalSigns.TRUE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule F_XOR_RIGHT_TRUE = new TwoPremisesOneConclusionRule(
			"F_XOR_RIGHT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
					ClassicalSigns.TRUE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static final TwoPremisesOneConclusionRule T_XOR_RIGHT_FALSE = new TwoPremisesOneConclusionRule(
			"T_XOR_RIGHT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
					ClassicalSigns.FALSE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static final TwoPremisesOneConclusionRule F_XOR_RIGHT_FALSE = new TwoPremisesOneConclusionRule(
			"F_XOR_RIGHT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
					ClassicalSigns.FALSE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	// Xor simplification rules
	static final SignConnectiveRoleSubformulaPattern pattern_X_XOR_F_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.XOR, ClassicalSigns.FALSE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_XOR_F_LEFT = new TwoPremisesOneConclusionRule(
			"X_XOR_F_LEFT", pattern_X_XOR_F_LEFT,

			new KEAction(ActionType.ADD_NODE, new SubformulaRoleGetter(pattern_X_XOR_F_LEFT,
					KERuleRole.RIGHT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_XOR_F_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.XOR, ClassicalSigns.FALSE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_XOR_F_RIGHT = new TwoPremisesOneConclusionRule(
			"X_XOR_F_RIGHT", pattern_X_XOR_F_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SubformulaRoleGetter(pattern_X_XOR_F_RIGHT,
					KERuleRole.LEFT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_XOR_T_LEFT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.XOR, ClassicalSigns.TRUE, KERuleRole.LEFT);

	public static final TwoPremisesOneConclusionRule X_XOR_T_LEFT = new TwoPremisesOneConclusionRule(
			"X_XOR_T_LEFT", pattern_X_XOR_T_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaConnectiveRoleGetter(pattern_X_XOR_T_LEFT, ClassicalConnectives.NOT,
							KERuleRole.RIGHT)));

	static final SignConnectiveRoleSubformulaPattern pattern_X_XOR_T_RIGHT = new SignConnectiveRoleSubformulaPattern(
			ClassicalConnectives.XOR, ClassicalSigns.TRUE, KERuleRole.RIGHT);

	public static final TwoPremisesOneConclusionRule X_XOR_T_RIGHT = new TwoPremisesOneConclusionRule(
			"X_XOR_T_RIGHT", pattern_X_XOR_T_RIGHT,

			new KEAction(ActionType.ADD_NODE, new SubformulaConnectiveRoleGetter(pattern_X_XOR_T_RIGHT,
					ClassicalConnectives.NOT, KERuleRole.LEFT)));

	// rules with top

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_AND_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.LEFT, ClassicalConnectives.AND);

	public static final OnePremiseOneConclusionRule X_TOP_AND_LEFT = new OnePremiseOneConclusionRule(
			"X_TOP_AND_LEFT", pattern_X_TOP_AND_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_TOP_AND_LEFT, KERuleRole.RIGHT)

			));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_AND_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.RIGHT, ClassicalConnectives.AND);

	public static final OnePremiseOneConclusionRule X_TOP_AND_RIGHT = new OnePremiseOneConclusionRule(
			"X_TOP_AND_RIGHT", pattern_X_TOP_AND_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_TOP_AND_RIGHT, KERuleRole.LEFT)

			));

	// ////

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_OR_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.LEFT, ClassicalConnectives.OR);

	public static final OnePremiseOneConclusionRule X_TOP_OR_LEFT = new OnePremiseOneConclusionRule(
			"X_TOP_OR_LEFT", pattern_X_TOP_OR_LEFT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_TOP_OR_LEFT, ClassicalConnectives.TOP)));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_OR_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.RIGHT, ClassicalConnectives.OR);

	public static final OnePremiseOneConclusionRule X_TOP_OR_RIGHT = new OnePremiseOneConclusionRule(
			"X_TOP_OR_RIGHT", pattern_X_TOP_OR_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_TOP_OR_RIGHT, ClassicalConnectives.TOP)));

	// ////

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_IMPLIES_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.LEFT, ClassicalConnectives.IMPLIES);

	public static final OnePremiseOneConclusionRule X_TOP_IMPLIES_LEFT = new OnePremiseOneConclusionRule(
			"X_TOP_IMPLIES_LEFT", pattern_X_TOP_IMPLIES_LEFT, new KEAction(

			ActionType.ADD_NODE, new SubformulaRoleGetter(pattern_X_TOP_IMPLIES_LEFT, KERuleRole.RIGHT)

			));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_IMPLIES_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.RIGHT, ClassicalConnectives.IMPLIES);

	public static final OnePremiseOneConclusionRule X_TOP_IMPLIES_RIGHT = new OnePremiseOneConclusionRule(
			"X_TOP_IMPLIES_RIGHT", pattern_X_TOP_IMPLIES_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_TOP_IMPLIES_RIGHT, ClassicalConnectives.TOP)));

	// //
	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_BIIMPLIES_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.LEFT, ClassicalConnectives.BIIMPLIES);

	public static final OnePremiseOneConclusionRule X_TOP_BIIMPLIES_LEFT = new OnePremiseOneConclusionRule(
			"X_TOP_BIIMPLIES_LEFT", pattern_X_TOP_BIIMPLIES_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_TOP_BIIMPLIES_LEFT, KERuleRole.RIGHT)));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_BIIMPLIES_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.RIGHT, ClassicalConnectives.BIIMPLIES);

	public static final OnePremiseOneConclusionRule X_TOP_BIIMPLIES_RIGHT = new OnePremiseOneConclusionRule(
			"X_TOP_BIIMPLIES_RIGHT", pattern_X_TOP_BIIMPLIES_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_TOP_BIIMPLIES_RIGHT, KERuleRole.LEFT)));

	// //
	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_XOR_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.LEFT, ClassicalConnectives.XOR);

	public static final OnePremiseOneConclusionRule X_TOP_XOR_LEFT = new OnePremiseOneConclusionRule(
			"X_TOP_XOR_LEFT", pattern_X_TOP_XOR_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaConnectiveRoleGetter(pattern_X_TOP_XOR_LEFT, ClassicalConnectives.NOT,
							KERuleRole.RIGHT)

			));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_XOR_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.RIGHT, ClassicalConnectives.XOR);

	public static final OnePremiseOneConclusionRule X_TOP_XOR_RIGHT = new OnePremiseOneConclusionRule(
			"X_TOP_XOR_RIGHT", pattern_X_TOP_XOR_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SubformulaConnectiveRoleGetter(pattern_X_TOP_XOR_RIGHT, ClassicalConnectives.NOT,
							KERuleRole.LEFT)

			));

	// //

	static final TwoConnectivesRoleSubformulaPattern pattern_X_TOP_NOT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.TOP, KERuleRole.LEFT, ClassicalConnectives.NOT);

	public static final OnePremiseOneConclusionRule X_TOP_NOT = new OnePremiseOneConclusionRule(
			"X_TOP_NOT", pattern_X_TOP_NOT, new KEAction(ActionType.ADD_NODE, new SimpleSubformulaGetter(
					pattern_X_TOP_NOT, ClassicalConnectives.BOTTOM)

			));

	// rules with bottom

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_OR_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.LEFT, ClassicalConnectives.OR);

	public static final OnePremiseOneConclusionRule X_BOTTOM_OR_LEFT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_OR_LEFT", pattern_X_BOTTOM_OR_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_BOTTOM_OR_LEFT, KERuleRole.RIGHT)

			));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_OR_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.RIGHT, ClassicalConnectives.OR);

	public static final OnePremiseOneConclusionRule X_BOTTOM_OR_RIGHT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_OR_RIGHT", pattern_X_BOTTOM_OR_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_BOTTOM_OR_RIGHT, KERuleRole.LEFT)

			));

	// ////

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_AND_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.LEFT, ClassicalConnectives.AND);

	public static final OnePremiseOneConclusionRule X_BOTTOM_AND_LEFT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_AND_LEFT", pattern_X_BOTTOM_AND_LEFT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_BOTTOM_AND_LEFT, ClassicalConnectives.BOTTOM)));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_AND_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.RIGHT, ClassicalConnectives.AND);

	public static final OnePremiseOneConclusionRule X_BOTTOM_AND_RIGHT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_AND_RIGHT", pattern_X_BOTTOM_AND_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_BOTTOM_AND_RIGHT, ClassicalConnectives.BOTTOM)));

	// ////

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_IMPLIES_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.LEFT, ClassicalConnectives.IMPLIES);

	public static final OnePremiseOneConclusionRule X_BOTTOM_IMPLIES_LEFT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_IMPLIES_LEFT", pattern_X_BOTTOM_IMPLIES_LEFT, new KEAction(

			ActionType.ADD_NODE, new SimpleSubformulaGetter(pattern_X_BOTTOM_IMPLIES_LEFT,
					ClassicalConnectives.TOP)));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_IMPLIES_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.RIGHT, ClassicalConnectives.IMPLIES);

	public static final OnePremiseOneConclusionRule X_BOTTOM_IMPLIES_RIGHT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_IMPLIES_RIGHT", pattern_X_BOTTOM_IMPLIES_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SubformulaConnectiveRoleGetter(pattern_X_BOTTOM_IMPLIES_RIGHT,
							ClassicalConnectives.NOT, KERuleRole.LEFT)

			));

	// ////

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_BIIMPLIES_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.LEFT, ClassicalConnectives.BIIMPLIES);

	public static final OnePremiseOneConclusionRule X_BOTTOM_BIIMPLIES_LEFT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_BIIMPLIES_LEFT", pattern_X_BOTTOM_BIIMPLIES_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaConnectiveRoleGetter(pattern_X_BOTTOM_BIIMPLIES_LEFT,
							ClassicalConnectives.NOT, KERuleRole.RIGHT)

			));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_BIIMPLIES_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.RIGHT, ClassicalConnectives.BIIMPLIES);

	public static final OnePremiseOneConclusionRule X_BOTTOM_BIIMPLIES_RIGHT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_BIIMPLIES_RIGHT", pattern_X_BOTTOM_BIIMPLIES_RIGHT, new KEAction(
					ActionType.ADD_NODE, new SubformulaConnectiveRoleGetter(pattern_X_BOTTOM_BIIMPLIES_RIGHT,
							ClassicalConnectives.NOT, KERuleRole.LEFT)

			));

	// //
	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_XOR_LEFT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.LEFT, ClassicalConnectives.XOR);

	public static final OnePremiseOneConclusionRule X_BOTTOM_XOR_LEFT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_XOR_LEFT", pattern_X_BOTTOM_XOR_LEFT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_BOTTOM_XOR_LEFT, KERuleRole.RIGHT)));

	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_XOR_RIGHT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.RIGHT, ClassicalConnectives.XOR);

	public static final OnePremiseOneConclusionRule X_BOTTOM_XOR_RIGHT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_XOR_RIGHT", pattern_X_BOTTOM_XOR_RIGHT, new KEAction(ActionType.ADD_NODE,
					new SubformulaRoleGetter(pattern_X_BOTTOM_XOR_RIGHT, KERuleRole.LEFT)));

	// //
	static final TwoConnectivesRoleSubformulaPattern pattern_X_BOTTOM_NOT = new TwoConnectivesRoleSubformulaPattern(
			ClassicalConnectives.BOTTOM, KERuleRole.LEFT, ClassicalConnectives.NOT);

	public static final OnePremiseOneConclusionRule X_BOTTOM_NOT = new OnePremiseOneConclusionRule(
			"X_BOTTOM_NOT", pattern_X_BOTTOM_NOT, new KEAction(ActionType.ADD_NODE,
					new SimpleSubformulaGetter(pattern_X_BOTTOM_NOT, ClassicalConnectives.TOP)

			));

	// / additional rules for configurable

	public static TwoPremisesOneConclusionRule F_AND_RIGHT = new TwoPremisesOneConclusionRule(
			"F_AND_RIGHT",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.AND,
					ClassicalSigns.TRUE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static TwoPremisesOneConclusionRule T_OR_RIGHT = new TwoPremisesOneConclusionRule(
			"T_OR_RIGHT",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.OR,
					ClassicalSigns.FALSE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static TwoPremisesOneConclusionRule T_IMPLIES_RIGHT = new TwoPremisesOneConclusionRule(
			"T_IMPLIES_RIGHT",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
					ClassicalSigns.FALSE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	// rules with biimplies

	public static final TwoPremisesOneConclusionRule T_BIIMPLIES_RIGHT_TRUE = new TwoPremisesOneConclusionRule(
			"T_BIIMPLIES_RIGHT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.TRUE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

	public static final TwoPremisesOneConclusionRule F_BIIMPLIES_RIGHT_TRUE = new TwoPremisesOneConclusionRule(
			"F_BIIMPLIES_RIGHT_TRUE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.TRUE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule T_BIIMPLIES_RIGHT_FALSE = new TwoPremisesOneConclusionRule(
			"T_BIIMPLIES_RIGHT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.FALSE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.FALSE_OTHER));

	public static final TwoPremisesOneConclusionRule F_BIIMPLIES_RIGHT_FALSE = new TwoPremisesOneConclusionRule(
			"F_BIIMPLIES_RIGHT_FALSE",

			new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
					ClassicalSigns.FALSE, KERuleRole.RIGHT), new KEAction(ActionType.ADD_NODE,
					BinaryTwoPremisesConnectiveGetter.TRUE_OTHER));

}