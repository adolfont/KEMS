/*
 * Created on 22/06/2005
 *
 */
package aspects;

import formulasNew.AtomicFormula;
import formulasNew.FormulaFactory;
import junit.framework.TestCase;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class ComplexityTest extends TestCase {
    
    public void testComplexity() {
        FormulaFactory ff = new FormulaFactory();
        AtomicFormula af1 = ff.createAtomicFormula("A");
        
        assertTrue (af1.getComplexity()==1);
        
    }


}
