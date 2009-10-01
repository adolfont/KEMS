/*
 * Created on 28/11/2005
 *
 */
package main.newstrategy.simple;

import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.ISimpleStrategy;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.IRuleApplicator;
import main.strategy.applicator.SimplificationTwoPremiseRuleApplicator;

/**
 * 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class SimpleStrategySimplificationTwoPremiseRuleApplicator extends
        SimplificationTwoPremiseRuleApplicator implements IRuleApplicator {
    
    private int counterMainCandidates;
    private SignedFormulaList mainCandidates;


    /**
     * @param strategy
     * @param ruleListName
     */
    public SimpleStrategySimplificationTwoPremiseRuleApplicator(ISimpleStrategy strategy, String ruleListName) {
        super(strategy, ruleListName);
    }
    
    protected void initializeMainCandidates(ClassicalProofTree proofTree,
            SignedFormula auxCandidate) {

        mainCandidates = intersect(proofTree.getPBCandidates(),
                getParentReferences(proofTree, auxCandidate.getFormula()));
        mainCandidates.remove(auxCandidate);

        counterMainCandidates = 0;
    }

    protected SignedFormula nextMainCandidate(ClassicalProofTree proofTree,
            SignedFormula auxCandidate) {
        if (counterMainCandidates < mainCandidates.size()) {
            return mainCandidates.get(counterMainCandidates++);
        }

        return null;
    }

    protected void decrementPositionMainCandidates() {
    }

    private SignedFormulaList intersect(PBCandidateList candidates,
            SignedFormulaList sfl) {
        SignedFormulaList result = new SignedFormulaList();

        for (int i = 0; i < candidates.size(); i++) {
            if (sfl.contains(candidates.get(i))) {
                result.add(candidates.get(i));
            }
        }

        return result;

    }


}
