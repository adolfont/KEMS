/*
 * Created on 27/10/2005
 *
 */
package main.newstrategy;

import java.util.LinkedList;
import java.util.List;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.newstrategy.util.ProofSaverLoader;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.proofTree.origin.IOrigin;
import main.strategy.ClassicalProofTree;
import main.strategy.IClassicalProofTree;
import main.strategy.applicator.IProofTransformation;
import main.strategy.applicator.IRuleApplicator;
import main.tableau.Method;
import rules.Rule;

/**
 * Interface for simple strategies.
 * 
 * Concerns: Origin, current branch, root branch, signed formula builder, open branches, proof method, proof transformations,
 * rule applicators, formula references (local, subformula, parent), proof saver, comparators, T Top and F Bottom formulas
 * 
 * Boolean options: discard closed branches, save discarded branches, save origin
 * 
 * Actions:finish branch, create Proof Tree instance
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public interface ISimpleStrategy extends IStrategy {

    /**
     * Creates an origin object for a node. Possibly returns null if one does
     * not want to save origin information.
     * 
     * @param rule
     * @param main
     * @param auxiliary
     * @return Origin object or null.
     */
    public IOrigin createOrigin(Rule rule, SignedFormulaNode main,
            SignedFormulaNode auxiliary);

    public IOrigin createOrigin(Rule rule, SignedFormulaNode main,
    		SignedFormulaNode auxiliary1, SignedFormulaNode auxiliary2);

    
    /**
     * sets the current branch.
     * 
     * @param newCurrent
     */
    public void setCurrent(ClassicalProofTree newCurrent);

    /**
     * @return the current branch
     */
    public ClassicalProofTree getCurrent();

    /**
     * @return the root branch of the proof tree
     */
    public ClassicalProofTree getProofTree();

    /**
     * @return the signed formula builder used
     */
    public SignedFormulaBuilder getSignedFormulaBuilder();

    /**
     * @return the stack of open branches
     */
    public LinkedList<IProofTree> getOpenBranches();

    /**
     * sets the stack of open branches
     * 
     * @param openBranches
     */
    public void setOpenBranches(LinkedList<IProofTree> openBranches);

    /**
     * @return the tableau method used
     */
    public Method getMethod();

    /**
     * @return the list of proof transformations
     */
    public List<IProofTransformation> getProofTransformations();

    /**
     * @return the list of rule applicators.
     */
    public List<IRuleApplicator> getRuleApplicators();

    /**
     * @param proofTree -
     *            a proof tree node
     * @param formula -
     *            a formula
     * @return a signed formula list containing the s-formulas where a formula
     *         appears in a proof tree node
     */
    public SignedFormulaList getLocalReferences(IClassicalProofTree proofTree,
            Formula formula);

    /**
     * @param proofTree -
     *            a proof tree node
     * @param formula -
     *            a formula
     * @param signedFormula -
     *            a signed formula
     * @return a formula list containing the formula the formulas where a
     *         formula appears as subformula in a signed formula of a proof tree
     *         node
     */
    public FormulaList getSubformulaLocalReferences(
            IClassicalProofTree proofTree, Formula formula,
            SignedFormula signedFormula);

    /**
     * @param proofTree -
     *            a proof tree node
     * @param formula -
     *            a formula
     * @return all references to a formula in a proof tree node, from the
     *         current branch up
     */
    public SignedFormulaList getParentReferences(IClassicalProofTree proofTree,
            Formula formula);

    /**
     * finishes the left branch
     */
    public void finishBranch(IProofTree branch);

    // TODO ASPECT save discarded branches
    /**
     * sets discard closed branches flag
     * 
     * @param discardClosedBranches
     */
    public void setDiscardClosedBranches(boolean discardClosedBranches);

    // TODO ASPECT save discarded branches
    /**
     * set save discarded closed branches flag
     * 
     * @param saveDiscardedBranches
     */
    public void setSaveDiscardedBranches(boolean saveDiscardedBranches);

    // TODO ASPECT save origin
    /**
     * sets save origin flag
     * 
     * @param saveOrigin
     */
    public void setSaveOrigin(boolean saveOrigin);

    /**
     * creates a proof tree instance
     * 
     * @param root -
     *            the root of the new proof tree
     * @return a proof tree instance
     */
    public IProofTree createPTInstance(SignedFormulaNode root);

    // TODO ASPECT proof saver
    /**
     * @return the proof saver and loader - for discarded proof tree branches
     */
    public ProofSaverLoader getProofSaver();

    
	/**
	 * @return a comparator that can sort the formulas before applying the PB rule
	 */
	public ISignedFormulaComparator getComparator();
	
	/**
	 * @param comparator - a comparator that can sort the formulas before applying the PB rule
	 */
	public void setComparator(ISignedFormulaComparator comparator);
	
	/**
	 * @return the F Bottom formula
	 */
	public SignedFormula getFBottomFormula();

	/**
	 * @return the T Top formula
	 */
	public SignedFormula getTTopFormula();


}
