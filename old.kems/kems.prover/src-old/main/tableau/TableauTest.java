/*
 * Created on 21/10/2004
 *
 */
package tableau;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

import junit.framework.TestCase;
import problem.Problem;
import ruleStructures.ConnectiveRoleSignRuleList;
import ruleStructures.OnePremiseRuleList;
import ruleStructures.PBRuleList;
import ruleStructures.RuleList;
import ruleStructures.RulesStructure;
import ruleStructures.TopBottomRoleRuleList;
import ruleStructures.TopBottomRuleList;
import rulesNew.KERuleRole;
import rulesNew.Rule;
import signedFormulasNew.SignedFormulaCreator;
import strategy.Strategy;
import strategy.simple.SimpleStrategy;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalRules;
import classicalLogic.ClassicalSigns;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class TableauTest extends TestCase {

    /**
     * Constructor for TableauTest.
     * 
     * @param arg0
     */
    public TableauTest(String arg0) {
        super(arg0);
    }

    public void testTableau() {

        // one premise rules
        OnePremiseRuleList onePremiseRules = new OnePremiseRuleList();

        onePremiseRules.add(ClassicalSigns.TRUE, ClassicalConnectives.NOT,
                ClassicalRules.T_NOT);
        onePremiseRules.add(ClassicalSigns.FALSE, ClassicalConnectives.NOT,
                ClassicalRules.F_NOT);
        onePremiseRules.add(ClassicalSigns.TRUE, ClassicalConnectives.AND,
                ClassicalRules.T_AND);
        onePremiseRules.add(ClassicalSigns.FALSE, ClassicalConnectives.OR,
                ClassicalRules.F_OR);
        onePremiseRules.add(ClassicalSigns.FALSE, ClassicalConnectives.IMPLIES,
                ClassicalRules.F_IMPLIES);

        // top and bottom rules
        TopBottomRuleList topAndBottomRules = new TopBottomRuleList();

        topAndBottomRules.add(ClassicalConnectives.TOP,
                ClassicalConnectives.AND, Rule.X_TOP_AND);
        topAndBottomRules.add(ClassicalConnectives.TOP,
                ClassicalConnectives.OR, Rule.X_TOP_OR);
        topAndBottomRules.add(ClassicalConnectives.TOP,
                ClassicalConnectives.IMPLIES, Rule.X_TOP_IMPLIES);
        topAndBottomRules.add(ClassicalConnectives.TOP,
                ClassicalConnectives.NOT, Rule.X_NOT_TOP);
        topAndBottomRules.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.AND, Rule.X_BOTTOM_AND);
        topAndBottomRules.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.OR, Rule.X_BOTTOM_OR);
        topAndBottomRules.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.IMPLIES, Rule.X_BOTTOM_IMPLIES);
        topAndBottomRules.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.NOT, Rule.X_NOT_BOTTOM);

        TopBottomRoleRuleList topAndBottomRulesNew = new TopBottomRoleRuleList();

        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalRules.X_TOP_AND_LEFT);
        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalRules.X_TOP_AND_RIGHT);
        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalRules.X_TOP_OR_LEFT);
        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalRules.X_TOP_OR_RIGHT);
        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalRules.X_TOP_IMPLIES_LEFT);
        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
                ClassicalRules.X_TOP_IMPLIES_RIGHT);
        topAndBottomRulesNew.add(ClassicalConnectives.TOP,
                ClassicalConnectives.NOT, KERuleRole.LEFT,
                ClassicalRules.X_TOP_NOT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalRules.X_BOTTOM_AND_LEFT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalRules.X_BOTTOM_AND_RIGHT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalRules.X_BOTTOM_OR_LEFT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalRules.X_BOTTOM_OR_RIGHT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalRules.X_BOTTOM_IMPLIES_LEFT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.IMPLIES, KERuleRole.RIGHT,
                ClassicalRules.X_BOTTOM_IMPLIES_RIGHT);
        topAndBottomRulesNew.add(ClassicalConnectives.BOTTOM,
                ClassicalConnectives.NOT, KERuleRole.LEFT,
                ClassicalRules.X_BOTTOM_NOT);

        // two premise rules
        RuleList twoPremiseRules = new RuleList();

        twoPremiseRules.add(Rule.X_AND);
        twoPremiseRules.add(Rule.X_AND_2);
        twoPremiseRules.add(Rule.X_OR);
        twoPremiseRules.add(Rule.X_OR_2);
        twoPremiseRules.add(Rule.X_IMPLIES);
        twoPremiseRules.add(Rule.X_IMPLIES_2);

        RuleList twoPremiseRulesNew = new RuleList();

        twoPremiseRulesNew.add(ClassicalRules.X_AND_F_LEFT);
        twoPremiseRulesNew.add(ClassicalRules.X_AND_F_RIGHT);
        twoPremiseRulesNew.add(ClassicalRules.X_AND_T_LEFT);
        twoPremiseRulesNew.add(ClassicalRules.X_AND_T_RIGHT);
        twoPremiseRulesNew.add(ClassicalRules.X_OR_F_LEFT);
        twoPremiseRulesNew.add(ClassicalRules.X_OR_F_RIGHT);
        twoPremiseRulesNew.add(ClassicalRules.X_OR_T_LEFT);
        twoPremiseRulesNew.add(ClassicalRules.X_OR_T_RIGHT);
        twoPremiseRulesNew.add(ClassicalRules.X_IMPLIES_F_LEFT);
        twoPremiseRulesNew.add(ClassicalRules.X_IMPLIES_F_RIGHT);
        twoPremiseRulesNew.add(ClassicalRules.X_IMPLIES_T_LEFT);
        twoPremiseRulesNew.add(ClassicalRules.X_IMPLIES_T_RIGHT);
        twoPremiseRulesNew.add(ClassicalRules.X_NOT_F);
        twoPremiseRulesNew.add(ClassicalRules.X_NOT_T);

        ConnectiveRoleSignRuleList twoPremiseRulesNewII = new ConnectiveRoleSignRuleList();

        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_AND_F_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.AND, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_AND_T_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalSigns.FALSE, ClassicalRules.X_OR_F_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.OR, KERuleRole.RIGHT,
                ClassicalSigns.TRUE, ClassicalRules.X_OR_T_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_IMPLIES_F_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES,
                KERuleRole.RIGHT, ClassicalSigns.FALSE,
                ClassicalRules.X_IMPLIES_F_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_IMPLIES_T_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.IMPLIES,
                KERuleRole.RIGHT, ClassicalSigns.TRUE,
                ClassicalRules.X_IMPLIES_T_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
                KERuleRole.LEFT, ClassicalSigns.FALSE,
                ClassicalRules.X_BIIMPLIES_F_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
                KERuleRole.RIGHT, ClassicalSigns.FALSE,
                ClassicalRules.X_BIIMPLIES_F_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
                KERuleRole.LEFT, ClassicalSigns.TRUE,
                ClassicalRules.X_BIIMPLIES_T_LEFT);
        twoPremiseRulesNewII.add(ClassicalConnectives.BIIMPLIES,
                KERuleRole.RIGHT, ClassicalSigns.TRUE,
                ClassicalRules.X_BIIMPLIES_T_RIGHT);
        twoPremiseRulesNewII.add(ClassicalConnectives.NOT, KERuleRole.LEFT,
                ClassicalSigns.FALSE, ClassicalRules.X_NOT_F);
        twoPremiseRulesNewII.add(ClassicalConnectives.NOT, KERuleRole.LEFT,
                ClassicalSigns.TRUE, ClassicalRules.X_NOT_T);

        PBRuleList PBRules = new PBRuleList();

        PBRules.add(ClassicalSigns.FALSE, ClassicalConnectives.AND,
                ClassicalRules.F_AND_LEFT);
        PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.OR,
                ClassicalRules.T_OR_LEFT);
        PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.IMPLIES,
                ClassicalRules.T_IMPLIES_LEFT);
        PBRules.add(ClassicalSigns.TRUE, ClassicalConnectives.BIIMPLIES,
                ClassicalRules.T_BIIMPLIES_LEFT_TRUE,
                ClassicalRules.T_BIIMPLIES_LEFT_FALSE);
        PBRules.add(ClassicalSigns.FALSE, ClassicalConnectives.BIIMPLIES,
                ClassicalRules.F_BIIMPLIES_LEFT_TRUE,
                ClassicalRules.F_BIIMPLIES_LEFT_FALSE);

        // the order is important!
        RulesStructure rules = new RulesStructure();
        rules.add("onePremiseRules", onePremiseRules);
        rules.add("topAndBottomRules", topAndBottomRules);
        rules.add("topAndBottomRulesNew", topAndBottomRulesNew);
        rules.add("twoPremiseRules", twoPremiseRules);
        rules.add("twoPremiseRulesNew", twoPremiseRulesNew);
        rules.add("twoPremiseRulesNewII", twoPremiseRulesNewII);
        rules.add("PBRules", PBRules);

        Method m = new Method(rules);
        Strategy s = new AbstractSimpleStrategy(m);
        Prover prover = new Prover();
        prover.setMethod(m);
        prover.setStrategy(s);

        runProblems(prover);
    }

    /**
     * @param sfc
     * @param prover
     */
    private void runProblems(Prover prover) {
        boolean show = true, grava = true;

        // TODO all that he can
        rodaComProblema("pelletier", 1, 8, prover, show, grava);
        rodaComProblema("gamma", 1, 11, prover, show, grava);
        rodaComProblema("gamman", 1, 7, prover, show, grava);
        rodaComProblema("h", 1, 5, prover, show, grava);
        rodaComProblema("hn", 1, 3, prover, show, grava);
        rodaComProblema("statman", 1, 6, prover, show, grava);
        rodaComProblema("statmann", 1, 4, prover, show, grava);
        rodaComProblema("php", 1, 4, prover, show, grava);
        rodaComProblema("phpn", 1, 4, prover, show, grava);
        // TODO only easy
        //      rodaComProblema("gamma", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("gamman", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("h", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("hn", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("statman", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("statmann", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("php", 1, 2, sfc, prover, show, grava);
        //      rodaComProblema("phpn", 1, 2, sfc, prover, show, grava);
        // TODO only difficult
        //        rodaComProblema("gamma", 11, 11, sfc, prover, show);
        //        rodaComProblema("gamman", 7, 7, sfc, prover, show);
        //        rodaComProblema("h", 5, 5, sfc, prover, show);
        //        rodaComProblema("hn", 3, 3, sfc, prover, show);
        //        rodaComProblema("statman", 6, 6, sfc, prover, show);
        //        rodaComProblema("statmann", 4, 4, sfc, prover, show);
        //        rodaComProblema("php", 4, 4, prover, show, true);
        //        rodaComProblema("phpn", 4, 4, prover, show, true);
        // not yet possible - see memory usage
        //        rodaComProblema("php", 5, 6, sfc, prover, show, false);
        //        rodaComProblema("phpn", 5, 6, sfc, prover, show);

    }

    public void rodaComProblema(String nomeProblema, int inicio, int max,
            Prover prover, boolean show, boolean grava) {
        SignedFormulaCreator sfc = new SignedFormulaCreator("sats4");
        for (int i = inicio; i <= max; i++) {
            Problem p = sfc.parseFile(System.getProperty("user.dir")
                    + "/../TPExe/problems/input/wagner/" + nomeProblema + i
                    + ".prove");
            String proofComplexity = "";

            String problemComplexity = generateProblemComplexityString(p,
                    "Problem");
            long beginning = System.currentTimeMillis();

            Proof proof = prover.prove(p);
            long end = System.currentTimeMillis();
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMinimumFractionDigits(6);

            assertTrue(proof.isClosed());
            if (show) {
                proofComplexity = generateProblemComplexityString(p, "Proof");

                System.out.println(nomeProblema + i + " closed? "
                        + proof.isClosed() + " in "
                        + nf.format((end - beginning) / 1000.0) + " s");

            }
            if (grava) {
                gravaNoArquivo(nomeProblema + i, proofComplexity,
                        problemComplexity, beginning, proof, end, nf);
            }

        }
    }

    /**
     * @param nomeProblema
     * @param proofComplexity
     * @param problemComplexity
     * @param beginning
     * @param proof
     * @param end
     * @param nf
     */
    private void gravaNoArquivo(String nomeProblema, String proofComplexity,
            String problemComplexity, long beginning, Proof proof, long end,
            NumberFormat nf) {
        String nomeProblemaCompleto = "Problem: " + nomeProblema
                + System.getProperty("line.separator")
                + System.getProperty("line.separator");

        String textoArquivo = nomeProblemaCompleto;

        textoArquivo = textoArquivo + "Execution time: "
                + nf.format((end - beginning) / 1000.0) + " s"
                + System.getProperty("line.separator")
                + System.getProperty("line.separator");
        textoArquivo = textoArquivo + problemComplexity;
        textoArquivo = textoArquivo + proofComplexity;

        textoArquivo = textoArquivo + proof.showSize()
                + System.getProperty("line.separator");

        textoArquivo = textoArquivo + proof.toString();

        String nomeCompletoArquivo = System.getProperty("user.dir")
                + "/../TPExe/problems/output/wagner/" + nomeProblema + ".proof";
        grava(textoArquivo, nomeCompletoArquivo);
    }

    /**
     * @param p
     * @return
     */
    private String generateProblemComplexityString(Problem p, String name) {
        String result = "";
        result = result + name + " complexity - "
                + System.getProperty("line.separator");
        result = result + "Atomic formulas: "
                + p.getFormulaFactory().getAtomicFormulasSize()
                + System.getProperty("line.separator");
        result = result + "Composite formulas: "
                + p.getFormulaFactory().getCompositeFormulasSize()
                + System.getProperty("line.separator");
        result = result + "Signed formulas: "
                + p.getSignedFormulaFactory().getSize()
                + System.getProperty("line.separator");
        result = result + System.getProperty("line.separator");
        return result;
    }

    /**
     * @param proof
     * @param string
     */
    private void grava(String s, String filename) {
        FileWriter f;
        try {
            f = new FileWriter(filename);
            f.write(s);
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

