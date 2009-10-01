/*
 * Created on 10/11/2004
 *
 */
package rules.getters;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalSigns;
import rules.KERuleRole;

/**
 * Gets a formula that appears only in the auxiliary formula.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class BinaryTwoPremisesAuxiliaryGetter implements KESignedFormulaGetter {
	private FormulaSign _sign;

	private KERuleRole _role;

	public static final BinaryTwoPremisesAuxiliaryGetter FALSE_LEFT = new BinaryTwoPremisesAuxiliaryGetter(
			ClassicalSigns.FALSE, KERuleRole.LEFT);

	private BinaryTwoPremisesAuxiliaryGetter(FormulaSign sign, KERuleRole role) {
		_sign = sign;
		_role = role;
	};

	public SignedFormula getSignedFormula(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {
		SignedFormula sfAux = sfl.get(1);
		Formula result = (Formula) (_role.getFormulas(sfAux.getFormula())
				.get(0));

		return sff.createSignedFormula(_sign, result);

	}

}