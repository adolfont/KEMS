/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.mCi;

import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCRules;
import rules.ActionType;
import rules.KEAction;
import rules.OnePremiseOneConclusionRule;
import rules.OnePremissTwoConclusionsRule;
import rules.getters.TrueBottomGetter;
import rules.getters.UnaryConnectiveConnectiveConnectiveGetter;
import rules.getters.UnaryConnectiveConnectiveGetter;
import rules.patterns.SignConnectiveConnectivePattern;
import rules.patterns.VerySpecialMCIRulePattern;

/**
 * Rules for mCi. The rules are public static members for efficiency
 * reasons. The releaseMemory() method allows us to save some memory when
 * necessary.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MCIRules extends MBCRules{


	
	// T *A = T !@A
	//        -----
	//        T   A
	//        T  !A
    public static OnePremissTwoConclusionsRule T_NOT_CONS = new OnePremissTwoConclusionsRule(
            "T_NOT_CONS",
            new SignConnectiveConnectivePattern(ClassicalSigns.TRUE,
                    ClassicalConnectives.NOT, MBCConnectives.CONSISTENCY),
            new KEAction(ActionType.ADD_NODE, UnaryConnectiveConnectiveGetter.TRUE),
            new KEAction(ActionType.ADD_NODE, UnaryConnectiveConnectiveConnectiveGetter.TRUE_NOT));

	// F @ (!^n) @ A
	// ---------------
	//        x
    public static OnePremiseOneConclusionRule F_CONS = new OnePremiseOneConclusionRule(
            "F_CONS",
            new VerySpecialMCIRulePattern(ClassicalSigns.FALSE,
            		MBCConnectives.CONSISTENCY, MBCConnectives.NOT),
            new KEAction(ActionType.ADD_NODE, TrueBottomGetter.INSTANCE));
// first version
    //    public static OnePremiseOneConclusionRule F_CONS = new OnePremiseOneConclusionRule(
//            "F_CONS",
//            new VerySpecialMCIRulePattern(ClassicalSigns.FALSE,
//            		MBCConnectives.CONSISTENCY, MBCConnectives.NOT),
//            new KEAction(ActionType.ADD_NODE, FullFormulaGetter.TRUE));
    
    
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
		
		T_NOT_CONS=null;
		F_CONS = null;
	}

}
