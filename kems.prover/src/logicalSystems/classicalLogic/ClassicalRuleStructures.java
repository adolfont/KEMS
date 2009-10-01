/*
 * Created on 14/12/2004
 *
 */
package logicalSystems.classicalLogic;

import logic.formulas.Connective;
import logic.logicalSystem.ISignature;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.OnePremiseOneConclusionRule;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;
import rules.structures.ConnectiveRuleStructureFactory;
import rules.structures.OnePremiseRuleList;
import rules.structures.PBRuleList;
import rules.structures.RuleList;
import rules.structures.RuleType;
import rules.structures.RulesStructure;
import rules.structures.TopBottomRoleRuleList;

/**
 * Structures of rules for Classical Logic KE with substitution
 * 
 * @author Adolfo Gustavo Serra Seaca NEto
 *  
 */

public class ClassicalRuleStructures {

    private ISignature signature;

    private RulesStructure _rules;

    private OnePremiseRuleList onePremiseRules;

    private TopBottomRoleRuleList topAndBottomRulesNew;

    private ConnectiveRoleSignRuleList twoPremiseRules;

    private PBRuleList PBRules;

    private ConnectiveRuleStructureFactory crsf = new ConnectiveRuleStructureFactory();

    public static final String ONE_PREMISE_RULE_LIST = "onePremiseRules";

    public static final String TOP_BOTTOM_ONE_PREMISE_RULE_LIST = "topAndBottomRulesNew";

    public static final String TWO_PREMISE_RULE_LIST = "twoPremiseRulesNewII";

    public static final String PB_RULE_LIST = "PBRules";

    public ClassicalRuleStructures(ISignature signature) {
        this.signature = signature;
        /** one premise rules */
        onePremiseRules = initializeOnePremiseRuleList();
        /** one premise simplification rules */
        topAndBottomRulesNew = initializeTBRuleList();
        /** Two premise substitution rules */
        twoPremiseRules = initializeTwoPremiseRuleList();
        /** rules for applying PB */
        PBRules = initializePBRuleList();
        /** Order od the sets of rules */
        // the order is important!
        _rules = new RulesStructure();
        _rules.add(ONE_PREMISE_RULE_LIST, onePremiseRules);
        _rules.add(TOP_BOTTOM_ONE_PREMISE_RULE_LIST, topAndBottomRulesNew);
        _rules.add(TWO_PREMISE_RULE_LIST, twoPremiseRules);
        _rules.add(PB_RULE_LIST, PBRules);
    }

    public RulesStructure getRuleStructure() {
        return _rules;
    }

    public RuleList getRules(Connective conn) {
        return crsf.createCRS(conn).getRules();
    }

    /**
     * @return
     */
    private PBRuleList initializePBRuleList() {
        PBRules = new PBRuleList();

        addToPBRules(ClassicalSigns.FALSE, ClassicalConnectives.AND,
                ClassicalRules.F_AND_LEFT);
        addToPBRules(ClassicalSigns.TRUE, ClassicalConnectives.OR,
                ClassicalRules.T_OR_LEFT);
        addToPBRules(ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
                ClassicalRules.T_IMPLIES_LEFT);

        /** rules for applying PB - with biimplication */
        addToPBRules(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
                ClassicalRules.T_BIIMPLIES_LEFT_TRUE,
                ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
        addToPBRules(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
                ClassicalRules.F_BIIMPLIES_LEFT_TRUE,
                ClassicalRules.F_BIIMPLIES_LEFT_FALSE);

        /** rules for applying PB - with XOR */
        PBRules
                .add(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
                        ClassicalRules.T_XOR_LEFT_TRUE,
                        ClassicalRules.T_XOR_LEFT_FALSE);
        PBRules
                .add(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
                        ClassicalRules.F_XOR_LEFT_TRUE,
                        ClassicalRules.F_XOR_LEFT_FALSE);
        return PBRules;
    }

    /**
     * @return
     */
    private ConnectiveRoleSignRuleList initializeTwoPremiseRuleList() {
        twoPremiseRules = new ConnectiveRoleSignRuleList();

        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_IMPLIES_F_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_IMPLIES_F_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_IMPLIES_T_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_IMPLIES_T_RIGHT);

        addToTwoPremiseRules(ClassicalConnectives.NOT, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_NOT_F);
        addToTwoPremiseRules(ClassicalConnectives.NOT, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_NOT_T);

        /** Two premise substitution rules with biimplication */
        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_BIIMPLIES_F_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_BIIMPLIES_F_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_BIIMPLIES_T_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_BIIMPLIES_T_RIGHT);

