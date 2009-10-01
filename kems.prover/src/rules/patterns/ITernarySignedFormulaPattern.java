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
 * Interface for patterns for three signed formulas.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public interface ITernarySignedFormulaPattern {

    /**
     * Verifies if three signed formulas satisfy a pattern.
     * 
     * @param main
     * @param auxiliary1
     * @param auxiliary2
     * @return
     */
    public boolean matches(SignedFormula main, SignedFormula auxiliary1, SignedFormula auxiliary2);

    /**
     *  Gets auxiliary candidates for a main formula and an auxiliary.
     * @param sff
     * @param ff
     * @param sfMain
     * @return
     */
    public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormula sfMain,  SignedFormula sfAuxiliary);

    /** Verifies if a formula can be main formula of this pattern. 
     * @param sfMain
     * @return
     */
    public boolean matchesMain(SignedFormula sfMain);

    /** Verifies if a formula can be auxiliary formula of this pattern for a given main formula.
     * @param sfMain
     * @param sfAuxiliary
     * @return
     */
    public boolean matchesFirstAuxiliary(SignedFormula sfMain, SignedFormula sfAuxiliary);

    /** Verifies if a formula can be auxiliary formula of this pattern for a given main formula.
     * @param sfMain
     * @param sfAuxiliary
     * @return
     */
    public boolean matchesSecondAuxiliary(SignedFormula sfMain, SignedFormula sfAuxiliary);


}