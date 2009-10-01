/*
 * Created on 02/02/2005
 *  
 */
package main.strategy;

import java.util.ArrayList;
import java.util.List;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import main.proofTree.INode;
import main.proofTree.IProofTree;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.origin.NamedOrigin;
import util.NotImplementedException;

class NullSign extends FormulaSign {

    private static NullSign __INSTANCE = null;

    public static NullSign getInstance() {
        if (__INSTANCE == null) {
            __INSTANCE = new NullSign(-1);
        }
        return __INSTANCE;
    }

    /**
     * @param value
     */
    private NullSign(int value) {
        super(value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.signedFormulas.FormulaSign#toString()
     */
    public String toString() {
        return "";
    }

}

class NullFormula extends Formula {

    private static NullFormula __INSTANCE = null;

    public static NullFormula getInstance() {
        if (__INSTANCE == null) {
            __INSTANCE = new NullFormula();
        }
        return __INSTANCE;
    }

    public int getComplexity(){
        return 0;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see logic.formulas.Formula#getImmediateSubformulas()
     */
    public List<Formula> getImmediateSubformulas() {
        return new ArrayList<Formula>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.formulas.Formula#toString()
     */
    public String toString() {
        return "null";
    }

    /*
     * (non-Javadoc)
     * 
     * @see logic.formulas.Formula#clone(logic.formulas.FormulaFactory)
     */
    public Formula clone(FormulaFactory ff) {
        return null;
    }

}

class NullSignedFormula extends SignedFormula  {
    private static NullSignedFormula __INSTANCE = null;

    public static NullSignedFormula getInstance() {
        if (__INSTANCE == null) {
            __INSTANCE = new NullSignedFormula(NullSign.getInstance(),
                    NullFormula.getInstance());
        }
        return __INSTANCE;
    }
    
    public int getComplexity(){
        return 0;
    }

    /**
     * @param sign
     * @param formula
     */
    private NullSignedFormula(FormulaSign sign, Formula formula) {
        super(sign, formula);
    }

}

class NullNode extends SignedFormulaNode {

    /**
     * @param sf
     * @param state
     * @param origin
     */
    public NullNode(SignedFormula sf, SignedFormulaNodeState state,
            NamedOrigin origin) {
        super(sf, state, origin);
    }

    private static NullNode __INSTANCE = null;

    public static NullNode getInstance() {
        if (__INSTANCE == null) {
            __INSTANCE = new NullNode(NullSignedFormula.getInstance(),
                    SignedFormulaNodeState.FULFILLED, NamedOrigin.EMPTY);
        }

        return __INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "null node";
    }

}
/**
 * @author Adolfo Gustavo Serra Seca neto
 *  
 */

public class NullClosedProofTree extends ProofTree implements
        ICloseableProofTree, IClassicalProofTree {
    public static final NullClosedProofTree INSTANCE = new NullClosedProofTree();

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTree#toString()
     */
    public String toString() {
        return "NullClosedProofTree.INSTANCE";
    }

    public IProofTree getParent() {
        return INSTANCE;
    }

    public IProofTree getRight() {
        return null;
    }

    public IProofTree getLeft() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.proofTree.IProofTree#getRoot()
     */
    public INode getRoot() {
        return NullNode.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ClassicalProofTree#isClosed()
     */
    public boolean isClosed() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTree#makeInstance(proofTree.Node)
     */
    protected IProofTree makeInstance(INode aNode) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see proofTree.ProofTree#setOtherStructures(proofTree.ProofTree,
     *      proofTree.Node)
     */
    protected void setOtherStructures(IProofTree pt, INode aNode) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#getPBCandidates()
     */
    public PBCandidateList getPBCandidates() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#getNode(logic.signedFormulas.SignedFormula)
     */
    public SignedFormulaNode getNode(SignedFormula formula) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#removeFromPBCandidates(logic.signedFormulas.SignedFormula,
     *      main.proofTree.SignedFormulaNodeState)
     */
    public void removeFromPBCandidates(SignedFormula sf,
            SignedFormulaNodeState state) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#isCompleted()
     */
    public boolean isCompleted() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#setCompleted(boolean)
     */
    public void setCompleted(boolean value) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#setOpenCompletedBranch(main.strategy.IClassicalProofTree)
     */
    public void setOpenCompletedBranch(IClassicalProofTree current) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#getOpenCompletedBranch()
     */
    public IClassicalProofTree getOpenCompletedBranch() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#contains(logic.signedFormulas.SignedFormula)
     */
    public boolean contains(SignedFormula sf) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.IClassicalProofTree#getClosingReason()
     */
    public SignedFormula getClosingReason() {
		return null;
    }

    /* (non-Javadoc)
     * @see main.strategy.IClassicalProofTree#setPBCandidates(logic.signedFormulas.SignedFormulaList)
     */
    public void setPBCandidates(SignedFormulaList candidates) {
		throw new NotImplementedException();
    }

	protected void removeOtherReferencesInChildren(SignedFormula sf) {
		throw new NotImplementedException();
	}

}
