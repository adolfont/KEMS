/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.c1.simple;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.C1Connectives;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IRuleApplicator;
import rules.KERuleRole;
import rules.Rule;
import rules.ThreePremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;

/**
 * A three premise rule applicator.
 * 
 * The current version (2009 May 04) works only for C1... The base class is
 * TwoPremiseRuleApplicator. The *"REPEATED CODE" TO DO comments are for helping
 * an unification in the future.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class C1ThreePremiseRuleApplicator implements IRuleApplicator {

	private ISimpleStrategy strategy;

	private String ruleListName;

	public C1ThreePremiseRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
		super();
		this.strategy = strategy;
		this.ruleListName = ruleListName;
	}

	private PBCandidateList mainCandidates;
	private int counterMainCandidates;

	// TODO ALMOST ALL IS REPEATED CODE
	public boolean applyAll(ClassicalProofTree current, SignedFormulaBuilder sfb) {
		// faz o seguinte:
		// para cada main, procurar referencias aos dois necessarios
		// auxiliary candidates
		// se encontrar, entao aplicar
		// exemplo: C1 T_NOT_ANY_ROLE rules
		// se encontrar T!(A%B)
		// procura: T(A%B) e T!(<chosen by role>&!<chosen by role>)
		// onde <chosen by role> é A ou B dependendo do role

		boolean hasApplied = false;

		ConnectiveRoleSignRuleList twoPremiseRules = (ConnectiveRoleSignRuleList) strategy.getMethod()
				.getRules().get(ruleListName);

		SignedFormula mainCandidate;

		initializeMainCandidates(current, null);

		while ((mainCandidate = nextMainCandidate(strategy.getProofTree(), null)) != null) {

//			System.out.println("MAIN CANDIDATE FOR 3P-RuleApplctr: " + mainCandidate);

			// Note that this connective is essential here!
			Connective notConnective = ((CompositeFormula) mainCandidate.getFormula()).getConnective();

			if (!notConnective.equals(C1Connectives.NOT)) {
//				 System.out.println("Not a C1 3-premise rule!");
				continue;
			}

			if (!(mainCandidate.getFormula().getImmediateSubformulas().get(0) instanceof CompositeFormula)){
//				System.out.println("Not a C1 3-premise rule!");
				continue;
			}
			
			CompositeFormula mainFormulaSubformula = (CompositeFormula) mainCandidate.getFormula()
					.getImmediateSubformulas().get(0);
			Connective mainConnective = mainFormulaSubformula.getConnective();
			FormulaSign mainSign = mainCandidate.getSign();

			Rule left_rule = twoPremiseRules.get(mainConnective, KERuleRole.LEFT, mainSign);
			Rule right_rule = twoPremiseRules.get(mainConnective, KERuleRole.RIGHT, mainSign);

			// verifies if left rule can be applied. If it can, apply it.
			if (left_rule != null) {
				boolean appliedHere = tryToApplyThreePremiseRule(strategy.getCurrent(), sfb, mainCandidate,
						left_rule);
				hasApplied = hasApplied || appliedHere;
				if (appliedHere)
					counterMainCandidates--;
			}

			// verifies if right rule can be applied. If it can, apply it.
			if (right_rule != null) {
				boolean appliedHere = tryToApplyThreePremiseRule(strategy.getCurrent(), sfb, mainCandidate,
						right_rule);
				hasApplied = hasApplied || appliedHere;
				if (appliedHere)
					counterMainCandidates--;
			}
		}

		return hasApplied;
	}

	// TODO REPEATED CODE
	/**
	 * @param current
	 * @param object
	 */
	private void initializeMainCandidates(ClassicalProofTree proofTree, Object object) {
		mainCandidates = proofTree.getPBCandidates();
		counterMainCandidates = 0;
	}

	// TODO DID NOT CHANGE
	protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree, SignedFormula auxCandidate) {
		// System.out.println(counterMainCandidates + ": " + mainCandidates);
		if (counterMainCandidates < mainCandidates.size()) {
			// System.out.println("Chosen:"
			// + mainCandidates.get(counterMainCandidates));
			return mainCandidates.get(counterMainCandidates++);
		}
		// else {
		// System.out.println("Chosen: none");
		// }

		return null;
	}

	private boolean tryToApplyThreePremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula mainCandidate, Rule rule) {

		boolean hasApplied = false;
		SignedFormulaList result;

		ThreePremisesOneConclusionRule aRule = ((ThreePremisesOneConclusionRule) rule);

		// FIND aux1Candidate
		// EASY: main is T!(A%B), aux1Candidate is T(A%B)

		SignedFormula aux1Candidate = sfb.getSignedFormulaFactory().createSignedFormula(
				mainCandidate.getSign(), mainCandidate.getFormula().getImmediateSubformulas().get(0));

		SignedFormulaList sfl = aRule.getPattern().getAuxiliaryCandidates(
				sfb.getSignedFormulaFactory(), sfb.getFormulaFactory(), mainCandidate, aux1Candidate);

		sfl.add(aux1Candidate);
		result = getReferences(proofTree, sfl);

		// se achou todas as sfl's da lista...
		if (result.size() == sfl.size()) {
			SignedFormula aux2Candidate = (SignedFormula) sfl.get(0);

			hasApplied = applyThreePremiseRule(proofTree, sfb, mainCandidate, aRule, result,
					aux1Candidate, aux2Candidate);

		}

		return hasApplied;

	}

	/**
	 * Applies a three premise rule given:
	 * 
	 * @param proofTree
	 * @param sfb
	 * @param mainCandidate
	 * @param rule
	 * @param aRule
	 * @param sfl
	 * @param auxCandidate
	 */
	private boolean applyThreePremiseRule(ClassicalProofTree proofTree, SignedFormulaBuilder sfb,
			SignedFormula mainCandidate, ThreePremisesOneConclusionRule aRule, SignedFormulaList sfl,
			SignedFormula aux1Candidate, SignedFormula aux2Candidate) {
		boolean hasApplied = false;
		sfl.add(0, mainCandidate);
		SignedFormulaList conclusion = (aRule.getPossibleConclusions(sfb.getSignedFormulaFactory(), sfb
				.getFormulaFactory(), sfl));

		if (conclusion != null && proofTree.getNode(conclusion.get(0)) == null) {
			proofTree.addLast(new SignedFormulaNode((SignedFormula) conclusion.get(0),
					SignedFormulaNodeState.NOT_ANALYSED, strategy.createOrigin(aRule, proofTree
							.getNode(mainCandidate), proofTree.getNode(aux1Candidate)
							 , proofTree.getNode(aux2Candidate)
							)));
			strategy.getCurrent().removeFromPBCandidates(mainCandidate, SignedFormulaNodeState.ANALYSED);

			hasApplied = true;
		}

		return hasApplied;
	}

	// TODO REPEATED CODE
	protected SignedFormulaList getReferences(ClassicalProofTree proofTree, SignedFormulaList sflInput) {

		SignedFormulaList sflResult = new SignedFormulaList();

		// iterates (bottom up) over the branch
		IProofTreeVeryBasicIterator it = proofTree.getTopDownIterator();

		while (it.hasNext()) {

			SignedFormulaNode sfn = (SignedFormulaNode) it.next();

			if (sflInput.contains((SignedFormula) sfn.getContent())) {
				sflResult.add((SignedFormula) sfn.getContent());
			}

		}
		return sflResult;
	}

}
