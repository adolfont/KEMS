/*
 * Created on 24/09/2004
 *
 */
package main.proofTree.iterator;

import main.proofTree.INode;
import main.proofTree.IProofTree;

/**
 * 
 * Iterator for ProofTrees that allows one to see other branches. The traversal
 * can be bottom-up or top-down.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeGlobalIterator implements IProofTreeAdvancedIterator {

//	private IProofTree pt;
	private IProofTree currentPT;

	private INode current;

	private IProofTreeBasicIterator pt_it;

	public ProofTreeGlobalIterator(IProofTree pt) {
//		this.pt = pt;
		this.currentPT = pt;
		this.current = currentPT.getRoot();
		this.pt_it = pt.getLocalIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#hasPrevious()
	 */
	public boolean hasPrevious() {
		return pt_it.hasPrevious() || currentPT.getParent() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#previous()
	 */
	public INode previous() {
		if (pt_it.hasPrevious()) {
			return current = pt_it.previous();
		} else if (currentPT.getParent() != null) {
			currentPT = currentPT.getParent();
			pt_it = currentPT.getBackwardLocalIterator();
			return current = pt_it.previous();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#current()
	 */
	public INode current() {
		return current;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#hasNextLeft()
	 */
	public boolean hasNextLeft() {
		return pt_it.hasNext() || currentPT.getLeft() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#hasNextRight()
	 */
	public boolean hasNextRight() {
		return pt_it.hasNext() || currentPT.getRight() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#nextLeft()
	 */
	public INode nextLeft() {
		if (pt_it.hasNext()) {
			return current = pt_it.next();
		} else if (currentPT.getLeft() != null) {
			currentPT = currentPT.getLeft();
			pt_it = currentPT.getLocalIterator();
			return current = pt_it.next();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see proofTree.ProofTreeAdvancedIterator#nextRight()
	 */
	public INode nextRight() {
		if (pt_it.hasNext()) {
			return current = pt_it.next();
		} else if (currentPT.getRight() != null) {
			currentPT = currentPT.getRight();
			pt_it = currentPT.getLocalIterator();
			return current = pt_it.next();
		}
		return null;
	}

}