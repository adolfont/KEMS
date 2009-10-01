/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import java.util.List;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;

/**
 * Another class for representing binary signed formula patterns used when rules
 * with two premisses are analysed.
 * 
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class TwoSignsTwoConnectivesRolePattern implements
		IBinarySignedFormulaPattern {

	private FormulaSign _mainSign, _auxiliarySign;

	private Connective _mainConnective;

	private Connective _auxiliaryConnective;

	private KERuleRole _auxiliaryRole;

	/**
	 * @param sign
	 * @param connective
	 * @param sign2
	 * @param connective2
	 * @param role
	 */
	public TwoSignsTwoConnectivesRolePattern(FormulaSign sign,
			Connective connective, FormulaSign sign2, Connective connective2,
			KERuleRole role) {
		super();
		_mainSign = sign;
		_mainConnective = connective;
		_auxiliarySign = sign2;
		_auxiliaryConnective = connective2;
		_auxiliaryRole = role;
	}

	/**
	 * Verifies if auxiliary formula is equal to the role in the main formula.
	 * 
	 * @param main
	 * @param auxiliary
	 * @return
	 */
	public boolean roleMatches(Formula main, Formula auxiliary) {
		return _auxiliaryRole.getFormulas(main).contains(auxiliary);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see patterns.BinarySignedFormulaPattern#matches(null, null)
	 */
	public boolean matches(SignedFormula main, SignedFormula auxiliary) {

		boolean mainMatch = matchesMain(main);

		boolean auxiliaryMatch = _auxiliarySign.equals(auxiliary.getSign())
				&& roleMatches(main.getFormula(), (Formula) _auxiliaryRole
						.getFormulas(auxiliary.getFormula()).get(0))
				&& matchesConnective(auxiliary.getFormula(),
						_auxiliaryConnective);

		return mainMatch && auxiliaryMatch;
	}

	/**
	 * @param main
	 * @return
	 */
	public boolean matchesMain(SignedFormula main) {
		boolean mainMatch = _mainSign.equals(main.getSign())
				&& matchesConnective(main.getFormula(), _mainConnective);
		return mainMatch;
	}

	private boolean matchesConnective(Formula f, Connective connective) {
		if (f instanceof AtomicFormula) {
			return false;
		} else
			return ((CompositeFormula) f).getConnective().equals(connective);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see patterns.BinarySignedFormulaPattern#getAuxiliaryCandidates(signedFormulasNew.SignedFormulaFactory,
	 *      formulasNew.FormulaFactory, signedFormulasNew.SignedFormula)
	 */
	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormula sfMain) {
		SignedFormulaList sfl = new SignedFormulaList();

		List<Formula> l = _auxiliaryRole.getFormulas(sfMain.getFormula());
		for (int i = 0; i < l.size(); i++) {
			sfl.add(sff.createSignedFormula(_auxiliarySign, ff
					.createCompositeFormula(_auxiliaryConnective, (Formula) l
							.get(i))));
		}

		return sfl;
	}

}