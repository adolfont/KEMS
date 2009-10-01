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
public class RuleTest_TOP_BOTTOM extends TestCase {

    SignedFormulaCreator sfc = new SignedFormulaCreator("sats5",
            "/home/kurumin/TableauProver/lib/generated/", true);

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

        KEClassicalRules.put(RuleCode.X_TOP_AND_LEFT,
                ClassicalRules.X_TOP_AND_LEFT);
        KEClassicalRules.put(RuleCode.X_TOP_AND_RIGHT,
                ClassicalRules.X_TOP_AND_RIGHT);

        KEClassicalRules.put(RuleCode.X_TOP_OR_LEFT,
                ClassicalRules.X_TOP_OR_LEFT);
        KEClassicalRules.put(RuleCode.X_TOP_OR_RIGHT,
                ClassicalRules.X_TOP_OR_RIGHT);

        KEClassicalRules.put(RuleCode.X_TOP_IMPLIES_LEFT,
                ClassicalRules.X_TOP_IMPLIES_LEFT);
        KEClassicalRules.put(RuleCode.X_TOP_IMPLIES_RIGHT,
                ClassicalRules.X_TOP_IMPLIES_RIGHT);

        KEClassicalRules.put(RuleCode.X_TOP_BIIMPLIES_LEFT,
                ClassicalRules.X_TOP_BIIMPLIES_LEFT);
        KEClassicalRules.put(RuleCode.X_TOP_BIIMPLIES_RIGHT,
                ClassicalRules.X_TOP_BIIMPLIES_RIGHT);

        KEClassicalRules.put(RuleCode.X_TOP_XOR_LEFT,
                ClassicalRules.X_TOP_XOR_LEFT);
        KEClassicalRules.put(RuleCode.X_TOP_XOR_RIGHT,
                ClassicalRules.X_TOP_XOR_RIGHT);

        KEClassicalRules.put(RuleCode.X_TOP_NOT, ClassicalRules.X_TOP_NOT);

        KEClassicalRules.put(RuleCode.X_BOTTOM_OR_LEFT,
                ClassicalRules.X_BOTTOM_OR_LEFT);
        KEClassicalRules.put(RuleCode.X_BOTTOM_OR_RIGHT,
                ClassicalRules.X_BOTTOM_OR_RIGHT);

        KEClassicalRules.put(RuleCode.X_BOTTOM_AND_LEFT,
                ClassicalRules.X_BOTTOM_AND_LEFT);
        KEClassicalRules.put(RuleCode.X_BOTTOM_AND_RIGHT,
                ClassicalRules.X_BOTTOM_AND_RIGHT);

        KEClassicalRules.put(RuleCode.X_BOTTOM_IMPLIES_LEFT,
                ClassicalRules.X_BOTTOM_IMPLIES_LEFT);
        KEClassicalRules.put(RuleCode.X_BOTTOM_IMPLIES_RIGHT,
                ClassicalRules.X_BOTTOM_IMPLIES_RIGHT);

        KEClassicalRules.put(RuleCode.X_BOTTOM_BIIMPLIES_LEFT,
                ClassicalRules.X_BOTTOM_BIIMPLIES_LEFT);
        KEClassicalRules.put(RuleCode.X_BOTTOM_BIIMPLIES_RIGHT,
                ClassicalRules.X_BOTTOM_BIIMPLIES_RIGHT);

        KEClassicalRules.put(RuleCode.X_BOTTOM_XOR_LEFT,
                ClassicalRules.X_BOTTOM_XOR_LEFT);
        KEClassicalRules.put(RuleCode.X_BOTTOM_XOR_RIGHT,
                ClassicalRules.X_BOTTOM_XOR_RIGHT);

