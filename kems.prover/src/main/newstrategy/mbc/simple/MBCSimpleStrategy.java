/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.mbc.simple;

import java.util.ArrayList;
import java.util.List;

import logicalSystems.mbc.MBCRuleStructures;
import main.newstrategy.simple.SimpleStrategy;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.applicator.PBRuleApplicator;
import main.tableau.Method;

/**
 * A strategy for mbC based on SimpleStrategy.
 * It uses the MBC rule structure.
 * It does not use the TopBottomOnePremiseRuleApplicator from SimpleStrategy,
 * nor SimpliicationTwoPremiseRuleApplicator.
 * 
 * It uses a TwoPremiseRuleApplicator.
 * 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class MBCSimpleStrategy extends SimpleStrategy {

	/**
     * @param method
     */
    public MBCSimpleStrategy(Method method) {
        super();
        setMethod(method);

        // initialize rule applicators
        List<IRuleApplicator> ruleApplicators = new ArrayList<IRuleApplicator>();
        ruleApplicators.add(new OnePremiseRuleApplicator(this,
                MBCRuleStructures.ONE_PREMISE_RULES));
//        ruleApplicators.add(new TopBottomOnePremiseRuleApplicator(this,
//                ClassicalRuleStructures.TOP_BOTTOM_ONE_PREMISE_RULE_LIST));
        ruleApplicators.add(new TwoPremiseRuleApplicator(this,
                MBCRuleStructures.TWO_PREMISE_RULES));

        
        setRuleApplicators(ruleApplicators);

        // initialize proof transformations
        List<IProofTransformation> proofTransformations = new ArrayList<IProofTransformation>();
        PBRuleApplicator pbr = new PBRuleApplicator(this, MBCRuleStructures.PB_RULES);
        pbr.setPbRuleChooser(new MBC_PBRuleChooser(this));
        proofTransformations.add(pbr);
        setProofTransformations(proofTransformations);
        
        
    }
    
    
    

}
