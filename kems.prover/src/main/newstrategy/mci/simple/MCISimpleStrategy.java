/*
 * Created on 05/04/2006
 *
 */
package main.newstrategy.mci.simple;

import java.util.ArrayList;
import java.util.List;

import logicalSystems.mCi.MCIRuleStructures;
import logicalSystems.mbc.MBCRuleStructures;
import main.newstrategy.CPLPBRuleChooser;
import main.newstrategy.mbc.simple.MBCSimpleStrategy;
import main.newstrategy.mbc.simple.TwoPremiseRuleApplicator;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.applicator.PBRuleApplicator;
import main.tableau.Method;

/**
 * A strategy for mCi based on MBCSimpleStrategy.
 * It uses the MCI rule structure.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class MCISimpleStrategy extends MBCSimpleStrategy {

    /**
     * @param method
     */
    public MCISimpleStrategy(Method method) {
        super(method);
        
        // initialize rule applicators
        List<IRuleApplicator> ruleApplicators = new ArrayList<IRuleApplicator>();
        ruleApplicators.add(new OnePremiseRuleApplicator(this,
                MCIRuleStructures.ONE_PREMISE_RULES));
//        ruleApplicators.add(new TopBottomOnePremiseRuleApplicator(this,
//                ClassicalRuleStructures.TOP_BOTTOM_ONE_PREMISE_RULE_LIST));
        ruleApplicators.add(new TwoPremiseRuleApplicator(this,
                MBCRuleStructures.TWO_PREMISE_RULES));
        
        setRuleApplicators(ruleApplicators);

        // initialize proof transformations
        List<IProofTransformation> proofTransformations = new ArrayList<IProofTransformation>();
        PBRuleApplicator pbr = new PBRuleApplicator(this, MBCRuleStructures.PB_RULES);
        pbr.setPbRuleChooser(new CPLPBRuleChooser());
        proofTransformations.add(pbr);
        setProofTransformations(proofTransformations);
    }

}
