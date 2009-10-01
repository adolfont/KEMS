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
public interface IProofTreeBasicIterator extends IProofTreeVeryBasicIterator{
    
    /**
     * @return true if it has a previous element
     */
    public boolean hasPrevious();
    
    /**
     * @return the previous element
     */
    public INode previous();

}
