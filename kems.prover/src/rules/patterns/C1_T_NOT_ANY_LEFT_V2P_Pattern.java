package rules.patterns;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.c1.C1Connectives;
import logicalSystems.c1.C1Signs;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.getters.C1_F_CONS_ANY_Getter;

/**
 * Pattern for a set of C1 rules Main: T !(A%B) Auxiliary: T(A%B)&!(A&!A) Where
 * % can be any connective in {&, |, ->}
 * 
 * @author Adolfo Neto
 * 
 */
public class C1_T_NOT_ANY_LEFT_V2P_Pattern implements IBinarySignedFormulaPattern {

	private C1ConsistencyPattern _consPattern;
	private SignConnectiveConnectivePattern _mainPattern;

	public C1_T_NOT_ANY_LEFT_V2P_Pattern(Connective conn) {
		_consPattern = new C1ConsistencyPattern(C1Connectives.NOT, C1Connectives.AND);
		_mainPattern = new SignConnectiveConnectivePattern(ClassicalSigns.TRUE, C1Connectives.NOT, conn);
	}

	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff, FormulaFactory ff,
			SignedFormula sfMain) {
		SignedFormulaList sfl = new SignedFormulaList();

		if (matchesMain(sfMain)) {

			Formula ms = getMainSubformula(sfMain);

			sfl.add(sff.createSignedFormula(C1Signs.TRUE, ff.createCompositeFormula(C1Connectives.AND,
			// A%B
					ms,
					// !(A&!A)
					C1_F_CONS_ANY_Getter.createC1ConsistencyFormula(ff, getMainSubformulaLeft(ms)))));
		}

		return sfl;
	}

	public boolean matches(SignedFormula main, SignedFormula auxiliary) {
		return matchesMain(main) && matchesAuxiliary(main, auxiliary);
	}

	public boolean matchesMain(SignedFormula main) {
		return _mainPattern.matches(main);
	}

	private boolean matchesAuxiliary(SignedFormula main, SignedFormula auxiliary) {
		Formula auxFormula = auxiliary.getFormula();
		Formula mainSubformula = getMainSubformula(main);

		if (auxiliary.getSign().equals(main.getSign())) {
			if (auxFormula instanceof CompositeFormula) {
				Connective auxConn = ((CompositeFormula) auxFormula).getConnective();
				if (auxConn.equals(C1Connectives.AND)) {
					Formula left = auxFormula.getImmediateSubformulas().get(0);
					Formula right = auxFormula.getImmediateSubformulas().get(1);

					if (!left.equals(mainSubformula))
						return false;

					if (!_consPattern.matches(right))
						return false;

					Formula consForm = C1ConsistencyPattern.getFormulaOfConsistencyFormula(right);
					Formula mainSubfLeft = ((CompositeFormula) mainSubformula).getImmediateSubformulas().get(
							0);

					if (consForm.equals(mainSubfLeft))
						return true;
					else
						return false;
				}
			} else
				return false;
		} else
			return false;

		return false;
	}

	/**
	 * @param main
	 * @return
	 */
	private Formula getMainSubformula(SignedFormula main) {
		Formula mainFormula = main.getFormula();
		Formula mainSubformula = ((CompositeFormula) mainFormula).getImmediateSubformulas().get(0);
		return mainSubformula;
	}

	private Formula getMainSubformulaLeft(Formula mainSubformula) {
		return mainSubformula.getImmediateSubformulas().get(0);
	}

}
