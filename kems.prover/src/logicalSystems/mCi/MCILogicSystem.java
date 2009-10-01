/*
 * Created on 04/08/2005
 *
 */
package logicalSystems.mCi;

import java.util.Collection;
import java.util.Map;

import logic.formulas.Connective;
import logic.formulas.ConnectiveCode;
import logic.logicalSystem.ILogicalSystem;
import logic.logicalSystem.ISignature;
import rules.IRule;
import rules.structures.IRulesStructure;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class MCILogicSystem implements ILogicalSystem {
	
	// TODO complete implementation - what for?
	
	/** 
	 * @param signature
	 * @param rulesStructure
	 */
	public MCILogicSystem(ISignature signature, IRulesStructure rulesStructure) {
		this.signature = signature;
//		this.rulesStructure = rulesStructure;
	}
    private ISignature signature;

//    private IRulesStructure rulesStructure;


    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getConnectives()
     */
    public Map<ConnectiveCode,Connective> getConnectives() {
        return signature.getConnectives();
    }

    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getConnective(formulasNew.ConnectiveCode)
     */
    public Connective getConnective(ConnectiveCode arg0) {
        return null;
    }

    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getRules()
     */
    public Collection<IRule> getRules() {
        return null;
    }

    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getSignature()
     */
    public ISignature getSignature() {
        return null;
    }

    /* (non-Javadoc)
     * @see logic.ILogicalSystem#getRulesStructure()
     */
    public IRulesStructure getRulesStructure() {
        return null;
    }

}
