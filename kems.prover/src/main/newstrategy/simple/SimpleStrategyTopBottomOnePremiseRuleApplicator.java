/*
 * Created on 28/11/2005
 *
 */
package main.newstrategy.simple;

import logic.formulas.Formula;
import logic.signedFormulas.PBCandidateList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import main.newstrategy.ISimpleStrategy;
import main.strategy.ClassicalProofTree;
import main.strategy.applicator.TopBottomOnePremiseRuleApplicator;

/**
 * [CLASS_DESCRIPTION]
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SimpleStrategyTopBottomOnePremiseRuleApplicator extends
        TopBottomOnePremiseRuleApplicator {
    
  private SignedFormulaList formulasWithTOPorBOTTOM = null;


    /**
     * @param strategy
     * @param ruleListName
     */
    public SimpleStrategyTopBottomOnePremiseRuleApplicator(
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

        if (formulasWithTOPorBOTTOM == null) {
        formulasWithTOPorBOTTOM = calculate(proofTree, topFormula,
              bottomFormula);
        }
        
        if (formulasWithTOPorBOTTOM.size() > 0) {
        return formulasWithTOPorBOTTOM.remove(0);
        }

        
        return null;
    }
    
    protected void updateFormulasWithTOPorBOTTOM(SignedFormula sf,
            Formula topFormula, Formula bottomFormula) {
        if (topFormula.isStrictSubformula(sf.getFormula())
                || bottomFormula.isStrictSubformula(sf.getFormula())) {
            if (!formulasWithTOPorBOTTOM.contains(sf)) {
                formulasWithTOPorBOTTOM.add(0, sf);
            }
        }
    }

    protected void resetFormulasWithTOPorBottom() {
        formulasWithTOPorBOTTOM = null;
    }
    
    /**
     * @param cpt
     * @param topFormula
     * @param bottomFormula
     * @return
     */
    private SignedFormulaList calculate(ClassicalProofTree cpt,
            Formula topFormula, Formula bottomFormula) {
        SignedFormulaList sfl = getStrategy().getLocalReferences(cpt, topFormula);
        sfl.addAll(getStrategy().getLocalReferences(cpt, bottomFormula));
        sfl = intersect(cpt.getPBCandidates(), sfl);
        return sfl;
    }

    
    /**
     * @param candidates
     * @param sfl
     * @return
     */
    private SignedFormulaList intersect(PBCandidateList  candidates,
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
