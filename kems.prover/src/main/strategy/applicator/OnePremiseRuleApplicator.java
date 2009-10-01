/*
 * Created on 26/10/2005
 *
 */
package main.strategy.applicator;

import logic.formulas.CompositeFormula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.strategy.ClassicalProofTree;
import rules.NullRule;
import rules.Rule;
import rules.structures.OnePremiseRuleList;

/**
 * Applies one premise rules.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class OnePremiseRuleApplicator implements IRuleApplicator {

	private ISimpleStrategy strategy;

	private String ruleListName;

	/**
	 * @param strategy
	 * @param sfb
	 */
	public OnePremiseRuleApplicator(ISimpleStrategy strategy,
			String ruleListName) {
		super();
		this.strategy = strategy;
		this.ruleListName = ruleListName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.applicator.IRuleApplicator#applyAll(main.strategy.ClassicalProofTree,
	 *      logic.signedFormulas.SignedFormulaBuilder)
	 */
	public boolean applyAll(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb) {
		boolean hasApplied = false;

		int i = 0;
		// notice that I am using a non-recommended "i--"
		while (i < proofTree.getPBCandidates().size() && !proofTree.isClosed()) {

			// for each signed formula not used, if it accepts a one premise
			// rule, apply the rule and remove it form the list of candidates.

			SignedFormula sf = (SignedFormula) proofTree.getPBCandidates().get(
					i);

			// System.err.println(sf);
			if (chooseAndApplyOnePremiseRule(proofTree, sfb, sf)) {
				hasApplied = true;
				i--;
			}
			i++;
		}

		return hasApplied;
	}

	private boolean chooseAndApplyOnePremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula sf) {
		boolean hasApplied = false;
		Rule r = chooseOnePremiseRule(proofTree, sf);

		if (r != NullRule.INSTANCE) {
			hasApplied = true;

			SignedFormulaList sfl = r.getPossibleConclusions(sfb
					.getSignedFormulaFactory(), sfb.getFormulaFactory(),
					new SignedFormulaList(sf));

			// TODO Translate: "Modificacao (ver se sfl!=null) necessaria PARA MCI
			// pois MCIRules.T_NOT_CONS não garantido ser aplicada" 
			if (sfl != null) {

				proofTree.removeFromPBCandidates(sf,
						SignedFormulaNodeState.ANALYSED);

				for (int j = 0; j < sfl.size(); j++) {
					proofTree.addLast(new SignedFormulaNode(sfl.get(j),
							SignedFormulaNodeState.NOT_ANALYSED, strategy
									.createOrigin(r, proofTree.getNode(sf),
											null)));
				}
			} else {
				return false;
			}
		}
		return hasApplied;
	}

	private Rule chooseOnePremiseRule(ClassicalProofTree cpt, SignedFormula sf) {

		OnePremiseRuleList onePremiseRules = (OnePremiseRuleList) strategy
				.getMethod().getRules().get(ruleListName);

		if (sf.getFormula() instanceof CompositeFormula) {
			return onePremiseRules.get(sf.getSign(), ((CompositeFormula) sf
					.getFormula()).getConnective());
		}

		return NullRule.INSTANCE;

	}

}
