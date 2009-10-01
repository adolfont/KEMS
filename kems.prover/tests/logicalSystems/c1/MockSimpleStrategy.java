/*
 * Created on 01/11/2005
 *
 */
package logicalSystems.c1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.newstrategy.mbc.simple.MBC_PBRuleChooser;
import main.newstrategy.mbc.simple.TwoPremiseRuleApplicator;
import main.newstrategy.simple.SimpleStrategy;
import main.proofTree.IProofTree;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.applicator.PBRuleApplicator;
import main.tableau.Method;


public class MockSimpleStrategy extends SimpleStrategy {
	
	
	/**
     * @param method
     */
    public MockSimpleStrategy(Method method) {
        super();
        setOpenBranches(new LinkedList<IProofTree>());
        setMethod(method);

        // initialize rule applicators
        List<IRuleApplicator> ruleApplicators = new ArrayList<IRuleApplicator>();
        ruleApplicators.add(new OnePremiseRuleApplicator(this,
                C1RuleStructures.ONE_PREMISE_RULES));
        ruleApplicators.add(new TwoPremiseRuleApplicator(this,
                C1RuleStructures.TWO_PREMISE_RULES));

        
        setRuleApplicators(ruleApplicators);

        // initialize proof transformations
        List<IProofTransformation> proofTransformations = new ArrayList<IProofTransformation>();
        PBRuleApplicator pbr = new PBRuleApplicator(this, C1RuleStructures.PB_RULES);
        pbr.setPbRuleChooser(new MBC_PBRuleChooser(this));
        proofTransformations.add(pbr);
        setProofTransformations(proofTransformations);
    }

	public void setProofTree(ClassicalProofTree cpt) {
		super.setProofTree(cpt);
	}
    
    
    

}
