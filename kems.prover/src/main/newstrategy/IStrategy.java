/*
 * Created on 27/10/2005
 *
 */
package main.newstrategy;

import logic.problem.Problem;
import main.proofTree.ProofTree;

/**
 * Interface for strategies.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public interface IStrategy {

	/**
	 * Tries to produce a closed tableau for a problem.
	 * 
	 * If it is successful, the proof tree is a closed tableau that shows that
	 * the problem is a tautology.
	 * 
	 * If it is not successful, from the proof tree branch that is open and
	 * completed we can obtain a valuation that shows that sequent is not valid.
	 * Remember that the list of signed formulas of the problem represents a
	 * falsification of the sequent.
	 * 
	 * That is, if the problem is A1, A2, ..., Am |- B1, B2, ..., Bn then the
	 * list of signed formulas is [T A1, T A2, ..., T Am, F B1, F B2, ..., F
	 * Bn]. If we close the tableau for this list of signed formulas, then there
	 * is no valuation such that v(Ai)=1 and v(Bj)=0, for all 1 <=i <=m, 1 <=j
	 * <=n. If we cannot close the tableau, from the first open and completed
	 * branch we find, we can obrtain a valuation such that v(Ai)=1 and v(Bi)=0.
	 * 
	 * @param p -
	 *            a problem that contains a list of signed formulas.
	 * @return
	 */
	public ProofTree close(Problem p);

}
