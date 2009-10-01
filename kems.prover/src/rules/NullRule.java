/*
 * Created on 09/12/2004
 *
 */
package rules;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author adolfo
 *
 */
public class NullRule extends Rule {

    /**
     * 
     */
    private NullRule() {
        super("NULL RULE");
    }
    
    public static final NullRule INSTANCE = new NullRule();


    /* (non-Javadoc)
     * @see rulesNew.Rule#getPossibleConclusions(signedFormulasNew.SignedFormulaFactory, formulasNew.FormulaFactory, signedFormulasNew.SignedFormulaList)
     */
    public SignedFormulaList getPossibleConclusions(SignedFormulaFactory sff, FormulaFactory ff, SignedFormulaList sfl) {
        return new SignedFormulaList();
    }

}
