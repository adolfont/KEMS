/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import logic.formulas.Connective;
import rules.KERuleRole;
import rules.OnePremiseOneConclusionRule;
import rules.Rule;

/**
 * @author adolfo
 *
 */
public class TopBottomRoleRuleList extends RuleList {

    ConnectiveConnectiveRoleRuleMap _topBottomRules;
    
    /**
     * 
     */
    public TopBottomRoleRuleList() {
        _topBottomRules = new ConnectiveConnectiveRoleRuleMap();
    }

    /**
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void add(Connective conn, Connective conn2, KERuleRole role, OnePremiseOneConclusionRule rulew) {
//        System.out.println (conn + "  " + conn2 + role + rulew);
        add(rulew);
        _topBottomRules.put(conn,conn2, role, rulew);
//        System.out.println (_topBottomRules);
        
    }
    
    public Rule get(Connective conn, Connective conn2, KERuleRole role) {
        return _topBottomRules.get(conn, conn2, role);
    }
    
    /* (non-Javadoc)
     * @see tableau.RuleList#toString()
     */
    public String toString() {
        return _topBottomRules.toString();
    }


}
