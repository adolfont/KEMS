/*
 * Created on 06/10/2005
 *
 */
package problemGenerator;

import logic.formulas.AtomicFormula;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaFactory;
import logicalSystems.mbc.MBCConnectives;
import logicalSystems.mbc.MBCSigns;

/**
 * Class that generates the fourth family of problems for evaluating mbC and C1
 * 
 * T &Ai, 
 * 
 * T &(i=1-n) [(Ai|Bi)->oAi+1], 
 * 
 * T (@A2&@A3&...&@An)->An+1, Obs.: If n less than 2, &(@Ai) is replaced by X&!X&@X  
 * 
 * F !!(An+1)
 * 
 * 
 * It also has 4 s-formulas, as the first LFI family
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FourthLFIProblemGenerator extends LFIProblemGenerator {

    /**
     */
    protected FourthLFIProblemGenerator() {
        super("FourthLFIFamily");
    }

    /**
     */
    protected FourthLFIProblemGenerator(String name) {
        super(name);
    }

    protected SignedFormula generateFirstSignedFormula(FormulaFactory ff, SignedFormulaFactory sff,
            int begin, int end) {
        //		Formula f = getInstance(ff, end);
        //		for (int i = end - 1; i >= begin; i--) {
        //			f = ff.createCompositeFormula(MBCConnectives.AND,
        //					getInstance(ff, i), f);
        //		}
        //		return (sff.createSignedFormula(ClassicalSigns.TRUE, f));
        AtomicFormulaGenerator fg = new AtomicFormulaGenerator(getAtomName());
        SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE, MBCConnectives.AND);

        return sfg.generate(ff, sff, begin, end, FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg);
    }

    protected SignedFormula generateSecondSignedFormula(FormulaFactory ff,
            SignedFormulaFactory sff, int begin, int end) {

        FormulaGenerator fg2 = new FourthLFIFamilySpecialFormulaGenerator(getAtomName(), "B",
                MBCConnectives.OR, MBCConnectives.CONSISTENCY, MBCConnectives.IMPLIES);
        SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE, MBCConnectives.AND);

        return sfg.generate(ff, sff, begin, end, FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg2);

    }

    /**
     * @param ff
     * @param sff
     * @param begin
     * @param end
     * @return
     */
    protected SignedFormula generateThirdSignedFormula(FormulaFactory ff, SignedFormulaFactory sff,
            int begin, int end) {
        FormulaGenerator fg = new FormulaGenerator(getAtomName(), MBCConnectives.CONSISTENCY);
        SignedFormulaGenerator sfg = new SignedFormulaGenerator(MBCSigns.TRUE, MBCConnectives.OR);

        // creates (A_n+1)
        Formula f_end = ff.createAtomicFormula(getAtomName() + (end + 1));

        // creates &(Ai) for i>=2 or X&!x&X for i=1
        SignedFormula sf_result = null;
        SignedFormula sf_begin = sfg.generate(ff, sff, begin + 1, end,
                FormulaIteratingGenerator.RIGHT_ASSOCIATED, fg);
        
        
        if (end >= begin + 1) {
            sf_result = sff.createSignedFormula(sf_begin.getSign(), ff.createCompositeFormula(
                    MBCConnectives.IMPLIES, sf_begin.getFormula(), f_end));
        } else {
            //            Formula x = ff.createAtomicFormula("X");
            //            Formula notX = ff.createCompositeFormula(MBCConnectives.NOT, x);
            //            Formula andXNotX = ff.createCompositeFormula(MBCConnectives.AND,
            // x, notX);
            //            Formula consX =
            // ff.createCompositeFormula(MBCConnectives.CONSISTENCY, x);
            //            Formula andXNotX_consX =
            // ff.createCompositeFormula(MBCConnectives.AND, andXNotX, consX);
            //            Formula notAndXNotX_consX =
            // ff.createCompositeFormula(MBCConnectives.NOT, andXNotX_consX);
            Formula TOP = ff.createCompositeFormula(MBCConnectives.TOP);

            sf_result = sff.createSignedFormula(sf_begin.getSign(), ff.createCompositeFormula(
                    MBCConnectives.IMPLIES, TOP, f_end));

        }

        return sf_result;
    }

	// comentado em 22-06-2006
//    private Formula getInstance(FormulaFactory ff, int i) {
//        String auxLetter1 = getAtomName();
//        return ff
//                .createCompositeFormula(MBCConnectives.NOT, ff.createAtomicFormula(auxLetter1 + i));
//    }

    /**
     * @param ff
     * @param sff
     * @return
     */
    protected SignedFormula generateFourthSignedFormula(FormulaFactory ff,
            SignedFormulaFactory sff, int size) {
        AtomicFormula an_plus1 = ff.createAtomicFormula(getAtomName() + (size + 1));
        Formula neg_anplus1 = ff.createCompositeFormula(MBCConnectives.NOT, an_plus1);
        Formula neg_neg_anplus1 = ff.createCompositeFormula(MBCConnectives.NOT, neg_anplus1);
        //        AtomicFormula a1 = ff.createAtomicFormula(getAtomName() + 1);
        //        Formula cons_a1 =
        // ff.createCompositeFormula(MBCConnectives.CONSISTENCY, a1);
        //        Formula neg_neg_anplus1_or_cons_a1 =
        // ff.createCompositeFormula(MBCConnectives.OR, neg_anplus1, cons_a1);

        return sff.createSignedFormula(MBCSigns.FALSE, neg_neg_anplus1);
    }

}
