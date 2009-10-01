/*
 * Created on 22/10/2004
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

/**
 * Class that has a method (getSignedFormula) that receives a unary signed
 * formula such as T !A or F !!B and returns a signed formula where the first
 * unary connective does not appear and the sign is the one that is received by
 * the constructor.
 * 
 * <br>
 * 
 * Only two objects of this class are available for classical logic: FALSE and
 * TRUE.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class UnaryConnectiveGetter implements KESignedFormulaGetter
		{

	FormulaSign _sign;

	public static final UnaryConnectiveGetter FALSE = new UnaryConnectiveGetter(
			ClassicalSigns.FALSE);

	public static final UnaryConnectiveGetter TRUE = new UnaryConnectiveGetter(
			ClassicalSigns.TRUE);

	private UnaryConnectiveGetter(FormulaSign sign) {
		_sign = sign;
	};

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see rulesNew.KESignedFormulaGetter#getSignedFormula(null,
//	 *      signedFormulasNew.SignedFormula)
//	 */
//	public SignedFormula getSignedFormula(SignedFormulaFactory sff,
//			SignedFormula sf) {
//		return sff.createSignedFormula(_sign, (Formula) sf.getFormula()
//				.getImmediateSubformulas().get(0));
//	}

	/**
	 * @param sff
	 * @param sfl
	 * @return
	 */
	public SignedFormula getSignedFormula(SignedFormulaFactory sff, FormulaFactory ff,
			SignedFormulaList sfl) {
		return sff.createSignedFormula(_sign, (Formula) sfl.get(0).getFormula()
				.getImmediateSubformulas().get(0));
	}

}