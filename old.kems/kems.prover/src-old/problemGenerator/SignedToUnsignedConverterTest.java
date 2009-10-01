/*
 * Created on 12/04/2005
 *
 */
package signedToUnsignedConverter;

import junit.framework.TestCase;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalSigns;
import formulasNew.AtomicFormula;
import formulasNew.Formula;
import formulasNew.FormulaFactory;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class SignedToUnsignedConverterTest extends TestCase {
    
    public void testSignedToUnsigned() {
        
        SignedFormulaList sfl = new SignedFormulaList();
        
        FormulaFactory ff = new FormulaFactory();
        SignedFormulaFactory sff = new SignedFormulaFactory();
        
        AtomicFormula a = ff.createAtomicFormula("A");
        AtomicFormula b = ff.createAtomicFormula("B");
        AtomicFormula c = ff.createAtomicFormula("C");
        
        Formula cf1 = ff.createCompositeFormula(ClassicalConnectives.TOP);
        Formula cf2 = ff.createCompositeFormula(ClassicalConnectives.AND, a, b);
        Formula cf3 = ff.createCompositeFormula(ClassicalConnectives.OR, b, c);
        Formula cf4 = ff.createCompositeFormula(ClassicalConnectives.IMPLIES, cf2, cf3);
        
        SignedFormula sf1 = sff.createSignedFormula(ClassicalSigns.TRUE, a);
        SignedFormula sf2 = sff.createSignedFormula(ClassicalSigns.TRUE, cf1);
        SignedFormula sf3 = sff.createSignedFormula(ClassicalSigns.TRUE, cf3);
        SignedFormula sf4 = sff.createSignedFormula(ClassicalSigns.FALSE, cf4);
               
        sfl.add(sf1);
        sfl.add(sf2);
        sfl.add(sf4);
        sfl.add(sf3);
        
        assertNotNull(SignedToUnsignedConverter.convert(ff,  sfl));
        
    }


}
