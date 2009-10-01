/*
 * Created on 24/06/2005
 *
 */
package main.strategy.simple;

import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.origin.Origin;
import main.strategy.NullClosedProofTree;
import main.tableau.Method;
import rules.Rule;

/**
 * Class that tries to save memory but uses the same structure of SimpleStrategy.
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class MemorySaverSimpleStrategy extends SimpleStrategy {

    /**
     * @param m
     */
    public MemorySaverSimpleStrategy(Method m) {
        super(m);
        setCurrent(null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see strategy.simple.AbstractSimpleStrategy#createOrigin(rulesNew.Rule,
     *           proofTree.SignedFormulaNode, proofTree.SignedFormulaNode)
     */
    public Origin createOrigin(Rule rule, SignedFormulaNode main,
            SignedFormulaNode auxiliary) {
        return null;
    }

    /* (non-Javadoc)
     * @see strategy.simple.AbstractSimpleStrategy#isRightBranchOfOpenLeftBranch()
     */
    public boolean isRightBranchOfOpenLeftBranch() {
        return (getThisCurrent().getParent() != null)
                && (getThisCurrent() == getThisCurrent().getParent().getRight())
                && (!((ProofTree) getThisCurrent().getParent().getLeft() == NullClosedProofTree.INSTANCE))
                && (!((FormulaReferenceClassicalProofTree) getThisCurrent()
                        .getParent().getLeft()).isClosed());
    }

    /* (non-Javadoc)
     * @see strategy.simple.AbstractSimpleStrategy#maybeEraseLeft()
     */
    protected void maybeEraseLeft() {
        if (getThisCurrent().getParent() != null
                && getThisCurrent() == getThisCurrent().getParent().getRight()) {
            ((FormulaReferenceClassicalProofTree) getThisCurrent().getParent())
                    .eraseLeft();
        }
    }

}
