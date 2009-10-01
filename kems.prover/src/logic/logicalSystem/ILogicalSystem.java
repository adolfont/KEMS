/*
 * Created on 01/08/2005
 *
 */
package logic.logicalSystem;

import java.util.Collection;
import java.util.Map;

import logic.formulas.Connective;
import logic.formulas.ConnectiveCode;
import rules.IRule;
import rules.structures.IRulesStructure;

/**
 * Interface for logic systems.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface ILogicalSystem {
    
    /**
     * @return a map of connectives
     */
    public Map<ConnectiveCode,Connective> getConnectives();
    
    /**
     * @param connectiveID
     * @return a connective
     */
    public Connective getConnective(ConnectiveCode code);
    
    /** returns alll rules of this logic system
     * @return a collection of rules
     */
    public Collection<IRule> getRules();
    
    /**
     * @return the signature of the logic system
     */
    public ISignature getSignature();
    
    /**
     * @return the structure of rules of the logic system
     */
    public IRulesStructure getRulesStructure();

}
