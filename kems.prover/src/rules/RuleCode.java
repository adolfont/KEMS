/*
 * Created on 03/12/2004
 *
 */
package rules;

/**
 * Class with constants for rule codes.
 * 
 * @author adolfo
 * 
 */
public class RuleCode {

    /**
     * @param _code
     */
    private RuleCode() {
        super();
    }

    public static final RuleCode T_NOT = new RuleCode();

    public static final RuleCode F_NOT = new RuleCode();

    public static final RuleCode X_NOT_T = new RuleCode();

    public static final RuleCode X_NOT_F = new RuleCode();

    public static final RuleCode T_AND = new RuleCode();

    public static final RuleCode F_AND_LEFT = new RuleCode();

    public static final RuleCode X_AND_T_LEFT = new RuleCode();

    public static final RuleCode X_AND_T_RIGHT = new RuleCode();

    public static final RuleCode X_AND_F_LEFT = new RuleCode();

    public static final RuleCode X_AND_F_RIGHT = new RuleCode();

    public static final RuleCode F_OR = new RuleCode();

    public static final RuleCode T_OR_LEFT = new RuleCode();

    public static final RuleCode X_OR_T_LEFT = new RuleCode();

    public static final RuleCode X_OR_T_RIGHT = new RuleCode();

    public static final RuleCode X_OR_F_LEFT = new RuleCode();

    public static final RuleCode X_OR_F_RIGHT = new RuleCode();

    public static final RuleCode F_IMPLIES = new RuleCode();

    public static final RuleCode T_IMPLIES_LEFT = new RuleCode();

    public static final RuleCode X_IMPLIES_T_LEFT = new RuleCode();

    public static final RuleCode X_IMPLIES_T_RIGHT = new RuleCode();

    public static final RuleCode X_IMPLIES_F_LEFT = new RuleCode();

    public static final RuleCode X_IMPLIES_F_RIGHT = new RuleCode();

    public static final RuleCode T_BIIMPLIES_LEFT_TRUE = new RuleCode();

    public static final RuleCode F_BIIMPLIES_LEFT_TRUE = new RuleCode();

    public static final RuleCode T_BIIMPLIES_LEFT_FALSE = new RuleCode();

    public static final RuleCode F_BIIMPLIES_LEFT_FALSE = new RuleCode();

    public static final RuleCode X_BIIMPLIES_T_LEFT = new RuleCode();

    public static final RuleCode X_BIIMPLIES_T_RIGHT = new RuleCode();

    public static final RuleCode X_BIIMPLIES_F_LEFT = new RuleCode();

    public static final RuleCode X_BIIMPLIES_F_RIGHT = new RuleCode();

    public static final RuleCode T_XOR_LEFT_TRUE = new RuleCode();

    public static final RuleCode F_XOR_LEFT_TRUE = new RuleCode();

    public static final RuleCode T_XOR_LEFT_FALSE = new RuleCode();

    public static final RuleCode F_XOR_LEFT_FALSE = new RuleCode();

    public static final RuleCode X_XOR_T_LEFT = new RuleCode();

    public static final RuleCode X_XOR_T_RIGHT = new RuleCode();

    public static final RuleCode X_XOR_F_LEFT = new RuleCode();

    public static final RuleCode X_XOR_F_RIGHT = new RuleCode();

    public static final RuleCode X_TOP_AND_LEFT = new RuleCode();

    public static final RuleCode X_TOP_AND_RIGHT = new RuleCode();

    public static final RuleCode X_TOP_OR_LEFT = new RuleCode();

    public static final RuleCode X_TOP_OR_RIGHT = new RuleCode();

    public static final RuleCode X_TOP_IMPLIES_LEFT = new RuleCode();

    public static final RuleCode X_TOP_IMPLIES_RIGHT = new RuleCode();

    public static final RuleCode X_TOP_BIIMPLIES_LEFT = new RuleCode();

    public static final RuleCode X_TOP_BIIMPLIES_RIGHT = new RuleCode();

    public static final RuleCode X_TOP_XOR_LEFT = new RuleCode();

    public static final RuleCode X_TOP_XOR_RIGHT = new RuleCode();

    public static final RuleCode X_TOP_NOT = new RuleCode();

    public static final RuleCode X_BOTTOM_AND_LEFT = new RuleCode();

    public static final RuleCode X_BOTTOM_AND_RIGHT = new RuleCode();

    public static final RuleCode X_BOTTOM_OR_LEFT = new RuleCode();

    public static final RuleCode X_BOTTOM_OR_RIGHT = new RuleCode();

    public static final RuleCode X_BOTTOM_IMPLIES_LEFT = new RuleCode();

    public static final RuleCode X_BOTTOM_IMPLIES_RIGHT = new RuleCode();

    public static final RuleCode X_BOTTOM_BIIMPLIES_LEFT = new RuleCode();

    public static final RuleCode X_BOTTOM_BIIMPLIES_RIGHT = new RuleCode();

    public static final RuleCode X_BOTTOM_XOR_LEFT = new RuleCode();

    public static final RuleCode X_BOTTOM_XOR_RIGHT = new RuleCode();

    public static final RuleCode X_BOTTOM_NOT = new RuleCode();

}