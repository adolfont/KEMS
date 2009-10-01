/*
 * Created on 05/12/2005
 *
 */
package main.newstrategy.cpl.simple.configurable;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.KERuleRole;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;

/**
 * A ConnectiveRoleSignRuleList used by the ConfigurableSimpleStrategy
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ConfigurableConnectiveRoleSignRuleList extends
        ConnectiveRoleSignRuleList {
    
    private ConnectiveRoleSignSignRuleMap connRules;
    
    public ConfigurableConnectiveRoleSignRuleList() {
        connRules = new ConnectiveRoleSignSignRuleMap();
    }

    public Rule get(Connective conn, KERuleRole role1, FormulaSign sign, FormulaSign sign2) {
        return connRules.get(conn, role1, sign, sign2);
    }
    
    /* (non-Javadoc)
     * @see tableau.RuleList#toString()
     */
    public String toString() {
        return connRules.toString();
    }

    public void add(Connective conn, KERuleRole role, FormulaSign sign,  FormulaSign sign2, TwoPremisesOneConclusionRule rule) {
        add(rule);
        connRules.put(conn,role, sign, sign2, rule);
    }

    
}
