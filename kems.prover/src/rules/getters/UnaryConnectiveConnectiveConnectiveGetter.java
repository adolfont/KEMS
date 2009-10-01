/*
 * Created on 22/10/2004
 *
 */
package rules.getters;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Class that has a method (getSignedFormula) that receives a unary signed
 * formula such as T !oA or F !!B and returns a signed formula where the two
 * first unary connectives do not appear, the sign is the one that is received
 * by the constructor, and the third unary connective is used to build the
 * formula in getSignedFormula.
 * 
 * <br>
 * 
 * Only one object of this class is available: TRUE_NOT
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class UnaryConnectiveConnectiveConnectiveGetter implements KESignedFormulaGetter {

    FormulaSign _sign;

    Connective _conn;

    public static final UnaryConnectiveConnectiveConnectiveGetter TRUE_NOT = new UnaryConnectiveConnectiveConnectiveGetter(
            ClassicalSigns.TRUE, ClassicalConnectives.NOT);

    private UnaryConnectiveConnectiveConnectiveGetter(FormulaSign sign, Connective conn) {
        _sign = sign;
        _conn = conn;
    };

    //	/*
    //	 * (non-Javadoc)
    //	 *
    //	 * @see rulesNew.KESignedFormulaGetter#getSignedFormula(null,
    //	 * signedFormulasNew.SignedFormula)
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

        Formula firstFormula = (Formula) sfl.get(0).getFormula().getImmediateSubformulas().get(0);

        return sff.createSignedFormula(_sign, ff.createCompositeFormula(_conn,
                (Formula) firstFormula.getImmediateSubformulas().get(0)));
    }

}