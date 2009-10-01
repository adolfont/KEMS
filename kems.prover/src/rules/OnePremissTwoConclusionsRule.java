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
public class OnePremissTwoConclusionsRule extends Rule {

    IUnarySignedFormulaPattern _premise;

    KEAction _conclusion1, _conclusion2;

    public OnePremissTwoConclusionsRule(String name,
            IUnarySignedFormulaPattern premise, KEAction conclusion1,
            KEAction conclusion2) {
    	super(name);
        _premise = premise;
        _conclusion1 = conclusion1;
        _conclusion2 = conclusion2;
    }

    public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
        SignedFormula premise = sfl.get(0);
        if (_premise.matches(premise)) {
            SignedFormulaList l = new SignedFormulaList();
            l.add(((KESignedFormulaGetter) _conclusion1.getContent())
                    .getSignedFormula(sff, ff, sfl));
            l.add(((KESignedFormulaGetter) _conclusion2.getContent())
                    .getSignedFormula(sff, ff, sfl));
            return l;
        } else
            return null;
    }


}