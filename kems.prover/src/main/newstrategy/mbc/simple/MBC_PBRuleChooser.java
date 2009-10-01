/*
 * Created on 01/11/2005
 *
 */
package main.newstrategy.mbc.simple;

import java.util.Iterator;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCRules;
import logicalSystems.mbc.MBCSigns;
import main.newstrategy.IPBRuleChooser;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNodeState;
import main.proofTree.iterator.IProofTreeVeryBasicIterator;
import rules.Rule;

/**
 * PB Rule Chooser for mbC
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class MBC_PBRuleChooser implements IPBRuleChooser {
    private ISimpleStrategy strategy;

    /**
     * @param strategy
     */
    public MBC_PBRuleChooser(ISimpleStrategy strategy) {
        super();
        this.strategy = strategy;
    }

    public boolean canApply(Rule r, SignedFormula sf, SignedFormulaBuilder sfb) {
        //        System.err.println(sf);
        if (sf.getSign().equals(MBCSigns.FALSE)
                && sf.getFormula() instanceof CompositeFormula
                && ((CompositeFormula) sf.getFormula()).getConnective().equals(
                        MBCConnectives.CONSISTENCY)) {
                        strategy.getCurrent().removeFromPBCandidates(sf, SignedFormulaNodeState.FULFILLED);
            return false;
        }

        // TODO too much specific 
        if (r == MBCRules.T_NOT_1) {
            //            Choosing for sf
            Formula f = sfb.getFormulaFactory().createCompositeFormula(
                    MBCConnectives.CONSISTENCY,
                    (Formula) sf.getFormula().getImmediateSubformulas().get(0));
            IProofTreeVeryBasicIterator it = strategy.getCurrent()
                    .getTopDownIterator();
            while (it.hasNext()) {
                SignedFormula sf2 = (SignedFormula) it.next().getContent();
                //                Choosing for sf and sf2
                if (includes(sf2.getFormula(), f)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Verifies if a formula contains other formula as subformula
     * 
     * @param container -
     *            a formula
     * @param contained -
     *            another formula
     * @return true if contained is a subformula of contained.
     */
    private boolean includes(Formula container, Formula contained) {
        if (container == contained) {
            return true;
        } else {
            if (container == null || (container instanceof AtomicFormula)) {
                return false;
            } else {
                Iterator<Formula> it = container.getImmediateSubformulas().iterator();
                while (it.hasNext()) {
                    if (includes((Formula) it.next(), contained)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
