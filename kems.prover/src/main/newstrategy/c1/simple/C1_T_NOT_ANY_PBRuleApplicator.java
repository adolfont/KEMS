package main.newstrategy.c1.simple;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.signedFormulas.SignedFormula;
import logicalSystems.c1.C1Connectives;
import main.newstrategy.ISimpleStrategy;
import main.strategy.applicator.PBRuleApplicator;
import rules.KERuleRole;
import rules.NullRule;
import rules.Rule;
import rules.patterns.C1_T_NOT_ANY_Pattern;
import rules.structures.PBRuleList;

/**
 * An extension to PBRuleApplicator for C1 that ignores formulas that are not of
 * the form T !(A%B) for any connective % in {&,|,->}
 * 
 * @author adolfo
 * 
 */
public class C1_T_NOT_ANY_PBRuleApplicator extends PBRuleApplicator {

	private C1_T_NOT_ANY_Pattern _andPattern;
	private C1_T_NOT_ANY_Pattern _orPattern;
	private C1_T_NOT_ANY_Pattern _impliesPattern;

	public C1_T_NOT_ANY_PBRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
		super(strategy, ruleListName);
		_andPattern = new C1_T_NOT_ANY_Pattern(C1Connectives.AND, KERuleRole.LEFT);
		_orPattern = new C1_T_NOT_ANY_Pattern(C1Connectives.OR, KERuleRole.LEFT);
		_impliesPattern = new C1_T_NOT_ANY_Pattern(C1Connectives.IMPLIES, KERuleRole.LEFT);
	}

	protected Rule choosePBRule(PBRuleList PBRules, SignedFormula candidateChosen) {
		Connective mainConnective = ((CompositeFormula) candidateChosen.getFormula()).getConnective();
		if (mainConnective.equals(C1Connectives.NOT)
				&& (_andPattern.matchesMain(candidateChosen) || _orPattern.matchesMain(candidateChosen) || _impliesPattern
						.matchesMain(candidateChosen))) {
			mainConnective = ((CompositeFormula) ((CompositeFormula) candidateChosen.getFormula())
					.getImmediateSubformulas().get(0)).getConnective();
			return PBRules.get(candidateChosen.getSign(), mainConnective);
		} else
			return NullRule.INSTANCE;
	}

}
