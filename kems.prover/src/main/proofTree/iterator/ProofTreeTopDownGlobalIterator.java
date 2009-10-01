/*
 * Created on 05/10/2005
 *
 */
package main.proofTree.iterator;

import java.util.LinkedList;

import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.Node;
import main.proofTree.ProofTree;

/**
 * An iterator for proof trees that goes from the original branch up, until
 * finds the root. Iterates over all nodes in this path. Does not include
 * branches below the original branch.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeTopDownGlobalIterator implements
		IProofTreeVeryBasicIterator {

//	private IProofTree originalBranch;

	private IProofTree currentBranch;

	private IProofTreeBasicIterator currentBranchIterator;

//	private boolean finished = false;

//	private INode current = null;
	
	private LinkedList<IProofTree> pathBranches;

	/**
	 * Creates a ProofTreeBackwardGlobalIterator
	 * 
	 * @param original -
	 *            starting branch
	 */
	public ProofTreeTopDownGlobalIterator(IProofTree original) {

		pathBranches = new LinkedList<IProofTree>();
		this.currentBranch = original;
		
		while (currentBranch.getParent() != null){
			pathBranches.addLast(currentBranch.getParent());
			currentBranch = currentBranch.getParent();
		}
		
//		this.originalBranch = original;
		this.currentBranchIterator = original.getLocalIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.proofTree.iterator.IProofTreeBasicIterator#hasPrevious()
	 */
	public boolean hasPrevious() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.proofTree.iterator.IProofTreeBasicIterator#previous()
	 */
	public Node previous() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.proofTree.iterator.IProofTreeBasicIterator#hasNext()
	 */
	public boolean hasNext() {
		if (currentBranchIterator.hasNext()) {
			return true;
		} else {
			if (!pathBranches.isEmpty()){
				return ((ProofTree)pathBranches.get(0)).getLocalIterator().hasNext();
			}
//			if (currentBranch.getParent() != null) {
//				ProofTreeLocalIterator it = currentBranch.getParent()
//						.getLocalIterator();
//				return it.hasNext();
//			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.proofTree.iterator.IProofTreeBasicIterator#next()
	 */
	public INode next() {
		if (currentBranchIterator.hasNext()) {
			return currentBranchIterator.next();
		} else {
			if (!pathBranches.isEmpty()){
				currentBranch = (ProofTree) pathBranches.remove(0);
				currentBranchIterator = currentBranch.getLocalIterator();
				return currentBranchIterator.next();
			}
//			if (currentBranch.getParent() != null) {
//				currentBranch = currentBranch.getParent();
//				currentBranchIterator = currentBranch.getLocalIterator();
//				return currentBranchIterator.next();
//			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.proofTree.iterator.IProofTreeBasicIterator#current()
	 */
	public INode current() {
		return currentBranchIterator.current();
	}

}
