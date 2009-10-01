package rules.patterns;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logicalSystems.c1.C1Connectives;

public class C1ConsistencyAnyBinaryConnectivePattern implements IFormulaPattern {

	C1ConsistencyPattern _pattern;
	Connective _connective;

	public C1ConsistencyAnyBinaryConnectivePattern(Connective conn) {
		_pattern = new C1ConsistencyPattern(C1Connectives.NOT,
				C1Connectives.AND);
		_connective=conn;
	}

	public boolean matches(Formula f) {
		return _pattern.matches(f) && matchesAnyConnective(f);
	}

	private boolean matchesAnyConnective(Formula f) {
		Formula left = ((CompositeFormula) ((CompositeFormula) f)
				.getImmediateSubformulas().get(0)).getImmediateSubformulas()
				.get(0);
		return (left instanceof CompositeFormula)
				&& ((CompositeFormula) left).getConnective()
						.equals(_connective);
	}

}
