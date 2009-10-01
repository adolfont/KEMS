package rules.getters;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.patterns.C1_Sign_T_NOT_1_Pattern;

public class C1_T_NOT_1_Getter implements KESignedFormulaGetter {

	private FormulaSign _sign;

	public C1_T_NOT_1_Getter(FormulaSign sign) {
		_sign = sign;
	}

	public SignedFormula getSignedFormula(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {
		// base is !(X&!X)
		return sff.createSignedFormula(_sign, C1_Sign_T_NOT_1_Pattern
				.getFormulaOfConsistency(sfl.get(0).getFormula()));
	}
}
