/*
 * Created on 28/11/2005
 *
 */
package main.newstrategy.memorysaver;

import logic.signedFormulas.SignedFormula;
import main.newstrategy.ISimpleStrategy;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.SimplificationTwoPremiseRuleApplicator;
import main.strategy.memorySaver.ReferenceFinder;

/**
 * A two premise simplification rule aapplicator for MemorySaverStrategy 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class MemorySaverSimplificationTwoPremiseRuleApplicator extends
        SimplificationTwoPremiseRuleApplicator implements IRuleApplicator {

    /**
     * @param strategy
     * @param ruleListName
     */
    public MemorySaverSimplificationTwoPremiseRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
        super(strategy, ruleListName);
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

	protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree,
			SignedFormula auxCandidate) {

		SignedFormula result = ReferenceFinder.getInstance().getSignedFormulaWith(proofTree
				.getPBCandidates(), auxCandidate.getFormula(),
				getPositionMainCandidates());
		positionMainCandidates++;
		return result;

	}


}
