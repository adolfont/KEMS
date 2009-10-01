/*
 * Created on Oct 22, 2004
 *
 */
package main.strategy.memorySaver;

import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import main.proofTree.Node;
import main.proofTree.ProofTree;
import main.proofTree.SignedFormulaNode;
import main.strategy.ClassicalProofTree;
import main.tableau.Method;

/**
 * Class that represents a strategy for KE Tableaus that tries to use the
 * minimum amount of memory possible.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MemorySaverStrategy extends AbstractMemorySaverStrategy {

	/**
	 * Creates a simple strategy for a proof method
	 */
	public MemorySaverStrategy(Method m) {
		super(m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see strategy.Strategy#createPTInstance(proofTree.Node)
	 */
	public ProofTree createPTInstance(Node aNode) {
		return new OptimizedClassicalProofTree((SignedFormulaNode) aNode);
	}

	protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {

		SignedFormula result = ReferenceFinder.getInstance().getSignedFormulaWith(proofTree
				.getPBCandidates(), auxCandidate.getFormula(),
				getPositionMainCandidates());
		positionMainCandidates++;
		return result;

	}

	protected void initializeMainCandidates(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {
		positionMainCandidates = 0;
	}

	protected int getPositionMainCandidates() {
		return positionMainCandidates;
	}

	protected void decrementPositionMainCandidates() {
		positionMainCandidates--;
	}

	private int positionMainCandidates;

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#resetFormulasWithTOPorBottom()
	 */
	protected void resetFormulasWithTOPorBottom() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#updateFormulasWithTOPorBOTTOM(logic.signedFormulas.SignedFormula,
	 *      logic.formulas.Formula, logic.formulas.Formula)
	 */
	protected void updateFormulasWithTOPorBOTTOM(SignedFormula sf,
			Formula topFormula, Formula bottomFormula) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see main.strategy.AbstractStrategy#nextSignedFormulaWithTOPorBOTTOM(main.strategy.ClassicalProofTree,
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
	 * @see main.strategy.AbstractStrategy#getSubformulaLocalReferences(main.strategy.ClassicalProofTree,
	 *      logic.formulas.Formula, logic.signedFormulas.SignedFormula)
	 */
	protected FormulaList getSubformulaLocalReferences(
			ClassicalProofTree proofTree, Formula tbFormula,
			SignedFormula aSignedFormulaWithTOPorBOTTOM) {
		return ReferenceFinder.getInstance().getSubformulaLocalReferences(proofTree,
				tbFormula, aSignedFormulaWithTOPorBOTTOM);
	}

}
