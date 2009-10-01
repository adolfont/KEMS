/*
 * Created on 10/11/2004
 *
 */
package rules;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.getters.KESignedFormulaGetter;
import rules.patterns.IUnarySignedFormulaPattern;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class OnePremiseOneConclusionRule extends OneConclusionRule {

    IUnarySignedFormulaPattern _premise;

    public OnePremiseOneConclusionRule(String name,
            IUnarySignedFormulaPattern premise, KEAction conclusion) {
    	super(name, conclusion);
        _premise = premise;
    }

    public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
        SignedFormula premise = sfl.get(0);
        if (_premise.matches(premise)) {
            return new SignedFormulaList(((KESignedFormulaGetter) getConclusion()
                    .getContent()).getSignedFormula(sff, ff, sfl));
        } else {
//            System.err.println(this+ " null for " + sfl);
            return null;
        }
    }

}