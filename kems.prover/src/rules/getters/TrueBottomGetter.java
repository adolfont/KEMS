/*
 * Created on 22/10/2004
 *
 */
package rules.getters;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Class that has a method (getSignedFormula) that receives any signed
 * formula and returns the signed formula T \bottom
 * 
 * <br>
 * 
 * Only one instance of this class is available.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class TrueBottomGetter implements KESignedFormulaGetter {


	public static TrueBottomGetter INSTANCE = new TrueBottomGetter();
	
	private TrueBottomGetter() {
	};

	/**
	 * @param sff
	 * @param sfl
	 * @return
	 */
	public SignedFormula getSignedFormula(SignedFormulaFactory sff,
			FormulaFactory ff, SignedFormulaList sfl) {
		return sff
				.createSignedFormula(ClassicalSigns.TRUE, 
						ff.createCompositeFormula(ClassicalConnectives.BOTTOM));
	}

}