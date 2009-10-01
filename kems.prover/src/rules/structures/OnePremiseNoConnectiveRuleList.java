/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import java.util.HashMap;
import java.util.Map;

import logic.signedFormulas.FormulaSign;
import rules.IRule;
import rules.NullRule;
import rules.Rule;

/**
 * List-map of rules with one premise and no connective.
 * 
 * @author adolfo
 *  
 */
public class OnePremiseNoConnectiveRuleList extends RuleList {

    Map<FormulaSign,IRule> _onePremiseRules;

    /**
     * Creates an empty one premise rule list
     */
    public OnePremiseNoConnectiveRuleList() {
        super();
        _onePremiseRules = new HashMap<FormulaSign, IRule>();
    }

    /**
     * Adds a one premise rule to the list.
     * 
     * @param fs -
     *                   the sign that identifies of the rule
     * @param r -
     *                   the rule
     */
    public void add(FormulaSign fs, Rule r) {
        add(r);
        _onePremiseRules.put(fs, r);
    }

    /**
     * Gives the rule for a given sign.
     * 
     * @param fs -
     *                   the sign
     * @return a rule for a given sign
     */
    public Rule get(FormulaSign fs) {
        return _onePremiseRules.get(fs) != null ? (Rule) _onePremiseRules
                .get(fs) : NullRule.INSTANCE;
    }
}