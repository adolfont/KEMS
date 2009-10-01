/*
 * Created on 28/10/2005
 *
 */
package main.strategy.applicator;

import logic.signedFormulas.SignedFormulaBuilder;
import main.strategy.ClassicalProofTree;

/**
 * A proof transformation generalizes PB rule applicators, among other things. 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IProofTransformation {
    
    boolean apply(ClassicalProofTree current, SignedFormulaBuilder sfb);


}
