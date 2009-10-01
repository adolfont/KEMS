/*
 * Created on 03/12/2004
 *
 */
package rulesNew;

import junit.framework.TestCase;
import ruleStructures.Rules;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import classicalLogic.ClassicalRules;
import formulasNew.FormulaFactory;

/**
 * @author adolfo
 *  
 */
public class RuleTest_BIIMPLIES extends TestCase {
    SignedFormulaCreator sfc = new SignedFormulaCreator("sats4");

    SignedFormulaFactory sff = sfc.getSignedFormulaFactory();

    FormulaFactory ff = sfc.getFormulaFactory();

    Rules KEClassicalRules = new Rules();

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        KEClassicalRules = new Rules();

        KEClassicalRules.put(RuleCode.T_BIIMPLIES_LEFT_TRUE,
                ClassicalRules.T_BIIMPLIES_LEFT_TRUE);
        KEClassicalRules.put(RuleCode.F_BIIMPLIES_LEFT_TRUE,
                ClassicalRules.F_BIIMPLIES_LEFT_TRUE);
        KEClassicalRules.put(RuleCode.T_BIIMPLIES_LEFT_FALSE,
                ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
        KEClassicalRules.put(RuleCode.F_BIIMPLIES_LEFT_FALSE,
                ClassicalRules.F_BIIMPLIES_LEFT_FALSE);
        KEClassicalRules.put(RuleCode.X_BIIMPLIES_T_LEFT,
                ClassicalRules.X_BIIMPLIES_T_LEFT);
        KEClassicalRules.put(RuleCode.X_BIIMPLIES_T_RIGHT,
                ClassicalRules.X_BIIMPLIES_T_RIGHT);
        KEClassicalRules.put(RuleCode.X_BIIMPLIES_F_LEFT,
                ClassicalRules.X_BIIMPLIES_F_LEFT);
        KEClassicalRules.put(RuleCode.X_BIIMPLIES_F_RIGHT,
                ClassicalRules.X_BIIMPLIES_F_RIGHT);

    }

    // rules with biimplies

    public void test_F_BIIMPLIES_LEFT_TRUE() {
        Rule r1 = KEClassicalRules.get(RuleCode.F_BIIMPLIES_LEFT_TRUE);

        SignedFormula sfMain = sfc.parseString("F A<=>B");
        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }

    public void test_T_BIIMPLIES_LEFT_TRUE() {
        Rule r1 = KEClassicalRules.get(RuleCode.T_BIIMPLIES_LEFT_TRUE);

        SignedFormula sfMain = sfc.parseString("T A<=>B");
        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }

    public void test_F_BIIMPLIES_LEFT_FALSE() {
        Rule r1 = KEClassicalRules.get(RuleCode.F_BIIMPLIES_LEFT_FALSE);

        SignedFormula sfMain = sfc.parseString("F A<=>B");
        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }

    public void test_T_BIIMPLIES_LEFT_FALSE() {
        Rule r1 = KEClassicalRules.get(RuleCode.T_BIIMPLIES_LEFT_FALSE);

        SignedFormula sfMain = sfc.parseString("T A<=>B");
        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }

    public void test_X_BIIMPLIES_T_LEFT() {

        aux_test_X_BIIMPLIES_T_LEFT(sfc.parseString("F A&(A<=>B)"), sfc
                .parseString("F A&B"));
        aux_test_X_BIIMPLIES_T_LEFT(sfc.parseString("T (A<=>B)&C"), sfc
                .parseString("T B&C"));
        aux_test_X_BIIMPLIES_T_LEFT(sfc.parseString("T (C&D)->(A<=>B)->(A&C)"),
                sfc.parseString("T (C&D)->B->(A&C)"));
        aux_test_X_BIIMPLIES_T_LEFT(sfc.parseString("T (C&D)->B->(A<=>C)"), sfc
                .parseString("T (C&D)->B->C"));
        aux_test_X_BIIMPLIES_T_LEFT(sfc.parseString("T (A<=>B)|(A<=>B)"), sfc
                .parseString("T B|B"));

    }

