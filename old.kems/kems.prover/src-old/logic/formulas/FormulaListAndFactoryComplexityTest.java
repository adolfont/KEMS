/*
 * Created on 05/04/2005
 *
 */
package formulas;

import formulasNew.Arity;
import formulasNew.Connective;
import formulasNew.Formula;
import formulasNew.FormulaFactory;
import formulasNew.FormulaList;
import junit.framework.TestCase;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * 
 */
public class FormulaListAndFactoryComplexityTest extends TestCase {
    public void testComplexity() {

        FormulaList fl = new FormulaList();
        FormulaFactory ff = new FormulaFactory();
        Connective c = new Connective("op", Arity.BINARY);
        Formula a, b;

        fl.add(a = ff.createAtomicFormula("A"));
        fl.add(b = ff.createAtomicFormula("B"));
        fl.add(ff.createCompositeFormula(c, a, b));
        
        assertTrue(fl.getComplexity()==5);
        ff.createAtomicFormula("C");
        assertTrue(ff.getComplexity()==6);
    }
}
