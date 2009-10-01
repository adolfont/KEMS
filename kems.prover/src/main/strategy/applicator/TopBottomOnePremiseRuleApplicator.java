/*
 * Created on 27/10/2005
 *
 */
package main.strategy.applicator;

import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaBuilder;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import main.exceptions.KEMSException;
import main.newstrategy.ISimpleStrategy;
import main.proofTree.SignedFormulaNode;
import main.proofTree.SignedFormulaNodeState;
import main.strategy.ClassicalProofTree;
import rules.KERuleRole;
import rules.Rule;
import rules.structures.TopBottomRoleRuleList;

/**
 * Applies top and bottom one premise rules
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class TopBottomOnePremiseRuleApplicator implements
        IRuleApplicator {

    private ISimpleStrategy strategy;

    private String ruleListName;

    //    private SignedFormulaList formulasWithTOPorBOTTOM = null;

    /**
     * @param strategy
     * @param sfb
     */
    public TopBottomOnePremiseRuleApplicator(ISimpleStrategy strategy,
            String ruleListName) {
        super();
        this.strategy = strategy;
        this.ruleListName = ruleListName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see main.strategy.applicator.IRuleApplicator#applyAll(main.strategy.ClassicalProofTree,
     *      logic.signedFormulas.SignedFormulaBuilder)
     */
    public boolean applyAll(ClassicalProofTree proofTree,
            SignedFormulaBuilder sfb) {
        Formula topFormula = sfb
                .createCompositeFormula(ClassicalConnectives.TOP);
        Formula bottomFormula = sfb
                .createCompositeFormula(ClassicalConnectives.BOTTOM);
        TopBottomRoleRuleList topAndBottomRules = (TopBottomRoleRuleList) strategy
                .getMethod().getRules().get(ruleListName);

        SignedFormula sfTB = nextSignedFormulaWithTOPorBOTTOM(proofTree,
                topFormula, bottomFormula);
        //		SignedFormulaList formulasWithTOPorBOTTOM = calculate(proofTree,
        //				topFormula, bottomFormula);

        boolean hasApplied = false;

        while (sfTB != null && !proofTree.isClosed()) {
            //			while (formulasWithTOPorBOTTOM.size() > 0 &&
            // !proofTree.isClosed()) {
            SignedFormula aSignedFormulaWithTOPorBOTTOM = sfTB;
            FormulaList fl = getSubformulaLocalReferences(proofTree,
                    topFormula, aSignedFormulaWithTOPorBOTTOM);
            fl.addAll(getSubformulaLocalReferences(proofTree, bottomFormula,
                    aSignedFormulaWithTOPorBOTTOM));

            CompositeFormula cf1 = (CompositeFormula) fl.get(0);

            CompositeFormula cf1_left = null, cf1_right = null;
            if (cf1.getImmediateSubformulas().get(0) instanceof CompositeFormula) {
                cf1_left = (CompositeFormula) cf1.getImmediateSubformulas()
                        .get(0);
            }
            if (cf1.getImmediateSubformulas().size() > 1
                    && cf1.getImmediateSubformulas().get(1) instanceof CompositeFormula) {
                cf1_right = (CompositeFormula) cf1.getImmediateSubformulas()
                        .get(1);
            }

            Rule r = null;
            SignedFormula sf = null;

            if (cf1_left == topFormula || cf1_left == bottomFormula) {
                r = topAndBottomRules.get(cf1_left.getConnective(), cf1
                        .getConnective(), KERuleRole.LEFT);
                sf = getSignedFormula(r, sfb, aSignedFormulaWithTOPorBOTTOM);
            } else {
                if (cf1_right == topFormula || cf1_right == bottomFormula) {
                    r = topAndBottomRules.get(cf1_right.getConnective(), cf1
                            .getConnective(), KERuleRole.RIGHT);
                    sf = getSignedFormula(r, sfb, aSignedFormulaWithTOPorBOTTOM);
                }
            }

            //			formulasWithTOPorBOTTOM.remove(aSignedFormulaWithTOPorBOTTOM);
            proofTree.removeFromPBCandidates(aSignedFormulaWithTOPorBOTTOM,
                    SignedFormulaNodeState.ANALYSED);
            proofTree.addLast(new SignedFormulaNode(sf,
                    SignedFormulaNodeState.NOT_ANALYSED, strategy.createOrigin(
                            r,
                            proofTree.getNode(aSignedFormulaWithTOPorBOTTOM),
                            null)));

            hasApplied = true;

            updateFormulasWithTOPorBOTTOM(sf, topFormula, bottomFormula);

            //			if (topFormula.isStrictSubformula(sf.getFormula())
            //					|| bottomFormula.isStrictSubformula(sf.getFormula())) {
            //				if (!formulasWithTOPorBOTTOM.contains(sf)) {
            //					formulasWithTOPorBOTTOM.add(0, sf);
            //				}
            //			}

            sfTB = nextSignedFormulaWithTOPorBOTTOM(proofTree, topFormula,
                    bottomFormula);
        }

        resetFormulasWithTOPorBottom();
        return hasApplied;
    }

    protected abstract SignedFormula nextSignedFormulaWithTOPorBOTTOM(
            ClassicalProofTree proofTree, Formula topFormula,
            Formula bottomFormula);

    protected FormulaList getSubformulaLocalReferences(
            ClassicalProofTree proofTree, Formula topFormula,
            SignedFormula aSignedFormula) {
        return strategy.getSubformulaLocalReferences(proofTree, topFormula,
                aSignedFormula);
    }

    /**
     * gets the conclusion of a rule.
     * 
     * @param r -
     *            the rule
     * @param sfb -
     *            the formula builder
     * @param aFormula -
     *            the signed formula
     * @return the conclusion
     */
    protected final SignedFormula getSignedFormula(Rule r,
            SignedFormulaBuilder sfb, SignedFormula aFormula) {
        SignedFormulaList sfl = r.getPossibleConclusions(sfb
                .getSignedFormulaFactory(), sfb.getFormulaFactory(),
                new SignedFormulaList(aFormula));
        if (sfl.size() > 0) {
            return sfl.get(0);
        } else
            throw new KEMSException("Rule applied but no conclusion: " + r);
    }

    protected abstract void updateFormulasWithTOPorBOTTOM(SignedFormula sf,
            Formula topFormula, Formula bottomFormula);

    protected abstract void resetFormulasWithTOPorBottom();

    protected ISimpleStrategy getStrategy() {
        return strategy;
    }

}