    private void aux_test_X_BIIMPLIES_T_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BIIMPLIES_T_LEFT);

        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BIIMPLIES_T_RIGHT() {

        aux_test_X_BIIMPLIES_T_RIGHT(sfc.parseString("F A&(A<=>B)"), sfc
                .parseString("F A&A"));
        aux_test_X_BIIMPLIES_T_RIGHT(sfc.parseString("T (A<=>B)&C"), sfc
                .parseString("T A&C"));
        aux_test_X_BIIMPLIES_T_RIGHT(
                sfc.parseString("T (C&D)->(A<=>B)->(A&C)"), sfc
                        .parseString("T (C&D)->A->(A&C)"));
        aux_test_X_BIIMPLIES_T_RIGHT(sfc.parseString("T (C&D)->B->(A<=>B)"),
                sfc.parseString("T (C&D)->B->A"));
        aux_test_X_BIIMPLIES_T_RIGHT(sfc.parseString("T (A<=>B)|(A<=>B)"), sfc
                .parseString("T A|A"));

    }

    private void aux_test_X_BIIMPLIES_T_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BIIMPLIES_T_RIGHT);

        SignedFormula sfAux = sfc.parseString("T B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BIIMPLIES_F_LEFT() {

        aux_test_X_BIIMPLIES_F_LEFT(sfc.parseString("F A&(A<=>B)"), sfc
                .parseString("F A&!B"));
        aux_test_X_BIIMPLIES_F_LEFT(sfc.parseString("T (A<=>B)&C"), sfc
                .parseString("T !B&C"));
        aux_test_X_BIIMPLIES_F_LEFT(sfc.parseString("T (C&D)->(A<=>B)->(A&C)"),
                sfc.parseString("T (C&D)->!B->(A&C)"));
        aux_test_X_BIIMPLIES_F_LEFT(sfc.parseString("T (C&D)->B->(A<=>C)"), sfc
                .parseString("T (C&D)->B->!C"));
        aux_test_X_BIIMPLIES_F_LEFT(sfc.parseString("T (A<=>B)|(A<=>B)"), sfc
                .parseString("T !B|!B"));

    }

    private void aux_test_X_BIIMPLIES_F_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BIIMPLIES_F_LEFT);

        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BIIMPLIES_F_RIGHT() {

        aux_test_X_BIIMPLIES_F_RIGHT(sfc.parseString("F A&(A<=>B)"), sfc
                .parseString("F A&!A"));
        aux_test_X_BIIMPLIES_F_RIGHT(sfc.parseString("T (A<=>B)&C"), sfc
                .parseString("T !A&C"));
        aux_test_X_BIIMPLIES_F_RIGHT(
                sfc.parseString("T (C&D)->(A<=>B)->(A&C)"), sfc
                        .parseString("T (C&D)->!A->(A&C)"));
        aux_test_X_BIIMPLIES_F_RIGHT(sfc.parseString("T (C&D)->B->(A<=>B)"),
                sfc.parseString("T (C&D)->B->!A"));
        aux_test_X_BIIMPLIES_F_RIGHT(sfc.parseString("T (A<=>B)|(A<=>B)"), sfc
                .parseString("T !A|!A"));

    }

    private void aux_test_X_BIIMPLIES_F_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BIIMPLIES_F_RIGHT);

        SignedFormula sfAux = sfc.parseString("F B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    //    Iterator i = fcpt.getSubformulaLocalReferences(sfAux.getFormula(),
    // sfMain).iterator();
    //  System.out.println(fcpt.getLocalReferences(sfAux.getFormula()));

    //    while (i.hasNext()){
    //        Formula f = (Formula) i.next();
    //        System.err.println(sfl + " " + r.getPossibleConclusions(sff, ff, sfl,
    // f));
    //    };

    //    System.out.println(fcpt.getSubformulaLocalReferences(sfAux.getFormula(),
    // sfMain));
}