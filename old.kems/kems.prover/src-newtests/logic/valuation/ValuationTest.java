/*
 * Created on 26/10/2005
 *
 */
package logic.valuation;

import junit.framework.TestCase;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaList;

/**
 * Test for valuations.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ValuationTest extends TestCase {

    public void testCPLValuation() {
        IValuation iv = new CPLValuation();

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats4");

        SignedFormula sf1 = sfc.parseString("T A");
        SignedFormula sf2 = sfc.parseString("F A->B");
        SignedFormula sf3 = sfc.parseString("T B");
        SignedFormula sf4 = sfc.parseString("F C");
        SignedFormula sf5 = sfc.parseString("T D");
        SignedFormulaList sfl = new SignedFormulaList(sf1);
        sfl.add(sf2);
        sfl.add(sf3);
        sfl.add(sf4);
        sfl.add(sf5);

        iv.create(sfl);

        assertTrue(iv.getValue(sf1.getFormula()));
        assertTrue(iv.getValue(sf3.getFormula()));
        assertFalse(iv.getValue(sf4.getFormula()));
        assertTrue(iv.getValue(sf5.getFormula()));
        
        System.err.println(iv);

    }

    public void testC1Valuation() {
        IValuation iv = new C1Valuation();

        SignedFormulaCreator sfc = new SignedFormulaCreator("satlfi");

        SignedFormula sf1 = sfc.parseString("T @A");
        SignedFormula sf2 = sfc.parseString("F @A->B");
        SignedFormula sf3 = sfc.parseString("T @(A->B)");
        SignedFormula sf4 = sfc.parseString("F C");
        SignedFormula sf5 = sfc.parseString("T D");
        SignedFormulaList sfl = new SignedFormulaList(sf1);
        sfl.add(sf2);
        sfl.add(sf3);
        sfl.add(sf4);
        sfl.add(sf5);

        iv.create(sfl);

        assertTrue(iv.getValue(sf1.getFormula()));
        assertTrue(iv.getValue(sf3.getFormula()));
        assertFalse(iv.getValue(sf4.getFormula()));
        assertTrue(iv.getValue(sf5.getFormula()));

        System.err.println(iv);

    }


    public void testMBCValuation() {
        IValuation iv = new MBCValuation();

        SignedFormulaCreator sfc = new SignedFormulaCreator("satlfi");

        SignedFormula sf1 = sfc.parseString("T @A");
        SignedFormula sf2 = sfc.parseString("T @A->B");
        SignedFormula sf3 = sfc.parseString("T @(A->B)");
        SignedFormula sf4 = sfc.parseString("F @C");
        SignedFormula sf5 = sfc.parseString("T D");
        SignedFormulaList sfl = new SignedFormulaList(sf1);
        sfl.add(sf2);
        sfl.add(sf3);
        sfl.add(sf4);
        sfl.add(sf5);

        iv.create(sfl);

        assertTrue(iv.getValue(sf1.getFormula()));
        assertTrue(iv.getValue(sf3.getFormula()));
        assertFalse(iv.getValue(sf4.getFormula()));
        assertTrue(iv.getValue(sf5.getFormula()));

        System.err.println(iv);

    }

}
