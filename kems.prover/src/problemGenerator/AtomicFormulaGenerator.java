/*
 * Created on 09/11/2005
 *
 */
package problemGenerator;

import logic.formulas.Formula;
import logic.formulas.FormulaFactory;

/**
 * A generator for atomic formulas
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class AtomicFormulaGenerator implements IFormulaGenerator {

    private String atom;

    public AtomicFormulaGenerator(String atom) {
        this.atom = atom;
    }

    /*
     * (non-Javadoc)
     * 
     * @see problemGenerator.IFormulaGenerator#getInstance(logic.formulas.FormulaFactory,
     *      int)
     */
    public Formula getInstance(FormulaFactory ff, int index) {
        return ff.createAtomicFormula(atom + index);
    }

}
