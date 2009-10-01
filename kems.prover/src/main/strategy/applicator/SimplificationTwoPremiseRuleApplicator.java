/*
 * Created on 28/10/2005
 *
 */
package main.strategy.applicator;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.ProofTreeGlobalIterator;
import main.strategy.ClassicalProofTree;
import main.strategy.memorySaver.ReferenceFinder;
import rules.KERuleRole;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;

/**
 * A rule applicator that applies simplification two premise rules.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public abstract class SimplificationTwoPremiseRuleApplicator implements
		IRuleApplicator {

	private ISimpleStrategy strategy;

	private String ruleListName;

	/**
	 * @param strategy
	 * @param sfb
	 */
	public SimplificationTwoPremiseRuleApplicator(ISimpleStrategy strategy,
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

		Formula topFormula = sfb
				.createCompositeFormula(ClassicalConnectives.TOP);
		Formula bottomFormula = sfb
				.createCompositeFormula(ClassicalConnectives.BOTTOM);

		SignedFormula T_TOP = sfb.createSignedFormula(ClassicalSigns.TRUE,
				topFormula);
		SignedFormula F_BOT = sfb.createSignedFormula(ClassicalSigns.FALSE,
				bottomFormula);

		ProofTreeGlobalIterator it = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator(), firstInTheBranch = (ProofTreeGlobalIterator) proofTree
				.getGlobalIterator();

		while (!proofTree.isClosed()) {

			SignedFormulaNode sfn = null;
			if (it.hasNextLeft()) {
				sfn = (SignedFormulaNode) it.nextRight();
			} else if (firstInTheBranch.hasPrevious()) {
				sfn = (SignedFormulaNode) firstInTheBranch.previous();
			} else
				break;

			// if it is T_TOP or F_BOT, forget

			if (((SignedFormula) sfn.getContent() == T_TOP)
					|| ((SignedFormula) sfn.getContent() == F_BOT)) {
				continue;
			}

			SignedFormula auxCandidate = (SignedFormula) sfn.getContent();
			SignedFormula mainCandidate;

			initializeMainCandidates(proofTree, auxCandidate);

			while ((mainCandidate = nextMainCandidate(proofTree, auxCandidate)) != null) {
				RuleSignedFormulaPair pair = tryToApplySomeRule(proofTree, sfb,
						auxCandidate,
						mainCandidate);
				
				if (pair!=null){
					
					SignedFormulaNode n2 = new SignedFormulaNode(pair.getSignedformula(),
							SignedFormulaNodeState.NOT_ANALYSED, strategy
									.createOrigin(pair.getRule(), proofTree
											.getNode(mainCandidate), proofTree
											.getNode(auxCandidate)));

					
					proofTree.addLast(n2);
					proofTree.removeFromPBCandidates(mainCandidate,
					SignedFormulaNodeState.ANALYSED);
					hasApplied = true;
				}
			}
		}

		return hasApplied;
	}

	public RuleSignedFormulaPair tryToApplySomeRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, 
			SignedFormula auxCandidate,
			SignedFormula mainCandidate) {
		
		RuleSignedFormulaPair result = null;
		
		ConnectiveRoleSignRuleList rules = (ConnectiveRoleSignRuleList) strategy
				.getMethod().getRules().get(ruleListName);

		SignedFormulaList sflToApply = new SignedFormulaList(mainCandidate);
		sflToApply.add(auxCandidate);

		FormulaList fl = ReferenceFinder.getInstance()
				.getSubformulaParentReferences(proofTree,
						auxCandidate.getFormula(), mainCandidate);

		if (fl.size() >= 1) {
			CompositeFormula f = (CompositeFormula) fl.get(0);
			Connective conn = f.getConnective();

			Formula f_left = (Formula) f.getImmediateSubformulas().get(0);
			Formula f_right = null;
			if (f.getImmediateSubformulas().size() > 1) {
				f_right = (Formula) f.getImmediateSubformulas().get(1);
			}
			KERuleRole role = (auxCandidate.getFormula() == f_left) ? KERuleRole.LEFT
					: ((f_right != null) && (auxCandidate.getFormula() == f_right)) ? KERuleRole.RIGHT
							: null;
			FormulaSign sign = auxCandidate.getSign();

			TwoPremisesOneConclusionRule r1 = (TwoPremisesOneConclusionRule) rules
					.get(conn, role, sign);

			SignedFormulaList sflResult = r1.getPossibleConclusions(sfb
					.getSignedFormulaFactory(), sfb.getFormulaFactory(),
					sflToApply, f);
			if (sflResult != null) {
				SignedFormula conclusion = sflResult.get(0);

				
//				SignedFormulaNode n = new SignedFormulaNode(conclusion,
//						SignedFormulaNodeState.NOT_ANALYSED, strategy
//								.createOrigin(r1, proofTree
//										.getNode(mainCandidate), proofTree
//										.getNode(auxCandidate)));
//				proofTree.addLast(n);
				
				result = new RuleSignedFormulaPair(r1, conclusion);
				

				decrementPositionMainCandidates();
//				proofTree.removeFromPBCandidates(mainCandidate,
//						SignedFormulaNodeState.ANALYSED);

//				hasApplied = true;
			}

		}
//		return hasApplied;
		
		return result;
	}

	protected abstract void initializeMainCandidates(
			ClassicalProofTree proofTree, SignedFormula auxCandidate);

	protected abstract SignedFormula nextMainCandidate(
			ClassicalProofTree proofTree, SignedFormula auxCandidate);

	protected abstract void decrementPositionMainCandidates();

	public SignedFormulaList getParentReferences(ClassicalProofTree proofTree,
			Formula f) {
		return strategy.getParentReferences(proofTree, f);
	}

}
