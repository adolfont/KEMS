/*
 * Created on 24/09/2004
 *
 */
package main.proofTree.iterator;

import main.proofTree.INode;
import main.proofTree.IProofTree;

/**
 * Iterates over the formulas of a branch backwards.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofTreeBackwardLocalIterator implements IProofTreeBasicIterator {

    private IProofTree pt;

    private INode current;

    /**
     *  
     */
    public ProofTreeBackwardLocalIterator(IProofTree pt) {
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
            return current = pt.getLast();
        } else
            return current = current.getPrevious();
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#hasNext()
     */
    public boolean hasNext() {
        return (current == null && pt.getLast() != null)
                || current.getNext() != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTreeBasicIterator#next()
     */
    public INode next() {
        if (current == null) {
            return current = pt.getLast();
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