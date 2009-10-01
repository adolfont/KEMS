/*
 * Created on 11/11/2005
 *
 */
package main.proofTree.iterator;

import main.proofTree.IProofTree;

/**
 * An Iterator that goes back to the root.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeBackwardNodeIterator implements
        IProofTreeBackwardNodeIterator {

    private IProofTree startNode;

    private IProofTree currentNode;

    public ProofTreeBackwardNodeIterator(IProofTree startNode) {
        this.startNode = startNode;
        this.currentNode = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.proofTree.iterator.IProofTreeBackwardNodeIterator#previous()
     */
    public IProofTree previous() {
        if (currentNode == null) {
            currentNode = startNode;
        } else {
            currentNode = currentNode.getParent();
        }
        return currentNode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.proofTree.iterator.IProofTreeBackwardNodeIterator#hasPrevious()
     */
    public boolean hasPrevious() {
        if (currentNode == null) {
            return startNode != null;
        } else {
            return currentNode.getParent() != null;
        }
    }

}
