package rules.patterns;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * A pattern specially developed for the mci rule F_CONS
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class VerySpecialMCIRulePattern implements IUnarySignedFormulaPattern {

	FormulaSign _sign;

	Connective _conn1;

	Connective _conn2;

	public VerySpecialMCIRulePattern(FormulaSign sign,
			Connective firstConnective, Connective secondConnective) {
		_sign = sign;
		_conn1 = firstConnective;
		_conn2 = secondConnective;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see patterns.SignedFormulaPattern#matches(signedFormulas.SignedFormula)
	 */
	public boolean matches(SignedFormula sf) {
		return sf.getSign().equals(_sign)
				&& matchesConnectives(sf.getFormula());
	}

	private boolean matchesConnectives(Formula f) {
		if (f instanceof AtomicFormula) {
			return false;
		} else
			return matchesFirstConnective(f) && matchesSecondConnective(f);

	}

	private boolean matchesFirstConnective(Formula f) {
		return ((CompositeFormula) f).getConnective().equals(_conn1);
	}

	private boolean matchesSecondConnective(Formula f) {

		// secondFormula must be (_conn1)^n _conn2 para n>=0

		Formula secondFormula = f;
		do {

			secondFormula = (Formula) ((CompositeFormula) secondFormula)
					.getImmediateSubformulas().get(0);

			if (secondFormula instanceof AtomicFormula) {
				return false;
			} else if (((CompositeFormula) secondFormula).getConnective()
					.equals(_conn1)) {
				return true;
			} else if (!((CompositeFormula) secondFormula).getConnective()
					.equals(_conn2)) {
				return false;
			}

		} while (true);
	}

}
