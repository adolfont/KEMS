/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.c1.simple;

import java.util.ArrayList;
import java.util.List;

import logicalSystems.c1.C1RuleStructures;
import main.newstrategy.mbc.simple.TwoPremiseRuleApplicator;
import main.newstrategy.simple.SimpleStrategy;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.applicator.PBRuleApplicator;
import main.tableau.Method;

/**
 * A strategy for C1 based on SimpleStrategy. It uses the C1 rule structure. It
 * does not use the TopBottomOnePremiseRuleApplicator from SimpleStrategy, nor
 * SimplificationTwoPremiseRuleApplicator.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class C1SimpleStrategy extends SimpleStrategy {

	/**
	 * @param method
	 */
	public C1SimpleStrategy(Method method) {
		super();
		setMethod(method);

		// initialize rule applicators
		List<IRuleApplicator> ruleApplicators = new ArrayList<IRuleApplicator>();
		ruleApplicators.add(new OnePremiseRuleApplicator(this, C1RuleStructures.ONE_PREMISE_RULES));
		ruleApplicators.add(new TwoPremiseRuleApplicator(this, C1RuleStructures.TWO_PREMISE_RULES));
		ruleApplicators
				.add(new C1ThreePremiseRuleApplicator(this, C1RuleStructures.THREE_PREMISE_RULES));

		setRuleApplicators(ruleApplicators);

		// initialize proof transformations
		List<IProofTransformation> proofTransformations = new ArrayList<IProofTransformation>();

		PBRuleApplicator pbr = new PBRuleApplicator(this, C1RuleStructures.PB_RULES);
		// pbr.setPbRuleChooser(new MBCPBRuleChooser(this));
		proofTransformations.add(pbr);

		C1_T_NOT_1_PBRuleApplicator third_pbr = new C1_T_NOT_1_PBRuleApplicator(this, C1RuleStructures.THIRD_PB_RULES);
		// special_pbr.setPbRuleChooser(new MBCPBRuleChooser(this));
		proofTransformations.add(third_pbr);

		C1_T_NOT_ANY_PBRuleApplicator second_pbr = new C1_T_NOT_ANY_PBRuleApplicator(this,
				C1RuleStructures.SPECIAL_PB_RULES);
		// special_pbr.setPbRuleChooser(new MBCPBRuleChooser(this));
		proofTransformations.add(second_pbr);


		setProofTransformations(proofTransformations);

	}

}
