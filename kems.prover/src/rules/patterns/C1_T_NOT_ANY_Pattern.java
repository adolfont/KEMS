package rules.patterns;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.C1Connectives;
import logicalSystems.c1.C1Signs;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.KERuleRole;
import rules.getters.C1_F_CONS_ANY_Getter;

public class C1_T_NOT_ANY_Pattern implements ITernarySignedFormulaPattern {

	private Connective _conn;
	private SignConnectiveConnectivePattern _mainPattern;
	private SignConnectivePattern _aux1Pattern;
	private C1SignConsistencyPattern _aux2Pattern;
	private KERuleRole _role;

	public C1_T_NOT_ANY_Pattern(Connective conn, KERuleRole role) {
		_conn = conn;
		_role = role;
		_mainPattern = new SignConnectiveConnectivePattern(ClassicalSigns.TRUE,
				C1Connectives.NOT, _conn);
		_aux1Pattern = new SignConnectivePattern(ClassicalSigns.TRUE, _conn);
		_aux2Pattern = new C1SignConsistencyPattern(ClassicalSigns.TRUE);

	}

	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormula sfMain, SignedFormula sfAuxiliary) {
		SignedFormulaList sfl = new SignedFormulaList();
		sfl.add(sff.createSignedFormula(C1Signs.TRUE, C1_F_CONS_ANY_Getter.createC1ConsistencyFormula(ff,
				_role.getFormulas(sfAuxiliary.getFormula()).get(0))));
		return sfl;
	}

	public boolean matches(SignedFormula main, SignedFormula auxiliary1,
			SignedFormula auxiliary2) {
		return matchesMain(main) && matchesFirstAuxiliary(main, auxiliary1)
				&& matchesSecondAuxiliary(main, auxiliary2);
	}

	public boolean matchesFirstAuxiliary(SignedFormula sfMain,
			SignedFormula sfAuxiliary) {
		return _aux1Pattern.matches(sfAuxiliary)
				&& sfMain.getSign().equals(sfAuxiliary.getSign())
				&& sfAuxiliary.getFormula().equals(
						sfMain.getFormula().getImmediateSubformulas().get(0));
	}

	public boolean matchesMain(SignedFormula sfMain) {
		return _mainPattern.matches(sfMain);
	}

	public boolean matchesSecondAuxiliary(SignedFormula sfMain,
			SignedFormula sfAuxiliary) {
		return _aux2Pattern.matches(sfAuxiliary)
				&& verifyRole(sfMain.getFormula().getImmediateSubformulas()
						.get(0), sfAuxiliary.getFormula());
	}

	private boolean verifyRole(Formula aux1, Formula aux2) {
		return _role.getFormulas(aux1).get(0).equals(
				C1ConsistencyPattern.getFormulaOfConsistencyFormula(aux2));
	}

}
