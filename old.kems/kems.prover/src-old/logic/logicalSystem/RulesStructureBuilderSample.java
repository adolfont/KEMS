/*
 * Created on 03/08/2005
 *
 */
package logic.logicalSystem;

import classicalLogic.ClassicalRuleStructures;
import ruleStructures.IRulesStructureBuilder;
import ruleStructures.RulesStructure;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class RulesStructureBuilderSample implements IRulesStructureBuilder {

    /* (non-Javadoc)
     * @see ruleStructures.IRulesStructureBuilder#getRuleStructure(logic.ISignature)
     */
    public RulesStructure getRuleStructure(ISignature signature) {
        ConfigurableClassicalRuleStructures crs = new ConfigurableClassicalRuleStructures(signature);
        return crs.getRuleStructure();
    }

}
