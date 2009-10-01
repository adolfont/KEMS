/*
 * Created on 24/09/2004
 *
 */
package main.proofTree.iterator;

import main.proofTree.INode;


/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public interface IProofTreeAdvancedIterator  {

    /**
     * @return true if it has a previous element
     */
    public boolean hasPrevious();

    /**
     * @return the previous element
     */
    public INode previous();

    /**
     * @return the cuurent element
     */
    public INode current();

    /**
     * @return true if it has a next element to the left
     */
    public boolean hasNextLeft();

    /**
     * @return true if it has a next element to the right
     */
    public boolean hasNextRight();

    /**
     * @return the next element to the left
     */
    public INode nextLeft();

    /**
     * @return the next element to the right
     */
    public INode nextRight();
}