        /** Two premise substitution rules with XOR */

        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_XOR_F_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_XOR_F_RIGHT);
        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_XOR_T_LEFT);
        addToTwoPremiseRules(ClassicalConnectives.XOR, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_XOR_T_RIGHT);
        return twoPremiseRules;
    }

    /**
     * @return
     */
    private TopBottomRoleRuleList initializeTBRuleList() {
        topAndBottomRulesNew = new TopBottomRoleRuleList();

        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.AND,
                KERuleRole.LEFT, ClassicalRules.X_TOP_AND_LEFT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.AND,
                KERuleRole.RIGHT, ClassicalRules.X_TOP_AND_RIGHT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.OR,
                KERuleRole.LEFT, ClassicalRules.X_TOP_OR_LEFT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.OR,
                KERuleRole.RIGHT, ClassicalRules.X_TOP_OR_RIGHT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.IMPLIES,
                KERuleRole.LEFT, ClassicalRules.X_TOP_IMPLIES_LEFT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.IMPLIES,
                KERuleRole.RIGHT, ClassicalRules.X_TOP_IMPLIES_RIGHT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.NOT,
                KERuleRole.LEFT, ClassicalRules.X_TOP_NOT);

        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.AND,
                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_AND_LEFT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.AND,
                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_AND_RIGHT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.OR,
                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_OR_LEFT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.OR,
                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_OR_RIGHT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.IMPLIES,
                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_IMPLIES_LEFT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.IMPLIES,
                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_IMPLIES_RIGHT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.NOT,
                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_NOT);

        /** One premise rules with biimplication */
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.BIIMPLIES,
                KERuleRole.LEFT, ClassicalRules.X_TOP_BIIMPLIES_LEFT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.BIIMPLIES,
                KERuleRole.RIGHT, ClassicalRules.X_TOP_BIIMPLIES_RIGHT);

        addToTBRules(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
                ClassicalRules.X_BOTTOM_BIIMPLIES_LEFT);
        addToTBRules(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
                ClassicalRules.X_BOTTOM_BIIMPLIES_RIGHT);

        /** One premise rules with XOR */
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.XOR,
                KERuleRole.LEFT, ClassicalRules.X_TOP_XOR_LEFT);
        addToTBRules(ClassicalConnectives.TOP, ClassicalConnectives.XOR,
                KERuleRole.RIGHT, ClassicalRules.X_TOP_XOR_RIGHT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.XOR,
                KERuleRole.LEFT, ClassicalRules.X_BOTTOM_XOR_LEFT);
        addToTBRules(ClassicalConnectives.BOTTOM, ClassicalConnectives.XOR,
                KERuleRole.RIGHT, ClassicalRules.X_BOTTOM_XOR_RIGHT);
        return topAndBottomRulesNew;
    }

    /**
     * @return
     */
    private OnePremiseRuleList initializeOnePremiseRuleList() {
        onePremiseRules = new OnePremiseRuleList();

        addToOnePremiseRules(ClassicalSigns.TRUE, ClassicalConnectives.NOT,
                ClassicalRules.T_NOT);
        addToOnePremiseRules(ClassicalSigns.FALSE, ClassicalConnectives.NOT,
                ClassicalRules.F_NOT);
        addToOnePremiseRules(ClassicalSigns.TRUE, ClassicalConnectives.AND,
                ClassicalRules.T_AND);
        addToOnePremiseRules(ClassicalSigns.FALSE, ClassicalConnectives.OR,
                ClassicalRules.F_OR);
        addToOnePremiseRules(ClassicalSigns.FALSE,
                ClassicalConnectives.IMPLIES, ClassicalRules.F_IMPLIES);
        return onePremiseRules;
    }

    private void addToPBRules(FormulaSign sign, Connective conn, Rule r1) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, r1, RuleType.PB);
            PBRules.add(sign, conn, r1);
        }
    }

    private void addToPBRules(FormulaSign sign, Connective conn, Rule r1,
            Rule r2) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, r1, RuleType.PB);
            addConnectiveRuleType(conn, r2, RuleType.PB);
            PBRules.add(sign, conn, r1, r2);
        }
    }

    protected void addToTwoPremiseRules(Connective conn, KERuleRole role,
            FormulaSign sign, TwoPremisesOneConclusionRule r) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, r, RuleType.SUBSTITUTION_2P);
            twoPremiseRules.add(conn, role, sign, r);
        }
    }

    private void addToTBRules(Connective conn1, Connective conn2,
            KERuleRole role, OnePremiseOneConclusionRule r) {
        if (signature.contains(conn1) && signature.contains(conn2)) {
            addConnectiveRuleType(conn1, r, RuleType.SUBSTITUTION_1P);
            addConnectiveRuleType(conn2, r, RuleType.SUBSTITUTION_1P);
            topAndBottomRulesNew.add(conn1, conn2, role, r);
        }
    }

    private void addToOnePremiseRules(FormulaSign sign, Connective conn, Rule r) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, r, RuleType.SIMPLE_1P);
            onePremiseRules.add(sign, conn, r);
        }
    }

    protected void addConnectiveRuleType(Connective conn, Rule r, RuleType rt) {
        crsf.createCRS(conn).add(r, rt);
    }

    //    

    // OLD IMPLEMENTATION

    //    static final int WITH_BIIMPLICATION = 0;
    //
    //    static final int WITHOUT_BIIMPLICATION = 1;
    //
    //    static final int WITH_BIIMPLICATION_AND_XOR = 2;
    //
    //    public static final ClassicalRuleStructures
    // KESubstitionRulesWithBiimplication = new ClassicalRuleStructures(
    //            WITH_BIIMPLICATION);
    //
    //    public static final ClassicalRuleStructures
    // KESubstitionRulesWithBiimplicationAndXor = new ClassicalRuleStructures(
    //            WITH_BIIMPLICATION_AND_XOR);
    //
    //    public static final ClassicalRuleStructures KESubstitionRules = new
    // ClassicalRuleStructures(
    //            WITHOUT_BIIMPLICATION);
    //
    //    RulesStructure _rulesOld;
    //
    //    private ClassicalRuleStructures(int option) {
    //        // one premise rules
    //        OnePremiseRuleList onePremiseRules = new OnePremiseRuleList();
    //
    //        onePremiseRules.add(ClassicalSigns.TRUE, ClassicalConnectives.NOT,
    //                ClassicalRules.T_NOT);
    //        onePremiseRules.add(ClassicalSigns.FALSE, ClassicalConnectives.NOT,
    //                ClassicalRules.F_NOT);
    //        onePremiseRules.add(ClassicalSigns.TRUE, ClassicalConnectives.AND,
    //                ClassicalRules.T_AND);
    //        onePremiseRules.add(ClassicalSigns.FALSE, ClassicalConnectives.OR,
    //                ClassicalRules.F_OR);
    //        onePremiseRules.add(ClassicalSigns.FALSE, ClassicalConnectives.IMPLIES,
    //                ClassicalRules.F_IMPLIES);
    //
    //        TopBottomRoleRuleList topAndBottomRulesNew = new TopBottomRoleRuleList();
    //
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.AND, KERuleRole.LEFT,
    //                ClassicalRules.X_TOP_AND_LEFT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.AND, KERuleRole.RIGHT,
    //                ClassicalRules.X_TOP_AND_RIGHT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.OR, KERuleRole.LEFT,
    //                ClassicalRules.X_TOP_OR_LEFT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.OR, KERuleRole.RIGHT,
    //                ClassicalRules.X_TOP_OR_RIGHT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
    //                ClassicalRules.X_TOP_IMPLIES_LEFT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
    //                ClassicalRules.X_TOP_IMPLIES_RIGHT);
    //
    //        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                ClassicalConnectives.NOT, KERuleRole.LEFT,
    //                ClassicalRules.X_TOP_NOT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.AND, KERuleRole.LEFT,
    //                ClassicalRules.X_BOTTOM_AND_LEFT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.AND, KERuleRole.RIGHT,
    //                ClassicalRules.X_BOTTOM_AND_RIGHT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.OR, KERuleRole.LEFT,
    //                ClassicalRules.X_BOTTOM_OR_LEFT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.OR, KERuleRole.RIGHT,
    //                ClassicalRules.X_BOTTOM_OR_RIGHT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
    //                ClassicalRules.X_BOTTOM_IMPLIES_LEFT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
    //                ClassicalRules.X_BOTTOM_IMPLIES_RIGHT);
    //        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                ClassicalConnectives.NOT, KERuleRole.LEFT,
    //                ClassicalRules.X_BOTTOM_NOT);
    //
    //        if (option == WITH_BIIMPLICATION) {
    //            topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                    ClassicalRules.X_TOP_BIIMPLIES_LEFT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                    ClassicalRules.X_TOP_BIIMPLIES_RIGHT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                    ClassicalRules.X_BOTTOM_BIIMPLIES_LEFT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                    ClassicalRules.X_BOTTOM_BIIMPLIES_RIGHT);
    //        }
    //
    //        if (option == WITH_BIIMPLICATION_AND_XOR) {
    //            topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                    ClassicalRules.X_TOP_BIIMPLIES_LEFT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                    ClassicalRules.X_TOP_BIIMPLIES_RIGHT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.LEFT,
    //                    ClassicalRules.X_BOTTOM_BIIMPLIES_LEFT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                    ClassicalConnectives.BIIMPLIES, KERuleRole.RIGHT,
    //                    ClassicalRules.X_BOTTOM_BIIMPLIES_RIGHT);
    //
    //            topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                    ClassicalConnectives.XOR, KERuleRole.LEFT,
    //                    ClassicalRules.X_TOP_XOR_LEFT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.TOP,
    //                    ClassicalConnectives.XOR, KERuleRole.RIGHT,
    //                    ClassicalRules.X_TOP_XOR_RIGHT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                    ClassicalConnectives.XOR, KERuleRole.LEFT,
    //                    ClassicalRules.X_BOTTOM_XOR_LEFT);
    //            topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
    //                    ClassicalConnectives.XOR, KERuleRole.RIGHT,
    //                    ClassicalRules.X_BOTTOM_XOR_RIGHT);
    //        }
    //
    //        ConnectiveRoleSignRuleList twoPremiseRulesNewII = new
    // ConnectiveRoleSignRuleList();
    //
    //        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_LEFT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_RIGHT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_LEFT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_RIGHT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_LEFT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.RIGHT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_RIGHT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_LEFT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.RIGHT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_RIGHT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_IMPLIES_F_LEFT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES,
    //                KERuleRole.RIGHT, ClassicalSigns.FALSE,
    //                ClassicalRules.X_IMPLIES_F_RIGHT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_IMPLIES_T_LEFT);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES,
    //                KERuleRole.RIGHT, ClassicalSigns.TRUE,
    //                ClassicalRules.X_IMPLIES_T_RIGHT);
    //
    //        twoPremiseRulesNewII.add(ClassicalConnectives.NOT, KERuleRole.LEFT,
    //                ClassicalSigns.FALSE, ClassicalRules.X_NOT_F);
    //        twoPremiseRulesNewII.add(ClassicalConnectives.NOT, KERuleRole.LEFT,
    //                ClassicalSigns.TRUE, ClassicalRules.X_NOT_T);
    //
    //        if (option == WITH_BIIMPLICATION) {
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.LEFT, ClassicalSigns.FALSE,
    //                    ClassicalRules.X_BIIMPLIES_F_LEFT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.RIGHT, ClassicalSigns.FALSE,
    //                    ClassicalRules.X_BIIMPLIES_F_RIGHT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.LEFT, ClassicalSigns.TRUE,
    //                    ClassicalRules.X_BIIMPLIES_T_LEFT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.RIGHT, ClassicalSigns.TRUE,
    //                    ClassicalRules.X_BIIMPLIES_T_RIGHT);
    //        }
    //
    //        if (option == WITH_BIIMPLICATION_AND_XOR) {
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.LEFT, ClassicalSigns.FALSE,
    //                    ClassicalRules.X_BIIMPLIES_F_LEFT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.RIGHT, ClassicalSigns.FALSE,
    //                    ClassicalRules.X_BIIMPLIES_F_RIGHT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.LEFT, ClassicalSigns.TRUE,
    //                    ClassicalRules.X_BIIMPLIES_T_LEFT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
    //                    KERuleRole.RIGHT, ClassicalSigns.TRUE,
    //                    ClassicalRules.X_BIIMPLIES_T_RIGHT);
    //
    //            twoPremiseRulesNewII.add(ClassicalConnectives.XOR, KERuleRole.LEFT,
    //                    ClassicalSigns.FALSE, ClassicalRules.X_XOR_F_LEFT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.XOR,
    //                    KERuleRole.RIGHT, ClassicalSigns.FALSE,
    //                    ClassicalRules.X_XOR_F_RIGHT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.XOR, KERuleRole.LEFT,
    //                    ClassicalSigns.TRUE, ClassicalRules.X_XOR_T_LEFT);
    //            twoPremiseRulesNewII.add(ClassicalConnectives.XOR,
    //                    KERuleRole.RIGHT, ClassicalSigns.TRUE,
    //                    ClassicalRules.X_XOR_T_RIGHT);
    //        }
    //
    //        PBRuleList PBRules = new PBRuleList();
    //
    //        PBRules.add(ClassicalSigns.FALSE, ClassicalConnectives.AND,
    //                ClassicalRules.F_AND_LEFT);
    //        PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.OR,
    //                ClassicalRules.T_OR_LEFT);
    //        PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
    //                ClassicalRules.T_IMPLIES_LEFT);
    //
    //        if (option == WITH_BIIMPLICATION) {
    //            PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
    //                    ClassicalRules.T_BIIMPLIES_LEFT_TRUE,
    //                    ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
    //            PBRules.add(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
    //                    ClassicalRules.F_BIIMPLIES_LEFT_TRUE,
    //                    ClassicalRules.F_BIIMPLIES_LEFT_FALSE);
    //        }
    //
    //        if (option == WITH_BIIMPLICATION_AND_XOR) {
    //            PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
    //                    ClassicalRules.T_BIIMPLIES_LEFT_TRUE,
    //                    ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
    //            PBRules.add(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
    //                    ClassicalRules.F_BIIMPLIES_LEFT_TRUE,
    //                    ClassicalRules.F_BIIMPLIES_LEFT_FALSE);
    //
    //            PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.XOR,
    //                    ClassicalRules.T_XOR_LEFT_TRUE,
    //                    ClassicalRules.T_XOR_LEFT_FALSE);
    //            PBRules.add(ClassicalSigns.FALSE, ClassicalConnectives.XOR,
    //                    ClassicalRules.F_XOR_LEFT_TRUE,
    //                    ClassicalRules.F_XOR_LEFT_FALSE);
    //        }
    //
    //        // the order is important!
    //        _rulesOld = new RulesStructure();
    //        _rulesOld.add(ONE_PREMISE_RULE_LIST, onePremiseRules);
    //        _rulesOld.add(TOP_BOTTOM_ONE_PREMISE_RULE_LIST, topAndBottomRulesNew);
    //        _rulesOld.add(TWO_PREMISE_RULE_LIST, twoPremiseRulesNewII);
    //        _rulesOld.add(PB_RULE_LIST, PBRules);
    //    }
    //
    //    public RulesStructure getRuleStructureOld() {
    //        return _rulesOld;
    //    }

    public ISignature getSignature() {
        return signature;
    }
    public ConnectiveRoleSignRuleList getTwoPremiseRules() {
        return twoPremiseRules;
    }
    
    /**
     * @param list
     */
    protected void setTwoPremiseRules(ConnectiveRoleSignRuleList list) {
        twoPremiseRules = list;
        
    }

}