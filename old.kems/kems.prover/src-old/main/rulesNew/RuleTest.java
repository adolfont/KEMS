/*
 * Created on 03/12/2004
 *
 */
package rulesNew;

import junit.framework.TestCase;
import proofTree.SignedFormulaNode;
import ruleStructures.Rules;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import strategy.simple.FormulaReferenceClassicalProofTree;
import classicalLogic.ClassicalRules;
import formulasNew.FormulaFactory;

/**
 * @author adolfo
 *  
 */
public class RuleTest extends TestCase {
    SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");

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

        KEClassicalRules.put(RuleCode.T_NOT, ClassicalRules.T_NOT);
        KEClassicalRules.put(RuleCode.F_NOT, ClassicalRules.F_NOT);
        KEClassicalRules.put(RuleCode.X_NOT_T, ClassicalRules.X_NOT_T);
        KEClassicalRules.put(RuleCode.X_NOT_F, ClassicalRules.X_NOT_F);

        KEClassicalRules.put(RuleCode.T_AND, ClassicalRules.T_AND);
        KEClassicalRules.put(RuleCode.F_AND_LEFT, ClassicalRules.F_AND_LEFT);
        KEClassicalRules
                .put(RuleCode.X_AND_T_LEFT, ClassicalRules.X_AND_T_LEFT);
        KEClassicalRules.put(RuleCode.X_AND_T_RIGHT,
                ClassicalRules.X_AND_T_RIGHT);
        KEClassicalRules
                .put(RuleCode.X_AND_F_LEFT, ClassicalRules.X_AND_F_LEFT);
        KEClassicalRules.put(RuleCode.X_AND_F_RIGHT,
                ClassicalRules.X_AND_F_RIGHT);

        KEClassicalRules.put(RuleCode.F_OR, ClassicalRules.F_OR);
        KEClassicalRules.put(RuleCode.T_OR_LEFT, ClassicalRules.T_OR_LEFT);
        KEClassicalRules.put(RuleCode.X_OR_T_LEFT, ClassicalRules.X_OR_T_LEFT);
        KEClassicalRules
                .put(RuleCode.X_OR_T_RIGHT, ClassicalRules.X_OR_T_RIGHT);
        KEClassicalRules.put(RuleCode.X_OR_F_LEFT, ClassicalRules.X_OR_F_LEFT);
        KEClassicalRules
                .put(RuleCode.X_OR_F_RIGHT, ClassicalRules.X_OR_F_RIGHT);

        KEClassicalRules.put(RuleCode.F_IMPLIES, ClassicalRules.F_IMPLIES);
        KEClassicalRules.put(RuleCode.T_IMPLIES_LEFT,
                ClassicalRules.T_IMPLIES_LEFT);
        KEClassicalRules.put(RuleCode.X_IMPLIES_T_LEFT,
                ClassicalRules.X_IMPLIES_T_LEFT);
        KEClassicalRules.put(RuleCode.X_IMPLIES_T_RIGHT,
                ClassicalRules.X_IMPLIES_T_RIGHT);
        KEClassicalRules.put(RuleCode.X_IMPLIES_F_LEFT,
                ClassicalRules.X_IMPLIES_F_LEFT);
        KEClassicalRules.put(RuleCode.X_IMPLIES_F_RIGHT,
                ClassicalRules.X_IMPLIES_F_RIGHT);

