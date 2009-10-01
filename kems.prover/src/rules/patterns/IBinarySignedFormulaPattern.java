/*
 * Created on 23/09/2004
 *
 */
package rules.patterns;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public interface IBinarySignedFormulaPattern {

    /**
     * Verifies if two signed formulas satisfy a pattern.
     * 
     * @param main
     * @param auxiliary
     * @return
     */
    public boolean matches(SignedFormula main, SignedFormula auxiliary);

    /**
     * @param sff
     * @param ff
     * @param sfMain
     * @return
     */
    public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormula sfMain);

    /**
     * @param sfMain
     * @return
     */
    public boolean matchesMain(SignedFormula sfMain);


}