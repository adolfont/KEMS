/*
 * Created on 23/03/2005
 *
 */
package problemGenerator;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.formulas.FormulaList;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.classicalLogic.ClassicalConnectives;
import logicalSystems.classicalLogic.ClassicalSigns;

/**
 * Problem generator for a "square" graph version of the Tseitin family
 * (Pelletier 75 problems)
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SquareTseitinProblemGenerator extends ProblemGenerator {

    /**
     * Creates a problem generator for the Square Tseitin family
     */
    public SquareTseitinProblemGenerator() {
        super("SquareTseitin");
    }

    /*
     * (non-Javadoc)
     * 
     * @see problemGenerator.ProblemGenerator#generate(problem.Problem, int)
     */
    protected Problem generate(Problem p, int index) {
        FormulaFactory ff = new FormulaFactory();
        SignedFormulaFactory sff = new SignedFormulaFactory();
        p.setFormulaFactory(ff);
        p.setSignedFormulaFactory(sff);

        p
                .setSignedFormulaList(new SignedFormulaList(sff
                        .createSignedFormula(ClassicalSigns.FALSE, 
//                                ff
//                                .createCompositeFormula(
//                                        ClassicalConnectives.NOT,
                                        generateFormula(ff, index))
//                                        )
                                        ));

        return p;
    }

    private Formula generateFormula(FormulaFactory ff, int index) {
        return generateFormula(ff, ClassicalConnectives.OR, generateList(ff,
                index));
    }

    private Formula generateFormula(FormulaFactory ff, Connective conn,
            FormulaList fl) {
        Formula aux = fl.get(fl.size() - 1);
        for (int i = fl.size() - 2; i >= 0; i--) {
            aux = ff.createCompositeFormula(conn, fl.get(i), aux);
        }

        return aux;
    }

    private FormulaList generateList(FormulaFactory ff, int index) {

        FormulaList fl = new FormulaList();

        for (int l = 0; l <= index; l++) {
            for (int c = 0; c <= index; c++) {
                fl.add(generateXorFormula(ff, l, c, index));
            }
        }

        return fl;
    }

    private Formula generateXorFormula(FormulaFactory ff, int l, int c,
            int index) {
        FormulaList fl = new FormulaList();

        if (l - 1 >= 0)
            fl.add(ff.createAtomicFormula("V" + (l - 1) + "" + c));
        if (l < index)
            fl.add(ff.createAtomicFormula("V" + l + "" + c));
        if (c - 1 >= 0)
            fl.add(ff.createAtomicFormula("H" + l + "" + (c - 1)));
        if (c < index)
            fl.add(ff.createAtomicFormula("H" + l + "" + c));

        return generateSpecialXorFormula(ff, fl, l, c);
    }

    /**
     * @param ff
     * @param fl
     * @param l
     * @param c
     * @return
     */
    private Formula generateSpecialXorFormula(FormulaFactory ff,
            FormulaList fl, int l, int c) {

        if ((l == 0) && (c == 0)) {
            return generateFormula(ff, ClassicalConnectives.BIIMPLIES, fl);
        }

        return generateFormula(ff, ClassicalConnectives.XOR, fl);
    }
}
