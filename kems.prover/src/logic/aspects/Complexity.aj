/*
 * Created on 22/06/2005
 *
 */
package logic.aspects;

import java.util.ArrayList;
import java.util.List;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * Aspect that introduces getComplexity() method in several classes. 
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public aspect Complexity {
	
	/* 
	 * Interface implemented by classes of objects that calculates
	 * its own complexity. 
	 *
	 * @author Adolfo Gustavo Serra Seca Neto
	 *
	 */ 
	interface OwnComplexityCalculator {
		
		/**
		 * Calculates the complexity of the object.
		 * 
		 * @return object complexity
		 */
		public int getComplexity();
	}
	
	// includes interface in classes
	declare parents: Formula implements OwnComplexityCalculator; 
	declare parents: FormulaList implements OwnComplexityCalculator; 
	declare parents: FormulaFactory implements OwnComplexityCalculator; 
	declare parents: SignedFormula implements OwnComplexityCalculator; 
	declare parents: SignedFormulaList implements OwnComplexityCalculator; 
	declare parents: SignedFormulaFactory implements OwnComplexityCalculator; 

    
    public abstract int Formula.getComplexity();

    public int AtomicFormula.getComplexity() {
        return 1;
    }

    public int CompositeFormula.getComplexity() {
        int complexity = 1;
        for (int i = 0; i < getImmediateSubformulas().size(); i++) {
            complexity = complexity
                    + ((Formula) getImmediateSubformulas().get(i))
                            .getComplexity();
        }
        return complexity;
    }

    public int FormulaList.getComplexity() {
        int complexity = 0;
        for (int i = 0; i < this.size(); i++) {
            complexity += this.get(i).getComplexity();
        }
        return complexity;
    }

    /**
     * returns the complexity of the formula factory.
     * 
     * @return the complexity of the formula factory
     */
    public int FormulaFactory.getComplexity() {
        int complexity = getAtomicFormulas().size();
        List<Formula> compositeFormulas = new ArrayList<Formula>
        (getCompositeFormulas().values());
        for (int i = 0; i < getCompositeFormulas().size(); i++) {
            complexity = complexity
                    + ((Formula) compositeFormulas.get(i)).getComplexity();
        }
        return complexity;
    }

    /**
     * @return the complexity of the signed formula.
     */
    public int SignedFormula.getComplexity() {
        return getFormula().getComplexity();
    }

    public int SignedFormulaList.getComplexity() {
        int complexity = 0;
        for (int i = 0; i < this.size(); i++) {
            complexity += this.get(i).getComplexity();
        }
        return complexity;
    }

    public int SignedFormulaFactory.getComplexity() {
        int complexity = 0;
        List<SignedFormula> signedFormulas = new ArrayList<SignedFormula>(getSignedFormulas().values());
        for (int i = 0; i < signedFormulas.size(); i++) {
            complexity += ((SignedFormula) signedFormulas.get(i))
                    .getComplexity();
        }
        return complexity;
    }

}
