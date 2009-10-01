package main.newstrategy.c1.simple;

import logic.signedFormulas.SignedFormula;
import logicalSystems.c1.C1Rules;
import logicalSystems.c1.C1Signs;
import main.newstrategy.ISimpleStrategy;
import main.strategy.applicator.PBRuleApplicator;
import rules.NullRule;
import rules.Rule;
import rules.patterns.C1SignConsistencyPattern;
import rules.structures.PBRuleList;

/**
 * An extension to PBRuleApplicator for C1 that ignores formulas that are not of
 * the form T !(A&!A)
 * 
 * @author adolfo
 * 
 */
public class C1_T_NOT_1_PBRuleApplicator extends PBRuleApplicator {

	private C1SignConsistencyPattern _consPattern;

	public C1_T_NOT_1_PBRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
		super(strategy, ruleListName);
		_consPattern = new C1SignConsistencyPattern(C1Signs.TRUE);
	}

	protected Rule choosePBRule(PBRuleList PBRules, SignedFormula candidateChosen) {
		if (_consPattern.matches(candidateChosen)) {
			return C1Rules.T_NOT_2;
		} else
			return NullRule.INSTANCE;
	}

}
