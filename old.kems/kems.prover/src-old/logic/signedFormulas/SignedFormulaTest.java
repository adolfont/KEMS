/*
 * Created on 05/04/2005
 *
 */
package signedFormulas;

import junit.framework.TestCase;
import signedFormulasNew.FormulaSign;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import formulasNew.Arity;
import formulasNew.Connective;
import formulasNew.Formula;
import formulasNew.FormulaFactory;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class SignedFormulaTest extends TestCase {

    public void testSignedFormula() {
        FormulaFactory ff = new FormulaFactory();
        Formula a = ff.createAtomicFormula("A");
        Formula b = ff.createAtomicFormula("B");
        Formula c = ff.createAtomicFormula("C");

        FormulaFactory ff2 = new FormulaFactory();
        Formula a2 = ff.createAtomicFormula("A");
        Formula b2 = ff.createAtomicFormula("B");
        Formula c2 = ff.createAtomicFormula("C");

        Connective co = new Connective ("op", Arity.BINARY);
        
        Formula cf = ff.createCompositeFormula(co, a, b);

        Formula cf2 = ff2.createCompositeFormula(co, a2, b2);
        
        FormulaSign sT = new FormulaSign(0);
        SignedFormulaFactory sff = new SignedFormulaFactory();
        SignedFormulaFactory sff2 = new SignedFormulaFactory();
        SignedFormula sf = sff.createSignedFormula(sT, cf);
        SignedFormula sf2 = sff2.createSignedFormula(sT, cf2);
        
        
        assertEquals (sf, sf2);
        assertTrue (sf.hashCode() == sf2.hashCode());
        assertTrue (sf != sf2);
        
        assertTrue(sf.getComplexity()==sf2.getComplexity());
        
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf);
        sfl.add(sf2);
        assertTrue(sfl.getComplexity()==6);
        
        sff.createSignedFormula(sT, c);
        assertTrue(sff.getComplexity()==4);
    }
}
