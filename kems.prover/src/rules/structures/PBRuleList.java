/*
 * Created on 30/11/2004
 *
 */
package rules.structures;

import java.util.HashMap;
import java.util.Map;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import rules.IRule;
import rules.NullRule;
import rules.Rule;

/**
 * List-map of rules with one premise.
 * 
 * @author adolfo
 *  
 */
public class PBRuleList extends RuleList {

    SignConnectiveRuleMap _PBRules;

    Map<IRule,IRule> _complementaryRules;

    /**
     * Creates an empty one premise rule list
     */
    public PBRuleList() {
        super();
        _PBRules = new SignConnectiveRuleMap();
        _complementaryRules = new HashMap<IRule, IRule>();
    }

    /**
     * Adds a one premise rule to the list.
     * 
     * @param fs -
     *            the sign that identifies of the rule
     * @param conn -
     *            the connective that identifies the rule
     * @param r -
     *            the rule
     */
    public void add(FormulaSign fs, Connective conn, Rule r) {
        add(r);
        _PBRules.put(fs, conn, r);
    }

    /**
     * Gives the rule for a given sign, connective pair.
     * 
     * @param fs -
     *            the sign
     * @param conn -
     *            the connective
     * @return a rule for a given sign, connective pair
     */
    public Rule get(FormulaSign fs, Connective conn) {
        return _PBRules.get(fs, conn) != null ? _PBRules.get(fs, conn)
                : NullRule.INSTANCE;
    }

    public void add(FormulaSign fs, Connective conn, Rule r1, Rule r2) {
        add(r1);
        add(r2);
        _PBRules.put(fs, conn, r1);
        _complementaryRules.put(r1, r2);
    }

    public Rule getComplementary(Rule r) {
        return (Rule) _complementaryRules.get(r);
    }
}