/*
 * Created on 25/11/2005
 *
 */
package main.newstrategy.memorysaver;

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
import main.strategy.memorySaver.OptimizedClassicalProofTree;
import main.strategy.memorySaver.ReferenceFinder;
import main.tableau.Method;

/**
 * A strategy that saves some memory compared to simple strategy. It uses a
 * Reference Finder to finds references to formulas, it does not create origins
 * and it discards closed left branches.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MemorySaverStrategy extends AbstractSimpleStrategy {

    public MemorySaverStrategy(Method m) {
        super(m);
        
// COMENTADO EM 07-07-2006
        //        setSaveOrigin(false);
//        setDiscardClosedBranches(true);
        
        // initialize rule applicators
        addRuleApplicator(new OnePremiseRuleApplicator(this,
                ClassicalRuleStructures.ONE_PREMISE_RULE_LIST));
        addRuleApplicator(new MemorySaverTopBottomOnePremiseRuleApplicator(this,
                ClassicalRuleStructures.TOP_BOTTOM_ONE_PREMISE_RULE_LIST));
        addRuleApplicator(new MemorySaverSimplificationTwoPremiseRuleApplicator(this,
                ClassicalRuleStructures.TWO_PREMISE_RULE_LIST));
    }
    
    /**
     * @param root
     * @return
     */
    public IProofTree createPTInstance(SignedFormulaNode root) {
        return new OptimizedClassicalProofTree(root);
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.newstrategy.ISimpleStrategy#getLocalReferences(main.strategy.IClassicalProofTree,
     *      logic.formulas.Formula)
     */
    public SignedFormulaList getLocalReferences(IClassicalProofTree proofTree,
            Formula formula) {
        return ReferenceFinder.getInstance().getLocalReferences(proofTree,
                formula);
    }

    public FormulaList getSubformulaLocalReferences(
            IClassicalProofTree proofTree, Formula formula,
            SignedFormula signedFormula) {
        return ReferenceFinder.getInstance().getSubformulaLocalReferences(
                proofTree, formula, signedFormula);
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.newstrategy.ISimpleStrategy#getParentReferences(main.strategy.IClassicalProofTree,
     *      logic.formulas.Formula)
     */
    public SignedFormulaList getParentReferences(IClassicalProofTree proofTree,
            Formula formula) {
        return ReferenceFinder.getInstance().getParentReferences(proofTree,
                formula);
    }

	//EMERSON: Temporário Algoritmo Genético
    private AGConfiguration.Abordagens _abordagemAG = AGConfiguration.Abordagens.NotApplyAG;
	public AGConfiguration.Abordagens getAbordagensAG() {return _abordagemAG;}
	public void setAbordagemAG(AGConfiguration.Abordagens abordagemAG) {this._abordagemAG = abordagemAG;}
    
}
