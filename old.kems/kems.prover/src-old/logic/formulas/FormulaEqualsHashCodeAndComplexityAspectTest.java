/*
 * Created on 05/04/2005
 *
 */
package formulas;

import junit.framework.TestCase;
import formulasNew.Arity;
import formulasNew.AtomicFormula;
import formulasNew.Connective;
import formulasNew.Formula;
import formulasNew.FormulaFactory;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * 
 */
public class FormulaEqualsHashCodeAndComplexityAspectTest extends TestCase {

    /** Formula factories */

    FormulaFactory ff = new FormulaFactory();

    FormulaFactory ff2 = new FormulaFactory();

    /** Atomic formulas */
    Formula a = ff.createAtomicFormula("A");

    Formula aCopy = ff.createAtomicFormula("A");

    Formula aCopy2 = ff2.createAtomicFormula("A");

    public void testEqualsAndHashCode() {
        assertEquals(a, aCopy);
        assertTrue(a == aCopy);
        assertTrue(a.hashCode() == aCopy.hashCode());

        assertEquals(a, aCopy2);
        assertFalse(a == aCopy2);
        assertTrue(a.hashCode() == aCopy2.hashCode());
    }

    public void testComplexity() {
        assertTrue(a.getComplexity() == 1);
        assertTrue(aCopy.getComplexity() == 1);
        assertTrue(aCopy2.getComplexity() == 1);

    }

    /** Connectives */

    Connective op1 = new Connective("<op1>", Arity.BINARY);

    Connective op2 = new Connective("<op2>", Arity.BINARY);

    Connective op3 = new Connective("<op3>", Arity.ZEROARY);

    /** Atomic and Composite formulas */

    AtomicFormula b = ff.createAtomicFormula("B");

    AtomicFormula bCopy2 = ff2.createAtomicFormula("B");

    Formula cf1 = ff.createCompositeFormula(op1, a, b);

    Formula cf2 = ff2.createCompositeFormula(op1, aCopy2, bCopy2);

    Formula cf3 = ff.createCompositeFormula(op2, cf1, b);

    Formula cf4 = ff.createCompositeFormula(op2, b, cf1);

    public void testEqualsHashCode() {
        assertEquals(cf1, cf2);
        assertTrue(cf1.hashCode() == cf2.hashCode());
        assertFalse(cf1 == cf2);

        assertTrue(cf3.getComplexity()== 5);
        assertTrue(cf3.getComplexity()== cf4.getComplexity());
        assertFalse(cf3.hashCode()==cf4.hashCode());

    }

}
