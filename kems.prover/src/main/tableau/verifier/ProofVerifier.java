/*
 * Created on 15/11/2005
 *
 */
package main.tableau.verifier;

import java.util.Iterator;
import java.util.List;

import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalRules;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.newstrategy.ISimpleStrategy;
import main.newstrategy.Prover;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.proofTree.origin.IOrigin;
import main.proofTree.origin.NamedOrigin;
import main.proofTree.origin.SignedFormulaNodeOrigin;
import main.strategy.IClassicalProofTree;
import main.tableau.IProof;
import rules.IRule;
import rules.Rule;

/**
 * A verifier of proof objects.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ProofVerifier {

	private Prover prover;

	private SignedFormulaBuilder sfBuilder;

	private ExtendedProofTree extendedProofTree;

	private Integer usedQuantity;

	private int nodesQuantity = 0;

	private boolean verificationResult;

	private ExtendedProofTree rootBranch;

	// Error messages
	private static final String EMPTY_ORIGIN_NODE = "Empty origin node";

	private static final String ORIGIN_OR_PROBLEM_NODE = "Origin or problem node";

	private static final String PB_NODE = "PB node";

	private static final String ABSENT_RULE_NODE = "Rule is not in the prover";

	private static final String PREMISE_PROBLEM = "There is some problem with premises";

	private static final String CONCLUSION_PROBLEM = "This node is not a concluion of its premises";

	private static final String NO_PROBLEM = "OK";

	/**
	 * Creates a ProofVerifier for a prover.
	 * 
	 * @param p
	 */
	public ProofVerifier(Prover p) {
		this.prover = p;
		this.sfBuilder = p.getStrategy().getSignedFormulaBuilder();
	}

	/**
	 * Verifies a proof.
	 * 
	 * @param proof
	 * @return
	 * @throws Exception
	 */
	public ExtendedProof verify(IProof proof) throws Exception {
		ExtendedProof ep = new ExtendedProof(proof);
		verificationResult = true;

		ep.setExtendedProofTree((ExtendedProofTree) verifyProofTree((IClassicalProofTree) proof
				.getProofTree()));

		ep.setVerificationResult(verificationResult);
		ep.setUsedQuantity(usedQuantity);
		ep.setNodesQuantity(nodesQuantity);

		return ep;
	}

	private IProofTree verifyProofTree(IClassicalProofTree proofTree) throws Exception {
		ExtendedProofTree ept = new ExtendedProofTree(verifyNode((SignedFormulaNode) proofTree
				.getRoot(), proofTree, null));
		ept.setClosed(proofTree.isClosed());

		nodesQuantity++;

		rootBranch = ept;
		return verifyProofTreeRecursively(proofTree, ept);
	}

	private IProofTree verifyProofTreeRecursively(IClassicalProofTree branch,
			IClassicalProofTree treeToAddNode) throws Exception {
		verifyBranch(branch, treeToAddNode);

		if (branch.getLeft() != null) {
			IProofTree left = ((ExtendedProofTree) treeToAddNode).myAddLeft(verifyNode(
					(SignedFormulaNode) branch.getLeft().getRoot(), (IClassicalProofTree) branch.getLeft(),
					treeToAddNode));

			nodesQuantity++;

			verifyProofTreeRecursively((IClassicalProofTree) branch.getLeft(), (IClassicalProofTree) left);
		}

		if (branch.getRight() != null) {
			IProofTree right = ((ExtendedProofTree) treeToAddNode).myAddRight(verifyNode(
					(SignedFormulaNode) branch.getRight().getRoot(), (IClassicalProofTree) branch.getRight(),
					treeToAddNode));

			nodesQuantity++;

			verifyProofTreeRecursively((IClassicalProofTree) branch.getRight(),
					(IClassicalProofTree) right);
		}
		return treeToAddNode;
	}

	private void verifyBranch(IClassicalProofTree proofTree, IClassicalProofTree ept)
			throws Exception {
		IProofTreeBasicIterator it = proofTree.getLocalIterator();
		it.next(); // to ignore root that was already added
		while (it.hasNext()) {
			INode node = it.next();
			ept.addLast(verifyNode(node, proofTree, ept));

			nodesQuantity++;
		}
		if (proofTree.isClosed()) {

			// System.err.println("CLOSED: " + proofTree.getRoot().getContent());
			SignedFormula closingReason = ((IClassicalProofTree) proofTree).getClosingReason();
			// System.err.println("CLOSING REASON: " +closingReason);
			if (closingReason != null) {
				ExtendedNode en = getClosingSignedFormulaInExtendedNode(ept, prover.getStrategy(),
						closingReason);
				ept.addLast(en);
				markAsUsed(en);
				((ExtendedProofTree) ept).setClosed(true);
				// System.err.println(ept.isClosed());
				ept.setCompleted(true);
			}
		} else {
			if (proofTree.isCompleted()) {
				// System.err.println("OPEN AND COMPLETED");
				if (rootBranch.getOpenCompletedBranch() == null) {
					rootBranch.setOpenCompletedBranch(ept);
					// TODO Changed in 11/05/2009
					rootBranch.setCompleted(true);
				}
			}
		}
	}

	/**
	 * Marks a node and its origins
	 * 
	 * @param ept
	 * @param en
	 */
	private void markAsUsed(ExtendedNode en) {

		// System.err.println("FORMULA: " + en);

		IOrigin origin = en.getOrigin();

		if (origin != NamedOrigin.EMPTY) {

			if (en.getUsed() != Boolean.TRUE && origin.getRule() != ClassicalRules.CLOSE) {
				en.setUsed(true);

				if (usedQuantity == null) {
					usedQuantity = new Integer(0);
				}
				usedQuantity = new Integer(usedQuantity.intValue() + 1);
			}

			ExtendedNode main = (ExtendedNode) origin.getMain();
			List<SignedFormulaNode> auxiliaries = origin.getAuxiliaries();
			// if (auxiliaries == null)
			// System.out.println(main);

			Iterator<SignedFormulaNode> auxIterator = auxiliaries.iterator();

			if (main != null) {
				if (main.getUsed() != Boolean.TRUE) {
					markAsUsed(main);
				}
			}
			while (auxIterator.hasNext()) {
				ExtendedNode aux = (ExtendedNode) auxIterator.next();
				if (aux.getUsed() != Boolean.TRUE) {
					markAsUsed(aux);
				}
			}
		}

	}

	private ExtendedNode getClosingSignedFormulaInExtendedNode(IClassicalProofTree ept,
			ISimpleStrategy strategy, SignedFormula sf) {

		SignedFormulaNode main = ept.getNode(sf);
		SignedFormulaNode aux = ept.getNode(sfBuilder.createSignedFormula(
				sf.getSign() == ClassicalSigns.TRUE ? ClassicalSigns.FALSE : ClassicalSigns.TRUE, sf
						.getFormula()));
		IOrigin o = strategy.createOrigin(ClassicalRules.CLOSE, main, aux);

		ExtendedNode en = new ExtendedNode(sfBuilder.createCloseSignedFormula(NullSign.getInstance(),
				sfBuilder.createCompositeFormula(ClassicalConnectives.BOTTOM)),
				SignedFormulaNodeState.FULFILLED, o);

		en.setVerified(true);
		return en;

	}

	private ExtendedNode verifyNode(INode node, IClassicalProofTree proofTree, IClassicalProofTree ept)
			throws Exception {

		SignedFormulaNode sfn = (SignedFormulaNode) node;

		ExtendedNode en = null;
		try {
			en = new ExtendedNode(sfn, ept);
		} catch (Exception e) {
			System.err.println("Exception while verifying " + node.getContent()
					+ ". Could not create an Extended Node for " + sfn);
			e.printStackTrace();
			en = new ExtendedNode((SignedFormula) sfn.getContent(), (SignedFormulaNodeState) sfn
					.getState(), NamedOrigin.UNKNOWN);
		}

		verifyOrigin(proofTree, en, sfn.getOrigin());

		return en;

	}

	private void verifyOrigin(IClassicalProofTree proofTree, ExtendedNode en, IOrigin origin)
			throws Exception {
		if (origin != null) {
			if (origin == NamedOrigin.EMPTY) {
				setNodeVerified(en, false, EMPTY_ORIGIN_NODE);
			} else {

				if ((origin == NamedOrigin.DEFINITION) || (origin == NamedOrigin.PROBLEM)
						|| (origin == NamedOrigin.BACKJUMPING) || (origin == NamedOrigin.LEARNING)) {
					setNodeVerified(en, true, ORIGIN_OR_PROBLEM_NODE);
				} else {
					SignedFormulaNodeOrigin sforigin = (SignedFormulaNodeOrigin) origin;
					IRule r = sforigin.getRule();

					if (r == ClassicalRules.PB) {
						if (verifyPBApplication(sforigin, proofTree)) {
							setNodeVerified(en, true, PB_NODE);
						} else {
							setNodeVerified(en, false, NO_PROBLEM);
						}
					} else {
						// verifies rule
						if (!prover.contains((Rule) r)) {
							setNodeVerified(en, false, ABSENT_RULE_NODE);
						} else {
							// verifies premises
							if (verifyPremises(proofTree, sforigin)) {
								// verifies conclusion
								if (verifyConclusions(r, sforigin, en)) {
									setNodeVerified(en, true, NO_PROBLEM);
								} else {
									setNodeVerified(en, false, CONCLUSION_PROBLEM);
								}
							} else {
								setNodeVerified(en, false, PREMISE_PROBLEM);
							}
						}

					}

				}
			}
		}
	}

	/**
	 * @param origin
	 * @param proofTree
	 * @return
	 */
	private boolean verifyPBApplication(IOrigin origin, IClassicalProofTree proofTree) {

		try {
			INode node = proofTree.getRoot();
			INode brother = null;

			IProofTree parent = proofTree.getParent();

			if (node == parent.getLeft().getRoot()) {
				brother = parent.getRight().getRoot();

			} else {
				if (node == parent.getRight().getRoot()) {
					brother = parent.getLeft().getRoot();
				} else {
					return false;
				}
			}

			SignedFormula expectedBrother = sfBuilder.createOppositeSignedFormula((SignedFormula) node
					.getContent());

			return expectedBrother == brother.getContent();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private void setNodeVerified(ExtendedNode node, boolean value, String reason) throws Exception {
		node.setVerified(value);

		if (!value) {
			verificationResult = false;
			// throw new Exception(
			// "Verification error. Node not verified!\nReason: " + reason
			// + "\nNode:" + node);
			// System.err
			// .println("Verification error. Node not verified!\nReason: "
			// + reason + "\nNode:" + node);
		}
	}

	private boolean verifyPremises(IClassicalProofTree proofTree, IOrigin origin) {
		SignedFormulaList premises = ((SignedFormulaNodeOrigin) origin).getSignedFormulaList();
		Iterator<SignedFormula> itPremises = premises.iterator();
		while (itPremises.hasNext()) {
			SignedFormula sf = (SignedFormula) (itPremises.next());
			if (proofTree.getNode(sf) == null) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyConclusions(IRule r, IOrigin origin, ExtendedNode en) {
		SignedFormulaList conclusions = r.getPossibleConclusions(sfBuilder.getSignedFormulaFactory(),
				sfBuilder.getFormulaFactory(), ((SignedFormulaNodeOrigin) origin).getSignedFormulaList());

		if (conclusions.contains((SignedFormula) en.getContent())) {
			return true;
		} else {
			return false;
		}

	}

	public ExtendedProofTree getExtendedProofTree() {
		return extendedProofTree;
	}

	public void setExtendedProofTree(ExtendedProofTree extendedProofTree) {
		this.extendedProofTree = extendedProofTree;
	}
}
