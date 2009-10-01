/*
 * Created on 06/07/2005
 *
 */
package main.strategy.simple;

import main.strategy.AbstractStrategy;
import main.tableau.Method;

/**
 * An abstract version of SimpleStrategy. To be used in the creation of new
 * strategies.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class AbstractSimpleStrategy extends AbstractStrategy {

	/**
	 * Creates an AbstractSimpleStrategy
	 * 
	 * @param m
	 */
	public AbstractSimpleStrategy(Method m) {
		super(m);
		setCurrent(null);
	}

	/**
	 * @return the current proof tree in the subclass used by this strategy
	 */
	protected FormulaReferenceClassicalProofTree getThisCurrent() {
		return (FormulaReferenceClassicalProofTree) getCurrent();
	}

	/**
	 * @return the proof tree in the subclass used by this strategy
	 */
	protected FormulaReferenceClassicalProofTree getThisProofTree() {
		return (FormulaReferenceClassicalProofTree) getProofTree();
	}
}
