/*
 * Created on Oct 22, 2004
 *
 */
package rules.structures;

import java.util.ArrayList;
import java.util.List;

import rules.IRule;
import rules.Rule;

/**
 * Class that represents an ordered list of rules.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class RuleList {

    private List<IRule> _rules = new ArrayList<IRule>();

    public void add(Rule r) {
        _rules.add(r);
    }

    /**
     * @return
     */
    public int size() {
        return _rules.size();
    }

    /**
     * @param index
     * @return
     */
    public Rule get(int index) {
        return (Rule) _rules.get(index);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String result = "[";
        for (int i = 0; i < _rules.size(); i++) {
            result = result + _rules.get(i).toString();
            if (i != _rules.size() - 1) {
                result = result + ", ";
            }
        }
        result = result + "]";
        return result;
    }
    
    public List<IRule> asList(){
        return _rules;
    }

    public List<IRule> getRules() {
        return _rules;
    }
}