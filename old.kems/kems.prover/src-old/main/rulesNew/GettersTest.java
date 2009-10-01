/*
 * Created on 12/11/2004
 *
 */
package rulesNew;

import junit.framework.TestCase;
import patterns.SignConnectiveRoleSubformulaPattern;
import patterns.TwoConnectivesRoleSubformulaPattern;
import rulesGetters.SimpleSubformulaGetter;
import rulesGetters.SubformulaConnectiveForTestRoleGetter;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalSigns;
import formulasNew.FormulaFactory;

/**
 * @author adolfo
 *  
 */
public class GettersTest extends TestCase {

    public void testGetters() {

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3" );

        SignedFormula sf1, sf2, sf3;
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();

        sf1 = sfc.parseString("T A->(B|C)->((B|C)|X)->((B|C)|K)->((B|C)|X)");
        sf2 = sfc.parseString("T B|C");
        SignConnectiveRoleSubformulaPattern pattern = new SignConnectiveRoleSubformulaPattern(
                ClassicalConnectives.OR, ClassicalSigns.TRUE, KERuleRole.ANY);

        SimpleSubformulaGetter getter = new SimpleSubformulaGetter(pattern,
                ClassicalConnectives.TOP);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf1);
        sfl.add(sf2);

        sf3 = getter.getSignedFormula(sff, ff, sfl);
        //        System.out.println(sf3);

    }

    public void testGetters2() {

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");

        SignedFormula sf1, sf2, sf3;
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();

        sf1 = sfc.parseString("T A->(TOP&A)->C->(TOP&A)->(A&TOP)");
 
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf1);
 
        TwoConnectivesRoleSubformulaPattern pattern_X_TOP_AND = new TwoConnectivesRoleSubformulaPattern(
                ClassicalConnectives.TOP, KERuleRole.ANY,
                ClassicalConnectives.AND);

        SubformulaConnectiveForTestRoleGetter getter = new SubformulaConnectiveForTestRoleGetter(pattern_X_TOP_AND,
                ClassicalConnectives.TOP, KERuleRole.OTHER);
        
        assertEquals(sfc.parseString("T A->A->C->A->(A&TOP)"), getter.getSignedFormula(sff,ff,sfl));
        
//        System.err.println(getter.getSignedFormula(sff,ff,sfl));

    }

}