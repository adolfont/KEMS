package rules.patterns;

import logic.formulas.Formula;

public interface IFormulaPattern {
	
    /** Verifies if a given formula matches the pattern.
     * 
     * @param f
     * @return true if it matches, false otherwise
     */
    public boolean matches (Formula f);

}
