/*
 * Created on 18/01/2006
 *
 */
package main.newstrategy.simple.newlearning;

import logic.formulas.Arity;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalRuleStructures;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.simple.SimpleStrategySimplificationTwoPremiseRuleApplicator;
import main.newstrategy.simple.backjumping.AbstractBackjumpingSimpleStrategy;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeBackwardNodeIterator;
import main.proofTree.origin.NamedOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.IClassicalProofTree;
import main.strategy.NullClosedProofTree;
import main.strategy.applicator.RuleSignedFormulaPair;
import main.tableau.Method;

/**
 * Another implementation of a strategy with learning for classical logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class NewLearningSimpleStrategy extends
		AbstractBackjumpingSimpleStrategy {

	/**
	 * Creates a backjumping simple strategy.
	 * 
	 * @param m -
	 *            a method
	 */
	public NewLearningSimpleStrategy(Method m) {
		super(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#finishBranch(main.proofTree.IProofTree)
	 */
	public void finishBranch(IProofTree closedBranchLastProofTreeNode) {

		IClassicalProofTree closedNode = (IClassicalProofTree) closedBranchLastProofTreeNode;

		SignedFormula closingReason = closedNode.getClosingReason();

		SignedFormula closingReasonOpposite = this.getSignedFormulaBuilder()
				.createOppositeSignedFormula(closingReason);

		SignedFormulaNode closingReasonNode = closedNode.getNode(closingReason);
		SignedFormulaNode closingReasonOppositeNode = closedNode
				.getNode(closingReasonOpposite);

		SignedFormula closingReasonOrigin = null, closingReasonOppositeOrigin = null;

		closingReasonOrigin = findUsefulOrigin(closingReasonNode.getOrigin()
				.getMain());
		closingReasonOppositeOrigin = findUsefulOrigin(closingReasonOppositeNode
				.getOrigin().getMain());

		if (closingReasonOrigin != null && closingReasonOppositeOrigin != null
				&& closingReasonOrigin != closingReasonOppositeOrigin) {
			applyLearning(closingReason, closingReasonOrigin,
					closingReasonOpposite, closingReasonOppositeOrigin);
		}

		
		// discards as much branches above as possible
		if (isDiscardClosedBranches()) {
			IProofTree current = closedBranchLastProofTreeNode;

			IProofTreeBackwardNodeIterator it = current
					.getBackwardNodeIterator();
			do {

				if (isRightNode(current)) {
					current.getParent().setLeft(NullClosedProofTree.INSTANCE);
				}

				current = it.previous();

			} while (current != null);

		}

	}

	private void applyLearning(SignedFormula closingReason,
			SignedFormula closingReasonOrigin,
			SignedFormula closingReasonOpposite,
			SignedFormula closingReasonOppositeOrigin) {
//		 System.err.println("CLOSING REASON: " + closingReason);
//		 System.err.println("CLOSING REASON ORIGIN: " + closingReasonOrigin);
//				
//		 System.err.println("CLOSING REASON OPPOSITE: " +
//		 closingReasonOpposite);
//		 System.err.println("CLOSING REASON OPPOSITE ORIGIN: "
//		 + closingReasonOppositeOrigin);

		SimpleStrategySimplificationTwoPremiseRuleApplicator ra = new SimpleStrategySimplificationTwoPremiseRuleApplicator(
				this, ClassicalRuleStructures.TWO_PREMISE_RULE_LIST);

//		try {
			RuleSignedFormulaPair pair1 = ra.tryToApplySomeRule(this
					.getProofTree(), this.getSignedFormulaBuilder(),
					closingReasonOpposite, closingReasonOrigin);
			// if (pair1 != null) {
			// System.err.println("RESULT 1: " + pair1.getSignedformula());
			// } else {
			// System.err.println("PAIR 1 null");
			// }

			RuleSignedFormulaPair pair2 = null;

			if (pair1 != null) {

				pair2 = ra.tryToApplySomeRule(this.getProofTree(), this
						.getSignedFormulaBuilder(), closingReason,
						closingReasonOppositeOrigin);
				
				// if (pair2 != null) {
				// System.err.println("RESULT 2: " + pair2.getSignedformula());
				// } else {
				// System.err.println("PAIR 2 null");
				// }
			}

			if (pair1 != null && pair2 != null) {
				SignedFormulaNode learnedNode = createLearnedFormula(pair1
						.getSignedformula(), pair2.getSignedformula());
				// before correction on 08-08-2006
//				this.getProofTree().addLast(learnedNode);
				// after correction on 08-08-2006
				this.getCurrent().getParent().addLast(learnedNode);
				
//				System.err.println(learnedNode.getContent());

				// before correction on 08-08-2006
//				Iterator it = this.getOpenBranches().iterator();
//
//				while (it.hasNext()) {
//					((ClassicalProofTree) it.next())
//							.addToPBCandidates((SignedFormula) learnedNode
//									.getContent());
//				}
				((ClassicalProofTree) this.getCurrent().getParent().getRight())
				.addToPBCandidates((SignedFormula) learnedNode
						.getContent());
				
			}

//		} catch (Exception e) {
//
//			System.err.println(((e.getClass() != null) ? e.getClass()
//					.getSimpleName() : "null exception class")
//					+ "  " + e.getMessage());
//
//			System.err.println("CLOSING REASON: " + closingReason);
//			System.err.println("CLOSING REASON ORIGIN: " + closingReasonOrigin);
//
//			System.err.println("CLOSING REASON OPPOSITE: "
//					+ closingReasonOpposite);
//			System.err.println("CLOSING REASON OPPOSITE ORIGIN: "
//					+ closingReasonOppositeOrigin);
//
//			// e.printStackTrace();
//		}

		// createLearnedFormula(sfn1, sfn2);
		//		
		// System.err.println(this.getProofTree().toStringShort());

	}

	private SignedFormulaNode createLearnedFormula(SignedFormula sf1,
			SignedFormula sf2) {
		Formula f1 = sf1.getFormula();

		if (sf1.getSign() == ClassicalSigns.FALSE) {
			f1 = this.getSignedFormulaBuilder().createCompositeFormula(
					ClassicalConnectives.NOT, f1);
		}

		Formula f2 = sf2.getFormula();

		if (sf2.getSign() == ClassicalSigns.FALSE) {
			f2 = this.getSignedFormulaBuilder().createCompositeFormula(
					ClassicalConnectives.NOT, f2);
		}

		Formula result = this.getSignedFormulaBuilder().createCompositeFormula(
				ClassicalConnectives.OR, f1, f2);

		SignedFormula sf = this.getSignedFormulaBuilder().createSignedFormula(
				ClassicalSigns.TRUE, result);

		SignedFormulaNode n = new SignedFormulaNode(sf,
				SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.LEARNING);

		// System.err.println(sf);

		return n;
	}

	private SignedFormula findUsefulOrigin(SignedFormulaNode sfnode) {

		if (sfnode == null) {
			return null;
		} else {
			SignedFormula sf = (SignedFormula) sfnode.getContent();
			if (sf.getFormula() instanceof CompositeFormula
					&& ((CompositeFormula) sf.getFormula()).getConnective()
							.getArity() == Arity.BINARY) {
				return sf;
			} else {
				return findUsefulOrigin(sfnode.getOrigin().getMain());
			}
		}
	}

}
