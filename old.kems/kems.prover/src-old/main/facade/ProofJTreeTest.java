/*
 * Created on 08/03/2005
 *
 */
package facade;

import junit.framework.TestCase;
import problem.Problem;
import ruleStructures.RulesStructure;
import signedFormulasNew.SignedFormulaCreator;
import strategy.Strategy;
import strategy.simple.SimpleStrategy;
import tableau.Method;
import tableau.Proof;
import tableau.Prover;
import classicalLogic.ClassicalRuleStructures;

/**
 * @author Adolfo Gustavo Serra Seca neto
 *  
 */
public class ProofJTreeTest extends TestCase {

    public void testProofJTree() {

        SignedFormulaCreator sfc = new SignedFormulaCreator("sats3");
        sfc.setLibDir("/home/kurumin/home2/teste/TableauProver/lib/generated/");
        Problem p = sfc.parseText("T A\nT A->B\nF B");

        System.out.println(p);

        RulesStructure rules = ConfigurableClassicalRuleStructures.KEKESubstitionRulesWithBiimplication
                .getRuleStructure();

        Method m = new Method(rules);

        Strategy s = new AbstractSimpleStrategy(m);
        Prover prover = new Prover();
        prover.setMethod(m);
        prover.setStrategy(s);

        Proof prf = null;
        prf = new Proof (prover, p);

        ProofJTree pjt = new ProofJTree (prf.getProofTree());

    }

}