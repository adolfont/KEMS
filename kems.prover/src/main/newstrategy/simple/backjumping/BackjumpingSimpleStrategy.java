/*
 * Created on 18/01/2006
 *
 */
package main.newstrategy.simple.backjumping;

import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import main.tableau.Method;

/**
 * An implementation of a strategy with backjumping for classical logic.
 * 
 * Algorithm: <br/>1. Every node has a signed formula list and a "used/not used"
 * flag. <br/>2. The first signed formula of a left node is called a decision. A
 * node that contains a decision is called a "decison node". <br/>3. Whenever a
 * node closes, the decision nodes used to close that node must be marked as
 * used. <br/>4. Whenever a right node closes, if any of its grandparents is a
 * not used decision, its sibling can be closed by backjumping.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class BackjumpingSimpleStrategy extends AbstractBackjumpingSimpleStrategy {

    /**
     * Creates a backjumping simple strategy.
     * 
     * @param m -
     *            a method
     */
    public BackjumpingSimpleStrategy(Method m) {
        super(m);
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.newstrategy.ISimpleStrategy#finishBranch(main.proofTree.IProofTree)
     */
    public void finishBranch(IProofTree closedBranchLastProofTreeNode) {
        // every time a branch closes, marks current node parents as used.
        marksClosingNodeUsedParents(closedBranchLastProofTreeNode);
        


        // verify if backjumping can be applied and, if it can, apply it.
        verifyAndMaybeApplyBackjumping(closedBranchLastProofTreeNode);
        
        // the options cannot be used with this strategy
//        super.finishBranch(closedBranchLastProofTreeNode);
    }


    /**
     * Verifies if backjumping can be applied and, if it can, applies it.
     * 
     * @param closedBranchLastProofTreeNode
     */
    private void verifyAndMaybeApplyBackjumping(IProofTree closedBranchLastProofTreeNode) {

        if (satisfiesBackjumpingConditions(closedBranchLastProofTreeNode)) {

            IProofTree current = closedBranchLastProofTreeNode;
            // goes up until a decision node or a null parent is found
            while (current.getParent() != null && isRightNode(current.getParent())) {
                current = current.getParent();
            }

            closedBranchLastProofTreeNode = current;

            if (closedBranchLastProofTreeNode.getParent() != null
                    && isLeftNode(closedBranchLastProofTreeNode.getParent())) {
                if (!usedDecisionNodes.contains(closedBranchLastProofTreeNode.getParent())) {
                    SignedFormulaNode contradiction = new SignedFormulaNode(this
                            .getSignedFormulaBuilder().getSignedFormulaFactory()
                            .createSignedFormula(
                                    ClassicalSigns.TRUE,
                                    this.getSignedFormulaBuilder().getFormulaFactory()
                                            .createCompositeFormula(ClassicalConnectives.BOTTOM)),
                            SignedFormulaNodeState.FULFILLED, NamedOrigin.BACKJUMPING);
                    closedBranchLastProofTreeNode.getParent().getParent().getRight().addLast(
                            contradiction);

                }
            
            }
            
            if (isDiscardClosedBranches()){
            	super.finishBranch(closedBranchLastProofTreeNode.getParent());
            }
            
            
        }
        
//        if (closedBranchLastProofTreeNode.getParent()!=null && isLeftNode(closedBranchLastProofTreeNode) && isDiscardClosedBranches()){
//        	super.finishBranch(closedBranchLastProofTreeNode.getParent());
//        }

    }

    /**  
     * @param ptNode
     * @return true if current node satisfies conditions for backjumping
     */
    private boolean satisfiesBackjumpingConditions(IProofTree ptNode) {
        return isRightNode(ptNode);
    }

}
