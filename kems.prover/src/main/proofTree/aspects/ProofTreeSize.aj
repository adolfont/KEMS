/*
 * Created on 14/04/2005
 *  
 */
package main.proofTree.aspects;

import main.proofTree.INode;
import main.proofTree.Node;
import main.proofTree.ProofTree;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public aspect ProofTreeSize {

    // attributes for measuring size
    private int ProofTree._leftHeight;

    private int ProofTree._rightHeight;

    private int ProofTree._branches;

    private int ProofTree._nodes;

    after(ProofTree pt): target(pt) && args(Node+) && execution (ProofTree.new(..)){
        pt._branches = 1;
        pt._nodes = 1;
    }

    after(ProofTree pt): target(pt) && args(Node+) && execution (* ProofTree.addLast(..))
    && !cflowbelow(execution(* ProofTree.addLast(..)))
    	{
        pt._nodes++;
    }

    /**
     * @return a string showing information about the size of the proof tree
     */
    public String ProofTree.showSize() {
        String result = "";
        result = result + "Nodes : " + this.getNumberOfNodes()
                + System.getProperty("line.separator");
        result = result + "Branches : " + this.getBranches()
                + System.getProperty("line.separator");
        result = result + "Height : " + (this.getHeight())
                + System.getProperty("line.separator");
        return result;
    }

    // aspect size

    public int ProofTree.getLongestPath() {
        int leftHeight = (getLeft() == null) ? 0 : ((ProofTree)getLeft()).getLongestPath();
        int rightHeight = (getRight() == null) ? 0 : ((ProofTree)getRight())
                .getLongestPath();
        return currentBranchHeight()
                + ((leftHeight > rightHeight) ? leftHeight : rightHeight);
    }

    private int ProofTree.currentBranchHeight() {
        int i = 0;
        INode current = this.getRoot();
        do {
            current = current.getNext();
            i++;
        } while (current != null);
        return i;
    }

    /**
     * @return the height of the proof tree
     */
    public int ProofTree.getHeight() {
        if (getLeft() == null) {
            return 0;
        } else {
            int leftHeight = getLeftHeight();
            int rightHeight = getRightHeight();
            return ((leftHeight > rightHeight) ? leftHeight : rightHeight);
        }
    }

    /**
     * @return the height of the left branch of teh proof tree
     */
    public int ProofTree.getLeftHeight() {
        return 1 + ((ProofTree)getLeft()).getHeight();
    }

    /**
     * @return the height of the right branch of teh proof tree
     */
    public int ProofTree.getRightHeight() {
        return 1 + ((ProofTree)getRight()).getHeight();
    }

    /**
     * @return the number of branches in the proof tree
     */
    public int ProofTree.getBranches() {
        return _branches + (getLeft() == null ? 0 : ((ProofTree)getLeft()).getBranches())
                + (getRight() == null ? 0 : ((ProofTree)getRight()).getBranches());
    }

    /**
     * @return the number of nodes in the proof tree
     */
    public int ProofTree.getNumberOfNodes_aspected() {
        return _nodes + (getLeft() == null ? 0 : getLeft().getNumberOfNodes())
                + (getRight() == null ? 0 : getRight().getNumberOfNodes());
    }

    /**
     * @return the number of nodes in the the first branch of proof tree
     */
    public int ProofTree.getNumberOfLocalNodes() {
        return _nodes;
    }
    
}
