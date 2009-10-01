/*
 * Created on 24/09/2004
 *
 */
package main.proofTree.iterator;

import main.proofTree.INode;
import main.proofTree.IProofTree;

/**
 * Iterates over the formulas of a branch.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeLocalIterator implements IProofTreeBasicIterator {

    private IProofTree pt;
    private INode current;
    
    /**
     *  
     */
    public ProofTreeLocalIterator(IProofTree pt) {
        this.pt = pt;
        this.current = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#hasPrevious()
     */
    public boolean hasPrevious() {
        return (current == null && pt.getRoot() != null)
                || current.getPrevious() != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#previous()
     */
    public INode previous() {
        if (current == null) {
            return current = pt.getRoot();
        } else
            return current = current.getPrevious();
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#hasNext()
     */
    public boolean hasNext() {
        return (current == null && pt.getRoot() != null)
                || current.getNext() != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#next()
     */
    public INode next() {
        if (current == null) {
            return current = pt.getRoot();
        } else
            return current = current.getNext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#current()
     */
    public INode current() {
        return current;
    }
}