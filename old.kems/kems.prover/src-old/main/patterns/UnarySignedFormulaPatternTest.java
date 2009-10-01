package patterns;

import junit.framework.TestCase;
import rulesNew.KERuleRole;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import signedFormulasNew.SignedFormulaList;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalSigns;

/*
 * Created on 23/09/2004
 *
 */

/**
 * Tests the UnarySignedFormulaPattern interface and its implementation
 * SignConnectivePattern.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class UnarySignedFormulaPatternTest extends TestCase {

    SignedFormulaCreator sfc = new SignedFormulaCreator();

    public void testMatchingSignConnectivePattern() {

        //        ZeroarySignedFormulaPattern

        UnarySignedFormulaPattern sfp1 = new SignConnectivePattern(
                ClassicalSigns.TRUE, ClassicalConnectives.AND);

        SignedFormula sf1, sf2;
        sf1 = sfc.parseString("T A&B");
        sf2 = sfc.parseString("T A->B");

        assertTrue(sfp1.matches(sf1));
        assertFalse(sfp1.matches(sf2));

    }

    public void testMatchingConnectiveSubformulaPattern() {

        //        ZeroarySignedFormulaPattern

        UnarySignedFormulaPattern sfp1 = new ConnectiveSubformulaPattern(
                ClassicalConnectives.AND);

        SignedFormulaCreator fc = new SignedFormulaCreator();

        SignedFormula sf[] = new SignedFormula[5];
        sf[0] = fc.parseString("T A&B");
        sf[1] = fc.parseString("T A->A&B");
        sf[2] = fc.parseString("F A&B->A&B");
        sf[3] = fc.parseString("F (A&B)|(A->B)");
        sf[4] = fc.parseString("T A->B");

        assertTrue(sfp1.matches(sf[0]));
        assertTrue(sfp1.matches(sf[1]));
        assertTrue(sfp1.matches(sf[2]));
        assertTrue(sfp1.matches(sf[3]));
        assertFalse(sfp1.matches(sf[4]));

    }

    public void testTwoConnectiveRoleSubformulaPattern() {
        UnarySignedFormulaPattern sfp1 = new TwoConnectivesRoleSubformulaPattern(
                ClassicalConnectives.TOP, KERuleRole.LEFT,
                ClassicalConnectives.IMPLIES);

        SignedFormula sf1, sf2, sf3, sf4, sf5;
        sf1 = sfc.parseString("T T()->B");
        sf2 = sfc.parseString("T A->(T()->B)");
        sf3 = sfc.parseString("F T()");
        sf4 = sfc.parseString("T B|C|(E&(T()->(B|D)))");
        sf5 = sfc.parseString("T A->B");

        assertTrue(sfp1.matches(sf1));
        assertTrue(sfp1.matches(sf2));
        assertFalse(sfp1.matches(sf3));
        assertTrue(sfp1.matches(sf4));
        assertFalse(sfp1.matches(sf5));

        sfp1 = new TwoConnectivesRoleSubformulaPattern(ClassicalConnectives.TOP,
                KERuleRole.RIGHT, ClassicalConnectives.IMPLIES);

        sf1 = sfc.parseString("T B->T()");
        sf2 = sfc.parseString("T A->(B->T())");
        sf3 = sfc.parseString("F T()");
        sf4 = sfc.parseString("T B|C|(E&((B|D)->T()))");
        sf5 = sfc.parseString("T A->B");

        assertTrue(sfp1.matches(sf1));
        assertTrue(sfp1.matches(sf2));
        assertFalse(sfp1.matches(sf3));
        assertTrue(sfp1.matches(sf4));
        assertFalse(sfp1.matches(sf5));

    }

    public void testTwoConnectiveRoleSubformulaPattern_II() {
        TwoConnectivesRoleSubformulaPattern sfp1 = new TwoConnectivesRoleSubformulaPattern(
                ClassicalConnectives.TOP, KERuleRole.ANY,
                ClassicalConnectives.ORN);

        SignedFormula sf1, sf2, sf3, sf4, sf5;
        sf1 = sfc.parseString("T (A|B|T())");
        sf2 = sfc.parseString("T A->!B->(C&(A|B|T()|D))");
        sf3 = sfc.parseString("F T()");
        sf4 = sfc.parseString("T A|B|T()|C|D");
        sf5 = sfc.parseString("T A->B");

        assertTrue(sfp1.matches(sf1));
        assertTrue(sfp1.matches(sf2));
        assertFalse(sfp1.matches(sf3));
        assertTrue(sfp1.matches(sf4));
        assertFalse(sfp1.matches(sf5));

        //        System.out.println(sfp1
        //                .getMatchedSubformula(new SignedFormulaList(sf1)));
        //        System.out.println(sfp1
        //                .getMatchedSubformula(new SignedFormulaList(sf2)));
        //        System.out.println(sfp1
        //                .getMatchedSubformula(new SignedFormulaList(sf4)));
        assertNotNull (sfp1
                .getMatchedSubformula(new SignedFormulaList(sf1)));
        assertNotNull (sfp1
                .getMatchedSubformula(new SignedFormulaList(sf2)));
        assertNull (sfp1
                .getMatchedSubformula(new SignedFormulaList(sf3)));

    }

    public void testTwoConnectiveRoleSubformulaPattern_III() {
        UnarySignedFormulaPattern sfp1 = new TwoConnectivesRoleSubformulaPattern(
                ClassicalConnectives.BOTTOM, KERuleRole.ANY,
                ClassicalConnectives.NOT);

        SignedFormula sf1, sf2, sf3, sf4, sf5;
        sf1 = sfc.parseString("T (A|B|!F())");
        sf2 = sfc.parseString("T A->!B->(C&(A|B|!F()|D))");
        sf3 = sfc.parseString("F !(A->F())");
        sf4 = sfc.parseString("T A|B|!(F())|C|D");
        sf5 = sfc.parseString("T A->!B->F()");

        assertTrue(sfp1.matches(sf1));
        assertTrue(sfp1.matches(sf2));
        assertFalse(sfp1.matches(sf3));
        assertTrue(sfp1.matches(sf4));
        assertFalse(sfp1.matches(sf5));

    }
    
    public void testTwoConnectiveRoleSubformulaPattern_2() {
        // notice that the order is the opposite: first second and second first
        TwoConnectivesRoleSubformulaPattern sfp1 = new TwoConnectivesRoleSubformulaPattern(
                ClassicalConnectives.TOP, KERuleRole.ANY, ClassicalConnectives.NOT);

        SignedFormula sf1, sf2, sf3, sf4, sf5;
        sf1 = sfc.parseString("T (A|!T())");
        assertTrue(sfp1.matches(sf1));
//        System.out.println(sfp1.getMatchedSubformula(new SignedFormulaList(sf1)));
        

        
    }
}