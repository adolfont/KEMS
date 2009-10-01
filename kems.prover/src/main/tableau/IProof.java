/*
 * Created on 15/11/2005
 *
 */
package main.tableau;

import logic.problem.Problem;
import main.proofTree.IProofTree;

/**
 * Interface for proofs
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public interface IProof {

    public boolean isClosed();

    public IProofTree getProofTree();

    public Problem getProblem();
}
