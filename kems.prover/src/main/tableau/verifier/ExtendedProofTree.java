/*
 * Created on 15/11/2005
 *
 */
package main.tableau.verifier;

import logic.signedFormulas.SignedFormula;
import main.proofTree.BranchId;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.strategy.memorySaver.OptimizedClassicalProofTree;

/**
 * A proof tree with more information.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ExtendedProofTree extends OptimizedClassicalProofTree {

    private int size = 0;

    private int level;

    private String branchNumber;

    private BranchId branchId;

	private boolean closed;

    public void setLevel(int l) {
        level = l;
    }

    public int getLevel() {
        return level;
    }

    public void setBranchNumber(String number) {
        branchNumber = number;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchId(BranchId id) {
        branchId = id;
    }

    public void setBranchId(BranchId id, int position) {
        branchId = new BranchId(id, position);
    }

    public BranchId getBranchId() {
        return branchId;
    }

    /**
     * @param aNode
     */
    public ExtendedProofTree(SignedFormulaNode aNode) {
        super(aNode);
        setLevel(0);
        setBranchNumber("");
        setBranchId(BranchId.ROOT);
        size += ((SignedFormula) aNode.getContent()).getComplexity();
//        System.err.println("size up to now:" + size + " " + aNode.getContent());
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.proofTree.ProofTree#addLeft(main.proofTree.INode)
     */
    public IProofTree myAddLeft(INode aNode) {
        ExtendedProofTree left = (ExtendedProofTree) super.addLeft(aNode);
        left.setLevel(((ExtendedProofTree) left.getParent()).getLevel() + 1);
        left.setBranchNumber(((ExtendedProofTree) left.getParent())
                .getBranchNumber()
                + "l");
        left.setBranchId(((ExtendedProofTree) left.getParent()).getBranchId(),
                BranchId.LEFT);

        return left;
    }


    /*
     * (non-Javadoc)
     * 
     * @see main.proofTree.ProofTree#addRight(main.proofTree.INode)
     */
    public IProofTree myAddRight(INode aNode) {
        ExtendedProofTree right = (ExtendedProofTree) super.addRight(aNode);
        right.setLevel(((ExtendedProofTree) right.getParent()).getLevel() + 1);
        right.setBranchNumber(((ExtendedProofTree) right.getParent())
                .getBranchNumber()
                + "r");
        right.setBranchId(
                ((ExtendedProofTree) right.getParent()).getBranchId(),
                BranchId.RIGHT);


        return right;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.ClassicalProofTree#addLast(main.proofTree.INode)
     */
    public void addLast(INode aNode) {
        super.addLast(aNode);
        size += ((SignedFormula) aNode.getContent()).getComplexity();
    }

    /**
     * @return
     */
    public int getSize() {
        int leftSize =((getLeft() == null) ? 0 : ((ExtendedProofTree) getLeft())
                .getSize());
        int rightSize = ((getRight() == null) ? 0 : ((ExtendedProofTree) getRight())
                .getSize()); 
//        System.err.println (size + " " + leftSize + " " +rightSize);
        
        return size + leftSize + rightSize;
    }
    
    protected IProofTree makeInstance(INode aNode) {
        return new ExtendedProofTree((SignedFormulaNode) aNode);
    }

	public void setClosed(boolean b) {
		closed=b;
		
	}

    public boolean isClosed() {
    	return closed;
    }

    

}
