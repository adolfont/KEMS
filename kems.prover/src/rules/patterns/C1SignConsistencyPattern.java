package rules.patterns;

import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logicalSystems.c1.C1Connectives;

public class C1SignConsistencyPattern implements IUnarySignedFormulaPattern {

	FormulaSign _sign;
	C1ConsistencyPattern formulaPattern;

	public C1SignConsistencyPattern(FormulaSign sign) {
		_sign = sign;
		formulaPattern = new C1ConsistencyPattern(C1Connectives.NOT,
				C1Connectives.AND);
	}

	public boolean matches(SignedFormula sf) {
		return sf.getSign().equals(_sign)
				&& formulaPattern.matches(sf.getFormula());
	}

}
