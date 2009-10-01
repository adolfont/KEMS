/*
 * Created on 15/11/2005
 *
 */
package main.strategy;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;

/**
 * Interface for Classical Proof Trees
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public interface IClassicalProofTree extends IProofTree {

	public boolean isClosed();

	public PBCandidateList getPBCandidates();

	public SignedFormulaNode getNode(SignedFormula formula);

	public void removeFromPBCandidates(SignedFormula sf,
			SignedFormulaNodeState state);

	public boolean isCompleted();

	public void setCompleted(boolean value);

	/**
	 * @param current
	 */
	public void setOpenCompletedBranch(IClassicalProofTree current);

	public IClassicalProofTree getOpenCompletedBranch();

	public boolean contains(SignedFormula sf);

	public SignedFormula getClosingReason();

}
