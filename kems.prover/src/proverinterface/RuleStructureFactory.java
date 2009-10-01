/*
 * Created on 27/10/2005
 *
 */
package proverinterface;

import logicalSystems.c1.C1RuleStructures;
import logicalSystems.c1.C1SignatureFactory;
import logicalSystems.classicalLogic.ClassicalRuleStructures;
import logicalSystems.classicalLogic.ClassicalSignatureFactory;
import logicalSystems.mCi.MCIRuleStructures;
import logicalSystems.mbc.MBCRuleStructures;
import logicalSystems.mbc.MBCSignatureFactory;
import main.exceptions.KEMSException;
import main.newstrategy.cpl.simple.configurable.ConfigurableClassicalRuleStructures;
import rules.structures.RulesStructure;

/**
 * Class that builds RulesStructure objects.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class RuleStructureFactory {

    public static final String MBC = "mbc".toUpperCase();

    public static final String MCI = "mci".toUpperCase();

    public static final String C1 = "C1".toUpperCase();

    public static final String CPL_NORMAL_BX = "cpl_normal_bx".toUpperCase();

    public static final String CPL_CONFIGURABLE = "cpl_configurable".toUpperCase();

    //    public static final String CPL_OLD = "cpl_old".toUpperCase();

    public static RulesStructure createRulesStructure(String rulesStructureName) {
        RulesStructure rulesStructure;
        
        if (rulesStructureName.equals(C1)){
            rulesStructure = new C1RuleStructures(C1SignatureFactory.getInstance()
                    .getMainSignature()).getRuleStructure();
        }
        else
        if (rulesStructureName.equals(MCI)) {
            rulesStructure = new MCIRuleStructures(MBCSignatureFactory.getInstance()
                    .getMainSignature()).getRuleStructure();
        } else {
            if (rulesStructureName.equals(MBC)) {
                rulesStructure = new MBCRuleStructures(MBCSignatureFactory.getInstance()
                        .getMainSignature()).getRuleStructure();
            } else {
                if (rulesStructureName.equals(CPL_NORMAL_BX)) {
                    rulesStructure = new ClassicalRuleStructures(ClassicalSignatureFactory
                            .getInstance().getNormalBXSignature()).getRuleStructure();
                    //              } else if (rulesStructureName.equals(CPL_OLD)) {
                    //              rulesStructure =
                    // ClassicalRuleStructures.KESubstitionRulesWithBiimplicationAndXor
                    //                      .getRuleStructureOld();
                } else if (rulesStructureName.equals(CPL_CONFIGURABLE)) {
                    rulesStructure = new ConfigurableClassicalRuleStructures(
                            ClassicalSignatureFactory.getInstance().getNormalBXSignature())
                            .getRuleStructure();
                } else {
                    throw new KEMSException("No rules structure configured for " + rulesStructureName
                            + " !");
                }
            }
        }

        return rulesStructure;
    }

}
