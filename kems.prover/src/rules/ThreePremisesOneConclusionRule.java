/*
 * Created on 21/09/2005
 *
 */
package rules;

import logic.formulas.CompositeFormula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.getters.KESignedFormulaGetter;
import rules.patterns.ITernarySignedFormulaPattern;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ThreePremisesOneConclusionRule extends
		OneConclusionRule{
	
    ITernarySignedFormulaPattern _premise;

	
	/**
	 * @param name
	 * @param premise
	 * @param conclusion
	 */
	public ThreePremisesOneConclusionRule(String name,
			ITernarySignedFormulaPattern premise, KEAction conclusion) {
		super(name, conclusion);
		_premise = premise;
	}
	
	/* (non-Javadoc)
	 * @see rules.TwoPremisesOneConclusionRule#getPossibleConclusions(logic.signedFormulas.SignedFormulaFactory, logic.formulas.FormulaFactory, logic.signedFormulas.SignedFormulaList)
	 */
	public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {
//        SignedFormula mainPremise = sfl.get(0);
//        SignedFormula auxPremise1 = sfl.get(1);
//        SignedFormula auxPremise2 = sfl.get(2);
        SignedFormulaList result = new SignedFormulaList();
        result.add(((KESignedFormulaGetter) getConclusion().getContent())
                    .getSignedFormula(sff, ff, sfl));
        return result;
	}
	
	/* (non-Javadoc)
	 * @see rules.TwoPremisesOneConclusionRule#getPossibleConclusions(logic.signedFormulas.SignedFormulaFactory, logic.formulas.FormulaFactory, logic.signedFormulas.SignedFormulaList, logic.formulas.CompositeFormula)
	 */
	public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl, CompositeFormula f) {
		throw new RuntimeException();
	}

	/**
	 * @return
	 */
	public ITernarySignedFormulaPattern getPattern() {
		return _premise;
	}
	
	
	

}
