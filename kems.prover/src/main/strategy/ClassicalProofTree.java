/*
 * Created on 19/11/2004
 *
 */
package main.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.formulas.CompositeFormula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.structures.FormulaSignMultimap;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;

/**
 * Class that represents a proof tree than can be closed.
 * 
 * @author adolfo
 * 
 */
public abstract class ClassicalProofTree extends ProofTree implements
		ICloseableProofTree, IClassicalProofTree {

	private FormulaSignMultimap _fsmm;

	private PBCandidateList _PBCandidates;

	private Map<SignedFormula, INode> _signedFormulasToNodes;

	private boolean completed;

	private IClassicalProofTree openCompletedBranch;

	protected List<SignedFormulaNode> discardedSignedFormulaNodesFromOtherProofTreeNodes;
	
    private SignedFormula closingReason;

	protected ClassicalProofTree() {
	};

	/**
	 * Creates a ClassicalProofTree with an initial node
	 * 
	 * @param aNode
	 */
	public ClassicalProofTree(SignedFormulaNode aNode) {
		super(aNode);

		_fsmm = new FormulaSignMultimap();
		_PBCandidates = new PBCandidateList();

		discardedSignedFormulaNodesFromOtherProofTreeNodes = new ArrayList<SignedFormulaNode>();

		_signedFormulasToNodes = new HashMap<SignedFormula,INode>();

		updateStructures(aNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTree#addLast(proofTree.Node)
	 */
	public void addLast(INode aNode) {
		// only adds if it is new
		// Looking for aNode
		if (getNode((SignedFormula) aNode.getContent()) == null) {
//			 NOT FOUND - adding
			super.addLast(aNode);
			updateStructures(aNode);
		} else {
			// found, not adding
			SignedFormulaNode main = ((SignedFormulaNode) aNode).getOrigin()
					.getMain();
			if (main != null && main.getBranch() != this) {
				discardedSignedFormulaNodesFromOtherProofTreeNodes.add(main);
			}
			// else, same branch. No need to add
		}
	}

	// TODO addLeft and addRight should only add a new node when it is new?
	// This is guaranteed by PB features?

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTree#setOtherStructures(proofTree.Node)
	 */
	protected void setOtherStructures(IProofTree pt, INode aNode) {
		((ClassicalProofTree) pt).addPBCandidates(this.getPBCandidates());
	}

	// /* (non-Javadoc)
	// * @see proofTree.ProofTree#makeInstance(proofTree.Node)
	// */
	// protected ProofTree makeInstance(Node aNode) {
	// return new ClassicalProofTree((SignedFormulaNode) aNode);
	// }

	public abstract boolean isClosed();

	// {
	// return isLocallyClosed() || isClosedByBranching();
	// }

	/**
	 * @param aNode
	 */
	protected void updateStructures(INode aNode) {
		updateMultimap((SignedFormulaNode) aNode);
		// System.err.println("BEFORE: "+getPBCandidates());
		// if (getParent()!=null){
		// System.err.println("BEFORE IN PARENT:
		// "+((FormulaReferenceClassicalProofTree)getParent()).getPBCandidates());
		// }
		updatePBCandidates((SignedFormulaNode) aNode);
		// System.err.println("AFTER 1: "+getPBCandidates());
		updateSignedFormulasToNodesMap((SignedFormulaNode) aNode);
		// System.err.println("AFTER 2: "+getPBCandidates());
	}

	/**
	 * @param node
	 */
	protected abstract void updateMultimap(SignedFormulaNode node);

	protected void updatePBCandidates(SignedFormulaNode aNode) {
		if (((SignedFormula) aNode.getContent()).getFormula() instanceof CompositeFormula) {
			// TODO only until problem is solved
			if (!_PBCandidates.contains((SignedFormula) aNode.getContent())) {
				_PBCandidates.add((SignedFormula) aNode.getContent());
			}
		} else {
			aNode.setState(SignedFormulaNodeState.FULFILLED);
		}

	}

	protected void updateSignedFormulasToNodesMap(SignedFormulaNode aNode) {
		_signedFormulasToNodes.put(aNode.getContent(), aNode);
	}

	/**
	 * @param candidates
	 */
	protected void addPBCandidates(PBCandidateList candidates) {
		_PBCandidates.addAll(candidates);
	}


	public PBCandidateList getPBCandidates() {
		return _PBCandidates;
	}

	public final SignedFormulaNode getNode(SignedFormula formula) {

		SignedFormulaNode sfn = (SignedFormulaNode) _signedFormulasToNodes
				.get(formula);
		if (sfn != null) {
			return sfn;
		} else {
			if (getParent() != null) {
				return ((ClassicalProofTree) getParent()).getNode(formula);
			}
		}

		return null;
	}

	public void removeFromPBCandidates(SignedFormula sf,
			SignedFormulaNodeState state) {
		SignedFormulaNode sfn = getNode(sf);
		sfn.setState(state);
		removeFromPBCandidates(sf);

		// Only if this formula is not from this proof tree node
		if (sfn.getBranch() != this) {
			// add it to the discarded PB candidates list
			discardedSignedFormulaNodesFromOtherProofTreeNodes.add(sfn);
		}
		// System.err.println("RemovePB: "+sf);
	}

	public void removeFromPBCandidates(SignedFormula sf) {
		_PBCandidates.remove(sf);
	}


	/**
	 * 
	 */
	protected String getStatus() {
		return "closed? : " + isClosed() + System.getProperty("line.separator");
	}

	/**
	 * @return Returns the _signedFormulasToNodes.
	 */
	public Map<SignedFormula, INode> getSignedFormulasToNodes() {
		return _signedFormulasToNodes;
	}

	/**
	 * @return Returns the _fsmm.
	 */
	public FormulaSignMultimap getFsmm() {
		return _fsmm;
	}

	/**
	 * erases the left branch
	 */
	public void eraseLeft() {
		// TODO Remove comment
		// setLeft(NullClosedProofTree.INSTANCE);
	}

	public boolean isCompleted() {
		if (isClosed()) {
			completed = true;
		}
		return completed;
	}

	public void setCompleted(boolean value) {
		completed = value;
	}

	/**
	 * @param current
	 */
	public void setOpenCompletedBranch(IClassicalProofTree current) {
		this.openCompletedBranch = current;
	}

	public IClassicalProofTree getOpenCompletedBranch() {
		return openCompletedBranch;
	}

	/**
	 * Verifies if the current branch contains a formula (in this branch or in
	 * some parent)
	 * 
	 * @param sf
	 * @return
	 */
	public boolean contains(SignedFormula sf) {
		IProofTreeVeryBasicIterator it = this.getTopDownIterator();
		while (it.hasNext()) {
			SignedFormulaNode sfNode = (SignedFormulaNode) it.next();

			if (sfNode.getContent() == sf) {
				return true;
			}
		}
		return false;
	}

	public List<SignedFormulaNode> getDiscardedSignedFormulaNodesFromOtherProofTreeNodes() {
		return discardedSignedFormulaNodesFromOtherProofTreeNodes;
	}

	protected void recoverInPbCandidatesHereAndAbove(SignedFormula sf,
			ClassicalProofTree parentIterator) {

		while (parentIterator != null) {
			SignedFormulaNode node = getNode(sf);

			SignedFormula main = (SignedFormula) node.getOrigin().getMain()
					.getContent();

			if (node.getBranch() != this) {

				if (!parentIterator.getPBCandidates().contains(main)) {
					parentIterator.getPBCandidates().add(main);
				}

			}
			parentIterator = (ClassicalProofTree) parentIterator
			.getParent();

		}
	}

	protected void removeOtherReferencesInChildren(SignedFormula sf) {

		recoverInPbCandidatesHereAndAbove(sf, (ClassicalProofTree) getParent());

		_fsmm.remove(sf);
		_PBCandidates.remove(sf);
		_signedFormulasToNodes.remove(sf);
	}
	
	protected void setClosingReason(SignedFormula sf){
		closingReason = sf;
	}
	
	public SignedFormula getClosingReason(){
		return closingReason;
	}

	public void addToPBCandidates(SignedFormula formula) {
		if (!_PBCandidates.contains(formula)){
			_PBCandidates.add(formula);
		}
	}

}