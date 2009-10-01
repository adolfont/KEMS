/*
 * Created on 06/04/2005
 *
 */
package rules.structures;

import java.util.HashMap;
import java.util.Map;

import logic.formulas.Connective;
import rules.Rule;

/**
 * A structure of rules associated to a connective.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class ConnectiveRuleStructure {
//    private Connective connective;

    private RuleList ruleList;

    private Map<Rule, RuleType> ruleTypes;

    /**
     * @param connective
     */
    public ConnectiveRuleStructure(Connective connective) {
        super();
//        this.connective = connective;
        ruleList = new RuleList();
        ruleTypes = new HashMap<Rule, RuleType>();
    }

    public void add(Rule r, RuleType rt) {
        ruleList.add(r);
        ruleTypes.put(r, rt);
    }

    public RuleType getRuleType(Rule r) {
        return (RuleType) ruleTypes.get(r);
    }

    public RuleList getRules() {
        return ruleList;
    }
}
