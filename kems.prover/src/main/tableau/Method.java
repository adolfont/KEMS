/*
 * Created on Oct 22, 2004
 *
 */
package main.tableau;

import rules.Rule;
import rules.structures.RulesStructure;


/**
 * Class that represents a tableau method.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class Method {

    private RulesStructure _rules;

    /**
     * @param _rules
     */
    public Method(RulesStructure _rules) {
        super();
        this._rules = _rules;
    }

    /**
     * @return Returns the _rules.
     */
    public RulesStructure getRules() {
        return _rules;
    }

    /**
     * @param r
     * @return
     */
    public boolean contains(Rule r) {
        return _rules.contains(r);
    }
}
