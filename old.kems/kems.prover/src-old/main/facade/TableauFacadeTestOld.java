/*
 * Created on 14/12/2004
 *
 */
package facade;

import junit.framework.TestCase;
import problem.ProblemType;
import ruleStructures.RulesStructure;
import strategy.Strategy;
import strategy.simple.SimpleStrategy;
import tableau.Method;
import tableau.Prover;
import classicalLogic.ClassicalRuleStructures;

/**
 * @author adolfo
 *  
 */
public class TableauFacadeTestOld extends TestCase {
    public void testCreationAndExecution() {

        RulesStructure rules = ConfigurableClassicalRuleStructures.KEKESubstitionRulesWithBiimplication
                .getRuleStructure();
        Method m = new Method(rules);
        Strategy s = new AbstractSimpleStrategy(m);
        Prover prover = new Prover();
        prover.setMethod(m);
        prover.setStrategy(s);

        TableauFacade tf = new TableauFacade(prover);
        tf.setShowSummary(true);
        tf.setSaveProof(false);
        //        tf.runProblemSequence("php", 1, 1);
        //        String proof = tf.prove ("php4");
        //        System.err.println(proof);
        //        tf.setProblemsInputDirectory("/home/adolfo/TableauProver/tf/");
        //        tf.setProblemsOutputDirectory("/home/adolfo/TableauProver/tf/");
        //        tf.setProblemsTermination(".wagner");
        //        tf.setAnalyserName("sats3");
        tf.setSaveProof(true);
        //        proof = tf.prove ("noara");
        //        System.err.println(proof);
        //        tf.setTwoPhases(false);
        //        tf.setAnalyserName("SATH2");
        //        tf.setProblemsTermination(".sat");
        //        proof = tf.prove ("teste2");
        //        tf.setAnalyserName("satcnf2");
        //        tf.setProblemsTermination(".cnf");
        //        proof = tf.prove ("teste");
        //        System.err.println(proof);
        //        System.err.println("uf20-01000");
        //        proof = tf.prove ("uf20-01000");
        //        System.err.println(proof);
        tf.setProblemsInputDirectory("/home/adolfo/project-tableau/newcases/");
        tf.setProblemsOutputDirectory("/home/adolfo/project-tableau/newcases/");

        // TODO criar xml para descrever toda essa config
        // TODO colocar hora, min, seg, dia no arq
        tf.proveSequence("gamma", 20, 20, 20, 100, ProblemType.NORMAL,
                "gamma_results.txt");
        //      tf.proveSequence("gamma", 20, 100, 20, -1);
        //        tf.proveSequence("gamma_l", 20, 100, 20, -1);
        //        tf.proveSequence("gamma_n", 20, 100, 20, -1);
        //        tf.proveSequence("gamma_n_l", 20, 100, 20, -1);
        //        tf.proveSequence("h", 1, 7, 2, -1);
        //        tf.proveSequence("h_n", 1, 7, 2, -1);
        //        tf.proveSequence("statman", 1, 21, 4, -1);
        //        tf.proveSequence("statman_n", 1, 21, 4, -1);
        //        tf.proveSequence("php_n", 2, 4, 2, -1);
        tf.proveSequence("php", 2, 6, 1, -1, ProblemType.NORMAL,
                "php_results.txt");
        //        tf.setSaveProof(false);

        //    tf.runProblems();
        //        proof = tf.prove ("f600");
        //        System.err.println(proof);
        //        proof = tf.prove ("f1000");
        //        System.err.println(proof);

        //        Proof p = tf.prove("php1");
        //
        //        List l = tf.proveAll("php", 1, 4);
        //
        //        tf.saveAll("php", 1, 4);

    }

}