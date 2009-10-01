/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import logic.formulas.AtomicFormula;
import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SignConnectiveConnectivePattern implements IUnarySignedFormulaPattern {

    FormulaSign _sign;

    Connective _conn1;

    Connective _conn2;

    public SignConnectiveConnectivePattern(FormulaSign s, Connective c1, Connective c2) {
        this._sign = s;
        this._conn1 = c1;
        this._conn2 = c2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see patterns.SignedFormulaPattern#matches(signedFormulas.SignedFormula)
     */
    public boolean matches(SignedFormula sf) {
        return sf.getSign().equals(_sign) && matchesConnectives(sf.getFormula());
    }

    private boolean matchesConnectives(Formula f) {
        if (f instanceof AtomicFormula) {
            return false;
        } else
            return matchesFirstConnective(f) && matchesSecondConnective(f);

    }

    private boolean matchesFirstConnective(Formula f) {
        return ((CompositeFormula) f).getConnective().equals(_conn1);
    }

    private boolean matchesSecondConnective(Formula f) {
        Formula secondFormula = (Formula) ((CompositeFormula) f).getImmediateSubformulas().get(0);

        if (secondFormula instanceof AtomicFormula) {
            return false;
        } else {
            return ((CompositeFormula) secondFormula).getConnective().equals(_conn2);
        }
    }
}
