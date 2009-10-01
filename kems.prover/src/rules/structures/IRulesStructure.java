/*
 * Created on 02/08/2005
 *
 */
package rules.structures;

import java.util.Collection;

import rules.IRule;

/**
 * Interface for structure of rules
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public interface IRulesStructure {
    public abstract void add(String ruleListName, RuleList rl);

    public abstract RuleList get(String ruleListName);
    
    public abstract Collection<IRule> getRules();
}