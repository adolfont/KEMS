/*
 * Created on 10/12/2004
 *
 */
package rules;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * Named rules do no contain implementation. They are implemented 
 * elsewhere. Here we have only a name for recordding their application.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class NamedRule extends Rule {

    /**
     * Creates a name rule
     */
    public NamedRule(String name) {
        super(name);
    }

    /* (non-Javadoc)
     * @see rulesNew.Rule#getPossibleConclusions(signedFormulasNew.SignedFormulaFactory, formulasNew.FormulaFactory, signedFormulasNew.SignedFormulaList)
     */
    public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormulaList sfl) {
        return null;
    }

}
