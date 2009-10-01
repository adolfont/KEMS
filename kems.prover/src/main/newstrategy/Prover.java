/*
 * Created on Oct 22, 2004
 *
 */
package main.newstrategy;

import rules.Rule;
import logic.problem.Problem;
import main.proofTree.ProofTree;
import main.strategy.ICloseableProofTree;
import main.tableau.Method;
import main.tableau.Proof;

/**
 * Class that represents a tableau prover. 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class Prover {

    /** the prover method **/
    private Method method;

    /** the current strategy **/
    private ISimpleStrategy strategy;

    public ISimpleStrategy getStrategy() {
        return strategy;
    }

    public void setMethod(Method m) {
        method = m;
    }

    public void setStrategy(ISimpleStrategy strategy) {
        this.strategy = strategy;
    }

    public Proof prove(Problem p) {
        // tries to close a problem
        ProofTree pt = strategy.close(p);
        
        // produces a proof for the problem
        return new Proof(((ICloseableProofTree) pt).isClosed(), pt, p);
    }

    /**
     * @param r
     * @return
     */
    public boolean contains(Rule r) {
        return method.contains(r);
    }

}