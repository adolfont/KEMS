/*
 * Created on 10/10/2005
 *
 */
package problemGenerator;

import logic.formulas.Connective;
import logic.formulas.Formula;
import logic.formulas.FormulaFactory;

/**
 * A specialized version of FormulaGenerator for the fourth family of problems.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FourthLFIFamilySpecialFormulaGenerator extends FormulaGenerator {

    private Connective conn2, conn3;

    private String atom2;

    /**
     * @param atom
     * @param conn
     */
    public FourthLFIFamilySpecialFormulaGenerator(String atom, String atom2, Connective conn,
            Connective conn2, Connective conn3) {
        super(atom, conn);
        this.conn2 = conn2;
        this.conn3 = conn3;
        this.atom2 = atom2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see problemGenerator.FormulaGenerator#getInstance(logic.formulas.FormulaFactory,
     *      int)
     */
    public Formula getInstance(FormulaFactory ff, int index) {

        //creates (Ai|Bi)
        Formula f_begin = ff.createCompositeFormula(getConn(), ff.createAtomicFormula(getAtom()
                + index), ff.createAtomicFormula(atom2 + index));

        // creates (@Ai+1)
        Formula f_end = ff.createCompositeFormula(conn2, ff.createAtomicFormula(getAtom()
                + (index + 1)));

        return ff.createCompositeFormula(conn3, f_begin, f_end);
    }
}
