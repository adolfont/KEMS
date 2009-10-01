/*
 * Created on 02/08/2005
 *
 */
package rules.structures;

import logic.logicalSystem.ISignature;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public interface IRulesStructureBuilder {
    
    public RulesStructure getRuleStructure (ISignature signature);

}
