/*
 * Created on 09/11/2005
 *
 */
package problemGenerator;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Generator for the PHP family of problems. Generates a PHP instance with many
 * signed formulas, which is different from Wagner Dias's generator that
 * generates only one big signed formula.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class PHPProblemGenerator extends ProblemGenerator {

    private int balls;

    private int bins;

    protected PHPProblemGenerator() {
        super("PHP");
    }

    /*
     * (non-Javadoc)
     * 
     * @see problemGenerator.ProblemGenerator#generate(logic.problem.Problem,
     *      int)
     */
    protected Problem generate(Problem p, int index) {
        balls = index;
        bins = index - 1;

        FormulaFactory ff = new FormulaFactory();
        SignedFormulaFactory sff = new SignedFormulaFactory();

        p.setFormulaFactory(ff);
        p.setSignedFormulaFactory(sff);

        SignedFormulaList sfl = new SignedFormulaList();

        sfl.addAll(generateFirstPart(ff, sff, index));
        sfl.addAll(generateSecondPart(ff, sff, index));

        p.setSignedFormulaList(sfl);

        return p;
    }

    private String getAtomName() {
        return "p";
    }

    /**
     * @param ff
     * @param sff
     * @param i
     * @param index
     * @return
     */
    private SignedFormulaList generateFirstPart(FormulaFactory ff,
            SignedFormulaFactory sff, int index) {
        SignedFormulaList result = new SignedFormulaList();

        for (int i = 0; i <= balls; i++) {
            FormulaIteratingGenerator fig = new FormulaIteratingGenerator(
                    ClassicalConnectives.OR);
            AtomicFormulaGenerator fg = new AtomicFormulaGenerator(
                    getAtomName() + i + ",");
            Formula f = fig.generate(ff, 0, bins,
                    FormulaIteratingGenerator.LEFT_ASSOCIATED, fg);
            SignedFormula sf = sff.createSignedFormula(ClassicalSigns.TRUE, f);
            result.add(sf);
        }

        return result;
    }

    /**
     * @param ff
     * @param sff
     * @param i
     * @param index
     * @return
     */
    private SignedFormulaList generateSecondPart(FormulaFactory ff,
            SignedFormulaFactory sff, int index) {
        SignedFormulaList result = new SignedFormulaList();
        for (int i = 0; i <= bins; i++) {
            for (int j = 0; j <= balls; j++) {
                for (int k = j + 1; k <= balls; k++) {

                    Formula f = ff.createCompositeFormula(
                            ClassicalConnectives.AND, ff
                                    .createAtomicFormula(getAtomName() + j
                                            + "," + i), ff
                                    .createAtomicFormula(getAtomName() + k
                                            + "," + i));
                    SignedFormula sf = sff.createSignedFormula(
                            ClassicalSigns.FALSE, f);
                    result.add(sf);
                }
            }
        }
        return result;
    }

}
