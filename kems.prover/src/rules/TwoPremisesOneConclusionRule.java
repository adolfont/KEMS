/*
 * Created on 10/11/2004
 *
 */
package rules;

import logic.formulas.CompositeFormula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.getters.KESignedFormulaGetter;
import rules.getters.SubformulaGetter;
import rules.patterns.IBinarySignedFormulaPattern;
import rules.patterns.ISubformulaPattern;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class TwoPremisesOneConclusionRule extends OneConclusionRule {

	IBinarySignedFormulaPattern _premise;

	public TwoPremisesOneConclusionRule(String name,
			IBinarySignedFormulaPattern premise, KEAction conclusion) {
		super(name, conclusion);
		_premise = premise;
	}

	public final SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {

		SignedFormula mainPremise = sfl.get(0);
		SignedFormula auxPremise = sfl.get(1);
		if (_premise.matches(mainPremise, auxPremise)) {
			SignedFormulaList result = new SignedFormulaList();
			result.add(((KESignedFormulaGetter) getConclusion().getContent())
					.getSignedFormula(sff, ff, sfl));
			return result;
		} else
			return null;
	}

	/**
	 * @param sff
	 * @param ff
	 * @param sfMain
	 * @return
	 */
	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormula sfMain) {
		return _premise.getAuxiliaryCandidates(sff, ff, sfMain);
	}

	/**
	 * @param sfMain
	 * @return
	 */
	public boolean matchesMain(SignedFormula sfMain) {
		return _premise.matchesMain(sfMain);
	}

	//    /**
	//     * @param sf
	//     * @param order
	//     * @return
	//     */
	//    public Formula getMainMatch(SignedFormula sf, int order) {
	//        return ((SubformulaPattern) _premise).getMainMatch(sf, order);
	//    }

	/**
	 * @param sf
	 * @return
	 */
	public FormulaList getMainMatches(SignedFormula sf) {
		return ((ISubformulaPattern) _premise).getMainMatches(sf);
	}

	/**
	 * @param signedFormulaFactory
	 * @param formulaFactory
	 * @param sflToApply
	 * @param f
	 * @return
	 */
	public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl, CompositeFormula f) {
//		SignedFormula mainPremise = sfl.get(0);
//		SignedFormula auxPremise = sfl.get(1);
		SignedFormulaList result = new SignedFormulaList();
		result.add(((SubformulaGetter) getConclusion().getContent())
				.getSignedFormula(sff, ff, sfl, f));
		return result;
	}

}