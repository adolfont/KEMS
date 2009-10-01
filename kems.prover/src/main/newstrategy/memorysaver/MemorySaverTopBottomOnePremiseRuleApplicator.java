/*
 * Created on 28/11/2005
 *
 */
package main.newstrategy.memorysaver;

import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.TopBottomOnePremiseRuleApplicator;
import main.strategy.memorySaver.ReferenceFinder;

/**
 * A top bottom one premise rule aapplicator for MemorySaverStrategy
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class MemorySaverTopBottomOnePremiseRuleApplicator extends
		TopBottomOnePremiseRuleApplicator {

	/**
	 * @param strategy
	 * @param ruleListName
	 */
	public MemorySaverTopBottomOnePremiseRuleApplicator(
			ISimpleStrategy strategy, String ruleListName) {
		super(strategy, ruleListName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.applicator.TopBottomOnePremiseRuleApplicator#nextSignedFormulaWithTOPorBOTTOM(main.strategy.ClassicalProofTree,
	 *      logic.formulas.Formula, logic.formulas.Formula)
	 */
	protected SignedFormula nextSignedFormulaWithTOPorBOTTOM(
			ClassicalProofTree proofTree, Formula topFormula,
			Formula bottomFormula) {
		return ReferenceFinder.getInstance().getSignedFormulaWith(
				proofTree.getPBCandidates(), topFormula, bottomFormula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.applicator.TopBottomOnePremiseRuleApplicator#updateFormulasWithTOPorBOTTOM(logic.signedFormulas.SignedFormula,
	 *      logic.formulas.Formula, logic.formulas.Formula)
	 */
	protected void updateFormulasWithTOPorBOTTOM(SignedFormula sf,
			Formula topFormula, Formula bottomFormula) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.applicator.TopBottomOnePremiseRuleApplicator#resetFormulasWithTOPorBottom()
	 */
	protected void resetFormulasWithTOPorBottom() {
	}

}
