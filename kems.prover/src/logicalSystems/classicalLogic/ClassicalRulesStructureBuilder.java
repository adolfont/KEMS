/*
 * Created on 03/08/2005
 *
 */
package logicalSystems.classicalLogic;

import logic.logicalSystem.ISignature;
import rules.structures.IRulesStructureBuilder;
import rules.structures.RulesStructure;

/** Creator of RulesStructure for classical logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class ClassicalRulesStructureBuilder implements IRulesStructureBuilder {

    /* (non-Javadoc)
     * @see ruleStructures.IRulesStructureBuilder#getRuleStructure(logic.ISignature)
     */
    public RulesStructure getRuleStructure(ISignature signature) {
        ClassicalRuleStructures crs = new ClassicalRuleStructures(signature);
        return crs.getRuleStructure();
    }

}
