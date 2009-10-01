/*
 * Created on 05/08/2005
 *
 */
package logicalSystems.mbC;

import junit.framework.TestCase;
import logic.formulas.FormulaFactory;
import logic.signedFormulas.SignedFormula;
import logic.signedFormulas.SignedFormulaCreator;
import logic.signedFormulas.SignedFormulaFactory;
import logic.signedFormulas.SignedFormulaList;
import logicalSystems.mbc.MBCRules;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *  
 */
public class MBCRulesTest extends TestCase {
    SignedFormulaCreator sfc = new SignedFormulaCreator("satlfi");

    SignedFormulaFactory sff = sfc.getSignedFormulaFactory();

    FormulaFactory ff = sfc.getFormulaFactory();

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    

    public void testF_AND_RIGHT_1() {
        SignedFormula sfMain = sfc.parseString("F A&B");
        SignedFormula sfAux = sfc.parseString("T B");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F A"), MBCRules.F_AND_RIGHT
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }

    public void testF_AND_RIGHT_2() {
        SignedFormula sfMain = sfc.parseString("F A&B");
        SignedFormula sfAux = sfc.parseString("T A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        try {
            assertEquals(sfc.parseString("F B"), MBCRules.F_AND_RIGHT
                    .getPossibleConclusions(sff, ff, sfl).get(0));
            assertTrue(false);

        } catch (Exception e) {
            // nada a fazer
        }

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }
    
    public void testT_OR_RIGHT_1() {
        SignedFormula sfMain = sfc.parseString("T A|B");
        SignedFormula sfAux = sfc.parseString("F B");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T A"), MBCRules.T_OR_RIGHT
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }

    public void testT_OR_RIGHT_2() {
        SignedFormula sfMain = sfc.parseString("T A|B");
        SignedFormula sfAux = sfc.parseString("F A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        try {
            assertEquals(sfc.parseString("T B"), MBCRules.T_OR_RIGHT
                    .getPossibleConclusions(sff, ff, sfl).get(0));
            assertTrue(false);

        } catch (Exception e) {
            // nada a fazer
        }

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }


    public void testT_IMPLIES_RIGHT_1() {
        SignedFormula sfMain = sfc.parseString("T A->B");
        SignedFormula sfAux = sfc.parseString("F B");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F A"), MBCRules.T_IMPLIES_RIGHT
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }

    public void testT_IMPLIES_RIGHT_2() {
        SignedFormula sfMain = sfc.parseString("T A->B");
        SignedFormula sfAux = sfc.parseString("T A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        try {
            assertEquals(sfc.parseString("T B"), MBCRules.T_IMPLIES_RIGHT
                    .getPossibleConclusions(sff, ff, sfl).get(0));
            assertTrue(false);

        } catch (Exception e) {
            // nada a fazer
        }

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }

    public void testT_NOT_2_1() {
        SignedFormula sfMain = sfc.parseString("T !A");
        SignedFormula sfAux = sfc.parseString("T A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F @A"), MBCRules.T_NOT_2
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertFalse(MBCRules.T_NOT_2.matchesMain(sfAux));
    }

    public void testT_NOT_2_2() {
        SignedFormula sfMain = sfc.parseString("T !(A->B&C)");
        SignedFormula sfAux = sfc.parseString("T A->B&C");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("F @(A->B&C)"), MBCRules.T_NOT_2
                .getPossibleConclusions(sff, ff, sfl).get(0));

    }


    public void testF_FORMULA() {
        SignedFormula sfMain = sfc.parseString("T (A->B&C)");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);

        assertEquals(sfc.parseString("F !(A->B&C)"), MBCRules.F_FORMULA
                .getPossibleConclusions(sff, ff, sfl).get(0));

    }
    
    public void testT_CONS_1() {
        SignedFormula sfMain = sfc.parseString("T @A");
        SignedFormula sfAux = sfc.parseString("T A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F !A"), MBCRules.T_CONS_1
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertFalse(MBCRules.T_CONS_1.matchesMain(sfAux));
    }


    public void testT_NOT_1() {
        SignedFormula sfMain = sfc.parseString("T !A");
        SignedFormula sfAux = sfc.parseString("T @A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F A"), MBCRules.T_NOT_1
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertFalse(MBCRules.T_NOT_1.matchesMain(sfAux));
    }

    public void testT_CONS_2() {
        SignedFormula sfMain = sfc.parseString("T @A");
        SignedFormula sfAux1 = sfc.parseString("T B->A");
        SignedFormula sfAux2 = sfc.parseString("T B->!A");
        //        System.err.println(sfMain);
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux1);
        sfl.add(sfAux2);
        assertEquals(sfc.parseString("F B"), MBCRules.T_CONS_2
                .getPossibleConclusions(sff, ff, sfl).get(0));

        assertTrue(MBCRules.T_CONS_2.getPattern().matchesMain(sfMain));
        assertTrue(MBCRules.T_CONS_2.getPattern().matchesFirstAuxiliary(sfMain,sfAux1));
        assertTrue(MBCRules.T_CONS_2.getPattern().matchesSecondAuxiliary(sfMain,sfAux2));
        assertTrue(MBCRules.T_CONS_2.getPattern().matches(sfMain, sfAux1, sfAux2));
        
        assertEquals(sfc.parseString("T B->!A"), MBCRules.T_CONS_2.getPattern().getAuxiliaryCandidates(sff,ff,sfMain,sfAux1).get(0));
        assertEquals(sfc.parseString("T B->A"), MBCRules.T_CONS_2.getPattern().getAuxiliaryCandidates(sff,ff,sfMain,sfAux2).get(0));
    }

}
