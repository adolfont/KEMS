/*
 * Created on 18/01/2006
 *
 */
package main.newstrategy.simple.backjumping;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.simple.SimpleStrategy;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.origin.SignedFormulaNodeOrigin;
import main.strategy.IClassicalProofTree;
import main.tableau.Method;

/**
 * An abstract strategy that is superclassof backjumping and learning strategies
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class AbstractBackjumpingSimpleStrategy extends SimpleStrategy {

    /**
     * Used decision nodes set.
     */
    protected Set<IProofTree> usedDecisionNodes;

    /**
     * Creates a backjumping simple strategy.
     * 
     * @param m -
     *            a method
     */
    public AbstractBackjumpingSimpleStrategy(Method m) {
        super(m);
        usedDecisionNodes = new HashSet<IProofTree>();
    }

    /**
     * Marks as used all proof tree nodes used to close this node (and its
     * branch)
     * 
     * @param closedBranchLastProofTreeNode -
     *            the last proof tree node of the current branch
     */
    protected void marksClosingNodeUsedParents(IProofTree closedBranchLastProofTreeNode) {

        // gets the formula that led to closing this branch
        SignedFormula closingReason = ((IClassicalProofTree) closedBranchLastProofTreeNode)
                .getClosingReason();

        // obtains a list of proof tree nodes in the path from the root to the
        // closed branch that have some formula that was used to obtain the
        // closing reason or its opposite.
        Set<IProofTree> usedProofTreeNodes = getUsedProofTreeNodes(closedBranchLastProofTreeNode,
                ((IClassicalProofTree) closedBranchLastProofTreeNode).getNode(closingReason));

        // records only used decision nodes
        Iterator<IProofTree> itUsedPTNodes = usedProofTreeNodes.iterator();
        while (itUsedPTNodes.hasNext()) {
            IProofTree current = (IProofTree) itUsedPTNodes.next();
            if (isLeftNode(current)) {
                usedDecisionNodes.add(current);
            }
        }
    }


    /**
     * @param currentNode - current proof tree node
     * @param closingReason -
     *            a signed formula node that caused the current proof tree node
     *            to close.
     * 
     * @return a set containing proof tree nodes used to close the current proof
     *         tree node whose closing reason is given
     */
    private Set<IProofTree> getUsedProofTreeNodes(IProofTree currentNode, SignedFormulaNode closingReasonNode) {
        Set<IProofTree> result = new HashSet<IProofTree>();

        LinkedList<SignedFormulaNode> nodesToSee = new LinkedList<SignedFormulaNode>();
        nodesToSee.add(closingReasonNode);
        nodesToSee.add(((IClassicalProofTree) currentNode).getNode(getSignedFormulaBuilder()
                .createOppositeSignedFormula((SignedFormula) closingReasonNode.getContent())));

        while (nodesToSee.size() > 0) {
            SignedFormulaNode current = (SignedFormulaNode) nodesToSee.removeFirst();
            result.add(current.getBranch());

            if (current.getOrigin() instanceof SignedFormulaNodeOrigin) {

                SignedFormulaNodeOrigin o = (SignedFormulaNodeOrigin) current.getOrigin();

                SignedFormulaNode main = o.getMain();
                if (main != null) {
                    nodesToSee.addLast(main);
                }
                List<SignedFormulaNode> auxiliaries = o.getAuxiliaries();
                Iterator<SignedFormulaNode> itAuxies = auxiliaries.iterator();
                while(itAuxies.hasNext()){
                    nodesToSee.addLast(itAuxies.next());
                }

            }
        }

        return result;
    }

    /**  
     * @param ptNode
     * @return true if current node satisfies conditions for backjumping
     */
//    private boolean satisfiesBackjumpingConditions(IProofTree ptNode) {
//        return isRightNode(ptNode);
//    }

    /**
     * @param current
     * @return true if current node is a right child.
     */
    protected boolean isRightNode(IProofTree current) {
        return current.getParent() != null && current == current.getParent().getRight();
    }

    /**
     * @param current
     * @return true if current node is a left child.
     */
    protected boolean isLeftNode(IProofTree current) {
        return current.getParent() != null && current == current.getParent().getLeft();
    }

}
