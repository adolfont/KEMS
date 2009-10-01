/*
 * Created on 02/08/2005
 *
 */
package rules;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public interface IRule {
    public abstract SignedFormulaList getPossibleConclusions(
            SignedFormulaFactory sff, FormulaFactory ff, SignedFormulaList sfl);
}