/*
 * Created on 03/12/2004
 *
 */
package rules.structures;

import java.util.HashMap;
import java.util.Map;

import rules.IRule;
import rules.Rule;
import rules.RuleCode;

/**
 * @author adolfo
 *  
 */
public class Rules {

    private Map<RuleCode,IRule> _map = new HashMap<RuleCode, IRule>();

    /**
     *  
     */
    public Rules() {
    }

    public Rule get(RuleCode _code) {
        return (Rule) _map.get(_code);
    }

    public void put(RuleCode _code, Rule _rule) {
        _map.put(_code, _rule);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return _map.toString();
    }
}