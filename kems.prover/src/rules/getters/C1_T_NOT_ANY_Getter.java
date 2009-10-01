package rules.getters;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;

public class C1_T_NOT_ANY_Getter implements KESignedFormulaGetter {

	private KERuleRole _role;
	private FormulaSign _sign;

	public C1_T_NOT_ANY_Getter(FormulaSign sign, KERuleRole role) {
		_sign = sign;
		_role = role;
	}

	public SignedFormula getSignedFormula(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {
		Formula base = sfl.get(0).getFormula();
		Formula f = KERuleRole.LEFT.getFormulas(base).get(0);
		f = _role.getFormulas(f).get(0);
		return sff
				.createSignedFormula(_sign, C1_F_CONS_ANY_Getter.createC1ConsistencyFormula(ff, f));
	}


}
