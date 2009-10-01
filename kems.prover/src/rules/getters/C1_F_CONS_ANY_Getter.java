package rules.getters;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.C1Connectives;
import rules.KERuleRole;

public class C1_F_CONS_ANY_Getter implements KESignedFormulaGetter {

	private KERuleRole _role;
	private FormulaSign _sign;

	public C1_F_CONS_ANY_Getter(FormulaSign sign, KERuleRole role) {
		_sign = sign;
		_role = role;
	}

	public SignedFormula getSignedFormula(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {
		Formula base = sfl.get(0).getFormula();
		Formula f = KERuleRole.LEFT.getFormulas(base).get(0);
		f = _role.getFormulas(f).get(0);
		return sff
				.createSignedFormula(_sign, createC1ConsistencyFormula(ff, f));
	}

	public static Formula createC1ConsistencyFormula(FormulaFactory ff,
			Formula f) {
		Formula not_f = ff.createCompositeFormula(C1Connectives.NOT, f);
		Formula f_and_not_f = ff.createCompositeFormula(C1Connectives.AND, f,
				not_f);
		Formula not__f_and_not_f = ff.createCompositeFormula(C1Connectives.NOT,
				f_and_not_f);

		return not__f_and_not_f;
	}

}
