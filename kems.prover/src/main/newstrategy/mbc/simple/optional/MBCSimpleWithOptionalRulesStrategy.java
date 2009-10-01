/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.mbc.simple.optional;

import logicalSystems.mbc.MBCRuleStructures;
import main.newstrategy.mbc.simple.MBCSimpleStrategy;
import main.newstrategy.mbc.simple.TwoPremiseRuleApplicator;
import main.tableau.Method;

/**
 * A strategy for mbC based on SimpleStrategy that uses two optional 
 * rules: T_NOT_2 and T_CONS_1
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class MBCSimpleWithOptionalRulesStrategy extends MBCSimpleStrategy {

    /**
     * @param method
     */
    public MBCSimpleWithOptionalRulesStrategy(Method method) {
        super(method);
        
        addRuleApplicator(new TwoPremiseRuleApplicator(this,
                MBCRuleStructures.OPTIONAL_TWO_PREMISE_RULES));
    }
    
    

}
