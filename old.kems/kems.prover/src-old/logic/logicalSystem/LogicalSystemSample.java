/*
 * Created on 05/04/2005
 *
 */
package logic.logicalSystem;

import java.util.Collection;
import java.util.Map;

import ruleStructures.IRulesStructure;
import ruleStructures.IRulesStructureBuilder;
import formulasNew.Connective;
import formulasNew.ConnectiveCode;

/**
 * Describes a logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class LogicalSystemSample implements ILogicalSystem {
    
    private ISignature signature;

    private IRulesStructure rulesStructure;

    /**
     * @param signature
     * @param rulesStructure
     */
    public LogicalSystemSample(ISignature signature, IRulesStructureBuilder rulesStructureBuilder) {
        this.signature = signature;
        this.rulesStructure = rulesStructureBuilder.getRuleStructure(signature);
    }

    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getRulesStructure()
     */
    public IRulesStructure getRulesStructure() {
        return rulesStructure;
    }
    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getSignature()
     */
    public ISignature getSignature() {
        return signature;
    }
    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getConnectives()
     */
    public Map getConnectives() {
        return signature.getConnectives();
    }
    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getConnective(int)
     */
    public Connective getConnective(ConnectiveCode connectiveID) {
        return signature.getConnective (connectiveID);
    }
    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getRules()
     */
    public Collection getRules() {
        return rulesStructure.getRules();
    }

}
