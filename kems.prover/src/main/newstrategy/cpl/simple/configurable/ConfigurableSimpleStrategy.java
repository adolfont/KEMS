/*
 * Created on 03/12/2005
 *
 */
package main.newstrategy.cpl.simple.configurable;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.AbstractSimpleStrategy;
import main.newstrategy.cpl.configurable.comparator.ISignedFormulaComparator;
import main.newstrategy.simple.SimpleStrategyTopBottomOnePremiseRuleApplicator;
import main.proofTree.IProofTree;
import main.proofTree.SignedFormulaNode;
import main.strategy.IClassicalProofTree;
import main.strategy.applicator.OnePremiseRuleApplicator;
import main.strategy.simple.FormulaReferenceClassicalProofTree;
import main.tableau.Method;

/**
 * A strategy that allows one to choose the order the rules are applied.
 * 
 * It can only be used with the ConfigurableClassicalRuleStructures.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ConfigurableSimpleStrategy extends AbstractSimpleStrategy {

    protected ConfigurableTwoPremiseRuleApplicator twoPremiseRuleApplicator;

    public ConfigurableSimpleStrategy(Method m) {
        super(m);

        // initialize rule applicators
        addRuleApplicator(new OnePremiseRuleApplicator(this,
                ConfigurableClassicalRuleStructures.ONE_PREMISE_RULE_LIST));
        addRuleApplicator(new SimpleStrategyTopBottomOnePremiseRuleApplicator(
                this,
                ConfigurableClassicalRuleStructures.TOP_BOTTOM_ONE_PREMISE_RULE_LIST));

        // The only difference: two lists of simple two-premise rules
        addRuleApplicator(twoPremiseRuleApplicator= new ConfigurableTwoPremiseRuleApplicator(
                this,
                ConfigurableClassicalRuleStructures.TWO_PREMISE_RULE_LIST,
                ConfigurableClassicalRuleStructures.SECOND_TWO_PREMISE_RULE_LIST
          ));
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

    /**
     * @param signedFormulaComparator
     */
    public void setComparator(ISignedFormulaComparator signedFormulaComparator) {
    	super.setComparator(signedFormulaComparator);
        twoPremiseRuleApplicator.setComparator(signedFormulaComparator);
    }

	//EMERSON: Temporário Algoritmo Genético
	private boolean _modoEstocastico;
	public boolean getModoEstocastico() {return _modoEstocastico;}
	public void setModoEstocastico(boolean option) {this._modoEstocastico = option;}
    
}