        KEClassicalRules
                .put(RuleCode.X_BOTTOM_NOT, ClassicalRules.X_BOTTOM_NOT);

    }

    // rules with top and bottom

    public void test_X_TOP_AND_LEFT() {

        aux_test_X_TOP_AND_LEFT(sfc.parseString("F A->(TOP&B)"), sfc
                .parseString("F A->B"));
        aux_test_X_TOP_AND_LEFT(sfc.parseString("T TOP&B->A"), sfc
                .parseString("T B->A"));
        aux_test_X_TOP_AND_LEFT(sfc.parseString("T (C&D)->(TOP&B)->(A&C)"), sfc
                .parseString("T (C&D)->B->(A&C)"));
        aux_test_X_TOP_AND_LEFT(sfc.parseString("T (C&D)->B->(TOP&C)"), sfc
                .parseString("T (C&D)->B->C"));
        aux_test_X_TOP_AND_LEFT(sfc.parseString("T (TOP&B)->(TOP&B)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_TOP_AND_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_AND_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_TOP_AND_RIGHT() {

        aux_test_X_TOP_AND_RIGHT(sfc.parseString("F A->(B&TOP)"), sfc
                .parseString("F A->B"));
        aux_test_X_TOP_AND_RIGHT(sfc.parseString("T B&TOP->A"), sfc
                .parseString("T B->A"));
        aux_test_X_TOP_AND_RIGHT(sfc.parseString("T (C&D)->(B&TOP)->(A&C)"),
                sfc.parseString("T (C&D)->B->(A&C)"));
        aux_test_X_TOP_AND_RIGHT(sfc.parseString("T (C&D)->B->(C&TOP)"), sfc
                .parseString("T (C&D)->B->C"));
        aux_test_X_TOP_AND_RIGHT(sfc.parseString("T (B&TOP)->(B&TOP)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_TOP_AND_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_AND_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////
    public void test_X_TOP_OR_LEFT() {

        aux_test_X_TOP_OR_LEFT(sfc.parseString("F A->(TOP|B)"), sfc
                .parseString("F A->TOP"));
        aux_test_X_TOP_OR_LEFT(sfc.parseString("T TOP|B->A"), sfc
                .parseString("T TOP->A"));
        aux_test_X_TOP_OR_LEFT(sfc.parseString("T (C|D)->(TOP|B)->(A|C)"), sfc
                .parseString("T (C|D)->TOP->(A|C)"));
        aux_test_X_TOP_OR_LEFT(sfc.parseString("T (C|D)->B->(TOP|C)"), sfc
                .parseString("T (C|D)->B->TOP"));
        aux_test_X_TOP_OR_LEFT(sfc.parseString("T (TOP|B)->(TOP|B)"), sfc
                .parseString("T TOP->TOP"));

    }

    private void aux_test_X_TOP_OR_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_OR_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_TOP_OR_RIGHT() {

        aux_test_X_TOP_OR_RIGHT(sfc.parseString("F A->(B|TOP)"), sfc
                .parseString("F A->TOP"));
        aux_test_X_TOP_OR_RIGHT(sfc.parseString("T B|TOP->A"), sfc
                .parseString("T TOP->A"));
        aux_test_X_TOP_OR_RIGHT(sfc.parseString("T (C|D)->(B|TOP)->(A|C)"), sfc
                .parseString("T (C|D)->TOP->(A|C)"));
        aux_test_X_TOP_OR_RIGHT(sfc.parseString("T (C|D)->B->(C|TOP)"), sfc
                .parseString("T (C|D)->B->TOP"));
        aux_test_X_TOP_OR_RIGHT(sfc.parseString("T (B|TOP)->(B|TOP)"), sfc
                .parseString("T TOP->TOP"));

    }

    private void aux_test_X_TOP_OR_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_OR_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////
    public void test_X_TOP_IMPLIES_LEFT() {

        aux_test_X_TOP_IMPLIES_LEFT(sfc.parseString("F A->(TOP->B)"), sfc
                .parseString("F A->B"));
        aux_test_X_TOP_IMPLIES_LEFT(sfc.parseString("T TOP->B->A"), sfc
                .parseString("T B->A"));
        aux_test_X_TOP_IMPLIES_LEFT(sfc
                .parseString("T (C->D)->(TOP->B)->(A->C)"), sfc
                .parseString("T (C->D)->B->(A->C)"));
        aux_test_X_TOP_IMPLIES_LEFT(sfc.parseString("T (C->D)->B->(TOP->C)"),
                sfc.parseString("T (C->D)->B->C"));
        aux_test_X_TOP_IMPLIES_LEFT(sfc.parseString("T (TOP->B)->(TOP->B)"),
                sfc.parseString("T B->B"));

    }

    private void aux_test_X_TOP_IMPLIES_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_IMPLIES_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_TOP_IMPLIES_RIGHT() {

        aux_test_X_TOP_IMPLIES_RIGHT(sfc.parseString("F A->(B->TOP)"), sfc
                .parseString("F A->TOP"));
        aux_test_X_TOP_IMPLIES_RIGHT(sfc.parseString("T (B->TOP)->A"), sfc
                .parseString("T TOP->A"));
        // Obs.: "T B->TOP->A" == "B->(TOP->A)"
        aux_test_X_TOP_IMPLIES_RIGHT(sfc
                .parseString("T (C->D)->(B->TOP)->(A->C)"), sfc
                .parseString("T (C->D)->TOP->(A->C)"));
        aux_test_X_TOP_IMPLIES_RIGHT(sfc.parseString("T (C->D)->B->(C->TOP)"),
                sfc.parseString("T (C->D)->B->TOP"));
        aux_test_X_TOP_IMPLIES_RIGHT(sfc.parseString("T (B->TOP)->(B->TOP)"),
                sfc.parseString("T TOP->TOP"));

    }

    private void aux_test_X_TOP_IMPLIES_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_IMPLIES_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////
    public void test_X_TOP_BIIMPLIES_LEFT() {

        aux_test_X_TOP_BIIMPLIES_LEFT(sfc.parseString("F A->(TOP<=>B)"), sfc
                .parseString("F A->B"));
        aux_test_X_TOP_BIIMPLIES_LEFT(sfc.parseString("T TOP<=>B->A"), sfc
                .parseString("T B->A"));
        aux_test_X_TOP_BIIMPLIES_LEFT(sfc
                .parseString("T (C->D)->(TOP<=>B)->(A->C)"), sfc
                .parseString("T (C->D)->B->(A->C)"));
        aux_test_X_TOP_BIIMPLIES_LEFT(
                sfc.parseString("T (C->D)->B->(TOP<=>C)"), sfc
                        .parseString("T (C->D)->B->C"));
        aux_test_X_TOP_BIIMPLIES_LEFT(
                sfc.parseString("T (TOP<=>B)->(TOP<=>B)"), sfc
                        .parseString("T B->B"));

    }

    private void aux_test_X_TOP_BIIMPLIES_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_BIIMPLIES_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_TOP_BIIMPLIES_RIGHT() {

        aux_test_X_TOP_BIIMPLIES_RIGHT(sfc.parseString("F A->(B<=>TOP)"), sfc
                .parseString("F A->B"));
        aux_test_X_TOP_BIIMPLIES_RIGHT(sfc.parseString("T (B<=>TOP)->A"), sfc
                .parseString("T B->A"));
        // Obs.: "T B->TOP->A" == "B->(TOP->A)"
        aux_test_X_TOP_BIIMPLIES_RIGHT(sfc
                .parseString("T (C->D)->(B<=>TOP)->(A->C)"), sfc
                .parseString("T (C->D)->B->(A->C)"));
        aux_test_X_TOP_BIIMPLIES_RIGHT(sfc
                .parseString("T (C->D)->B->(C<=>TOP)"), sfc
                .parseString("T (C->D)->B->C"));
        aux_test_X_TOP_BIIMPLIES_RIGHT(sfc
                .parseString("T (B<=>TOP)->(B<=>TOP)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_TOP_BIIMPLIES_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_BIIMPLIES_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /

    public void test_X_TOP_XOR_LEFT() {

        aux_test_X_TOP_XOR_LEFT(sfc.parseString("F A->(TOP+B)"), sfc
                .parseString("F A->!B"));
        aux_test_X_TOP_XOR_LEFT(sfc.parseString("T TOP+(B->A)"), sfc
                .parseString("T !(B->A)"));
        aux_test_X_TOP_XOR_LEFT(sfc.parseString("T (C->D)->(TOP+B)->(A->C)"),
                sfc.parseString("T (C->D)->!B->(A->C)"));
        aux_test_X_TOP_XOR_LEFT(sfc.parseString("T (C->D)->B->(TOP+C)"), sfc
                .parseString("T (C->D)->B->!C"));
        aux_test_X_TOP_XOR_LEFT(sfc.parseString("T (TOP+B)->(TOP+B)"), sfc
                .parseString("T !B->!B"));

    }

    private void aux_test_X_TOP_XOR_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_XOR_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_TOP_XOR_RIGHT() {

        aux_test_X_TOP_XOR_RIGHT(sfc.parseString("F A->(B+TOP)"), sfc
                .parseString("F A->!B"));
        aux_test_X_TOP_XOR_RIGHT(sfc.parseString("T (B+TOP)->A"), sfc
                .parseString("T !B->A"));
        // Obs.: "T B->TOP->A" == "B->(TOP->A)"
        aux_test_X_TOP_XOR_RIGHT(sfc.parseString("T (C->D)->(B+TOP)->(A->C)"),
                sfc.parseString("T (C->D)->!B->(A->C)"));
        aux_test_X_TOP_XOR_RIGHT(sfc.parseString("T (C->D)->B->(C+TOP)"), sfc
                .parseString("T (C->D)->B->!C"));
        aux_test_X_TOP_XOR_RIGHT(sfc.parseString("T (B+TOP)->(B+TOP)"), sfc
                .parseString("T !B->!B"));

    }

    private void aux_test_X_TOP_XOR_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_XOR_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // //

    public void test_X_TOP_NOT() {

        aux_test_X_TOP_NOT(sfc.parseString("F A->(!TOP)"), sfc
                .parseString("F A->BOT"));
        aux_test_X_TOP_NOT(sfc.parseString("T !TOP->A"), sfc
                .parseString("T BOT->A"));
        aux_test_X_TOP_NOT(sfc.parseString("T (C&D)->(!TOP)->(A&C)"), sfc
                .parseString("T (C&D)->BOT->(A&C)"));
        aux_test_X_TOP_NOT(sfc.parseString("T (C&D)->B->(C&!TOP)"), sfc
                .parseString("T (C&D)->B->(C&BOT)"));
        aux_test_X_TOP_NOT(sfc.parseString("T (B&!TOP)->(B&!TOP)"), sfc
                .parseString("T (B&BOT)->(B&BOT)"));

    }

    private void aux_test_X_TOP_NOT(SignedFormula sfMain, SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_TOP_NOT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////

    public void test_X_BOTTOM_OR_LEFT() {

        aux_test_X_BOTTOM_OR_LEFT(sfc.parseString("F A->(BOT|B)"), sfc
                .parseString("F A->B"));
        aux_test_X_BOTTOM_OR_LEFT(sfc.parseString("T BOT|B->A"), sfc
                .parseString("T B->A"));
        aux_test_X_BOTTOM_OR_LEFT(sfc.parseString("T (C|D)->(BOT|B)->(A|C)"),
                sfc.parseString("T (C|D)->B->(A|C)"));
        aux_test_X_BOTTOM_OR_LEFT(sfc.parseString("T (C|D)->B->(BOT|C)"), sfc
                .parseString("T (C|D)->B->C"));
        aux_test_X_BOTTOM_OR_LEFT(sfc.parseString("T (BOT|B)->(BOT|B)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_BOTTOM_OR_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_OR_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        // System.err.println(sfl + " " + r1.getPossibleConclusions(sff, ff,
        // sfl, null) + " " + result);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BOTTOM_OR_RIGHT() {

        aux_test_X_BOTTOM_OR_RIGHT(sfc.parseString("F A->(B|BOT)"), sfc
                .parseString("F A->B"));
        aux_test_X_BOTTOM_OR_RIGHT(sfc.parseString("T B|BOT->A"), sfc
                .parseString("T B->A"));
        aux_test_X_BOTTOM_OR_RIGHT(sfc.parseString("T (C|D)->(B|BOT)->(A|C)"),
                sfc.parseString("T (C|D)->B->(A|C)"));
        aux_test_X_BOTTOM_OR_RIGHT(sfc.parseString("T (C|D)->B->(C|BOT)"), sfc
                .parseString("T (C|D)->B->C"));
        aux_test_X_BOTTOM_OR_RIGHT(sfc.parseString("T (B|BOT)->(B|BOT)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_BOTTOM_OR_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_OR_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // //////////////////////////////////
    public void test_X_BOTTOM_AND_LEFT() {

        aux_test_X_BOTTOM_AND_LEFT(sfc.parseString("F A->(BOT&B)"), sfc
                .parseString("F A->BOT"));
        aux_test_X_BOTTOM_AND_LEFT(sfc.parseString("T BOT&B->A"), sfc
                .parseString("T BOT->A"));
        aux_test_X_BOTTOM_AND_LEFT(sfc.parseString("T (C&D)->(BOT&B)->(A&C)"),
                sfc.parseString("T (C&D)->BOT->(A&C)"));
        aux_test_X_BOTTOM_AND_LEFT(sfc.parseString("T (C&D)->B->(BOT&C)"), sfc
                .parseString("T (C&D)->B->BOT"));
        aux_test_X_BOTTOM_AND_LEFT(sfc.parseString("T (BOT&B)->(BOT&B)"), sfc
                .parseString("T BOT->BOT"));

    }

    private void aux_test_X_BOTTOM_AND_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_AND_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BOTTOM_AND_RIGHT() {

        aux_test_X_BOTTOM_AND_RIGHT(sfc.parseString("F A->(B&BOT)"), sfc
                .parseString("F A->BOT"));
        aux_test_X_BOTTOM_AND_RIGHT(sfc.parseString("T B&BOT->A"), sfc
                .parseString("T BOT->A"));
        aux_test_X_BOTTOM_AND_RIGHT(sfc.parseString("T (C&D)->(B&BOT)->(A&C)"),
                sfc.parseString("T (C&D)->BOT->(A&C)"));
        aux_test_X_BOTTOM_AND_RIGHT(sfc.parseString("T (C&D)->B->(C&BOT)"), sfc
                .parseString("T (C&D)->B->BOT"));
        aux_test_X_BOTTOM_AND_RIGHT(sfc.parseString("T (B&BOT)->(B&BOT)"), sfc
                .parseString("T BOT->BOT"));

    }

    private void aux_test_X_BOTTOM_AND_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_AND_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////
    public void test_X_BOTTOM_IMPLIES_LEFT() {

        aux_test_X_BOTTOM_IMPLIES_LEFT(sfc.parseString("F A->(BOT->B)"), sfc
                .parseString("F A->TOP"));
        aux_test_X_BOTTOM_IMPLIES_LEFT(sfc.parseString("T (BOT->B)->A"), sfc
                .parseString("T TOP->A"));
        aux_test_X_BOTTOM_IMPLIES_LEFT(sfc
                .parseString("T (C->D)->(BOT->B)->(A->C)"), sfc
                .parseString("T (C->D)->TOP->(A->C)"));
        aux_test_X_BOTTOM_IMPLIES_LEFT(
                sfc.parseString("T (C->D)->B->(BOT->C)"), sfc
                        .parseString("T (C->D)->B->TOP"));
        aux_test_X_BOTTOM_IMPLIES_LEFT(sfc.parseString("T (BOT->B)->(BOT->B)"),
                sfc.parseString("T TOP->TOP"));

    }

    private void aux_test_X_BOTTOM_IMPLIES_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_IMPLIES_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BOTTOM_IMPLIES_RIGHT() {

        aux_test_X_BOTTOM_IMPLIES_RIGHT(sfc.parseString("F A->(B->BOT)"), sfc
                .parseString("F A->!B"));
        aux_test_X_BOTTOM_IMPLIES_RIGHT(sfc.parseString("T (B->BOT)->A"), sfc
                .parseString("T !B->A"));
        // Obs.: "T B->BOT->A" == "B->(BOT->A)"
        aux_test_X_BOTTOM_IMPLIES_RIGHT(sfc
                .parseString("T (C->D)->(B->BOT)->(A->C)"), sfc
                .parseString("T (C->D)->!B->(A->C)"));
        aux_test_X_BOTTOM_IMPLIES_RIGHT(sfc
                .parseString("T (C->D)->B->(C->BOT)"), sfc
                .parseString("T (C->D)->B->!C"));
        aux_test_X_BOTTOM_IMPLIES_RIGHT(
                sfc.parseString("T (B->BOT)->(B->BOT)"), sfc
                        .parseString("T !B->!B"));

    }

    private void aux_test_X_BOTTOM_IMPLIES_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_IMPLIES_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////
    public void test_X_BOTTOM_BIIMPLIES_LEFT() {

        aux_test_X_BOTTOM_BIIMPLIES_LEFT(sfc.parseString("F A->(BOT<=>B)"), sfc
                .parseString("F A->!B"));
        aux_test_X_BOTTOM_BIIMPLIES_LEFT(sfc.parseString("T (BOT<=>B)->A"), sfc
                .parseString("T !B->A"));
        aux_test_X_BOTTOM_BIIMPLIES_LEFT(sfc
                .parseString("T (C->D)->(BOT<=>B)->(A->C)"), sfc
                .parseString("T (C->D)->!B->(A->C)"));
        aux_test_X_BOTTOM_BIIMPLIES_LEFT(sfc
                .parseString("T (C->D)->B->(BOT<=>C)"), sfc
                .parseString("T (C->D)->B->!C"));
        aux_test_X_BOTTOM_BIIMPLIES_LEFT(sfc
                .parseString("T (BOT<=>B)->(BOT<=>B)"), sfc
                .parseString("T !B->!B"));

    }

    private void aux_test_X_BOTTOM_BIIMPLIES_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_BIIMPLIES_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BOTTOM_BIIMPLIES_RIGHT() {

        aux_test_X_BOTTOM_BIIMPLIES_RIGHT(sfc.parseString("F A->(B<=>BOT)"),
                sfc.parseString("F A->!B"));
        aux_test_X_BOTTOM_BIIMPLIES_RIGHT(sfc.parseString("T (B<=>BOT)->A"),
                sfc.parseString("T !B->A"));
        // Obs.: "T B->BOT->A" == "B->(BOT->A)"
        aux_test_X_BOTTOM_BIIMPLIES_RIGHT(sfc
                .parseString("T (C->D)->(B<=>BOT)->(A->C)"), sfc
                .parseString("T (C->D)->!B->(A->C)"));
        aux_test_X_BOTTOM_BIIMPLIES_RIGHT(sfc
                .parseString("T (C->D)->B->(C<=>BOT)"), sfc
                .parseString("T (C->D)->B->!C"));
        aux_test_X_BOTTOM_BIIMPLIES_RIGHT(sfc
                .parseString("T (B<=>BOT)->(B<=>BOT)"), sfc
                .parseString("T !B->!B"));

    }

    private void aux_test_X_BOTTOM_BIIMPLIES_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_BIIMPLIES_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // /////////////////////
    public void test_X_BOTTOM_XOR_LEFT() {

        aux_test_X_BOTTOM_XOR_LEFT(sfc.parseString("F A->(BOT+B)"), sfc
                .parseString("F A->B"));
        aux_test_X_BOTTOM_XOR_LEFT(sfc.parseString("T (BOT+B)->A"), sfc
                .parseString("T B->A"));
        aux_test_X_BOTTOM_XOR_LEFT(
                sfc.parseString("T (C->D)->(BOT+B)->(A->C)"), sfc
                        .parseString("T (C->D)->B->(A->C)"));
        aux_test_X_BOTTOM_XOR_LEFT(sfc.parseString("T (C->D)->B->(BOT+C)"), sfc
                .parseString("T (C->D)->B->C"));
        aux_test_X_BOTTOM_XOR_LEFT(sfc.parseString("T (BOT+B)->(BOT+B)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_BOTTOM_XOR_LEFT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_XOR_LEFT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    public void test_X_BOTTOM_XOR_RIGHT() {

        aux_test_X_BOTTOM_XOR_RIGHT(sfc.parseString("F A->(B+BOT)"), sfc
                .parseString("F A->B"));
        aux_test_X_BOTTOM_XOR_RIGHT(sfc.parseString("T (B+BOT)->A"), sfc
                .parseString("T B->A"));
        // Obs.: "T B->BOT->A" == "B->(BOT->A)"
        aux_test_X_BOTTOM_XOR_RIGHT(sfc
                .parseString("T (C->D)->(B+BOT)->(A->C)"), sfc
                .parseString("T (C->D)->B->(A->C)"));
        aux_test_X_BOTTOM_XOR_RIGHT(sfc.parseString("T (C->D)->B->(C+BOT)"),
                sfc.parseString("T (C->D)->B->C"));
        aux_test_X_BOTTOM_XOR_RIGHT(sfc.parseString("T (B+BOT)->(B+BOT)"), sfc
                .parseString("T B->B"));

    }

    private void aux_test_X_BOTTOM_XOR_RIGHT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_XOR_RIGHT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

    // //
    public void test_X_BOTTOM_NOT() {

        aux_test_X_BOTTOM_NOT(sfc.parseString("F A->(!BOT)"), sfc
                .parseString("F A->TOP"));
        aux_test_X_BOTTOM_NOT(sfc.parseString("T !BOT->A"), sfc
                .parseString("T TOP->A"));
        aux_test_X_BOTTOM_NOT(sfc.parseString("T (C&D)->(!BOT)->(A&C)"), sfc
                .parseString("T (C&D)->TOP->(A&C)"));
        aux_test_X_BOTTOM_NOT(sfc.parseString("T (C&D)->B->(C&!BOT)"), sfc
                .parseString("T (C&D)->B->(C&TOP)"));
        aux_test_X_BOTTOM_NOT(sfc.parseString("T (B&!BOT)->(B&!BOT)"), sfc
                .parseString("T (B&TOP)->(B&TOP)"));

    }

    private void aux_test_X_BOTTOM_NOT(SignedFormula sfMain,
            SignedFormula result) {
        Rule r1 = KEClassicalRules.get(RuleCode.X_BOTTOM_NOT);

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sfMain);

        assertEquals(result, r1.getPossibleConclusions(sff, ff, sfl).get(0));
    }

}