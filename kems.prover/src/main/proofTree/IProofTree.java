/*
 * Created on 11/11/2005
 *
 */
package main.proofTree;

import main.proofTree.iterator.IProofTreeAdvancedIterator;
import main.proofTree.iterator.IProofTreeBackwardNodeIterator;
import main.proofTree.iterator.IProofTreeBasicIterator;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;

//import org.jdom.Element;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IProofTree {
    /**
     * Adds a node as the last node
     */
    public abstract void addLast(INode aNode);

    /**
     * Creates a new ProofTree with the node and puts it as the left subtree of
     * the current tree.
     * 
     * @param aNode
     * @return
     */
    public abstract IProofTree addLeft(INode aNode);

    /**
     * Creates a new ProofTree with the node and puts it as the right subtree of
     * the current tree.
     * 
     * @param aNode
     * @return
     */
    public abstract IProofTree addRight(INode aNode);

    /**
     * Returns the root of the tree.
     */
    public abstract INode getRoot();

    /**
     * @return the parent of this proof tree or null.
     */
    public abstract IProofTree getParent();

    /**
     * @return the left subtree of this proof tree or null.
     */
    public abstract IProofTree getLeft();

    /**
     * @return the right subtree of this proof tree or null.
     */
    public abstract IProofTree getRight();

    /**
     * @return the last node added.
     */
    public abstract INode getLast();

    /**
     * @param _currentpt
     *            The _currentPT to set.
     */
    public abstract void setCurrentProofTree(IProofTree _currentpt);

    /**
     * @param other
     *            proof tree
     */
    public abstract void setLeft(IProofTree other);

    /**
     * @param other
     *            proof tree
     */
    public abstract void setRight(IProofTree other);

    /**
     * @return current node
     */
    public abstract INode getCurrent();

    /**
     * @return local iterator (no other branches)
     */
	public abstract IProofTreeBasicIterator getLocalIterator();

    /**
     * @return backward local iterator
     */
    public abstract IProofTreeBasicIterator getBackwardLocalIterator();

    /**
     * @return backward global iterator
     */
    public abstract IProofTreeVeryBasicIterator getTopDownIterator();

    /**
     * @return global iterator (other branches can be seen)
     */
    public abstract IProofTreeAdvancedIterator getGlobalIterator();

    /**
     * @return global iterator (other branches can be seen)
     */
    public abstract IProofTreeBackwardNodeIterator getBackwardNodeIterator();

    public abstract int getNumberOfNodes();

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */public abstract String toString();

    /**
     * @return a short version of the string that shows the proof tree
     */
    public abstract String toStringShort();

//    // XML aspect
//    public abstract Element asXMLElement();
    
}