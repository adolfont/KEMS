/*
 * Created on 23/03/2005
 *
 */
package problemGenerator;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Problem generator for the U' family (that appears in proofs of the U family of Pelletier 75 problems)
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class UPrimeProblemGenerator extends ProblemGenerator {

    /**
     *  Creates a problem generator for the U' family
     */
    public UPrimeProblemGenerator(){
        super("UPrime");
    }
    
    /* (non-Javadoc)
     * @see problemGenerator.ProblemGenerator#generate(problem.Problem, int)
     */
    protected Problem generate(Problem p, int index) {
        return generate(p, 1, index);
    }

    private Problem generate(Problem p, int begin, int end) {
        FormulaFactory ff = new FormulaFactory();
        SignedFormulaFactory sff = new SignedFormulaFactory();

        p.setFormulaFactory(ff);
        p.setSignedFormulaFactory(sff);

        p.setSignedFormulaList(new SignedFormulaList(sff.createSignedFormula(
                ClassicalSigns.TRUE, generateFormula(ff, begin, end))));

        return p;
    }

    private Formula generateFormula(FormulaFactory ff, int begin, int end) {
        if (begin <= end) {
            return ff.createCompositeFormula(ClassicalConnectives.BIIMPLIES, ff
                    .createAtomicFormula("P" + begin), generateFormula(ff,
                    begin + 1, end));
        } else {
            return ff.createCompositeFormula(ClassicalConnectives.NOT, generateTailFormula(ff, end));
        }
    }

    // equals to UProblemGenerator.generateTailFormula(..)
    private Formula generateTailFormula(FormulaFactory ff, int end) {
        if (end == 1) {
            return ff.createAtomicFormula("P" + end);
        } else {
            Formula aux = ff.createCompositeFormula(
                    ClassicalConnectives.BIIMPLIES, ff.createAtomicFormula("P"
                            + (end - 1)), ff.createAtomicFormula("P" + end));
            for (int i = end - 2; i >= 1; i--) {
                aux = ff.createCompositeFormula(ClassicalConnectives.BIIMPLIES,
                        ff.createAtomicFormula("P" + i), aux);
            }
            return aux;
        }
    }

}
