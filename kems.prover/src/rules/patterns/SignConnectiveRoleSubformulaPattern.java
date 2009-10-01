/*
 * Created on 11/11/2004
 *
 */
package rules.patterns;

import java.util.List;

import logic.formulas.CompositeFormula;
import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;
import logic.signedFormulas.FormulaSign;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import rules.KERuleRole;

/**
 * Pattern used for rules such as X_OR:
 * <p>
 * _mainConnective: connective that may appear in any subformula of the main
 * formula
 * <p>
 * _auxiliarySign: sign of the auxiliary formula
 * <p>
 * _auxiliaryRole: role of the auxiliary formula in the subformula of the main
 * formula where the _mainConnective was found
 * <p>
 * 
 * @author adolfo
 *  
 */
public class SignConnectiveRoleSubformulaPattern implements
        IBinarySignedFormulaPattern, ISubformulaPattern {

    Connective _mainConnective;

    FormulaSign _auxiliarySign;

    KERuleRole _auxiliaryRole;

    Formula _match;

    /**
     *  
     */
    public SignConnectiveRoleSubformulaPattern(Connective conn,
            FormulaSign sign, KERuleRole role) {
        _mainConnective = conn;
        _auxiliarySign = sign;
        _auxiliaryRole = role;
    }

    public Formula getMatchedSubformula(SignedFormulaList sfl) {
        return getMatchedSubformula(sfl.get(0), sfl.get(1));
    }

    private Formula getMatchedSubformula(SignedFormula main,
            SignedFormula auxiliary) {
        if (!(auxiliary.getSign().equals(_auxiliarySign))) {
            return null;
        } else {
            //			System.err.println(recursivelyGetMatchedSubformula(main.getFormula(),
            // auxiliary)+" aqui � recGet");
            return recursivelyGetMatchedSubformula(main.getFormula(), auxiliary);

        }
    }

    private Formula recursivelyGetMatchedSubformula(Formula main,
            SignedFormula auxiliary) {

        Formula tryMatch = getMatchedSubformula(main, auxiliary);
        if (tryMatch != null) {

            //			System.err.println(tryMatch + " aqui � tryMatch");
            return tryMatch;
        } else {
            for (int i = 0; i < main.getImmediateSubformulas().size(); i++) {
                tryMatch = recursivelyGetMatchedSubformula((Formula) main
                        .getImmediateSubformulas().get(i), auxiliary);
                if (tryMatch != null) {
                    //					System.err.println(main.getImmediateSubformulas().get(i)
                    // + " aqui � ...");
                    return tryMatch;
                    //					(Formula)main.getImmediateSubformulas().get(i);
                }
            }
        }
        return null;
    }

    private Formula getMatchedSubformula(Formula main, SignedFormula auxiliary) {

        boolean mainMatch = matchesConnective(main);
        //		System.err.println(mainMatch);

        if (mainMatch) {
        	List<Formula> l = _auxiliaryRole.getFormulas(main);

            for (int i = 0; i < l.size(); i++) {
                Formula f1 = (Formula) l.get(i);
                //				System.err.println(f1);
                //				System.err.println(auxiliary);
                if (f1.equals(auxiliary.getFormula())) {
                    //					System.err.println(main + " aqui");
                    return main;
                }

            }
        }

        return null;
    }

    //////////////////////////////////

    public boolean matches(SignedFormula main, SignedFormula auxiliary) {
        //		System.err.println(auxiliary.getSign().equals(_auxiliarySign));
        return auxiliary.getSign().equals(_auxiliarySign)
                && recursivelyMatches(main.getFormula(), auxiliary);
    }

    private boolean recursivelyMatches(Formula main, SignedFormula auxiliary) {
        if (matches(main, auxiliary)) {
            return true;
        } else {
            for (int i = 0; i < main.getImmediateSubformulas().size(); i++) {
                if (recursivelyMatches((Formula) main.getImmediateSubformulas()
                        .get(i), auxiliary)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matches(Formula main, SignedFormula auxiliary) {

        boolean mainMatch = matchesConnective(main);
        //		System.err.println(mainMatch);

        if (mainMatch) {
        	List<Formula> l = _auxiliaryRole.getFormulas(main);

            for (int i = 0; i < l.size(); i++) {
                Formula f1 = (Formula) l.get(i);
                //				System.err.println(f1);
                //				System.err.println(auxiliary);
                if (f1.equals(auxiliary.getFormula())) {
                    return true;
                }

            }
        }

        return false;
    }

    private boolean matchesConnective(Formula f) {
        if (!(f instanceof CompositeFormula)) {
            return false;
        } else
            return ((CompositeFormula) f).getConnective().equals(
                    _mainConnective);
    }

    /*
     * (non-Javadoc)
     * 
     * @see patterns.BinarySignedFormulaPattern#getAuxiliaryCandidates(signedFormulasNew.SignedFormulaFactory,
     *      formulasNew.FormulaFactory, signedFormulasNew.SignedFormula)
     */
    public SignedFormulaList getAuxiliaryCandidates(SignedFormulaFactory sff,
            FormulaFactory ff, SignedFormula sfMain) {
        return null;
    }

    public boolean matchesMain(SignedFormula sfMain) {
        return getMainMatches(sfMain).size() != 0;
    }

    public FormulaList getMainMatches(SignedFormula sf) {
        return recursivelyGetMainMatch(sf.getFormula());
    }

    private FormulaList recursivelyGetMainMatch(Formula f) {
        FormulaList fl_result = new FormulaList();

        if ((f instanceof CompositeFormula)
                && ((CompositeFormula) f).getConnective().equals(
                        _mainConnective)) {
            fl_result.add(f);
        }

        for (int i = 0; i < f.getImmediateSubformulas().size(); i++) {
            FormulaList fl_result_sub = recursivelyGetMainMatch((Formula) f
                    .getImmediateSubformulas().get(i));
            fl_result.addAll(fl_result_sub);
        }
        return fl_result;
    }

}