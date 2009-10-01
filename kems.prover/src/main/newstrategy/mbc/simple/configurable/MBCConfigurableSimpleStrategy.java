package main.newstrategy.mbc.simple.configurable;

import java.util.ArrayList;
import java.util.List;

import logicalSystems.mbc.MBCRuleStructures;
import main.newstrategy.cpl.simple.configurable.ConfigurableSimpleStrategy;
import main.newstrategy.cpl.simple.configurable.ConfigurableTwoPremiseRuleApplicator;
import main.newstrategy.mbc.simple.MBC_PBRuleChooser;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.applicator.PBRuleApplicator;
import main.tableau.Method;

public class MBCConfigurableSimpleStrategy extends ConfigurableSimpleStrategy {

	public MBCConfigurableSimpleStrategy(Method m) {
		super(m);

		// initialize rule applicators
		List<IRuleApplicator> ruleApplicators = new ArrayList<IRuleApplicator>();
		ruleApplicators.add(new OnePremiseRuleApplicator(this,
				MBCRuleStructures.ONE_PREMISE_RULES));
		// ruleApplicators.add(new TopBottomOnePremiseRuleApplicator(this,
		// ClassicalRuleStructures.TOP_BOTTOM_ONE_PREMISE_RULE_LIST));

		// trocado pelo de baixo
		// ruleApplicators.add(new TwoPremiseRuleApplicator(this,
		// MBCRuleStructures.TWO_PREMISE_RULES));

		ruleApplicators
				.add(twoPremiseRuleApplicator = new ConfigurableTwoPremiseRuleApplicator(
						this, MBCRuleStructures.TWO_PREMISE_RULES, null));

		setRuleApplicators(ruleApplicators);

		// initialize proof transformations
		List<IProofTransformation> proofTransformations = new ArrayList<IProofTransformation>();
		PBRuleApplicator pbr = new PBRuleApplicator(this,
				MBCRuleStructures.PB_RULES);
		pbr.setPbRuleChooser(new MBC_PBRuleChooser(this));
		proofTransformations.add(pbr);
		setProofTransformations(proofTransformations);

	}

}
