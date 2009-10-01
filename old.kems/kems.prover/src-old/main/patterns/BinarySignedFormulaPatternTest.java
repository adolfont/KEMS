/*
 * Created on 23/09/2004
 *
 */
package patterns;

import junit.framework.TestCase;
import rulesNew.KERuleRole;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalSigns;

/**
 * 
 * Tests the interface BinarySignedFormulaPattern and the class that implements
 * this interface: BinarySignConnectivePattern.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class BinarySignedFormulaPatternTest extends TestCase {
    SignedFormulaCreator fc;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        fc = new SignedFormulaCreator();
    }

    public void testTwoSignsConnectiveRolePattern() {
        BinarySignedFormulaPattern sfp1 = new TwoSignsConnectiveRolePattern(
                ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
                ClassicalSigns.TRUE, KERuleRole.LEFT);

        SignedFormula sf1, sf2, sf3, sf4, sf5;
        sf1 = fc.parseString("T A->B");
        sf2 = fc.parseString("T A");
        sf3 = fc.parseString("F A");
        sf4 = fc.parseString("T B");
        sf5 = fc.parseString("T A->B");

        sfp1 = new TwoSignsConnectiveRolePattern(ClassicalSigns.FALSE,
                ClassicalConnectives.AND, ClassicalSigns.TRUE, KERuleRole.RIGHT);

        sf1 = fc.parseString("F A&B");
        sf2 = fc.parseString("T A");
        sf3 = fc.parseString("T B");
        sf4 = fc.parseString("F A");
        sf5 = fc.parseString("F A&B");

        assertFalse(sfp1.matches(sf1, sf2));
        assertTrue(sfp1.matches(sf1, sf3));
        assertFalse(sfp1.matches(sf1, sf4));
        assertFalse(sfp1.matches(sf1, sf5));

        BinarySignedFormulaPattern sfp2 = new TwoSignsConnectiveRolePattern(
                ClassicalSigns.TRUE, ClassicalConnectives.ORN,
                ClassicalSigns.FALSE, KERuleRole.ANY);

        sf1 = fc.parseString("T A|B|C");
        sf2 = fc.parseString("F A");
        sf3 = fc.parseString("F B");
        sf4 = fc.parseString("F C");
        sf5 = fc.parseString("T A|B");

        assertTrue(sfp2.matches(sf1, sf2));
        assertTrue(sfp2.matches(sf1, sf3));
        assertTrue(sfp2.matches(sf1, sf4));
        assertFalse(sfp2.matches(sf1, sf5));

        sfp2 = new TwoSignsConnectiveRolePattern(ClassicalSigns.TRUE,
                ClassicalConnectives.OR, ClassicalSigns.FALSE, KERuleRole.ANY);

        sf1 = fc.parseString("T A|B");
        sf2 = fc.parseString("F A");
        sf3 = fc.parseString("F B");
        sf4 = fc.parseString("F C");
        sf5 = fc.parseString("T A|B");

        assertTrue(sfp2.matches(sf1, sf2));
        assertTrue(sfp2.matches(sf1, sf3));
        assertFalse(sfp2.matches(sf1, sf4));
        assertFalse(sfp2.matches(sf1, sf5));
    }

    public void testSignConnectiveRoleSubformulaPattern() {
        SignedFormula sf1, sf2, sf3, sf3b, sf4, sf5, sf6, sf7, sf8, sf9, sf10;

        SignConnectiveRoleSubformulaPattern sfp = new SignConnectiveRoleSubformulaPattern(
                ClassicalConnectives.OR, ClassicalSigns.TRUE, KERuleRole.ANY);

        // TODO pode dar diferente excluindo n-ários!

        sf1 = fc.parseString("T A|B");
        sf2 = fc.parseString("T A|(B|C)");
        sf3 = fc.parseString("F A->A->(B|C|D)");
        sf3b = fc.parseString("F A->A->(B|(C|D))");
        sf4 = fc.parseString("T A");
        sf5 = fc.parseString("T B");
        sf6 = fc.parseString("T C");
        sf7 = fc.parseString("F D");
        // TODO E se fosse D|C? Claro que não serviria!
        sf8 = fc.parseString("T B|C");
        sf9 = fc.parseString("T C|D");
        sf10 = fc.parseString("F ((B|C)|X)->(A->((B|C)|Y))");

        assertTrue(sfp.matches(sf1, sf4));
//        System.out.println(sfp.getMatchedSubformula(sf1, sf4));
//        System.out.println(sfp.getMatchedSubformula(sf1, sf5));
//        System.out.println(sfp.getMatchedSubformula(sf2, sf4));
//        System.out.println(sfp.getMatchedSubformula(sf2, sf6));
//        System.out.println(sfp.getMatchedSubformula(sf3b, sf9));
//        System.out.println(sfp.getMatchedSubformula(sf10, sf9));
//        System.out.println(sfp.getMatchedSubformula(sf10, sf8));

        assertTrue(sfp.matches(sf1, sf5));
        assertFalse(sfp.matches(sf1, sf6));

        assertTrue(sfp.matches(sf2, sf4));
        assertTrue(sfp.matches(sf2, sf5));
        assertTrue(sfp.matches(sf2, sf6));
        assertTrue(sfp.matches(sf2, sf8));
        assertFalse(sfp.matches(sf2, sf7));

        assertFalse(sfp.matches(sf3, sf4));
        assertFalse(sfp.matches(sf3, sf5));
        assertFalse(sfp.matches(sf3, sf6));
        assertFalse(sfp.matches(sf3, sf8));
        assertFalse(sfp.matches(sf3, sf7));

        assertFalse(sfp.matches(sf3b, sf4));
        assertTrue(sfp.matches(sf3b, sf5));
        assertTrue(sfp.matches(sf3b, sf6));
        assertTrue(sfp.matches(sf3b, sf9));
        assertFalse(sfp.matches(sf3b, sf7));

    }

    public void testTwoSignsConnectiveTwoRolesSubformulaPattern() {
        SignedFormula sf1, sf2, sf3, sf3b, sf4, sf5, sf6, sf7, sf8, sf9, sf10;

        TwoSignsConnectiveTwoRolesSubformulaPattern sfp = new TwoSignsConnectiveTwoRolesSubformulaPattern(
                ClassicalConnectives.IMPLIES, ClassicalSigns.TRUE,
                KERuleRole.LEFT, ClassicalSigns.FALSE, KERuleRole.RIGHT);

        // TODO pode dar diferente excluindo n-ários!

        sf1 = fc.parseString("T A->B");
        sf2 = fc.parseString("T A|((B&C)->C)");
        sf3 = fc.parseString("F B");
        sf4 = fc.parseString("T B");
        sf5 = fc.parseString("T A");
        sf6 = fc.parseString("T B&C");
        sf7 = fc.parseString("T C");
        sf8 = fc.parseString("F C");

        assertTrue(sfp.matches(sf1, sf5));
        assertTrue(sfp.matches(sf1, sf3));
//        System.out.println(sfp.matches(sf1, sf4));
        assertFalse(sfp.matches(sf1, sf4));
        assertTrue(sfp.matches(sf2, sf6));
        assertTrue(sfp.matches(sf2, sf8));
        assertFalse(sfp.matches(sf2, sf7));
//        System.out.println(sfp.getMatchedSubformula(sf1, sf5));

    }

}