        KEClassicalRules.put(RuleCode.X_TOP_AND_LEFT,
                ClassicalRules.X_TOP_AND_LEFT);
    }

    // rules with not
    public void test_T_NOT_and_F_NOT() {
        Rule r1 = KEClassicalRules.get(RuleCode.T_NOT);

        SignedFormula sf = sfc.parseString("T !!A"), output;
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf);
        SignedFormula premiss = sf;
        assertEquals(sfc.parseString("F !A"), r1.getPossibleConclusions(sff,
                ff, new SignedFormulaList(premiss)).get(0));
        premiss = r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0);
        assertNull(r1.getPossibleConclusions(sff, ff, new SignedFormulaList(
                premiss)));

        Rule r2 = KEClassicalRules.get(RuleCode.F_NOT);
        assertEquals(sfc.parseString("T A"), r2.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0));

    }

    public void test_X_NOT_T() {
        Rule r = KEClassicalRules.get(RuleCode.X_NOT_T);
        SignedFormula sfMain = sfc
                .parseString("T (D&E)->(!A)->(!A&(A&(A&!B)))");
        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        FormulaReferenceClassicalProofTree fcpt = new FormulaReferenceClassicalProofTree(
                new SignedFormulaNode(sfMain, null, null));
        fcpt.addLast(new SignedFormulaNode(sfAux, null, null));

        assertEquals(sfc
                .parseString("T ((D&E)->((BOT)->((BOT)&(A&(A&!(B))))))"), r
                .getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_NOT_F() {
        Rule r = KEClassicalRules.get(RuleCode.X_NOT_F);
        SignedFormula sfMain = sfc
                .parseString("T (D&E)->(!A)->(!A&(A&(A&!B)))");
        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        FormulaReferenceClassicalProofTree fcpt = new FormulaReferenceClassicalProofTree(
                new SignedFormulaNode(sfMain, null, null));
        fcpt.addLast(new SignedFormulaNode(sfAux, null, null));

        assertEquals(sfc
                .parseString("T ((D&E)->((TOP)->((TOP)&(A&(A&!(B))))))"), r
                .getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // rules with and
    public void test_T_AND() {
        Rule r1 = KEClassicalRules.get(RuleCode.T_AND);

        SignedFormula sf = sfc.parseString("T A&B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf);
        SignedFormula premiss = sf;
        assertEquals(sfc.parseString("T A"), r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0));
        assertEquals(sfc.parseString("T B"), r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(1));
    }

    public void test_F_AND_LEFT() {
        Rule r1 = KEClassicalRules.get(RuleCode.F_AND_LEFT);

        SignedFormula sfMain = sfc.parseString("F A&B");
        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("F B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
        
//        System.out.println (((TwoPremisesOneConclusionRule) r1).getAuxiliaryCandidates(sff, ff, sfMain));
    }

    public void test_X_AND_T_LEFT() {

        aux_test_X_AND_T_LEFT(sfc.parseString("F A->A&B"), sfc
                .parseString("F A->B"));
        aux_test_X_AND_T_LEFT(sfc.parseString("T A&B->A"), sfc
                .parseString("T B->A"));
        aux_test_X_AND_T_LEFT(sfc.parseString("T (C&D)->(A&B)->(A&C)"), sfc
                .parseString("T (C&D)->B->(A&C)"));
        aux_test_X_AND_T_LEFT(sfc.parseString("T (C&D)->B->(A&C)"), sfc
                .parseString("T (C&D)->B->C"));
        aux_test_X_AND_T_LEFT(sfc.parseString("T (A&B)->(A&B)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_AND_T_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_AND_T_LEFT);

        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_AND_T_RIGHT() {

        aux_test_X_AND_T_RIGHT(sfc.parseString("F A->A&B"), sfc
                .parseString("F A->A"));
        aux_test_X_AND_T_RIGHT(sfc.parseString("T A&B->A"), sfc
                .parseString("T A->A"));
        aux_test_X_AND_T_RIGHT(sfc.parseString("T (C&D)->(A&B)->(A&C)"), sfc
                .parseString("T (C&D)->A->(A&C)"));
        aux_test_X_AND_T_RIGHT(sfc.parseString("T (C&D)->B->(C&B)"), sfc
                .parseString("T (C&D)->B->C"));
        aux_test_X_AND_T_RIGHT(sfc.parseString("T (A&B)->(A&B)"), sfc
                .parseString("T A->A"));

    }

    private void aux_test_X_AND_T_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_AND_T_RIGHT);

        SignedFormula sfAux = sfc.parseString("T B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_AND_F_LEFT() {

        aux_test_X_AND_F_LEFT(sfc.parseString("F A->A&B"), sfc
                .parseString("F A->BOT"));
        aux_test_X_AND_F_LEFT(sfc.parseString("T A&B->A"), sfc
                .parseString("T BOT->A"));
        aux_test_X_AND_F_LEFT(sfc.parseString("T (C&D)->(A&B)->(A&C)"), sfc
                .parseString("T (C&D)->BOT->(A&C)"));
        aux_test_X_AND_F_LEFT(sfc.parseString("T (C&D)->B->(A&C)"), sfc
                .parseString("T (C&D)->B->BOT"));
        aux_test_X_AND_F_LEFT(sfc.parseString("T (A&B)->(A&B)"), sfc
                .parseString("T BOT->BOT"));

    }

    private void aux_test_X_AND_F_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_AND_F_LEFT);

        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_AND_F_RIGHT() {

        aux_test_X_AND_F_RIGHT(sfc.parseString("F A->A&B"), sfc
                .parseString("F A->BOT"));
        aux_test_X_AND_F_RIGHT(sfc.parseString("T A&B->A"), sfc
                .parseString("T BOT->A"));
        aux_test_X_AND_F_RIGHT(sfc.parseString("T (C&D)->(A&B)->(A&C)"), sfc
                .parseString("T (C&D)->BOT->(A&C)"));
        aux_test_X_AND_F_RIGHT(sfc.parseString("T (C&D)->B->(C&B)"), sfc
                .parseString("T (C&D)->B->BOT"));
        aux_test_X_AND_F_RIGHT(sfc.parseString("T (A&B)->(A&B)"), sfc
                .parseString("T BOT->BOT"));

    }

    private void aux_test_X_AND_F_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_AND_F_RIGHT);

        SignedFormula sfAux = sfc.parseString("F B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    // rules with or
    public void test_F_OR() {
        Rule r1 = KEClassicalRules.get(RuleCode.F_OR);

        SignedFormula sf = sfc.parseString("F A|B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf);
        SignedFormula premiss = sf;
        assertEquals(sfc.parseString("F A"), r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0));
        assertEquals(sfc.parseString("F B"), r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(1));
    }

    public void test_T_OR_LEFT() {
        Rule r1 = KEClassicalRules.get(RuleCode.T_OR_LEFT);

        SignedFormula sfMain = sfc.parseString("T A|B");
        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
//      System.out.println (((TwoPremisesOneConclusionRule) r1).getAuxiliaryCandidates(sff, ff, sfMain));
        }

    public void test_X_OR_T_LEFT() {

        aux_test_X_OR_T_LEFT(sfc.parseString("F A->A|B"), sfc
                .parseString("F A->TOP"));
        aux_test_X_OR_T_LEFT(sfc.parseString("T A|B->A"), sfc
                .parseString("T TOP->A"));
        aux_test_X_OR_T_LEFT(sfc.parseString("T (C&D)->(A|B)->(A|C)"), sfc
                .parseString("T (C&D)->TOP->(A|C)"));
        aux_test_X_OR_T_LEFT(sfc.parseString("T (C&D)->B->(A|C)"), sfc
                .parseString("T (C&D)->B->TOP"));
        aux_test_X_OR_T_LEFT(sfc.parseString("T (A|B)->(A|B)"), sfc
                .parseString("T TOP->TOP"));

    }

    private void aux_test_X_OR_T_LEFT(SignedFormula sfMain, SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_OR_T_LEFT);

        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_OR_T_RIGHT() {

        aux_test_X_OR_T_RIGHT(sfc.parseString("F A->A|B"), sfc
                .parseString("F A->TOP"));
        aux_test_X_OR_T_RIGHT(sfc.parseString("T A|B->A"), sfc
                .parseString("T TOP->A"));
        aux_test_X_OR_T_RIGHT(sfc.parseString("T (C|D)->(A|B)->(A|C)"), sfc
                .parseString("T (C|D)->TOP->(A|C)"));
        aux_test_X_OR_T_RIGHT(sfc.parseString("T (C|D)->B->(C|B)"), sfc
                .parseString("T (C|D)->B->TOP"));
        aux_test_X_OR_T_RIGHT(sfc.parseString("T (A|B)->(A|B)"), sfc
                .parseString("T TOP->TOP"));

    }

    private void aux_test_X_OR_T_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_OR_T_RIGHT);

        SignedFormula sfAux = sfc.parseString("T B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_OR_F_LEFT() {

        aux_test_X_OR_F_LEFT(sfc.parseString("F A->A|B"), sfc
                .parseString("F A->B"));
        aux_test_X_OR_F_LEFT(sfc.parseString("T A|B->A"), sfc
                .parseString("T B->A"));
        aux_test_X_OR_F_LEFT(sfc.parseString("T (C|D)->(A|B)->(A|C)"), sfc
                .parseString("T (C|D)->B->(A|C)"));
        aux_test_X_OR_F_LEFT(sfc.parseString("T (C|D)->B->(A|C)"), sfc
                .parseString("T (C|D)->B->C"));
        aux_test_X_OR_F_LEFT(sfc.parseString("T (A|B)->(A|B)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_OR_F_LEFT(SignedFormula sfMain, SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_OR_F_LEFT);

        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_OR_F_RIGHT() {

        aux_test_X_OR_F_RIGHT(sfc.parseString("F A->A|B"), sfc
                .parseString("F A->A"));
        aux_test_X_OR_F_RIGHT(sfc.parseString("T A|B->A"), sfc
                .parseString("T A->A"));
        aux_test_X_OR_F_RIGHT(sfc.parseString("T (C|D)->(A|B)->(A|C)"), sfc
                .parseString("T (C|D)->A->(A|C)"));
        aux_test_X_OR_F_RIGHT(sfc.parseString("T (C|D)->B->(C|B)"), sfc
                .parseString("T (C|D)->B->C"));
        aux_test_X_OR_F_RIGHT(sfc.parseString("T (A|B)->(A|B)"), sfc
                .parseString("T A->A"));

    }

    private void aux_test_X_OR_F_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_OR_F_RIGHT);

        SignedFormula sfAux = sfc.parseString("F B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    // rules with implies
    public void test_F_IMPLIES() {
        Rule r1 = KEClassicalRules.get(RuleCode.F_IMPLIES);

        SignedFormula sf = sfc.parseString("F A->B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf);
        SignedFormula premiss = sf;
        assertEquals(sfc.parseString("T A"), r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0));
        assertEquals(sfc.parseString("F B"), r1.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(1));
    }

    public void test_T_IMPLIES_LEFT() {
        Rule r1 = KEClassicalRules.get(RuleCode.T_IMPLIES_LEFT);

        SignedFormula sfMain = sfc.parseString("T A->B");
        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T B"), r1.getPossibleConclusions(sff, ff,
                sfl).get(0));
//      System.out.println (((TwoPremisesOneConclusionRule) r1).getAuxiliaryCandidates(sff, ff, sfMain));
}

    public void test_X_IMPLIES_T_LEFT() {

        aux_test_X_IMPLIES_T_LEFT(sfc.parseString("F A&(A->B)"), sfc
                .parseString("F A&B"));
        aux_test_X_IMPLIES_T_LEFT(sfc.parseString("T (A->B)&C"), sfc
                .parseString("T B&C"));
        aux_test_X_IMPLIES_T_LEFT(sfc.parseString("T (C&D)->(A->B)->(A&C)"),
                sfc.parseString("T (C&D)->B->(A&C)"));
        aux_test_X_IMPLIES_T_LEFT(sfc.parseString("T (C&D)->B->(A->C)"), sfc
                .parseString("T (C&D)->B->C"));
        aux_test_X_IMPLIES_T_LEFT(sfc.parseString("T (A->B)|(A->B)"), sfc
                .parseString("T B|B"));

    }

    private void aux_test_X_IMPLIES_T_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_IMPLIES_T_LEFT);

        SignedFormula sfAux = sfc.parseString("T A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_IMPLIES_T_RIGHT() {

        aux_test_X_IMPLIES_T_RIGHT(sfc.parseString("F A&(A->B)"), sfc
                .parseString("F A&TOP"));
        aux_test_X_IMPLIES_T_RIGHT(sfc.parseString("T (A->B)&C"), sfc
                .parseString("T TOP&C"));
        aux_test_X_IMPLIES_T_RIGHT(sfc.parseString("T (C&D)->(A->B)->(A&C)"),
                sfc.parseString("T (C&D)->TOP->(A&C)"));
        aux_test_X_IMPLIES_T_RIGHT(sfc.parseString("T (C&D)->B->(A->B)"), sfc
                .parseString("T (C&D)->B->TOP"));
        aux_test_X_IMPLIES_T_RIGHT(sfc.parseString("T (A->B)|(A->B)"), sfc
                .parseString("T TOP|TOP"));

    }

    private void aux_test_X_IMPLIES_T_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_IMPLIES_T_RIGHT);

        SignedFormula sfAux = sfc.parseString("T B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_IMPLIES_F_LEFT() {

        aux_test_X_IMPLIES_F_LEFT(sfc.parseString("F A&(A->B)"), sfc
                .parseString("F A&TOP"));
        aux_test_X_IMPLIES_F_LEFT(sfc.parseString("T (A->B)&C"), sfc
                .parseString("T TOP&C"));
        aux_test_X_IMPLIES_F_LEFT(sfc.parseString("T (C&D)->(A->B)->(A&C)"),
                sfc.parseString("T (C&D)->TOP->(A&C)"));
        aux_test_X_IMPLIES_F_LEFT(sfc.parseString("T (C&D)->B->(A->C)"), sfc
                .parseString("T (C&D)->B->TOP"));
        aux_test_X_IMPLIES_F_LEFT(sfc.parseString("T (A->B)|(A->B)"), sfc
                .parseString("T TOP|TOP"));

    }

    private void aux_test_X_IMPLIES_F_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_IMPLIES_F_LEFT);

        SignedFormula sfAux = sfc.parseString("F A");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
    }

    public void test_X_IMPLIES_F_RIGHT() {

        aux_test_X_IMPLIES_F_RIGHT(sfc.parseString("F A&(A->B)"), sfc
                .parseString("F A&!A"));
        aux_test_X_IMPLIES_F_RIGHT(sfc.parseString("T (A->B)&C"), sfc
                .parseString("T !A&C"));
        aux_test_X_IMPLIES_F_RIGHT(sfc.parseString("T (C&D)->(A->B)->(A&C)"),
                sfc.parseString("T (C&D)->!A->(A&C)"));
        aux_test_X_IMPLIES_F_RIGHT(sfc.parseString("T (C&D)->B->(A->B)"), sfc
                .parseString("T (C&D)->B->!A"));
        aux_test_X_IMPLIES_F_RIGHT(sfc.parseString("T (A->B)|(A->B)"), sfc
                .parseString("T !A|!A"));

    }

    private void aux_test_X_IMPLIES_F_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_IMPLIES_F_RIGHT);

        SignedFormula sfAux = sfc.parseString("F B");
        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);
        sfl.add(sfAux);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(
                0));
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