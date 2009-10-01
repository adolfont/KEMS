/*
 * Created on 03/08/2005
 *
 */
package logicalSystems.classicalLogic;

import java.util.Collection;
import java.util.Map;

import logic.formulas.Connective;
import logic.formulas.ConnectiveCode;
import logic.logicalSystem.ILogicalSystem;
import logic.logicalSystem.ISignature;
import rules.IRule;
import rules.structures.IRulesStructure;
import rules.structures.IRulesStructureBuilder;

/**
 * Class that concentrates features of Classical Logic Systems.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class ClassicalLogicSystem implements ILogicalSystem {
    
    /**
     * signature used
     */
	private ISignature signature;
    
	/**
	 * structure of rules used
	 */
    private IRulesStructure rulesStructure;

    /** Creates a classical logic system for a pair signature-rules structure
     * @param signature
     * @param rulesStructure
     */
    public ClassicalLogicSystem(ISignature signature, IRulesStructureBuilder rulesStructureBuilder) {
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
    public Map<ConnectiveCode,Connective> getConnectives() {
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
    public Collection<IRule> getRules() {
        return rulesStructure.getRules();
    }

}
