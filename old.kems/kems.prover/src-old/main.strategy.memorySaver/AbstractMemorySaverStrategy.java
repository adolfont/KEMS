/*
 * Created on 29/09/2005
 *
 */
package main.strategy.memorySaver;

import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.origin.Origin;
import main.strategy.AbstractStrategy;
import main.strategy.ClassicalProofTree;
import main.strategy.NullClosedProofTree;
import main.tableau.Method;
import rules.Rule;

/**
 * Class that abstracts main features of MemorySaverStrategy
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public abstract class AbstractMemorySaverStrategy extends AbstractStrategy {
	

     /** Creates an AbstractmemorySaverStrategy
	 * @param m
	 */
	public AbstractMemorySaverStrategy(Method m) {
        super(m);
	}

	/**
     * @return the current proof tree in the subclass used by this strategy
     */
    protected OptimizedClassicalProofTree getThisCurrent() {
        return (OptimizedClassicalProofTree) getCurrent();
    }

    /**
     * @return the proof tree in the subclass used by this strategy
     */
    protected OptimizedClassicalProofTree getThisProofTree() {
        return (OptimizedClassicalProofTree) getProofTree();
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#createOrigin(rules.Rule,
	 *      main.proofTree.SignedFormulaNode, main.proofTree.SignedFormulaNode)
	 */
	protected Origin createOrigin(Rule rule, SignedFormulaNode main,
			SignedFormulaNode auxiliary) {
		// Não faz nada aqui
		return null;
	}

	/* (non-Javadoc)
	 * @see main.strategy.AbstractStrategy#isRightBranchOfOpenLeftBranch()
	 */
	protected boolean isRightBranchOfOpenLeftBranch() {
		// father not null (current is not root)
		// current is right branch
		// NOT left brother is null closed proof tree
		// NOT left brother is closed proof tree
		return ((getCurrent().getParent() != null)
				&& (getCurrent() == getCurrent().getParent().getRight())
				&& (!((ProofTree) getCurrent().getParent().getLeft() == NullClosedProofTree.INSTANCE))
				&& (!((ClassicalProofTree) getCurrent().getParent().getLeft())
						.isClosed()));
	}

	/* (non-Javadoc)
	 * @see main.strategy.AbstractStrategy#maybeEraseLeft()
	 */
	protected void maybeEraseLeft() {
		if (getCurrent().getParent() != null
				&& getCurrent() == getCurrent().getParent().getRight()) {
			((OptimizedClassicalProofTree) getCurrent().getParent())
					.eraseLeft();
		}		
	}


}
