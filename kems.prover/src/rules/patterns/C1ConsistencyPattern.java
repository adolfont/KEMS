package rules.patterns;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;

public class C1ConsistencyPattern implements IFormulaPattern {

	private Connective connective1, connective2;

	public C1ConsistencyPattern(Connective conn1, Connective conn2) {
		connective1 = conn1;
		connective2 = conn2;
	}

	public boolean matches(Formula f) {
		return matchesFirstConnective(f) && matchesSecondConnective(f)
				&& matchesSpecialCondition(f);
	}

	private boolean matchesFirstConnective(Formula f) {
		return (f instanceof CompositeFormula)
				&& ((CompositeFormula) f).getConnective().equals(connective1);
	}

	private boolean matchesSecondConnective(Formula f) {
		Formula fLeft = ((CompositeFormula) f).getImmediateSubformulas().get(0);
		return (fLeft instanceof CompositeFormula)
				&& (((CompositeFormula) fLeft).getConnective()
						.equals(connective2));
	}

	private boolean matchesSpecialCondition(Formula f) {
		CompositeFormula fLeft = (CompositeFormula) ((CompositeFormula) f)
				.getImmediateSubformulas().get(0);
//		System.out.println(((CompositeFormula) fLeft.getImmediateSubformulas()
//				.get(1)).getConnective());
//		System.out.println(fLeft.getImmediateSubformulas().get(0));
		return (fLeft.getImmediateSubformulas().get(1) instanceof CompositeFormula)
				&& ((CompositeFormula) fLeft.getImmediateSubformulas().get(1))
						.getConnective().equals(connective1)
				&& (fLeft.getImmediateSubformulas().get(0))
						.equals(((CompositeFormula) fLeft
								.getImmediateSubformulas().get(1))
								.getImmediateSubformulas().get(0));
	}
	
	public static Formula getFormulaOfConsistencyFormula(Formula f){
		return f.getImmediateSubformulas().get(0).getImmediateSubformulas().get(0);
	}

}
