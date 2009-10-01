/*
 * Created on 22/10/2004
 *
 */
package rules;

import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;

/**
 * Class that represents rules. <br>
 * A rule has zero or more premisses and one or more conclusions. <br>
 * Each conclusion is an action which may also contain a formula or a signed
 * formula.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 */
public abstract class Rule implements IRule {

	private String _name;

	public Rule(String name) {
		_name = name;
	};

	public String toString() {
		return _name;
	}

	abstract public SignedFormulaList getPossibleConclusions(
			SignedFormulaFactory sff, FormulaFactory ff, SignedFormulaList sfl);

}