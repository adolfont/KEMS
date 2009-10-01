/*
 * Created on 22/06/2005
 *
 */
package formulas;

import formulasNew.AtomicFormula;
import formulasNew.FormulaFactory;
import junit.framework.TestCase;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class AtomicFormulaTest extends TestCase {
    
    public void testCreation() {
        
        FormulaFactory ff1 = new FormulaFactory();
        FormulaFactory ff2 = new FormulaFactory();
        
        AtomicFormula af11 = ff1.createAtomicFormula("A");
        AtomicFormula af12 = ff1.createAtomicFormula("B");
        AtomicFormula af13 = ff1.createAtomicFormula("A");

        AtomicFormula af21 = ff2.createAtomicFormula("A");
        AtomicFormula af22 = ff2.createAtomicFormula("B");
        AtomicFormula af23 = ff2.createAtomicFormula("A");
        
        assertEquals(af11,af13);
        assertNotSame(af11,af12);
        
        assertTrue(af11.hashCode()==af13.hashCode());
        assertTrue(af11.hashCode()==af21.hashCode());
        assertFalse(af12.hashCode()==af11.hashCode());
        assertFalse(af12.hashCode()==af21.hashCode());

        assertTrue(af11.getImmediateSubformulas().isEmpty());
    }

}
