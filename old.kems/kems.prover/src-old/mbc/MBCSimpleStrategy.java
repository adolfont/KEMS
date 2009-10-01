/*
 * Created on 30/09/2005
 *
 */
package main.strategy.simple.mbc;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.PBCandidatesSignedFormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.mbc.MBCConnectives;
import main.exceptions.TPException;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import main.strategy.ClassicalProofTree;
import main.strategy.simple.SimpleStrategy;
import main.tableau.Method;
import rules.KERuleRole;
import rules.Rule;
import rules.TwoPremisesOneConclusionRule;
import rules.structures.ConnectiveRoleSignRuleList;

/**
 * First strategy for mbC. Based on SimpleStratgey but with variations provided
 * by this class and by an aspect.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MBCSimpleStrategy extends SimpleStrategy {

	/** Creates an MBCSimpleStrategy
	 * @param m
	 */
	public MBCSimpleStrategy(Method m) {
		super(m);
	}

	/**
	 * Applies as much two premise rules as possible.
	 * 
	 * @param proofTree -
	 *            the current proof tree
	 * @param sfb -
	 *            the formula builder
	 * @return true if any rule has been applied
	 */
	protected final boolean applySimpleTwoPremiseRules(
			ClassicalProofTree proofTree, SignedFormulaBuilder sfb) {

		// faz o seguinte:
		// para cada main, procurar referências a um dos dois possíveis
		// auxiliary candidates
		// se encontrar, então aplicar
		boolean hasApplied = false;

		ConnectiveRoleSignRuleList twoPremiseRules = (ConnectiveRoleSignRuleList) getMethod()
				.getRules().get("twoPremiseRules");

		SignedFormula mainCandidate;

		initializeMainCandidates(proofTree, null);

		while ((mainCandidate = nextMainCandidate(proofTree, null)) != null) {

			Connective mainConnective = ((CompositeFormula) mainCandidate
					.getFormula()).getConnective();
			FormulaSign mainSign = mainCandidate.getSign();

			// only for mbc
			if (mainConnective == MBCConnectives.CONSISTENCY) {
//				System.err.println("old PB candidates here "
//						+ proofTree.getPBCandidates());
				proofTree.removeFromPBCandidates(mainCandidate,
						SignedFormulaNodeState.FULFILLED);
//				System.err.println("Removeu "
//						+ proofTree.getNode(mainCandidate));
//				System.err.println("new PB candidates here "
//						+ proofTree.getPBCandidates());
			} 
//				else {
//				System.err
//						.println("Deixou " + proofTree.getNode(mainCandidate));
//			}

			Rule left_rule = twoPremiseRules.get(mainConnective,
					KERuleRole.LEFT, mainSign);
			Rule right_rule = twoPremiseRules.get(mainConnective,
					KERuleRole.RIGHT, mainSign);

			//			System.err.println("Main " + mainCandidate);

//			SignedFormulaList sfl = new SignedFormulaList();
			// verifies if left rule can be applied. If it can apply it.
			if (left_rule != null) {
				hasApplied = hasApplied
						|| tryToApplyTwoPremiseRule(proofTree, sfb,
								mainCandidate, left_rule);
			}
			//			System.err.println("has applied "+hasApplied);
			// verifies if right rule can be applied. If it can apply it.
			if (right_rule != null) {
				hasApplied = hasApplied
						|| tryToApplyTwoPremiseRule(proofTree, sfb,
								mainCandidate, right_rule);
			}
			//			System.err.println("has applied "+hasApplied);

		}

		return hasApplied;
	}

	/**
	 * applies a two premise rule.
	 * 
	 * @param proofTree
	 * @param sfb
	 * @param mainCandidate
	 * @param auxCandidate
	 * @param rule
	 */
	private void applyTwoPremiseRule(ClassicalProofTree proofTree,
			SignedFormulaBuilder sfb, SignedFormula mainCandidate,
			SignedFormula auxCandidate, Rule rule) {

		TwoPremisesOneConclusionRule aRule = ((TwoPremisesOneConclusionRule) rule);

		SignedFormulaList sfl = aRule.getAuxiliaryCandidates(sfb
				.getSignedFormulaFactory(), sfb.getFormulaFactory(),
				mainCandidate);

		// TODO apenas considera uma conclusão
		if (sfl.get(0) == auxCandidate) {
			applyTwoPremiseRule(proofTree, sfb, mainCandidate, aRule, sfl,
					auxCandidate);
		}

	}

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

		// TODO supõe apenas uma conclusão
		if (proofTree.getNode(conclusion.get(0)) == null) {
			proofTree.addLast(new SignedFormulaNode((SignedFormula) conclusion
					.get(0), SignedFormulaNodeState.NOT_ANALYSED, createOrigin(
					aRule, proofTree.getNode(mainCandidate), proofTree
							.getNode(auxCandidate))));
			hasApplied = true;
		}

		// removes main from the list of PB candidates
		getCurrent().removeFromPBCandidates(mainCandidate,
				SignedFormulaNodeState.ANALYSED);

		return hasApplied;
	}
	
	/**
	 * Applies as much two premise rules as possible. Does it by brute force.
	 * 
	 * @param proofTree -
	 *            the current proof tree
	 * @param sfb -
	 *            the formula builder
	 * @return true if any rule has been applied
	 */
	protected final boolean applySimpleTwoPremiseRules_BruteForce(
			ClassicalProofTree proofTree, SignedFormulaBuilder sfb) {

		boolean hasApplied = false;

		ConnectiveRoleSignRuleList twoPremiseRules = (ConnectiveRoleSignRuleList) getMethod()
				.getRules().get("twoPremiseRules");

		SignedFormula mainCandidate;

		initializeMainCandidates(proofTree, null);

		while ((mainCandidate = nextMainCandidate(proofTree, null)) != null) {

			IProofTreeVeryBasicIterator it = proofTree
					.getTopDownIterator();

			while (!proofTree.isClosed() && it.hasNext()) {
				SignedFormulaNode sfn = null;

				sfn = (SignedFormulaNode) it.next();

				SignedFormula auxCandidate = (SignedFormula) sfn.getContent();

				//				System.err.println("PAR " + mainCandidate + " " +
				// auxCandidate);

				// if main and aux are the same formula, ignore this pair
				if (auxCandidate == mainCandidate) {
					continue;
				}

				if (!(mainCandidate.getFormula() instanceof CompositeFormula)) {
					throw new TPException(
							"Main candidate to two premise rule must be a composite formula.");
				}

				// see which rule can be applied to this formula as main
				// see if the auxcandidate can be used as minor premise
				// if true, apply
				// continue

				Connective mainConnective = ((CompositeFormula) mainCandidate
						.getFormula()).getConnective();
				FormulaSign mainSign = mainCandidate.getSign();

				Rule left_rule = twoPremiseRules.get(mainConnective,
						KERuleRole.LEFT, mainSign);
				Rule right_rule = twoPremiseRules.get(mainConnective,
						KERuleRole.RIGHT, mainSign);

				// verifies if left rule can be applied. If it can apply it.
				if (left_rule != null) {
					applyTwoPremiseRule(proofTree, sfb, mainCandidate,
							auxCandidate, left_rule);
				}
				// verifies if right rule can be applied. If it can apply it.
				if (right_rule != null) {
					applyTwoPremiseRule(proofTree, sfb, mainCandidate,
							auxCandidate, right_rule);
				}

			}
		}

		return hasApplied;
	}

	/**
	 * Returns a list containing all signed formulas in sflInput that appear in
	 * the branch whose last node is proofTree.
	 * 
	 * @param proofTree -
	 *            last node of the branch
	 * @param sflInput -
	 *            list of signed formulas to search for.
	 * @return a list containing all signed formulas in sflInput that appear in
	 *         the branch whose last node is proofTree.
	 */
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

	/**
	 * Sets the list of main candidates.
	 * 
	 * @param mc -
	 *            new list of main candidates
	 */
	protected void setMainCandidates(PBCandidatesSignedFormulaList mc) {
		mainCandidates = mc;
	}

	/**
	 * @return list of main candidates
	 */
	protected SignedFormulaList getMainCandidates() {
		return mainCandidates;
	}

	/**
	 * sets the new value for the counterMainCandidates
	 * 
	 * @param cmc -
	 *            new value
	 */
	protected void setCounterMainCandidates(int cmc) {
		counterMainCandidates = cmc;
	}

}
