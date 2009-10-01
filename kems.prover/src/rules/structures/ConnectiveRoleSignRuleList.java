/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.OneConclusionRule;
import rules.Rule;

/**
 * @author adolfo
 *
 */
public class ConnectiveRoleSignRuleList extends RuleList {

    private ConnectiveRoleSignRuleMap connRules;
    
    /**
     * 
     */
    public ConnectiveRoleSignRuleList() {
        connRules = new ConnectiveRoleSignRuleMap();
    }

    /**
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void add(Connective conn, KERuleRole role, FormulaSign sign,  OneConclusionRule rulew) {
        add(rulew);
        connRules.put(conn,role, sign, rulew);
        
    }


    public Rule get(Connective conn, KERuleRole role, FormulaSign sign) {
        return connRules.get(conn, role, sign);
    }
    
    /* (non-Javadoc)
     * @see tableau.RuleList#toString()
     */
    public String toString() {
        return connRules.toString();
    }


}
