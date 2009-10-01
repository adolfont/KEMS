/*
 * Created on 12/04/2005
 *
 */
package problemGenerator;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Class that has static methods to convert lists of signed formulas to unsiged
 * formulas.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * 
 */
public class SignedToUnsignedConverter {

    /**
     * Converts a signed formula list to a formula. T A, TA->B, FB is converted
     * to !A | !(A->B) | B. The signed formula list is closed by a classical
     * propositional tableau if and only if the converted formula is a
     * tautology.
     * 
     * Why? Because (!A|!B|!C|D)=(A&B&C)->D: <BR>
     * F !A|!B|!C|C <BR>
     * F !A<BR>
     * T A <BR>
     * F !B<BR>
     * T B <BR>
     * F !C<BR>
     * T C <BR>
     * F D <BR>
     * 
     * @param ff
     * @param sfl
     * @return conversion of a signed formula list to a formula.
     */
    public static Formula convert(FormulaFactory ff, SignedFormulaList sfl) {
        Formula result;
        // if it has no formula, it returns the empty disjunction: BOTTOM
        if (sfl.size() == 0) {
            return ff.createCompositeFormula(ClassicalConnectives.BOTTOM);
        } else
            result = chooseBySign(ff, sfl.get(sfl.size() - 1));

        for (int i = sfl.size() - 2; i >= 0; i--) {
            SignedFormula sf = sfl.get(i);
            // associates to the left
            result = ff.createCompositeFormula(ClassicalConnectives.OR,
                    chooseBySign(ff, sf), result);
        }

        return result;
    }

    /**
     * Creates a formula based on the sign of a signed formula.
     * 
     * @param ff
     * @param sf
     * @return a formula: !X if T X, X if F X
     */
    private static Formula chooseBySign(FormulaFactory ff, SignedFormula sf) {
//        Formula result;
        if (sf.getSign().equals(ClassicalSigns.TRUE)) {
            return ff.createCompositeFormula(ClassicalConnectives.NOT, sf
                    .getFormula());
        } else {
            if (sf.getSign().equals(ClassicalSigns.FALSE)) {
                return sf.getFormula();
            }
        }
        return null;
    }
}
