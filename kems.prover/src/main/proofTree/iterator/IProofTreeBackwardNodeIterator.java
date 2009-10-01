/*
 * Created on 11/11/2005
 *
 */
package main.proofTree.iterator;

import main.proofTree.IProofTree;

/**
 * From a node goes up until finds root node.  
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IProofTreeBackwardNodeIterator {
    
    public IProofTree previous();
    
    public boolean hasPrevious();

}
