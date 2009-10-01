package rules.patterns;

import java.util.List;

import rules.KERuleRole;
import logic.formulas.CompositeFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.C1Connectives;
import logicalSystems.c1.C1Signs;

public class C1_Sign_T_NOT_1_Pattern implements IBinarySignedFormulaPattern {

	private FormulaSign _sign;
	private C1SignConsistencyPattern _consPattern;
	private KERuleRole _auxiliaryRole;

	public C1_Sign_T_NOT_1_Pattern(FormulaSign sign) {
		_sign = sign;
		_consPattern = new C1SignConsistencyPattern(C1Signs.TRUE);
		_auxiliaryRole = KERuleRole.LEFT;
	}

	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormula sfMain) {
		SignedFormulaList sfl = new SignedFormulaList();

		if (matchesMain(sfMain)) {

			// main is T!(X&!X): l will be [ X&!X ]
			List<Formula> l = _auxiliaryRole.getFormulas(sfMain.getFormula());
			Formula x_and_not_x = l.get(0);
			Formula x = ((CompositeFormula) x_and_not_x)
					.getImmediateSubformulas().get(0);
			sfl.add(sff.createSignedFormula(_sign, ff.createCompositeFormula(
					C1Connectives.NOT, x)));
			// OLD VERSION: creates T !(X&!X)
			// List<Formula> l =
			// _auxiliaryRole.getFormulas(sfMain.getFormula());
			// for (int i = 0; i < l.size(); i++) {
			// sfl.add(sff.createSignedFormula(_sign, ff
			// .createCompositeFormula(C1Connectives.NOT, ff
			// .createCompositeFormula(C1Connectives.AND,
			// (Formula) l.get(i), ff
			// .createCompositeFormula(
			// C1Connectives.NOT,
			// (Formula) l.get(i))))));
		}

		return sfl;
	}
	

	public static Formula getFormulaOfConsistency(Formula f) {
		// f is !(X&!X)
		Formula x_and_not_x = (KERuleRole.LEFT.getFormulas(f)).get(0);
		return ((CompositeFormula) x_and_not_x).getImmediateSubformulas()
				.get(0);
	}

	public boolean matches(SignedFormula main, SignedFormula auxiliary) {
		return // main is T!A for some A
		matchesMain(main) && matchesAuxiliary(main, auxiliary);
	}

	public boolean matchesMain(SignedFormula main) {
		return _consPattern.matches(main);
	}

	private boolean matchesAuxiliary(SignedFormula main, SignedFormula auxiliary) {
		return
		// auxiliary is T ! (A&!A)
		auxiliary.getSign().equals(_sign)
				&& auxiliary.getFormula() instanceof CompositeFormula
				&& ((CompositeFormula) auxiliary.getFormula()).getConnective()
						.equals(C1Connectives.NOT)
				&& roleMatches(auxiliary.getFormula(), (Formula) _auxiliaryRole
						.getFormulas(
								_auxiliaryRole.getFormulas(main.getFormula())
										.get(0)).get(0));
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
}
