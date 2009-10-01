package rules.patterns;

import logic.formulas.Connective;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;

public class C1SignConsistencyAnyBinaryConnectivePattern implements
		IUnarySignedFormulaPattern {

	Connective _connective;
	FormulaSign _sign;
	C1ConsistencyAnyBinaryConnectivePattern _pattern;

	public C1SignConsistencyAnyBinaryConnectivePattern(FormulaSign sign,
			Connective conn) {
		_connective = conn;
		_sign = sign;
		_pattern = new C1ConsistencyAnyBinaryConnectivePattern(_connective);
	}

	public boolean matches(SignedFormula sf) {
		return sf.getSign().equals(_sign) && _pattern.matches(sf.getFormula());
	}

}
