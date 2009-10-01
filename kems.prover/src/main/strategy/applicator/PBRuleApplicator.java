/*
 * Created on 28/10/2005
 *
 */
package main.strategy.applicator;

import logic.formulas.CompositeFormula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalRules;
import main.newstrategy.CPLPBRuleChooser;
import main.newstrategy.IPBRuleChooser;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.strategy.ClassicalProofTree;
import rules.NullRule;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.PBRuleList;

/**
 * This class is used to apply the PB rule
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class PBRuleApplicator implements IProofTransformation {

	private ISimpleStrategy strategy;

	private String ruleListName;

	private IPBRuleChooser pbRuleChooser;

	/**
	 * @param strategy
	 * @param sfb
	 */
	public PBRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
		super();
		this.strategy = strategy;
		this.ruleListName = ruleListName;
		this.pbRuleChooser = new CPLPBRuleChooser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemain.strategy.applicator.IProofTransformation#apply(main.strategy.
	 * ClassicalProofTree, logic.signedFormulas.SignedFormulaBuilder)
	 */
	public boolean apply(ClassicalProofTree current, SignedFormulaBuilder sfb) {
		if (current.getPBCandidates().size() > 0) {
			current.getPBCandidates().sort(strategy.getComparator());
			return tryToApplyPBOnce(current, sfb, current.getPBCandidates());
		}
		return false;
	}

	protected boolean tryToApplyPBOnce(ClassicalProofTree current, SignedFormulaBuilder sfb,
			PBCandidateList candidates) {
		PBRuleList PBRules = (PBRuleList) strategy.getMethod().getRules().get(ruleListName);
		boolean foundRule = false;
		SignedFormula candidateChosen = null;
		Rule ruleChosen = NullRule.INSTANCE;
		SignedFormula aux = null;
		TwoPremisesOneConclusionRule r = null;
		SignedFormula auxOpposite = null, conclusion = null;
		int candidateIndex = 0;

		// looks for a rule that can be applied
		while (candidateIndex < candidates.size()) {
			candidateChosen = candidates.get(candidateIndex);
//			 System.out.println("main candidate chosen: "+ candidateChosen);
			candidateIndex++;
			ruleChosen = choosePBRule(PBRules, candidateChosen);
//			 System.out.println("rule chosen:"+ruleChosen);
			if (ruleChosen != NullRule.INSTANCE) {
				aux = ((TwoPremisesOneConclusionRule) ruleChosen).getAuxiliaryCandidates(
						sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(), candidateChosen).get(0);
				if ((strategy.getCurrent().contains(aux) || strategy.getCurrent().contains(
						sfb.createOppositeSignedFormula(aux)))) {
					continue;
				}
				if (pbRuleChooser.canApply(ruleChosen, candidateChosen, sfb)) {
					r = (TwoPremisesOneConclusionRule) ruleChosen;
					auxOpposite = sfb.createOppositeSignedFormula(aux);
					// Verifica se conclusao ja esta na ProofTree - se ja
					// estiver, nao aplica PB
					SignedFormulaList sfl = new SignedFormulaList(candidateChosen);
					sfl.add(aux);
					conclusion = r.getPossibleConclusions(sfb.getSignedFormulaFactory(),
							sfb.getFormulaFactory(), sfl).get(0);
					if (strategy.getCurrent().getNode(conclusion) == null) {
						foundRule = true;
						break;
					}
				}
			}
		}
		if (!foundRule || ruleChosen == NullRule.INSTANCE) {
			return false;
		} else
			return applyPB(current, sfb, PBRules, candidateChosen, aux, r, auxOpposite, conclusion);
	}

	private boolean applyPB(ClassicalProofTree current, SignedFormulaBuilder sfb, PBRuleList PBRules,
			SignedFormula candidateChosen, SignedFormula aux, TwoPremisesOneConclusionRule r,
			SignedFormula auxOpposite, SignedFormula conclusion) {
		ClassicalProofTree right = (ClassicalProofTree) current.addRight(new SignedFormulaNode(
				auxOpposite, SignedFormulaNodeState.NOT_ANALYSED, strategy
				// TODO informar a regra (r) que motivou o uso de PB?
						.createOrigin(ClassicalRules.PB, current.getNode(candidateChosen), null)));

		Rule complementary = PBRules.getComplementary(r);
		if (complementary != null) {
			SignedFormulaList sflComplementary = new SignedFormulaList(candidateChosen);
			sflComplementary.add(auxOpposite);
			SignedFormula complementaryConclusion = complementary.getPossibleConclusions(
					sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(), sflComplementary).get(0);
			right.addLast(new SignedFormulaNode(complementaryConclusion,
					SignedFormulaNodeState.NOT_ANALYSED, strategy.createOrigin(complementary, current
							.getNode(candidateChosen), right.getNode(auxOpposite))));
		}
		// System.err.println("current PB: "+ getCurrent().getPBCandidates());
		ClassicalProofTree left = (ClassicalProofTree) current.addLeft(new SignedFormulaNode(aux,
				SignedFormulaNodeState.NOT_ANALYSED, strategy.createOrigin(ClassicalRules.PB, current
						.getNode(candidateChosen), null)));
		// System.err.println("Adds to left: "+ aux);
		left.addLast(new SignedFormulaNode(conclusion, SignedFormulaNodeState.NOT_ANALYSED, strategy
				.createOrigin(r, current.getNode(candidateChosen), left.getNode(aux))));
		// System.err.println("Adds to left: "+ conclusion);
		strategy.getOpenBranches().addFirst(right);
		left.removeFromPBCandidates(candidateChosen, SignedFormulaNodeState.ANALYSED);
		right.removeFromPBCandidates(candidateChosen, SignedFormulaNodeState.ANALYSED);
		strategy.setCurrent(left);
		return true;
	}

	protected Rule choosePBRule(PBRuleList PBRules, SignedFormula candidateChosen) {
		return PBRules.get(candidateChosen.getSign(), ((CompositeFormula) candidateChosen.getFormula())
				.getConnective());
	}

	public void setPbRuleChooser(IPBRuleChooser pbRuleChooser) {
		this.pbRuleChooser = pbRuleChooser;
	}
}
