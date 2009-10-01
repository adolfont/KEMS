/*
 * Created on 17/11/2004
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
 * Pattern used for rules such as X_IMPLIES:
 * <p>
 * _mainConnective: connective that may appear in any subformula of the main
 * formula
 * <p>
 * _auxiliarySign1: first possible sign of the auxiliary formula
 * <p>
 * _auxiliaryRole1: first possible role of the auxiliary formula in the
 * subformula of the main formula where the _mainConnective was found
 * <p>
 * _auxiliarySign2: second possible sign of the auxiliary formula
 * <p>
 * _auxiliaryRole2: second possible role of the auxiliary formula in the
 * subformula of the main formula where the _mainConnective was found
 * <p>
 * 
 * @author adolfo
 *  
 */
public class TwoSignsConnectiveTwoRolesSubformulaPattern implements
        IBinarySignedFormulaPattern, ISubformulaPattern {

    Connective _mainConnective;

    FormulaSign _auxiliarySign1, _auxiliarySign2;

    KERuleRole _auxiliaryRole1, _auxiliaryRole2;

    Formula _match;

    /**
     * @param connective
     * @param sign1
     * @param role1
     * @param sign2
     * @param role2
     */
    public TwoSignsConnectiveTwoRolesSubformulaPattern(Connective connective,
            FormulaSign sign1, KERuleRole role1, FormulaSign sign2,
            KERuleRole role2) {
        super();
        _mainConnective = connective;
        _auxiliarySign1 = sign1;
        _auxiliaryRole1 = role1;
        _auxiliarySign2 = sign2;
        _auxiliaryRole2 = role2;
    }

    public Formula getMatchedSubformula(SignedFormulaList sfl) {
        return getMatchedSubformula(sfl.get(0), sfl.get(1));
    }

    private Formula getMatchedSubformula(SignedFormula main,
            SignedFormula auxiliary) {
        //			System.err.println(recursivelyGetMatchedSubformula(main.getFormula(),
        // auxiliary)+" aqui � recGet");
        return recursivelyGetMatchedSubformula(main.getFormula(), auxiliary);

        //        }
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

            if ((auxiliary.getSign().equals(_auxiliarySign1))) {
            	List<Formula> l1 = _auxiliaryRole1.getFormulas(main);

                for (int i = 0; i < l1.size(); i++) {
                    Formula f1 = (Formula) l1.get(i);
                    //				System.err.println(f1);
                    //				System.err.println(auxiliary);
                    if (f1.equals(auxiliary.getFormula())) {
                        //					System.err.println(main + " aqui");
                        return main;
                    }

                }

            }

            if (auxiliary.getSign().equals(_auxiliarySign2)) {

            	List<Formula> l2 = _auxiliaryRole2.getFormulas(main);
                for (int i = 0; i < l2.size(); i++) {
                    Formula f2 = (Formula) l2.get(i);
                    //				System.err.println(f1);
                    //				System.err.println(auxiliary);
                    if (f2.equals(auxiliary.getFormula())) {
                        //					System.err.println(main + " aqui");
                        return main;
                    }

                }
            }

        }

        return null;
    }

    //////////////////////////////////

    public boolean matches(SignedFormula main, SignedFormula auxiliary) {
        //		System.err.println(auxiliary.getSign().equals(_auxiliarySign));
        return (auxiliary.getSign().equals(_auxiliarySign1) | auxiliary
                .getSign().equals(_auxiliarySign2))
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

            if (auxiliary.getSign().equals(_auxiliarySign1)) {
            	List<Formula> l = _auxiliaryRole1.getFormulas(main);

                for (int i = 0; i < l.size(); i++) {
                    Formula f1 = (Formula) l.get(i);
                    //				System.err.println(f1);
                    //				System.err.println(auxiliary);
                    if (f1.equals(auxiliary.getFormula())) {
                        return true;
                    }

                }

            }
            if ((auxiliary.getSign().equals(_auxiliarySign2))) {
            	List<Formula> l = _auxiliaryRole2.getFormulas(main);

                for (int i = 0; i < l.size(); i++) {
                    Formula f1 = (Formula) l.get(i);
                    //				System.err.println(f1);
                    //				System.err.println(auxiliary);
                    if (f1.equals(auxiliary.getFormula())) {
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
        } else
            return ((CompositeFormula) f).getConnective().equals(
                    _mainConnective);
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

}