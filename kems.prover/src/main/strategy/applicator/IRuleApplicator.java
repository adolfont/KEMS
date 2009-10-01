/*
 * Created on 26/10/2005
 *
 */
package main.strategy.applicator;

import logic.signedFormulas.SignedFormulaBuilder;
import main.strategy.ClassicalProofTree;

/**
 * Interface for objects that apply rules to a proof tree.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public interface IRuleApplicator {

    /** 
     * Tries to apply some rules to a proof tree.
     * 
     * @param current
     * @param sfb
     * @return true if some rule was applied.
     */
    boolean applyAll(ClassicalProofTree current, SignedFormulaBuilder sfb);

}
