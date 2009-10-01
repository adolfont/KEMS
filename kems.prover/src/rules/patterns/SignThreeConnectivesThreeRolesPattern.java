/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

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
 * Class for representing ternary signed formula patterns used when rules with
 * three premisses are analysed.
 * 
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignThreeConnectivesThreeRolesPattern implements
		ITernarySignedFormulaPattern {

	private FormulaSign _sign;

	private Connective _mainConnective;

	private Connective _auxiliaryConnective1;

	private Connective _auxiliaryConnective2;

	private KERuleRole _mainRole;

	private KERuleRole _auxiliaryRole1;

	private KERuleRole _auxiliaryRole2;

	private KERuleRole _auxiliaryRole3;

	/**
	 * @param sign
	 * @param connective
	 * @param connective1
	 * @param connective2
	 * @param role
	 * @param role1
	 * @param role2
	 * @param role3
	 */
	public SignThreeConnectivesThreeRolesPattern(FormulaSign sign,
			Connective connective, Connective connective1,
			Connective connective2, KERuleRole role, KERuleRole role1,
			KERuleRole role2, KERuleRole role3) {
		super();
		_sign = sign;
		_mainConnective = connective;
		_auxiliaryConnective1 = connective1;
		_auxiliaryConnective2 = connective2;
		_mainRole = role;
		_auxiliaryRole1 = role1;
		_auxiliaryRole2 = role2;
		_auxiliaryRole3 = role3;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rules.patterns.TernarySignedFormulaPattern#matches(logic.signedFormulas.SignedFormula,
	 *      logic.signedFormulas.SignedFormula,
	 *      logic.signedFormulas.SignedFormula)
	 */
	public boolean matches(SignedFormula main, SignedFormula auxiliary1,
			SignedFormula auxiliary2) {
		return matchesMain(main) && matchesFirstAuxiliary(main, auxiliary1)
				&& matchesSecondAuxiliary(main, auxiliary2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rules.patterns.TernarySignedFormulaPattern#matchesFirstAuxiliary(logic.signedFormulas.SignedFormula,
	 *      logic.signedFormulas.SignedFormula)
	 */
	public boolean matchesFirstAuxiliary(SignedFormula sfMain,
			SignedFormula sfAuxiliary) {
		if (sfAuxiliary.getSign().equals(_sign)) {
			Formula mainFormula = (Formula) _mainRole.getFormulas(
					sfMain.getFormula()).get(0);

			if (matchesAuxiliaryConnective(sfMain, sfAuxiliary)) {
				Formula auxSubFormula = ((Formula) _auxiliaryRole1.getFormulas(
						sfAuxiliary.getFormula()).get(0));

				if (mainFormula.equals(auxSubFormula)) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * @param sfMain
	 * @param sfAuxiliary
	 * @return
	 */
	private boolean matchesAuxiliaryConnective(SignedFormula sfMain,
			SignedFormula sfAuxiliary) {
		return sfAuxiliary.getFormula() instanceof CompositeFormula
				&& _auxiliaryConnective1
						.equals(((CompositeFormula) (sfAuxiliary.getFormula()))
								.getConnective());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rules.patterns.TernarySignedFormulaPattern#matchesSecondAuxiliary(logic.signedFormulas.SignedFormula,
	 *      logic.signedFormulas.SignedFormula)
	 */
	public boolean matchesSecondAuxiliary(SignedFormula sfMain,
			SignedFormula sfAuxiliary) {
		if (sfAuxiliary.getSign().equals(_sign)) {
			Formula mainFormula = (Formula) _mainRole.getFormulas(
					sfMain.getFormula()).get(0);

			// This implementation is specific for the following pattern:
			// T @ A (sfMAin)
			// T B -> A (sfAux1)
			// T B -> !A (sfAux2)
			// where
			// main conn = @
			// aux conn 1 = ->
			// aux conn 2 = !
			// main role = LEFT (of sfMain)
			// aux role 1 = RIGHT (of sfAux1)
			// aux role 2 = RIGHT (of sfAux2)
			// aux role 3 = LEFT (of role2 of sfAux2)

			if (matchesAuxiliaryConnective(sfMain, sfAuxiliary)) {
				Formula auxiliaryFormula = (Formula) _auxiliaryRole2
						.getFormulas(sfAuxiliary.getFormula()).get(0);

				if (auxiliaryFormula instanceof CompositeFormula
						&& _auxiliaryConnective2
								.equals(((CompositeFormula) auxiliaryFormula)
										.getConnective())) {

					Formula auxSubFormula = ((Formula) _auxiliaryRole3
							.getFormulas(auxiliaryFormula).get(0));

					if (mainFormula.equals(auxSubFormula)) {
						return true;
					}

				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rules.patterns.TernarySignedFormulaPattern#getAuxiliaryCandidates(logic.signedFormulas.SignedFormulaFactory,
	 *      logic.formulas.FormulaFactory, logic.signedFormulas.SignedFormula,
	 *      logic.signedFormulas.SignedFormula)
	 */
	public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormula sfMain, SignedFormula sfAuxiliary) {
		SignedFormulaList sfl = new SignedFormulaList();

		// Implementation very specific for the pattern cited above
		if (matchesFirstAuxiliary(sfMain, sfAuxiliary)) {
			sfl.add(sff.createSignedFormula(sfMain.getSign(), ff
					.createCompositeFormula(_auxiliaryConnective1,
							(Formula) sfAuxiliary.getFormula()
									.getImmediateSubformulas().get(0), ff
									.createCompositeFormula(
											_auxiliaryConnective2,
											(Formula) sfAuxiliary.getFormula()
													.getImmediateSubformulas()
													.get(1)))));
		} else {
			if (matchesSecondAuxiliary(sfMain, sfAuxiliary)) {
				sfl.add(sff.createSignedFormula(sfMain.getSign(), ff
						.createCompositeFormula(_auxiliaryConnective1,
								(Formula) sfAuxiliary.getFormula()
										.getImmediateSubformulas().get(0),
								(Formula) ((Formula) sfAuxiliary.getFormula()
										.getImmediateSubformulas().get(1))
										.getImmediateSubformulas().get(0))));

			}
		}

		return sfl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see rules.patterns.TernarySignedFormulaPattern#matchesMain(logic.signedFormulas.SignedFormula)
	 */
	public boolean matchesMain(SignedFormula sfMain) {
		return (_sign.equals(sfMain.getSign()))
				&& (sfMain.getFormula() instanceof CompositeFormula)
				&& (_mainConnective.equals(((CompositeFormula) sfMain
						.getFormula()).getConnective()));
	}

}