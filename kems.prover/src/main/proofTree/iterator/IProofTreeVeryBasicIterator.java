/*
 * Created on 05/10/2005
 *
 */
package main.proofTree.iterator;

import main.proofTree.INode;

/**
 * The most basic iterator interface for proof trees.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public interface IProofTreeVeryBasicIterator {

	/**
	 * @return true if it has a next element
	 */
	public boolean hasNext();

	/**
	 * @return the next element
	 */
	public INode next();

	/**
	 * @return the cuurent element
	 */
	public INode current();

}
