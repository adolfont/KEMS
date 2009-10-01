/*
 * Created on 18/01/2006
 *
 */
package main.newstrategy.simple.learning;

import java.util.Iterator;
import java.util.LinkedList;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.simple.backjumping.AbstractBackjumpingSimpleStrategy;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.strategy.NullClosedProofTree;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

/**
 * An implementation of a strategy with learning for classical logic.
 * 
 * Algorithm: <br/>1. Every node has a signed formula list and a "used/not used"
 * flag. <br/>2. The first signed formula of a left node is called a decision. A
 * node that contains a decision is called a "decison node". <br/>3. Whenever a
 * node closes, if it is the left child of one or more left children, replace
 * the top left child with a formula constructed using only used decisions. / \
 * T A1 F A1 / \ F B1 T B1 / . . . x
 * 
 * We have two or more used decisions T Ai or F Bj Build the formula <br/> A1 &
 * A2 & ... & Am -> B1 | B2 | ... | Bn
 * 
 * Replace the left and right children T A1 and F A1 By T A1 & A2 & ... & Am ->
 * B1 & B2 & ... & Bn and F A1 & A2 & ... & Am -> B1 & B2 & ... & Bn
 * respectively
 * 
 * Add the formulas from the used nodes to the left child. It will close as
 * expected.
 * 
 * 
 * The result is a comb-like proof.
 * 
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class LearningSimpleStrategy extends AbstractBackjumpingSimpleStrategy {

	/**
	 * Creates a learning simple strategy.
	 * 
	 * @param m -
	 *            a method
	 */
	public LearningSimpleStrategy(Method m) {
		super(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#finishBranch(main.proofTree.IProofTree)
	 */
	public void finishBranch(IProofTree closedBranchLastProofTreeNode) {

//		System.out.println("CLOSED BRANCH "
//				+ closedBranchLastProofTreeNode.getRoot());

		// every time a branch closes, marks current node parents as used.
		marksClosingNodeUsedParents(closedBranchLastProofTreeNode);

		// verify if learning can be applied and, if it can, apply it.
		verifyAndMaybeApplyLearning(closedBranchLastProofTreeNode);

		super.finishBranch(closedBranchLastProofTreeNode);
	}

	/**
	 * Verifies if learning can be applied and, if it can, applies it.
	 * 
	 * @param closedBranchLastProofTreeNode
	 */
	private void verifyAndMaybeApplyLearning(
			IProofTree closedBranchLastProofTreeNode) {

		if (satisfiesBasicLearningConditions(closedBranchLastProofTreeNode)) {
			applyLearning(closedBranchLastProofTreeNode);
		}

	}

	private void applyLearning(IProofTree current) {
		SignedFormulaList usedNodes = new SignedFormulaList();

		int learningBranchHeight = 0;

		// goes up until a decision node or a null parent is found
		while (current != null && isLeftNode(current)) {
			if (usedDecisionNodes.contains(current)) {
				usedNodes
						.add(0, (SignedFormula) current.getRoot().getContent());
			}
			current = current.getParent();
			learningBranchHeight++;
		}

		if (learningBranchHeight > 1) {
			// if (learningBranchHeight>1 && usedNodes.size() > 1) {
			Formula learnedFormula = createLearnedFormula(usedNodes, this
					.getSignedFormulaBuilder().getFormulaFactory());

			// ((FormulaReferenceClassicalProofTree) current).removeLeft();
			((FormulaReferenceClassicalProofTree) current).removeRight();

			updateOpenBranches();

			// old way
			// // restarts left branch with learned formula
			// ((FormulaReferenceClassicalProofTree) this.getProofTree())
			// .setClosed(false);
			// this.getOpenBranches().addLast(createsNewLeftBranch(current,
			// learnedFormula));

			// NEW WAY - 27-07-2006
			if (isDiscardClosedBranches()) {
				current.setLeft(NullClosedProofTree.INSTANCE);
			} else {
				((FormulaReferenceClassicalProofTree) this.getProofTree())
						.setClosed(false);
				((FormulaReferenceClassicalProofTree) current).removeLeft();
				// restarts left branch with learned formula
				this.getOpenBranches().addLast(
						createsNewLeftBranch(current, learnedFormula));

			}

			// removido - era para testes
			// setDiscardClosedBranches(true);
			// super.finishBranch(rightNew.getParent().getLeft());
			// rightNew.getParent().setLeft(NullClosedProofTree.INSTANCE);

			this.getOpenBranches().addLast(
					createsNewRightBranch(current, learnedFormula));

		}
	}

	private IProofTree createsNewLeftBranch(IProofTree current,
			Formula learnedFormula) {
		return ((FormulaReferenceClassicalProofTree) current)
				.addLeft(new SignedFormulaNode(getSignedFormulaBuilder()
						.getSignedFormulaFactory().createSignedFormula(
								ClassicalSigns.FALSE, learnedFormula),
						SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.LEARNING));
	}

	private IProofTree createsNewRightBranch(IProofTree current,
			Formula learnedFormula) {
		return ((FormulaReferenceClassicalProofTree) current)
				.addRight(new SignedFormulaNode(getSignedFormulaBuilder()
						.getSignedFormulaFactory().createSignedFormula(
								ClassicalSigns.TRUE, learnedFormula),
						SignedFormulaNodeState.NOT_ANALYSED, NamedOrigin.LEARNING));
	}

	private void updateOpenBranches() {
		this.setOpenBranches(new LinkedList<IProofTree>());
	}

	/**
	 * @param ptNode
	 * @return true if current node satisfies conditions for backjumping
	 */
	private boolean satisfiesBasicLearningConditions(IProofTree ptNode) {
		return ptNode.getParent() != null && isLeftNode(ptNode);
	}

	private static Formula createLearnedFormula(SignedFormulaList sfl,
			FormulaFactory ff) {
		FormulaList positives = new FormulaList();
		FormulaList negatives = new FormulaList();

		Iterator<SignedFormula> it = sfl.iterator();

		while (it.hasNext()) {
			SignedFormula f = (SignedFormula) it.next();

			if (f.getSign().equals(ClassicalSigns.TRUE)) {
				positives.add(f.getFormula());
			} else {
				negatives.add(f.getFormula());
			}
		}

		// positives conjunction
		Formula positiveConjunction = createIteratedFormula(positives,
				ClassicalConnectives.AND, ff);
		// negatives disjunction
		Formula negativeDisjunction = createIteratedFormula(negatives,
				ClassicalConnectives.OR, ff);

		if (positiveConjunction == null) {
			if (negativeDisjunction == null) {
				return null;
			} else {
				return negativeDisjunction;
				// return ff.createCompositeFormula(ClassicalConnectives.NOT,
				// negativeDisjunction);
			}
		} else {
			if (negativeDisjunction == null) {
				return ff.createCompositeFormula(ClassicalConnectives.NOT,
						positiveConjunction);
				// return positiveConjunction;
			} else {
				return ff.createCompositeFormula(ClassicalConnectives.IMPLIES,
						positiveConjunction, negativeDisjunction);

			}
		}

	}

	public SignedFormulaNode getContradiction() {
		return new SignedFormulaNode(this.getSignedFormulaBuilder()
				.getSignedFormulaFactory().createSignedFormula(
						ClassicalSigns.TRUE,
						this.getSignedFormulaBuilder().getFormulaFactory()
								.createCompositeFormula(
										ClassicalConnectives.BOTTOM)),
				SignedFormulaNodeState.FULFILLED, NamedOrigin.LEARNING);

	}

	public static Formula createIteratedFormula(FormulaList formulas,
			Connective conn, FormulaFactory ff) {
		if (formulas.size() == 0) {
			return null;
		} else {
			Formula result = formulas.get(formulas.size() - 1);
			for (int i = 1; i < formulas.size(); i++) {
				result = ff.createCompositeFormula(conn, formulas.get(formulas
						.size()
						- i - 1), result);
			}

			return result;
		}
	}

}

// /**
// * Verifies if learning can be applied and, if it can, applies it.
// *
// * @param closedBranchLastProofTreeNode
// */
// private void verifyAndMaybeApplyLearning(
// IProofTree closedBranchLastProofTreeNode) {
//
// // counter ++;
// //
// // if (counter > 1) return;
// if (satisfiesLearningConditions(closedBranchLastProofTreeNode)) {
//
// SignedFormulaList usedNodes = new SignedFormulaList();
//
// // System.err.println("Learn?");
// IProofTree current = closedBranchLastProofTreeNode;
//
// // if it does not have a left parent, no learning is possible
// if (current.getParent() == null) {
// // System.err.println("No learn");
// return;
// } else {
// // goes up until a decision node or a null parent is found
// while (current != null && isLeftNode(current)) {
// if (usedDecisionNodes.contains(current)) {
// usedNodes.add(0, (SignedFormula) current.getRoot()
// .getContent());
// // System.err.println(current.getRoot());
// } else {
// // System.err.println(current.getRoot() + " not used");
// }
// current = current.getParent();
// }
// }
//
// if (usedNodes.size() > 1) {
// Formula learnedFormula = createLearnedFormula(usedNodes, this
// .getSignedFormulaBuilder().getFormulaFactory());
// // System.err.println(learnedFormula);
// // System.err.println(current.getRoot());
// SignedFormula sTLearnedFormula = getSignedFormulaBuilder()
// .getSignedFormulaFactory().createSignedFormula(
// ClassicalSigns.TRUE, learnedFormula);
// SignedFormula sFLearnedFormula = getSignedFormulaBuilder()
// .getSignedFormulaFactory().createSignedFormula(
// ClassicalSigns.FALSE, learnedFormula);
// // System.err.println("RAIZ DO CURRENT: "+current.getRoot());
// ((FormulaReferenceClassicalProofTree) current).removeLeft();
// ((FormulaReferenceClassicalProofTree) current).removeRight();
//
// ((FormulaReferenceClassicalProofTree) current).setClosed(false);
//
// // System.err.println(current.getBranch());
//
// IProofTree leftNew = ((FormulaReferenceClassicalProofTree) current)
// .addLeft(new SignedFormulaNode(sFLearnedFormula,
// SignedFormulaNodeState.NOT_ANALYSED,
// Origin.LEARNING));
// IProofTree rightNew = ((FormulaReferenceClassicalProofTree) current)
// .addRight(new SignedFormulaNode(sTLearnedFormula,
// SignedFormulaNodeState.NOT_ANALYSED,
// Origin.LEARNING));
// // ((FormulaReferenceClassicalProofTree)current).setClosed(true);
//
// // atualizar open branches
// Iterator it = this.getOpenBranches().iterator();
// Set removed = new HashSet();
// while (it.hasNext()) {
// IProofTree discardedBranch = (IProofTree) it.next();
// // discardedBranch.addLast(getContradiction());
// removed.add(discardedBranch);
// }
// this.getOpenBranches().removeAll(removed);
//
//			
// ((FormulaReferenceClassicalProofTree) this.getProofTree())
// .setClosed(false);
// this.getOpenBranches().addLast(leftNew);
//			
// // removido - era para testes
// // setDiscardClosedBranches(true);
// // super.finishBranch(rightNew.getParent().getLeft());
// // rightNew.getParent().setLeft(NullClosedProofTree.INSTANCE);
//
// this.getOpenBranches().addLast(rightNew);
//
// // debugShowRight(rightNew);
//
// // current.getRight().
// // addLast(new SignedFormulaNode(sLearnedFormula,
// // SignedFormulaNodeState.NOT_ANALYSED, Origin.DEFINITION));
// // ((FormulaReferenceClassicalProofTree)
// // current.getRight()).removeSignedFormula(sLearnedFormula);
// }
//
// // closedBranchLastProofTreeNode = current;
// //
// // if (closedBranchLastProofTreeNode.getParent() != null
// // && isLeftNode(closedBranchLastProofTreeNode.getParent())) {
// // if
// // (!usedDecisionNodes.contains(closedBranchLastProofTreeNode.getParent()))
// // {
// // // SignedFormulaNode contradiction = new SignedFormulaNode(this
// // // .getSignedFormulaBuilder().getSignedFormulaFactory()
// // // .createSignedFormula(
// // // ClassicalSigns.TRUE,
// // // this.getSignedFormulaBuilder().getFormulaFactory()
// // // .createCompositeFormula(ClassicalConnectives.BOTTOM)),
// // // SignedFormulaNodeState.FULFILLED, Origin.DEFINITION);
// // closedBranchLastProofTreeNode.getParent().getParent().getRight().addLast(
// // contradiction);
// //
// // }
// // }
// }
//
// }
//
// private void debugShowRight(IProofTree branch) {
//	
// IProofTree parent = branch.getParent();
//	
// if (parent !=null){
// IProofTreeBasicIterator it = parent.getLocalIterator();
// while (it.hasNext()){
// System.err.println(it.next());
// }
// System.err.println("-------------------------------------------------------------------------");
// }
//	
//	
// }
