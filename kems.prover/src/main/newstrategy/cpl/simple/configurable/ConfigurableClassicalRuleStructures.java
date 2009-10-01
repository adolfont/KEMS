/*
 * Created on 14/12/2004
 *
 */
package main.newstrategy.cpl.simple.configurable;

import logic.formulas.Connective;
import logic.logicalSystem.ISignature;
import logic.signedFormulas.FormulaSign;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalRuleStructures;
import logicalSystems.classicalLogic.ClassicalRules;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.KERuleRole;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;
import rules.structures.RuleType;

/**
 * Structures of rules for Classical Logic KE without substitution.
 * It uses two-premise simple rules that were not used in the ClassicalRuleStructures class
 * because these rules were sbsumed by substitution rules.  
 * 
 * @author Adolfo Gustavo Serra Seaca NEto
 *  
 */

public class ConfigurableClassicalRuleStructures extends
        ClassicalRuleStructures {

    public static final String SECOND_TWO_PREMISE_RULE_LIST = "secondTwoPremiseRules";

    private ConfigurableConnectiveRoleSignRuleList secondTwoPremiseRuleList;

    public ConfigurableClassicalRuleStructures(ISignature signature) {
        super(signature);
        secondTwoPremiseRuleList = new ConfigurableConnectiveRoleSignRuleList();
        initializeTwoPremiseRuleList();
        getRuleStructure().add(TWO_PREMISE_RULE_LIST, getTwoPremiseRules());

        getRuleStructure().add(SECOND_TWO_PREMISE_RULE_LIST,
                secondTwoPremiseRuleList);
    }

    private ConnectiveRoleSignRuleList initializeTwoPremiseRuleList() {
        setTwoPremiseRules(new ConnectiveRoleSignRuleList());

        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.F_AND_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.F_AND_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.T_OR_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.T_OR_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.T_IMPLIES_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.T_IMPLIES_RIGHT);

        // Rules with BIIMPLIES and XOR
        // TODO: Talvez fosse melhor generalizar a estrutura que guarda as two-premise rules
        // para que para um trio <Conn, role, sign> retornasse zero ou mais regras

        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.T_BIIMPLIES_LEFT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.F_BIIMPLIES_LEFT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.F_BIIMPLIES_LEFT_FALSE);
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.T_BIIMPLIES_RIGHT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.T_BIIMPLIES_RIGHT_FALSE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.F_BIIMPLIES_RIGHT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.F_BIIMPLIES_RIGHT_FALSE);

        
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.XOR, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.T_XOR_LEFT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.XOR, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.T_XOR_LEFT_FALSE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.XOR, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.F_XOR_LEFT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.XOR, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.F_XOR_LEFT_FALSE);
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.XOR, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.T_XOR_RIGHT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.TRUE,
                ClassicalConnectives.XOR, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.T_XOR_RIGHT_FALSE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.XOR, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.F_XOR_RIGHT_TRUE);
        addToSecondTwoPremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.XOR, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.F_XOR_RIGHT_FALSE);

        return getTwoPremiseRules();
    }

    protected void addToTwoPremiseRules(Connective conn, KERuleRole role,
            FormulaSign sign, TwoPremisesOneConclusionRule r) {
        if (getSignature().contains(conn)) {
            addConnectiveRuleType(conn, r, RuleType.SIMPLE_2P);
            getTwoPremiseRules().add(conn, role, sign, r);
        }
    }

    
    /**
     * @param true1
     * @param biimplies
     * @param left
     * @param true2
     * @param t_biimplies_left_true
     */
    private void addToSecondTwoPremiseRules(FormulaSign sign1,
            Connective connective, KERuleRole role, FormulaSign sign2,
            TwoPremisesOneConclusionRule rule) {
        if (getSignature().contains(connective)) {
            secondTwoPremiseRuleList.add(connective, role, sign1, sign2, rule);
            addConnectiveRuleType(connective, rule, RuleType.SIMPLE_2P);
        }
    }

    /**
     * @param sign
     * @param conn
     * @param role
     * @param rule
     */
    //	private void addToTwoPremiseRules(FormulaSign sign, Connective conn,
    // KERuleRole role, TwoPremisesOneConclusionRule rule) {
    //        if (getSignature().contains(conn)) {
    //            addConnectiveRuleType(conn, rule, RuleType.SIMPLE_2P);
    //            twoPremiseRules.add(sign, conn, rule);
    //        }
    //	}
    //    private void addToTwoPremiseRules(FormulaSign sign, Connective conn,
    // KERuleRole role,
    //            TwoPremisesOneConclusionRule r1, TwoPremisesOneConclusionRule r2) {
    //        if (getSignature().contains(conn)) {
    //            addConnectiveRuleType(conn, r1, RuleType.SUBSTITUTION_2P);
    //            addConnectiveRuleType(conn, r2, RuleType.SUBSTITUTION_2P);
    //            twoPremiseRules.add(sign, conn, r1, r2);
    //        }
    //    }

    //    public RulesStructure getRuleStructure() {
    //        return _rules;
    //    }
    //
    //    public RuleList getRules(Connective conn) {
    //        return crsf.createCRS(conn).getRules();
    //    }
    //    /**
    //     * @return
    //     */
    //    private PBRuleList initializePBRuleList() {
    //        PBRules = new PBRuleList();
    //
    //        addToPBRules(ClassicalSigns.FALSE, ClassicalConnectives.AND,
    //                ClassicalRules.F_AND_LEFT);
    //        addToPBRules(ClassicalSigns.TRUE, ClassicalConnectives.OR,
    //                ClassicalRules.T_OR_LEFT);
    //        addToPBRules(ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
    //                ClassicalRules.T_IMPLIES_LEFT);
    //
    //        /** rules for applying PB - with biimplication */
    //        addToPBRules(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
    //                ClassicalRules.T_BIIMPLIES_LEFT_TRUE,
    //                ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
    //        addToPBRules(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
    //                ClassicalRules.F_BIIMPLIES_LEFT_TRUE,
    //                ClassicalRules.F_BIIMPLIES_LEFT_FALSE);
    //
    //        /** rules for applying PB - with XOR */
    //        PBRules
    //                .add(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
    //                        ClassicalRules.T_XOR_LEFT_TRUE,
    //                        ClassicalRules.T_XOR_LEFT_FALSE);
    //        PBRules
    //                .add(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
    //                        ClassicalRules.F_XOR_LEFT_TRUE,
    //                        ClassicalRules.F_XOR_LEFT_FALSE);
    //        return PBRules;
    //    }
    //
    //    /**
    //     * @return
    //     */
    //    private ConnectiveRoleSignRuleList initializeTwoPremiseRuleList() {
    //        twoPremiseRulesNew = new ConnectiveRoleSignRuleList();
    //
    //        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_IMPLIES_F_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_IMPLIES_F_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_IMPLIES_T_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_IMPLIES_T_RIGHT);
    //
    //        addToTwoPremiseRules(ClassicalConnectives.NOT, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_NOT_F);
    //        addToTwoPremiseRules(ClassicalConnectives.NOT, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_NOT_T);
    //
    //        /** Two premise substitution rules with biimplication */
    //        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_BIIMPLIES_F_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_BIIMPLIES_F_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_BIIMPLIES_T_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_BIIMPLIES_T_RIGHT);
    //
    //        /** Two premise substitution rules with XOR */
    //
    //        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_XOR_F_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_XOR_F_RIGHT);
    //        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_XOR_T_LEFT);
    //        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_XOR_T_RIGHT);
    //        return twoPremiseRulesNew;
    //    }
    //
    //    /**
    //     * @return
    //     */
    //    private TopBottomRoleRuleList initializeTBRuleList() {
    //        topAndBottomRulesNew = new TopBottomRoleRuleList();
    //
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.AND,
    //                KERuleRole.LEFT, ClassicalRules.X_TOP_AND_LEFT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.AND,
    //                KERuleRole.RIGHT, ClassicalRules.X_TOP_AND_RIGHT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.OR,
    //                KERuleRole.LEFT, ClassicalRules.X_TOP_OR_LEFT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.OR,
    //                KERuleRole.RIGHT, ClassicalRules.X_TOP_OR_RIGHT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.IMPLIES,
    //                KERuleRole.LEFT, ClassicalRules.X_TOP_IMPLIES_LEFT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.IMPLIES,
    //                KERuleRole.RIGHT, ClassicalRules.X_TOP_IMPLIES_RIGHT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.NOT,
    //                KERuleRole.LEFT, ClassicalRules.X_TOP_NOT);
    //
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.AND,
    //                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_AND_LEFT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.AND,
    //                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_AND_RIGHT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.OR,
    //                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_OR_LEFT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.OR,
    //                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_OR_RIGHT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.IMPLIES,
    //                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_IMPLIES_LEFT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.IMPLIES,
    //                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_IMPLIES_RIGHT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.NOT,
    //                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_NOT);
    //
    //        /** One premise rules with biimplication */
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.BIIMPLIES,
    //                KERuleRole.LEFT, ClassicalRules.X_TOP_BIIMPLIES_LEFT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.BIIMPLIES,
    //                KERuleRole.RIGHT, ClassicalRules.X_TOP_BIIMPLIES_RIGHT);
    //
    //        addToTBRules(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                ClassicalRules.X_BOTTOM_BIIMPLIES_LEFT);
    //        addToTBRules(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                ClassicalRules.X_BOTTOM_BIIMPLIES_RIGHT);
    //
    //        /** One premise rules with XOR */
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.XOR,
    //                KERuleRole.LEFT, ClassicalRules.X_TOP_XOR_LEFT);
    //        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.XOR,
    //                KERuleRole.RIGHT, ClassicalRules.X_TOP_XOR_RIGHT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.XOR,
    //                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_XOR_LEFT);
    //        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.XOR,
    //                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_XOR_RIGHT);
    //        return topAndBottomRulesNew;
    //    }
    //
    //    /**
    //     * @return
    //     */
    //    private OnePremiseRuleList initializeOnePremiseRuleList() {
    //        onePremiseRules = new OnePremiseRuleList();
    //
    //        addToOnePremiseRules(ClassicalSigns.TRUE, ClassicalConnectives.NOT,
    //                ClassicalRules.T_NOT);
    //        addToOnePremiseRules(ClassicalSigns.FALSE, ClassicalConnectives.NOT,
    //                ClassicalRules.F_NOT);
    //        addToOnePremiseRules(ClassicalSigns.TRUE, ClassicalConnectives.AND,
    //                ClassicalRules.T_AND);
    //        addToOnePremiseRules(ClassicalSigns.FALSE, ClassicalConnectives.OR,
    //                ClassicalRules.F_OR);
    //        addToOnePremiseRules(ClassicalSigns.FALSE,
    //                ClassicalConnectives.IMPLIES, ClassicalRules.F_IMPLIES);
    //        return onePremiseRules;
    //    }
    //
    //    private void addToPBRules(FormulaSign sign, Connective conn, Rule r1) {
    //        if (signature.contains(conn)) {
    //            addConnectiveRuleType(conn, r1, RuleType.PB);
    //            PBRules.add(sign, conn, r1);
    //        }
    //    }
    //
    //    private void addToPBRules(FormulaSign sign, Connective conn, Rule r1,
    //            Rule r2) {
    //        if (signature.contains(conn)) {
    //            addConnectiveRuleType(conn, r1, RuleType.PB);
    //            addConnectiveRuleType(conn, r2, RuleType.PB);
    //            PBRules.add(sign, conn, r1, r2);
    //        }
    //    }
    //
    //    private void addToTwoPremiseRules(Connective conn, KERuleRole role,
    //            FormulaSign sign, TwoPremisesOneConclusionRule r) {
    //        if (signature.contains(conn)) {
    //            addConnectiveRuleType(conn, r, RuleType.SUBSTITUTION_2P);
    //            twoPremiseRulesNew.add(conn, role, sign, r);
    //        }
    //    }
    //
    //    private void addToTBRules(Connective conn1, Connective conn2,
    //            KERuleRole role, OnePremiseOneConclusionRule r) {
    //        if (signature.contains(conn1) && signature.contains(conn2)) {
    //            addConnectiveRuleType(conn1, r, RuleType.SUBSTITUTION_1P);
    //            addConnectiveRuleType(conn2, r, RuleType.SUBSTITUTION_1P);
    //            topAndBottomRulesNew.add(conn1, conn2, role, r);
    //        }
    //    }
    //
    //    private void addToOnePremiseRules(FormulaSign sign, Connective conn, Rule
    // r) {
    //        if (signature.contains(conn)) {
    //            addConnectiveRuleType(conn, r, RuleType.SIMPLE_1P);
    //            onePremiseRules.add(sign, conn, r);
    //        }
    //    }
    //
    //    private void addConnectiveRuleType(Connective conn, Rule r, RuleType rt)
    // {
    //        crsf.createCRS(conn).add(r, rt);
    //    }

}