/*
 * Created on 05/04/2006
 *
 */
package main.newstrategy.mci.simple.optional;

import logicalSystems.mbc.MBCRuleStructures;
import main.newstrategy.mbc.simple.TwoPremiseRuleApplicator;
import main.newstrategy.mci.simple.MCISimpleStrategy;
import main.tableau.Method;

/**
 * A strategy for mCi based on MBCSimpleStrategy.
 * It uses the MCI rule structure.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class MCISimpleWithOptionalRulesStrategy extends MCISimpleStrategy {

    /**
     * @param method
     */
    public MCISimpleWithOptionalRulesStrategy(Method method) {
        super(method);

        // TODO this is equal to MBC SIMPLE WITH OPTIONAL RULES STRATEGY
        addRuleApplicator(new TwoPremiseRuleApplicator(this,
                MBCRuleStructures.OPTIONAL_TWO_PREMISE_RULES));
    }

}
