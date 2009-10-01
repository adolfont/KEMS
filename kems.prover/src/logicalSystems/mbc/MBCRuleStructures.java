/*
 * Created on 07/04/2005
 *
 */
package logicalSystems.mbc;

import logic.formulas.Connective;
import logic.logicalSystem.ISignature;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;
import rules.structures.ConnectiveRuleStructureFactory;
import rules.structures.OnePremiseNoConnectiveRuleList;
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
public class MBCRuleStructures {

    private ISignature signature;

    protected OnePremiseRuleList onePremiseRules;

    private OnePremiseNoConnectiveRuleList optionalOnePremiseRules;

    private ConnectiveRoleSignRuleList twoPremiseRules;

    private ConnectiveRoleSignRuleList optionalTwoPremiseRules;

//    private ConnectiveRoleSignRuleList optionalThreePremiseRules;

    private PBRuleList PBRules;

    private RulesStructure _rules;

    private ConnectiveRuleStructureFactory crsf = new ConnectiveRuleStructureFactory();

    // constants for lists of rules
    public static final String ONE_PREMISE_RULES = "onePremiseRules";

    public static final String TWO_PREMISE_RULES = "twoPremiseRules";

    public static final String OPTIONAL_TWO_PREMISE_RULES = "optionalTwoPremiseRules";

    public static final String OPTIONAL_ONE_PREMISE_RULES = "optionalOnePremiseRules";

    public static final String OPTIONAL_THREE_PREMISE_RULES = "optionalThreePremiseRules";

    public static final String PB_RULES = "PBRules";

    public MBCRuleStructures(ISignature signature) {
        this.signature = signature;

        /** mandatory one premise rules */
        onePremiseRules = initializeOnePremiseRuleList();

        /** mandatory two premise rules */
        twoPremiseRules = initializeTwoPremiseRuleList();

        /** optional two premise rules */
        optionalTwoPremiseRules = initializeoOptionalTwoPremiseRuleList();

        /** optional one premise rules */
        optionalOnePremiseRules = initializeOptionalOnePremiseRuleList();

        // TODO optional three premise rules
        /** optional three premise rules */
//        optionalThreePremiseRules = initializeOptionalThreePremiseRuleList();

        /** mandatory rules for applying PB */
        PBRules = initializePBRuleList();

        /** Order of the sets of rules */
        // the order is important!
        _rules = new RulesStructure();
        _rules.add(ONE_PREMISE_RULES, onePremiseRules);
        _rules.add(TWO_PREMISE_RULES, twoPremiseRules);
        _rules.add(OPTIONAL_TWO_PREMISE_RULES, optionalTwoPremiseRules);
        _rules.add(OPTIONAL_ONE_PREMISE_RULES, optionalOnePremiseRules);
        // TODO optional three premise rules
//        _rules.add(OPTIONAL_THREE_PREMISE_RULES, optionalThreePremiseRules);
        _rules.add(PB_RULES, PBRules);
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
    protected OnePremiseRuleList initializeOnePremiseRuleList() {
        onePremiseRules = new OnePremiseRuleList();

        addToOnePremiseRules(MBCSigns.FALSE, MBCConnectives.NOT,
                MBCRules.F_NOT);
        addToOnePremiseRules(MBCSigns.TRUE, MBCConnectives.AND,
                MBCRules.T_AND);
        addToOnePremiseRules(MBCSigns.FALSE, MBCConnectives.OR,
                MBCRules.F_OR);
        addToOnePremiseRules(MBCSigns.FALSE,
                MBCConnectives.IMPLIES, MBCRules.F_IMPLIES);

        
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
    	
    	addToTwoPremiseRules(MBCSigns.FALSE, MBCConnectives.AND,
    			KERuleRole.LEFT, MBCRules.F_AND_LEFT);
    	addToTwoPremiseRules(MBCSigns.FALSE, MBCConnectives.AND,
    			KERuleRole.RIGHT, MBCRules.F_AND_RIGHT);
    	addToTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.OR,
    			KERuleRole.LEFT, MBCRules.T_OR_LEFT);
    	addToTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.OR,
    			KERuleRole.RIGHT, MBCRules.T_OR_RIGHT);
    	addToTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.IMPLIES,
    			KERuleRole.LEFT, MBCRules.T_IMPLIES_LEFT);
    	addToTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.IMPLIES,
    			KERuleRole.RIGHT, MBCRules.T_IMPLIES_RIGHT);
    	addToTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.NOT,
    			KERuleRole.LEFT, MBCRules.T_NOT_1);
    	return twoPremiseRules;
    }

    /**
	 * @param sign
	 * @param conn
	 * @param role
	 * @param rule
	 */
	private void addToTwoPremiseRules(FormulaSign sign, Connective conn, KERuleRole role, TwoPremisesOneConclusionRule rule) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, rule, RuleType.SIMPLE_2P);
            twoPremiseRules.add(conn, role, sign, rule);
        }
	}

	/**
     * @return
     */
    private ConnectiveRoleSignRuleList initializeoOptionalTwoPremiseRuleList() {
    	optionalTwoPremiseRules= new ConnectiveRoleSignRuleList();

    	addToOptionalTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.NOT,
    			KERuleRole.LEFT, MBCRules.T_NOT_2);
    	addToOptionalTwoPremiseRules(MBCSigns.TRUE, MBCConnectives.CONSISTENCY,
    			KERuleRole.LEFT, MBCRules.T_CONS_1);

    	return optionalTwoPremiseRules;
    }
    
	private void addToOptionalTwoPremiseRules(FormulaSign sign, Connective conn, KERuleRole role, TwoPremisesOneConclusionRule rule) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, rule, RuleType.SIMPLE_2P);
            optionalTwoPremiseRules.add(conn, role, sign, rule);
        }
	}


    /**
     * @return
     */
    private OnePremiseNoConnectiveRuleList initializeOptionalOnePremiseRuleList() {
        optionalOnePremiseRules= new OnePremiseNoConnectiveRuleList();

        optionalOnePremiseRules.add (MBCSigns.FALSE, MBCRules.F_FORMULA);
        
        return optionalOnePremiseRules;
    }

    /**
     * @return
     */
    private PBRuleList initializePBRuleList() {
        PBRules = new PBRuleList();

        addToPBRules(MBCSigns.FALSE, MBCConnectives.AND,
                MBCRules.F_AND_LEFT);
        addToPBRules(MBCSigns.TRUE, MBCConnectives.OR,
                MBCRules.T_OR_LEFT);
        addToPBRules(MBCSigns.TRUE, MBCConnectives.IMPLIES,
                MBCRules.T_IMPLIES_LEFT);
        addToPBRules(MBCSigns.TRUE, MBCConnectives.NOT,
                MBCRules.T_NOT_1);

        return PBRules;
    }

	/**
	 * @param false1
	 * @param and
	 * @param f_and_left
	 */
    private void addToPBRules(FormulaSign sign, Connective conn, Rule r1) {
        if (signature.contains(conn)) {
            addConnectiveRuleType(conn, r1, RuleType.PB);
            PBRules.add(sign, conn, r1);
        }
    }

}
