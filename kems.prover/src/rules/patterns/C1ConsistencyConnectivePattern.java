package rules.patterns;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;
import rules.getters.C1_F_CONS_ANY_Getter;

public class C1ConsistencyConnectivePattern implements
		IBinarySignedFormulaPattern {

	private FormulaSign _sign1;
	private Connective _conn;
	private FormulaSign _sign2;
	private KERuleRole _role1;
	private KERuleRole _role2;
	private KERuleRole _role3;
	private C1SignConsistencyAnyBinaryConnectivePattern _main_sub_pattern;
	private C1SignConsistencyPattern _auxiliary_sub_pattern;

	public C1ConsistencyConnectivePattern(FormulaSign sign1, Connective conn,
			FormulaSign sign2, KERuleRole role1, KERuleRole role2,
			KERuleRole role3) {
		_sign1 = sign1;
		_conn = conn;
		_sign2 = sign2;
		_role1 = role1;
		_role2 = role2;
		_role3 = role3;
		_main_sub_pattern = new C1SignConsistencyAnyBinaryConnectivePattern(
				_sign1, _conn);
		_auxiliary_sub_pattern = new C1SignConsistencyPattern(_sign2);
	}

	public boolean matches(SignedFormula main, SignedFormula auxiliary) {
		return matchesMain(main) && matchesAuxiliary(auxiliary, main);
	}

	private boolean matchesAuxiliary(SignedFormula auxiliary, SignedFormula main) {
		// main (we know) is F !( X%Y & !(X%Y))
		// LEFT LEFT LEFT gives us X
		// LEFT LEFT RIGHT gives us Y
		Formula f1 = getAuxiliaryBaseFormula(main);

		if (_auxiliary_sub_pattern.matches(auxiliary)) {
			// aux (we know) is T !(A&!A) for some A
			Formula f2 = _role1.getFormulas(auxiliary.getFormula()).get(0);
			f2 = _role2.getFormulas(f2).get(0);
			return f1.equals(f2);
		} else
			return false;

	}

	private Formula getAuxiliaryBaseFormula(SignedFormula main) {
		Formula f1 = _role1.getFormulas(main.getFormula()).get(0);
		f1 = _role2.getFormulas(f1).get(0);
		f1 = _role3.getFormulas(f1).get(0);
		return f1;
	}

	public boolean matchesMain(SignedFormula sfMain) {
		// F !( X%Y & !(X%Y)) using main sub pattern
		return _main_sub_pattern.matches(sfMain);
	}

	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormula sfMain) {

		SignedFormulaList sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(_sign2,
				C1_F_CONS_ANY_Getter.createC1ConsistencyFormula(ff,
						getAuxiliaryBaseFormula(sfMain))));
		return sfl;
	}
}
