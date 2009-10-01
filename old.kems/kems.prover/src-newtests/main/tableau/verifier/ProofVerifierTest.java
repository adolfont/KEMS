/*
 * Created on 15/11/2005
 *
 */
package main.tableau.verifier;

import java.text.NumberFormat;
import java.util.Locale;

import junit.framework.TestCase;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaCreator;
import main.newstrategy.Prover;
import main.newstrategy.mbc.simple.MBCSimpleStrategy;
import main.tableau.Method;
import main.tableau.Proof;
import proverinterface.RuleStructureFactory;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ProofVerifierTest extends TestCase {

    public void testVerify() throws Exception {
        
        SignedFormulaCreator sfc = new SignedFormulaCreator("satlfi");
        Problem problem = sfc.parseText("T A->B\nT A\nT B->(C->!A)");

        // creating a proof method
        Method method = new Method(RuleStructureFactory
                .createRulesStructure(RuleStructureFactory.MBC));

        // creating a strategy
        MBCSimpleStrategy s = new MBCSimpleStrategy(method);

        // creating and configuring a prover
        Prover prover = new Prover();

        prover.setMethod(method);
        prover.setStrategy(s);

        int times=1;
        long begin = System.currentTimeMillis();
        Proof proof = prover.prove(problem);
        for (int i = 1; i < times; i++) {
            prover.prove(problem);
        }
        long end = System.currentTimeMillis();
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(3);
        System.err.println(nf.format((end - begin) / 3000.0));
        
        System.err.println(proof);
        
        ProofVerifier pverif = new ProofVerifier(prover);
        ExtendedProof ep = pverif.verify(proof);
        
        System.err.println(ep.getVerificationResult());
        System.err.println(ep);


        
    }

}
