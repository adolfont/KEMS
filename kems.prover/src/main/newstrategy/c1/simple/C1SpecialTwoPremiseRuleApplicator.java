/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.c1.simple;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.C1Connectives;
import logicalSystems.c1.C1Signs;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IRuleApplicator;
import rules.KERuleRole;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.patterns.C1SignConsistencyAnyBinaryConnectivePattern;
import rules.structures.ConnectiveRoleSignRuleList;

/**
 * A two premise rule applicator for special C1 consistency rules
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class C1SpecialTwoPremiseRuleApplicator implements IRuleApplicator {

	private ISimpleStrategy strategy;

	private String ruleListName;

	private C1SignConsistencyAnyBinaryConnectivePattern pattern;

	/**
	 * @param strategy
	 * @param sfb
	 */
	public C1SpecialTwoPremiseRuleApplicator(ISimpleStrategy strategy,
			String ruleListName) {
		super();
		this.strategy = strategy;
		this.ruleListName = ruleListName;
	}

	private PBCandidateList mainCandidates;
	private int counterMainCandidates;

	/*
	 * (non-Javadoc)
	 * 
	 * @seemain.strategy.applicator.IRuleApplicator#applyAll(main.strategy.
	 * ClassicalProofTree, logic.signedFormulas.SignedFormulaBuilder)
	 */
	public boolean applyAll(ClassicalProofTree current, SignedFormulaBuilder sfb) {
		// faz o seguinte:
		// para cada main, procurar refer�ncias a um dos dois poss�veis
		// auxiliary candidates
		// se encontrar, ent�o aplicar
		boolean hasApplied = false;

		ConnectiveRoleSignRuleList twoPremiseRules = (ConnectiveRoleSignRuleList) strategy
				.getMethod().getRules().get(ruleListName);

		SignedFormula mainCandidate;

		initializeMainCandidates(current, null);

		while ((mainCandidate = nextMainCandidate(strategy.getProofTree(), null)) != null) {

//			System.out.println("MAIN CANDIDATE: " + mainCandidate);

			// first check: tries to get connective "%" in a s-formula such as F
			// !( (A%B)&!(A%B) )
			// it may get, for instance, a "%" in a T !( (A%B)&!(A->B) )
			Connective mainConnective = getInternConsistenyConnective(mainCandidate);

			if (mainConnective != null) {

				pattern = new C1SignConsistencyAnyBinaryConnectivePattern(
						C1Signs.FALSE, mainConnective);
				// second check: pattern F !( (A%B)&!(A%B) )
				if (pattern.matches(mainCandidate)) {

					FormulaSign mainSign = mainCandidate.getSign();

					Rule left_rule = twoPremiseRules.get(mainConnective,
							KERuleRole.LEFT, mainSign);
					Rule right_rule = twoPremiseRules.get(mainConnective,
							KERuleRole.RIGHT, mainSign);

					// verifies if left rule can be applied. If it can apply it.
					if (left_rule != null) {
						boolean appliedHere = tryToApplyTwoPremiseRule(strategy
								.getCurrent(), sfb, mainCandidate, left_rule);
						hasApplied = hasApplied || appliedHere;
						if (appliedHere)
							counterMainCandidates--;
					}
					// System.out.println("hasApplied1="+hasApplied);
					// System.out.println(counterMainCandidates + ": "
					// +mainCandidates);

					// verifies if right rule can be applied. If it can apply
					// it.
					if (right_rule != null) {
						boolean appliedHere = tryToApplyTwoPremiseRule(strategy
								.getCurrent(), sfb, mainCandidate, right_rule);
						hasApplied = hasApplied || appliedHere;
						if (appliedHere)
							counterMainCandidates--;
					}
					// System.out.println("hasApplied2="+hasApplied);
					// System.out.println(counterMainCandidates + ": "
					// +mainCandidates);
				}

			}
		}

		return hasApplied;

	}

	private Connective getInternConsistenyConnective(SignedFormula mainCandidate) {
		Formula mainFormula = mainCandidate.getFormula();
		// main is !X
		if (mainFormula instanceof CompositeFormula) {
			if (((CompositeFormula) mainFormula).getConnective().equals(
					C1Connectives.NOT)) {
				// second is X
				Formula secondFormula = mainFormula.getImmediateSubformulas()
						.get(0);
				// second is Y&Z
				if ((secondFormula instanceof CompositeFormula)
						&& (((CompositeFormula) secondFormula).getConnective()
								.equals(C1Connectives.AND))) {
					// third is Y
					Formula thirdFormula = secondFormula
							.getImmediateSubformulas().get(0);
					// third is T%W
					if (thirdFormula instanceof CompositeFormula) {
						// return "%", the connective
						return ((CompositeFormula) thirdFormula)
								.getConnective();
					} else
						return null;
				} else
					return null;

			} else
				return null;
		} else
			return null;
	}

	/**
	 * @param current
	 * @param object
	 */
	private void initializeMainCandidates(ClassicalProofTree proofTree,
			Object object) {
		mainCandidates = proofTree.getPBCandidates();
		counterMainCandidates = 0;
	}

	protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {
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

	// comentado em 22-06-2006
	// /**
	// * applies a two premise rule.
	// *
	// * @param proofTree
	// * @param sfb
	// * @param mainCandidate
	// * @param auxCandidate
	// * @param rule
	// */
	// private void applyTwoPremiseRule(ClassicalProofTree proofTree,
	// SignedFormulaBuilder sfb, SignedFormula mainCandidate,
	// SignedFormula auxCandidate, Rule rule) {
	//
	// TwoPremisesOneConclusionRule aRule = ((TwoPremisesOneConclusionRule)
	// rule);
	//
	// SignedFormulaList sfl = aRule.getAuxiliaryCandidates(sfb
	// .getSignedFormulaFactory(), sfb.getFormulaFactory(),
	// mainCandidate);
	//
	// // TODO apenas considera uma conclus�o
	// if (sfl.get(0) == auxCandidate) {
	// applyTwoPremiseRule(proofTree, sfb, mainCandidate, aRule, sfl,
	// auxCandidate);
	// }
	//
	// }

	/**
	 * @param proofTree
	 * @param sfb
	 * @param mainCandidate
	 * @param rule
	 * @return
	 */
	private boolean tryToApplyTwoPremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula mainCandidate, Rule rule) {

		boolean hasApplied = false;

		TwoPremisesOneConclusionRule aRule = ((TwoPremisesOneConclusionRule) rule);

		SignedFormulaList sfl = aRule.getAuxiliaryCandidates(sfb
				.getSignedFormulaFactory(), sfb.getFormulaFactory(),
				mainCandidate);

		SignedFormulaList result = getReferences(proofTree, sfl);

		if (result.size() > 0) {
			SignedFormula auxCandidate = (SignedFormula) result.get(0);

			hasApplied = applyTwoPremiseRule(proofTree, sfb, mainCandidate,
					aRule, result, auxCandidate);

		}

		return hasApplied;

	}

	/**
	 * Applies a two premise rulem given:
	 * 
	 * @param proofTree
	 * @param sfb
	 * @param mainCandidate
	 * @param rule
	 * @param aRule
	 * @param sfl
	 * @param auxCandidate
	 */
	private boolean applyTwoPremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula mainCandidate,
			TwoPremisesOneConclusionRule aRule, SignedFormulaList sfl,
			SignedFormula auxCandidate) {
		boolean hasApplied = false;
		sfl.add(0, mainCandidate);
		SignedFormulaList conclusion = (aRule.getPossibleConclusions(sfb
				.getSignedFormulaFactory(), sfb.getFormulaFactory(), sfl));

		// TODO sup�e apenas uma conclus�o
		if (conclusion != null && proofTree.getNode(conclusion.get(0)) == null) {
			proofTree.addLast(new SignedFormulaNode((SignedFormula) conclusion
					.get(0), SignedFormulaNodeState.NOT_ANALYSED, strategy
					.createOrigin(aRule, proofTree.getNode(mainCandidate),
							proofTree.getNode(auxCandidate))));
			// System.err.println(proofTree.getBranchId()+ " " +
			// conclusion.get(0));
			// try {
			// System.in.read();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// removes main from the list of PB candidates
			strategy.getCurrent().removeFromPBCandidates(mainCandidate,
					SignedFormulaNodeState.ANALYSED);

			hasApplied = true;
		}

		return hasApplied;
	}

	protected SignedFormulaList getReferences(ClassicalProofTree proofTree,
			SignedFormulaList sflInput) {

		// TODO do as in simple strategy? keep it in memory?
		// TODO do it top-down?

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
