/*
 * Created on 22/10/2004
 *
 */
package rules.getters;

import logic.formulas.Arity;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * Class that has a method (getSignedFormula) that receives a signed formula
 * such as T !A or F !!B and returns a signed formula where the unary connective
 * received as a parameter become
 * 
 * unary connective does not appear and the sign is the one that is received by
 * the constructor.
 * 
 * <br>
 * 
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class UnarySignConnectiveGetter implements KESignedFormulaGetter {

    private FormulaSign _sign;

    private Connective _connective;

    public UnarySignConnectiveGetter(FormulaSign sign, Connective connective) {
        _sign = sign;
        if (connective.getArity().equals(Arity.UNARY)) {
            _connective = connective;
        } else
            throw new RuntimeException(
                    "UnaryConnectiveOnlyGetter  - connective must be unary");

    };

    /**
     * @param sff
     * @param sfl
     * @return
     */
    public SignedFormula getSignedFormula(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
        return sff.createSignedFormula(_sign, ff.createCompositeFormula(
                _connective, (Formula) sfl.get(0).getFormula()));
    }

}