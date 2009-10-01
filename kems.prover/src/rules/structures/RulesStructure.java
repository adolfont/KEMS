/*
 * Created on Oct 22, 2004
 *
 */
package rules.structures;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rules.IRule;
import rules.Rule;

/**
 * Class that represents a structure of lists of rules.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class RulesStructure implements IRulesStructure {

    private Map<String,RuleList> _structure = new HashMap<String, RuleList>();

    private List<IRule> allRules = new ArrayList<IRule>();

    /*
     * (non-Javadoc)
     * 
     * @see rules.structures.IRulesStructure#add(java.lang.String,
     *      rules.structures.RuleList)
     */
    public void add(String ruleListName, RuleList rl) {
        _structure.put(ruleListName, rl);
        if (rl != null) {
            allRules.addAll(rl.asList());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see rules.structures.IRulesStructure#get(java.lang.String)
     */
    public RuleList get(String ruleListName) {

        if (_structure.containsKey(ruleListName)) {
            return (RuleList) _structure.get(ruleListName);
        } else
            throw new RuntimeException("N�o existe conjunto de regras com o nome: "
                    + ruleListName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ruleStructures.IRulesStructure#getRules()
     */
    public Collection<IRule> getRules() {
//        List l = new ArrayList();
//
//        Iterator it = _structure.values().iterator();
//
//        while (it.hasNext()) {
//            RuleList rl = (RuleList) it.next();
//            if (rl != null) {
//                l.addAll(rl.asList());
//            }
//        }
//
//        return l;
        return allRules;
    }

    /**
     * @param r
     * @return
     */
    public boolean contains(Rule r) {
        return getRules().contains(r);
    }

}