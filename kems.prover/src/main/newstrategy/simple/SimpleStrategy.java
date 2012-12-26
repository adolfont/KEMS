/*
 * Created on 27/10/2005
 *
 */
package main.newstrategy.simple;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalRuleStructures;
import main.ag.strategy.util.AGConfiguration;
import main.newstrategy.AbstractSimpleStrategy;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.strategy.IClassicalProofTree;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

/**
 * A simple strategy for classical propositional logic.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SimpleStrategy extends AbstractSimpleStrategy {

	/**
	 * Empty constructor to be used by subclasses.
	 */
	protected SimpleStrategy() {
	}

	public SimpleStrategy(Method m) {
		super(m);

		// initialize rule applicators
		addRuleApplicator(new OnePremiseRuleApplicator(this,
				ClassicalRuleStructures.ONE_PREMISE_RULE_LIST));
		addRuleApplicator(new SimpleStrategyTopBottomOnePremiseRuleApplicator(
				this, ClassicalRuleStructures.TOP_BOTTOM_ONE_PREMISE_RULE_LIST));
		addRuleApplicator(new SimpleStrategySimplificationTwoPremiseRuleApplicator(
				this, ClassicalRuleStructures.TWO_PREMISE_RULE_LIST));

	}

	/**
	 * @param root
	 * @return
	 */
	public IProofTree createPTInstance(SignedFormulaNode root) {
		return new FormulaReferenceClassicalProofTree(root);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#getLocalReferences(main.strategy.IClassicalProofTree,
	 *      logic.formulas.Formula)
	 */
	public SignedFormulaList getLocalReferences(IClassicalProofTree proofTree,
			Formula formula) {
		return ((FormulaReferenceClassicalProofTree) proofTree)
				.getLocalReferences(formula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#getSubformulaLocalReferences(main.strategy.IClassicalProofTree,
	 *      logic.formulas.Formula, logic.signedFormulas.SignedFormula)
	 */
	public FormulaList getSubformulaLocalReferences(
			IClassicalProofTree proofTree, Formula formula,
			SignedFormula signedFormula) {
		return ((FormulaReferenceClassicalProofTree) proofTree)
				.getSubformulaLocalReferences(formula, signedFormula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.newstrategy.ISimpleStrategy#getParentReferences(main.strategy.IClassicalProofTree,
	 *      logic.formulas.Formula)
	 */
	public SignedFormulaList getParentReferences(IClassicalProofTree proofTree,
			Formula formula) {
		return ((FormulaReferenceClassicalProofTree) proofTree)
				.getParentReferences(formula);
	}

	//EMERSON: Temporário Algoritmo Genético
	private AGConfiguration.Abordagens _abordagemAG = AGConfiguration.Abordagens.NotApplyAG;
	public AGConfiguration.Abordagens getAbordagensAG() {return _abordagemAG;}
	public void setAbordagemAG(AGConfiguration.Abordagens abordagemAG) {this._abordagemAG = abordagemAG;}

}
