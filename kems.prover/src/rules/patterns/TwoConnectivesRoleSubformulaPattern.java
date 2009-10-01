/*
 * Created on 19/10/2004
 *
 */
package rules.patterns;

import java.util.List;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaList;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;

/**
 * @author adolfo
 *
 */
public class TwoConnectivesRoleSubformulaPattern
	implements IUnarySignedFormulaPattern, ISubformulaPattern {

	Connective _auxiliaryConnective, _mainConnective;
	KERuleRole _auxiliaryRole;

	public TwoConnectivesRoleSubformulaPattern(
		Connective auxiliaryConnective,
		KERuleRole auxiliaryRole,
		Connective mainConnective) {
		_mainConnective = mainConnective;
		_auxiliaryConnective = auxiliaryConnective;
		_auxiliaryRole = auxiliaryRole;
	}

	/* (non-Javadoc)
	 * @see patterns.UnarySignedFormulaPattern#matches(signedFormulasNew.SignedFormula)
	 */
	public boolean matches(SignedFormula sf) {
	    
//	    System.err.println("matches "+sf + " "+ recursivelyMatches(sf.getFormula()));
	    

		return recursivelyMatches(sf.getFormula());
	}

	/** Verifies if the whole formula or each subformula (recursively) matches the pattern
	 * @param f
	 * @return
	 */
	private boolean recursivelyMatches(Formula f) {
		if (matches(f)) {
			return true;
		} else {
			for (int i = 0; i < f.getImmediateSubformulas().size(); i++) {
			    
//			    System.err.println("rec matches " +f
//					.getImmediateSubformulas()
//					.get(i)+ " "+ recursivelyMatches((Formula) f
//					.getImmediateSubformulas()
//					.get(i)));
//			    System.err.println(f
//					.getImmediateSubformulas()
//					.get(i));
				if (recursivelyMatches((Formula) f
					.getImmediateSubformulas()
					.get(i))) {
					return true;
				}
			}
		}
		return false;
	}

	/** Verifies if a given formula matches the pattern
	 * @param f
	 * @return
	 */
	private boolean matches(Formula f) {

		boolean mainMatch = matchesConnective(f);
//		System.err.println("main match "+ f + " d� " + mainMatch);

		if (mainMatch) {
			List<Formula> l = _auxiliaryRole.getFormulas(f);
//			System.err.println("aux role d� "+l);
			
			for (int i = 0; i < l.size(); i++) {
				Formula f1 = (Formula) l.get(i);
//				System.out.println("F1 � "+ f1 + " "+ (f1 instanceof CompositeFormula));
//				System.out.println("F1 � "+ f1.getClass().getName());
				if (f1 instanceof CompositeFormula) {
					CompositeFormula cf = (CompositeFormula) f1;
//					System.err.println (cf.getConnective().equals(_auxiliaryConnective) + " " +
//					        cf.getConnective() + " "+_auxiliaryConnective);
					if (cf.getConnective().equals(_auxiliaryConnective)) {
						return true;
					}
				}

			}
		}

		return false;
	}

	private boolean matchesConnective(Formula f) {
		if (!(f instanceof CompositeFormula)) {
			return false;
		} else{
//		    System.out.println("Comparando conn de "+f+ " com " + _mainConnective + " d� "+ ((CompositeFormula) f).getConnective().equals(
//					_mainConnective));
			return ((CompositeFormula) f).getConnective().equals(
					_mainConnective);
		    
		}
	}
	
	/* (non-Javadoc)
     * @see patterns.SubformulaPattern#getMatchedSubformula(signedFormulasNew.SignedFormulaList)
     */
    public Formula getMatchedSubformula(SignedFormulaList sfl) {
		return recursivelyGetMatchedSubformula(sfl.get(0).getFormula());
    }
    
    /**
     * @param formula
     * @return
     */
    private Formula recursivelyGetMatchedSubformula(Formula f) {
		if (matches(f)) {
			return f;
		} else {
			for (int i = 0; i < f.getImmediateSubformulas().size(); i++) {
			    Formula result = recursivelyGetMatchedSubformula((Formula) f
						.getImmediateSubformulas()
						.get(i)); 
				if (result != null) {
					return result;
				}
			}
		}
		return null;
    }


    /* (non-Javadoc)
     * @see patterns.SubformulaPattern#getMainMatches(signedFormulasNew.SignedFormula)
     */
    public FormulaList getMainMatches(SignedFormula sf) {
        throw new RuntimeException("not implemented");
    }


}
