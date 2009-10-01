/*
 * Created on Oct 22, 2004
 *
 */
package main.tableau;

import logic.problem.Problem;
import main.newstrategy.IStrategy;
import main.proofTree.ProofTree;
import main.strategy.ICloseableProofTree;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class Prover {

    Method _method;

    IStrategy _strategy;

    public IStrategy getStrategy() {
        return _strategy;
    }

    public void setMethod(Method m) {
        _method = m;
    }

    /**
     * @param _strategy
     *                   The _strategy to set.
     */
    public void setStrategy(IStrategy _strategy) {
        this._strategy = _strategy;
    }

    public Proof prove(Problem p) {
//        SignedFormulaBuilder sfb = new SignedFormulaBuilder(p.getSignedFormulaFactory(), p.getFormulaFactory());
        
        ProofTree pt = _strategy.close(p);
//        ProofTree pt = _strategy.close(p, sfb);
        return new Proof(((ICloseableProofTree) pt).isClosed(), pt, p);
    }

}