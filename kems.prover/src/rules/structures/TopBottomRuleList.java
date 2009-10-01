/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import logic.formulas.Connective;
import rules.NullRule;
import rules.OnePremiseOneConclusionRule;
import rules.Rule;

/**
 * List of rules with TOP or BOTTOM unary connectives.
 * 
 * @author adolfo
 *  
 */
public class TopBottomRuleList extends RuleList {

    ConnectiveConnectiveRuleMap _topBottomRules;

    /**
     *  Creates a TopBottomRuleList
     */
    public TopBottomRuleList() {
        _topBottomRules = new ConnectiveConnectiveRuleMap();
    }

    /** adds a rule to the list
     * @param conn
     * @param conn2
     * @param rulew
     */
    public void add(Connective conn, Connective conn2,
            OnePremiseOneConclusionRule rulew) {
        add(rulew);
        _topBottomRules.put(conn, conn2, rulew);
    }

    /** gets a rule from the list
     * @param conn
     * @param conn2
     * @return a rule ou NullRule.INSTANCE
     */
    public Rule get(Connective conn, Connective conn2) {
        return _topBottomRules.get(conn, conn2) != null ? _topBottomRules.get(
                conn, conn2) : NullRule.INSTANCE;
    }

}