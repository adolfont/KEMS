/*
 * Created on 15/10/2004
 *
 */
package rules.patterns;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.signedFormulas.SignedFormula;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ConnectiveSubformulaPattern implements IUnarySignedFormulaPattern {

	Connective _connective;

	/**
	 * 
	 */
	public ConnectiveSubformulaPattern(Connective conn) {
		_connective = conn;
	}

	/* (non-Javadoc)
	 * @see patterns.UnarySignedFormulaPattern#matches(signedFormulas.SignedFormula)
	 */
	public boolean matches(SignedFormula sf) {

		return matches(sf.getFormula());
	}

	private boolean matches(Formula f) {
		// if it is atomic, it cannot match
		if (!(f instanceof CompositeFormula)) {
			return false;
		}

		// if it is composite and its connective is the one we are looking for
		// then it matches
		if (((CompositeFormula) f).getConnective().equals(_connective)) {
			return true;
		}

		// if it is composite and one of its subformulas matches, then it matches
		CompositeFormula cf = (CompositeFormula) f;
		for (int i = 0; i < cf.getImmediateSubformulas().size(); i++) {
			if (matches((Formula) cf.getImmediateSubformulas().get(i))) {
				return true;
			}
		}

			return false;
		}

	}
