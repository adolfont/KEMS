/*
 * Created on 11/10/2004
 *
 */
package rulesNew;

import junit.framework.TestCase;
import rulesGetters.BinaryConnectiveGetter;
import rulesGetters.UnaryConnectiveGetter;
import signedFormulasNew.SignedFormula;
import signedFormulasNew.SignedFormulaCreator;
import signedFormulasNew.SignedFormulaFactory;
import signedFormulasNew.SignedFormulaList;
import formulasNew.FormulaFactory;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class KERuleTest extends TestCase {

    SignedFormulaCreator sfc = new SignedFormulaCreator();

    SignedFormulaFactory sff = sfc.getSignedFormulaFactory();

    FormulaFactory ff = sfc.getFormulaFactory();

    /**
     *  
     */
    public void test_T_NOT_and_F_NOT() {

        // zeroary connective rules
        // T FALSE -> close
        // T TRUE -> do nothing
        // F TRUE -> close
        // F FALSE -> do nothing

        // unary connective rules
        // T !A -> F A
        // F !A -> T A

        // binary connective rules
        // T A&B -> T A, T B
        // F A&B ->
        //			T A -> F B
        //			T B -> F A
        // 			F A ->
        //			F B ->

        // F AvB -> F A, F B
        // T AvB ->
        //			F A -> T B
        //			F B -> T A
        // 			T A ->
        //			T B ->

        // F A->B -> T A, F B
        // T A->B ->
        //			T A -> T B
        //			F B -> F A
        // 			F A ->
        //			T B ->

        // TODO nary rules
        // nary connective rules
        // T A1&A2&...&An -> T A1, T A2, ..., T An
        // F A1&A2&...&An ->
        //					T Ai -> F A1&...&Ai-1&Ai+1&...&An
        // 					F Ai -> nothing

        // F A1vA2v...vAn -> F A1, F A2, ..., F An
        // T A1vA2v...vAn ->
        //					F Ai -> T A1v...vAi-1vAi+1v...&An
        // 					F Ai -> nothing

        // closing rules (or part of the strategy)
        // T A -> F A -> close (A is atomic)
        // T X -> F X -> close

        // substitution rules (unstable)
        // X \phi(A|B) e.g. T A->A|B, T A |-> T A->\top, T A
        // T A (or T B)
        // |-> X \phi(\top)

        // X \phi(A&B)
        // F A (or F B)
        // |-> X \phi(\bot)

        // X \phi(A->B)
        // F A (or T B)
        // |-> X \phi(\top)

        // Top and bottom rules

        // top
        // X \phi(\top \and A)
        // X \phi(A)

        // X \phi(\top \or A)
        // X \phi(\top)

        // X \phi(\top \impl A)
        // X \phi(A)

        // X \phi(A \impl \top)
        // X \phi(A)

        // X \phi(\not \top)
        // X \phi(\bot)

        // bottom
        // X \phi(\bot \and A)
        // X \phi(\bot)

        // X \phi(\bot \or A)
        // X \phi(A)

        // X \phi(\bot \impl A)
        // X \phi(\top)

        // X \phi(A \impl \bot)
        // X \phi(!A)

        // X \phi(\not \bot)
        // X \phi(\top)

        OnePremiseOneConclusionRule r = Rule.T_NOT;

        SignedFormula sf = sfc.parseString("T !!A"), output;

        SignedFormulaList sfl = new SignedFormulaList();
        sfl.add(sf);

        assertNotNull(UnaryConnectiveGetter.FALSE
                .getSignedFormula(sff, ff, sfl));

        SignedFormula premiss = sf;

        assertEquals(sfc.parseString("F !A"), r.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0));
        premiss = r.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0);
        assertNull(r.getPossibleConclusions(sff, ff, new SignedFormulaList(
                premiss)));

        r = Rule.F_NOT;
        assertEquals(sfc.parseString("T A"), r.getPossibleConclusions(sff, ff,
                new SignedFormulaList(premiss)).get(0));

    }

    // Binary connective rules with only one (main or major) premiss

    public void testBinaryRules_T_AND() {
        OnePremissTwoConclusionsRule r = Rule.T_AND;

        SignedFormula sf = sfc.parseString("T A&B"), output;
        
        assertEquals(sfc.parseString("T A"), BinaryConnectiveGetter.TRUE_LEFT
                .getSignedFormula(sff, ff, new SignedFormulaList(sf)));
        assertEquals(sfc.parseString("T B"), BinaryConnectiveGetter.TRUE_RIGHT
                .getSignedFormula(sff, ff, new SignedFormulaList(sf)));

        assertTrue(r.getPossibleConclusions(sff, ff, new SignedFormulaList(sf))
                .contains(sfc.parseString("T A")));
        assertTrue(r.getPossibleConclusions(sff, ff, new SignedFormulaList(sf))
                .contains(sfc.parseString("T B")));

    }

    public void testBinaryRules_F_OR() {
        OnePremissTwoConclusionsRule r = Rule.F_OR;

        SignedFormula sf = sfc.parseString("F A|B"), output;

        assertEquals(sfc.parseString("F A"), BinaryConnectiveGetter.FALSE_LEFT
                .getSignedFormula(sff, ff, new SignedFormulaList(sf)));
        assertEquals(sfc.parseString("F B"), BinaryConnectiveGetter.FALSE_RIGHT
                .getSignedFormula(sff, ff, new SignedFormulaList(sf)));

        assertTrue(r.getPossibleConclusions(sff, ff, new SignedFormulaList(sf))
                .contains(sfc.parseString("F A")));
        assertTrue(r.getPossibleConclusions(sff, ff, new SignedFormulaList(sf))
                .contains(sfc.parseString("F B")));

    }

    public void testBinaryRules_F_IMPLIES() {
        OnePremissTwoConclusionsRule r = Rule.F_IMPLIES;

        SignedFormula sf = sfc.parseString("F A->B"), output;

        assertEquals(sfc.parseString("T A"), BinaryConnectiveGetter.TRUE_LEFT
                .getSignedFormula(sff, ff, new SignedFormulaList(sf)));
        assertEquals(sfc.parseString("F B"), BinaryConnectiveGetter.FALSE_RIGHT
                .getSignedFormula(sff, ff, new SignedFormulaList(sf)));

        assertTrue(r.getPossibleConclusions(sff, ff, new SignedFormulaList(sf))
                .contains(sfc.parseString("T A")));
        assertTrue(r.getPossibleConclusions(sff, ff, new SignedFormulaList(sf))
                .contains(sfc.parseString("F B")));

    }

    //  Binary rules with only two (major) premiss

    public void testBinaryRules_F_AND() {
        TwoPremisesOneConclusionRule r = Rule.F_AND;

        SignedFormula sfMain = sfc.parseString("F A&B");
        SignedFormula sfAux = sfc.parseString("T A"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("F B"), r.getPossibleConclusions(sff, ff,
                sfl).get(0));

        
        assertEquals(sfc.parseString("T A"), r.getAuxiliaryCandidates(sff, ff, sfMain).get(0));
        assertEquals(sfc.parseString("T B"), r.getAuxiliaryCandidates(sff, ff, sfMain).get(1));
        assertEquals(2, r.getAuxiliaryCandidates(sff, ff, sfMain).size());

        assertTrue(r.matchesMain(sfMain));

    }

    public void testBinaryRules_F_AND_2() {
        TwoPremisesOneConclusionRule r = Rule.F_AND;

        SignedFormula sfMain = sfc.parseString("F A&B");
        SignedFormula sfAux = sfc.parseString("T B"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("F A"), r.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }

    public void testBinaryRules_T_OR() {
        TwoPremisesOneConclusionRule r = Rule.T_OR;

        SignedFormula sfMain = sfc.parseString("T A|B");
        SignedFormula sfAux = sfc.parseString("F A"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("T B"), r.getPossibleConclusions(sff, ff,
                sfl).get(0));

        assertEquals(sfc.parseString("F A"), r.getAuxiliaryCandidates(sff, ff, sfMain).get(0));
        assertEquals(sfc.parseString("F B"), r.getAuxiliaryCandidates(sff, ff, sfMain).get(1));
        assertEquals(2, r.getAuxiliaryCandidates(sff, ff, sfMain).size());

        assertTrue(r.matchesMain(sfMain));
    }

    public void testBinaryRules_T_OR_2() {
        TwoPremisesOneConclusionRule r = Rule.T_OR;

        SignedFormula sfMain = sfc.parseString("T A|B");
        SignedFormula sfAux = sfc.parseString("F B"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("T A"), r.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }

    public void testBinaryRules_T_IMPLIES() {
        TwoPremisesOneConclusionRule r = Rule.T_IMPLIES;

        SignedFormula sfMain = sfc.parseString("T A->B");
        SignedFormula sfAux = sfc.parseString("T A"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("T B"), r.getPossibleConclusions(sff, ff,
                sfl).get(0));

        assertEquals(sfc.parseString("T A"), r.getAuxiliaryCandidates(sff, ff, sfMain).get(0));
        assertEquals(sfc.parseString("F B"), r.getAuxiliaryCandidates(sff, ff, sfMain).get(1));
        assertEquals(2, r.getAuxiliaryCandidates(sff, ff, sfMain).size());

        assertTrue(r.matchesMain(sfMain));
}

    public void testBinaryRules_T_IMPLIES_2() {
        TwoPremisesOneConclusionRule r = Rule.T_IMPLIES;

        SignedFormula sfMain = sfc.parseString("T A->B");
        SignedFormula sfAux = sfc.parseString("F B"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        assertEquals(sfc.parseString("F A"), r.getPossibleConclusions(sff, ff,
                sfl).get(0));
    }
    
    // subs rules
    // TODO getAux...

    public void testSubsRules_X_OR() {
        TwoPremisesOneConclusionRule r = Rule.X_OR;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sfMain = sfc.parseString("T C->(A|B)");
        SignedFormula sfAux = sfc.parseString("T B"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T C->TOP"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));
        sfAux = sfc.parseString("T A");
        sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T C->TOP"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));

        // no conclusion
        sfAux = sfc.parseString("T A|B");
        sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));


        assertTrue(r.matchesMain(sfMain));
}

    public void testSubsRules_X_AND() {
        TwoPremisesOneConclusionRule r = Rule.X_AND;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sfMain = sfc.parseString("T (A&B)->C");
        SignedFormula sfAux = sfc.parseString("F B"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T BOT->C"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));
        sfAux = sfc.parseString("F A");
        sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T BOT->C"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));

        // no conclusion
        sfAux = sfc.parseString("F A&B");
        sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

        assertTrue(r.matchesMain(sfMain));
    }

    public void testSubsRules_X_IMPLIES() {
        TwoPremisesOneConclusionRule r = Rule.X_IMPLIES;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sfMain = sfc.parseString("T D->(A->B)->C");
        SignedFormula sfAux = sfc.parseString("T B"), output;
        SignedFormulaList sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T D->TOP->C"), r.getPossibleConclusions(
                sff, ff, sfl).get(0));
        sfAux = sfc.parseString("F A");
        sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(sfc.parseString("T D->TOP->C"), r.getPossibleConclusions(
                sff, ff, sfl).get(0));

        // no conclusion
        sfMain = sfc.parseString("T D&(A->B)&C");
        sfAux = sfc.parseString("T A->B");
        sfl = new SignedFormulaList(sfMain);
        sfl.add(sfAux);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

        assertTrue(r.matchesMain(sfMain));
    }

    public void testSubsRules_X_TOP_AND() {
        OnePremiseOneConclusionRule r = Rule.X_TOP_AND;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sf = sfc.parseString("T (TOP&A)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T A->C"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));

        // no conclusion
        sf = sfc.parseString("F (TOP|A)&A&B");
        sfl = new SignedFormulaList(sf);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

    }

    public void testSubsRules_X_TOP_OR() {
        OnePremiseOneConclusionRule r = Rule.X_TOP_OR;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sf = sfc.parseString("T (A|TOP)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T TOP->C"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));

        // no conclusion
        sf = sfc.parseString("F (TOP&A)&A&B");
        sfl = new SignedFormulaList(sf);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

    }

    public void testSubsRules_X_TOP_IMPLIES() {
        OnePremiseOneConclusionRule r = Rule.X_TOP_IMPLIES;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();

        SignedFormula sf = sfc.parseString("T D->E->(A->TOP)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T D->E->TOP->C"), r
                .getPossibleConclusions(sff, ff, sfl).get(0));

        sf = sfc.parseString("T D->E->(TOP->A)->C");
        sfl = new SignedFormulaList(sf);

        assertEquals(sfc.parseString("T D->E->A->C"), r.getPossibleConclusions(
                sff, ff, sfl).get(0));

        // no conclusion
        sf = sfc.parseString("F (TOP&A)->A&B");
        sfl = new SignedFormulaList(sf);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

    }

    public void testSubsRules_X_NOT_TOP() {
        OnePremiseOneConclusionRule r = Rule.X_NOT_TOP;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();

        SignedFormula sf = sfc.parseString("T D->E->(A->!TOP)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfl.get(0)));

        assertEquals(sfc.parseString("T D->E->(A->BOT)->C"), r
                .getPossibleConclusions(sff, ff, sfl).get(0));

    }

    // bottom

    public void testSubsRules_X_BOTTOM_AND() {
        OnePremiseOneConclusionRule r = Rule.X_BOTTOM_AND;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sf = sfc.parseString("T (BOT&A)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T BOT->C"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));

        // no conclusion
        sf = sfc.parseString("F (BOT|A)&A&B");
        sfl = new SignedFormulaList(sf);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

    }

    public void testSubsRules_X_BOTTOM_OR() {
        OnePremiseOneConclusionRule r = Rule.X_BOTTOM_OR;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();
        SignedFormula sf = sfc.parseString("T (A|BOT)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T A->C"), r.getPossibleConclusions(sff,
                ff, sfl).get(0));

        // no conclusion
        sf = sfc.parseString("F (BOT&A)&A&B");
        sfl = new SignedFormulaList(sf);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

    }

    public void testSubsRules_X_BOTTOM_IMPLIES() {
        OnePremiseOneConclusionRule r = Rule.X_BOTTOM_IMPLIES;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();

        SignedFormula sf = sfc.parseString("T D->E->(A->BOT)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfMain, sfAux));

        assertEquals(sfc.parseString("T D->E->(!A)->C"), r.getPossibleConclusions(
                sff, ff, sfl).get(0));

        sf = sfc.parseString("T D->E->(BOT->A)->C");
        sfl = new SignedFormulaList(sf);

        assertEquals(sfc.parseString("T D->E->TOP->C"), r
                .getPossibleConclusions(sff, ff, sfl).get(0));

        // no conclusion
        sf = sfc.parseString("F (BOT&A)->A&B");
        sfl = new SignedFormulaList(sf);
        assertEquals(null, r.getPossibleConclusions(sff, ff, sfl));

    }

    public void testSubsRules_X_NOT_BOTTOM() {
        OnePremiseOneConclusionRule r = Rule.X_NOT_BOTTOM;

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        SignedFormulaFactory sff = sfc.getSignedFormulaFactory();
        FormulaFactory ff = sfc.getFormulaFactory();

        SignedFormula sf = sfc.parseString("T D->E->(A->!BOT)->C");
        SignedFormulaList sfl = new SignedFormulaList(sf);

        //        System.out.println(r._premise.matches(sfl.get(0)));

        assertEquals(sfc.parseString("T D->E->(A->TOP)->C"), r
                .getPossibleConclusions(sff, ff, sfl).get(0));

    }

}