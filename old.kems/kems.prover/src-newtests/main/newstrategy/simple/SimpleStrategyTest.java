/*
 * Created on 27/10/2005
 *
 */
package main.newstrategy.simple;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import junit.framework.TestCase;
import logic.problem.Problem;
import logic.signedFormulas.SignedFormulaCreator;
import main.newstrategy.Prover;
import main.tableau.Method;
import main.tableau.Proof;
import proverinterface.RuleStructureFactory;

/**
 * Tests SimpleStrategy.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class SimpleStrategyTest extends TestCase {

    public void testClose() {

        testCloseAux(PB_TESTERS_1, false, true, 1);
        testCloseAux(PB_TESTERS_2, false, true, 1);
        testCloseAux(CLOSE_TESTERS_1, true, true, 1);
        testCloseAux(PHP_4, true, false, 3);
        testCloseAux(PHP_5, true, false, 1);
//        testCloseAux(PHP_6, true, false, 1);

    }

    public void testCloseAux(String[] tester, boolean expectedClose,
            boolean show, int times) {
        // creating a problem
        SignedFormulaCreator sfc = new SignedFormulaCreator("sats5");
        Problem problem = sfc.parseText(createString(tester));

        // creating a proof method
        Method method = new Method(RuleStructureFactory
                .createRulesStructure(RuleStructureFactory.CPL_NORMAL_BX));

        // creating a strategy
        SimpleStrategy s = new SimpleStrategy(method);

        // creating and configuring a prover
        Prover prover = new Prover();

        prover.setMethod(method);
        prover.setStrategy(s);

        long begin = System.currentTimeMillis();
        Proof proof = prover.prove(problem);
        for (int i = 1; i < times; i++) {
            prover.prove(problem);
        }
        long end = System.currentTimeMillis();
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        nf.setMinimumFractionDigits(3);
        System.err.println(nf.format((end - begin) / 3000.0));

        if (expectedClose) {
            assertTrue(proof.isClosed());
        } else {
            assertFalse(proof.isClosed());
        }

        if (show) {
            System.out.println(proof);
        }
        else{
            FileWriter fw;
            try {
                fw = new FileWriter("/home/kurumin/result"+ System.currentTimeMillis()+ ".txt");
                fw.write(proof.toString());
                fw.flush();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // TODO: proof checher
        // valuation
        // printing
        // xml generation
        // proof viewer

    }

//    private static String[] ONE_PREMISE_TESTERS = { "T A1&A2", "F A3|A4",
//            "F A5->A6", "T !A7", "F !A8" };
//
//    private static String[] TWO_PREMISE_TESTERS = { "T A1&A2", "F A3|A4",
//            "F A5->A6", "T !A7", "F !A8", "F A8&B1", "F B2&!A7", "T !A8|B3",
//            "T B4|(A5->A6)", "T A1->B5", "T !!B6->A3"
//
//    };

    private static String[] PB_TESTERS_1 = { "T A->B", "T B->C" };

    private static String[] PB_TESTERS_2 = { "T A->B", "T C->D", "T E|F",
    "F G&H" };

    private static String[] CLOSE_TESTERS_1 = { "T A->B", "T C->D", 
            "T A|C", "F B|D" };

    private static String[] PHP_4 = { "F(((p0,0|p0,1|p0,2|p0,3)&(p1,0|p1,1|p1,2|p1,3)&(p2,0|p2,1|p2,2|p2,3)&(p3,0|p3,1|p3,2|p3,3)&(p4,0|p4,1|p4,2|p4,3))->((p0,0&p1,0)|(p0,0&p2,0)|(p0,0&p3,0)|(p0,0&p4,0)|(p1,0&p2,0)|(p1,0&p3,0)|(p1,0&p4,0)|(p2,0&p3,0)|(p2,0&p4,0)|(p3,0&p4,0)|(p0,1&p1,1)|(p0,1&p2,1)|(p0,1&p3,1)|(p0,1&p4,1)|(p1,1&p2,1)|(p1,1&p3,1)|(p1,1&p4,1)|(p2,1&p3,1)|(p2,1&p4,1)|(p3,1&p4,1)|(p0,2&p1,2)|(p0,2&p2,2)|(p0,2&p3,2)|(p0,2&p4,2)|(p1,2&p2,2)|(p1,2&p3,2)|(p1,2&p4,2)|(p2,2&p3,2)|(p2,2&p4,2)|(p3,2&p4,2)|(p0,3&p1,3)|(p0,3&p2,3)|(p0,3&p3,3)|(p0,3&p4,3)|(p1,3&p2,3)|(p1,3&p3,3)|(p1,3&p4,3)|(p2,3&p3,3)|(p2,3&p4,3)|(p3,3&p4,3)))" };

    private static String[] PHP_5 = { "F(((p0,0|p0,1|p0,2|p0,3|p0,4)&(p1,0|p1,1|p1,2|p1,3|p1,4)&(p2,0|p2,1|p2,2|p2,3|p2,4)&(p3,0|p3,1|p3,2|p3,3|p3,4)&(p4,0|p4,1|p4,2|p4,3|p4,4)&(p5,0|p5,1|p5,2|p5,3|p5,4))->((p0,0&p1,0)|(p0,0&p2,0)|(p0,0&p3,0)|(p0,0&p4,0)|(p0,0&p5,0)|(p1,0&p2,0)|(p1,0&p3,0)|(p1,0&p4,0)|(p1,0&p5,0)|(p2,0&p3,0)|(p2,0&p4,0)|(p2,0&p5,0)|(p3,0&p4,0)|(p3,0&p5,0)|(p4,0&p5,0)|(p0,1&p1,1)|(p0,1&p2,1)|(p0,1&p3,1)|(p0,1&p4,1)|(p0,1&p5,1)|(p1,1&p2,1)|(p1,1&p3,1)|(p1,1&p4,1)|(p1,1&p5,1)|(p2,1&p3,1)|(p2,1&p4,1)|(p2,1&p5,1)|(p3,1&p4,1)|(p3,1&p5,1)|(p4,1&p5,1)|(p0,2&p1,2)|(p0,2&p2,2)|(p0,2&p3,2)|(p0,2&p4,2)|(p0,2&p5,2)|(p1,2&p2,2)|(p1,2&p3,2)|(p1,2&p4,2)|(p1,2&p5,2)|(p2,2&p3,2)|(p2,2&p4,2)|(p2,2&p5,2)|(p3,2&p4,2)|(p3,2&p5,2)|(p4,2&p5,2)|(p0,3&p1,3)|(p0,3&p2,3)|(p0,3&p3,3)|(p0,3&p4,3)|(p0,3&p5,3)|(p1,3&p2,3)|(p1,3&p3,3)|(p1,3&p4,3)|(p1,3&p5,3)|(p2,3&p3,3)|(p2,3&p4,3)|(p2,3&p5,3)|(p3,3&p4,3)|(p3,3&p5,3)|(p4,3&p5,3)|(p0,4&p1,4)|(p0,4&p2,4)|(p0,4&p3,4)|(p0,4&p4,4)|(p0,4&p5,4)|(p1,4&p2,4)|(p1,4&p3,4)|(p1,4&p4,4)|(p1,4&p5,4)|(p2,4&p3,4)|(p2,4&p4,4)|(p2,4&p5,4)|(p3,4&p4,4)|(p3,4&p5,4)|(p4,4&p5,4)))" };
//    private static String[] PHP_6 = { "F(((p0,0|p0,1|p0,2|p0,3|p0,4|p0,5)&(p1,0|p1,1|p1,2|p1,3|p1,4|p1,5)&(p2,0|p2,1|p2,2|p2,3|p2,4|p2,5)&(p3,0|p3,1|p3,2|p3,3|p3,4|p3,5)&(p4,0|p4,1|p4,2|p4,3|p4,4|p4,5)&(p5,0|p5,1|p5,2|p5,3|p5,4|p5,5)&(p6,0|p6,1|p6,2|p6,3|p6,4|p6,5))->((p0,0&p1,0)|(p0,0&p2,0)|(p0,0&p3,0)|(p0,0&p4,0)|(p0,0&p5,0)|(p0,0&p6,0)|(p1,0&p2,0)|(p1,0&p3,0)|(p1,0&p4,0)|(p1,0&p5,0)|(p1,0&p6,0)|(p2,0&p3,0)|(p2,0&p4,0)|(p2,0&p5,0)|(p2,0&p6,0)|(p3,0&p4,0)|(p3,0&p5,0)|(p3,0&p6,0)|(p4,0&p5,0)|(p4,0&p6,0)|(p5,0&p6,0)|(p0,1&p1,1)|(p0,1&p2,1)|(p0,1&p3,1)|(p0,1&p4,1)|(p0,1&p5,1)|(p0,1&p6,1)|(p1,1&p2,1)|(p1,1&p3,1)|(p1,1&p4,1)|(p1,1&p5,1)|(p1,1&p6,1)|(p2,1&p3,1)|(p2,1&p4,1)|(p2,1&p5,1)|(p2,1&p6,1)|(p3,1&p4,1)|(p3,1&p5,1)|(p3,1&p6,1)|(p4,1&p5,1)|(p4,1&p6,1)|(p5,1&p6,1)|(p0,2&p1,2)|(p0,2&p2,2)|(p0,2&p3,2)|(p0,2&p4,2)|(p0,2&p5,2)|(p0,2&p6,2)|(p1,2&p2,2)|(p1,2&p3,2)|(p1,2&p4,2)|(p1,2&p5,2)|(p1,2&p6,2)|(p2,2&p3,2)|(p2,2&p4,2)|(p2,2&p5,2)|(p2,2&p6,2)|(p3,2&p4,2)|(p3,2&p5,2)|(p3,2&p6,2)|(p4,2&p5,2)|(p4,2&p6,2)|(p5,2&p6,2)|(p0,3&p1,3)|(p0,3&p2,3)|(p0,3&p3,3)|(p0,3&p4,3)|(p0,3&p5,3)|(p0,3&p6,3)|(p1,3&p2,3)|(p1,3&p3,3)|(p1,3&p4,3)|(p1,3&p5,3)|(p1,3&p6,3)|(p2,3&p3,3)|(p2,3&p4,3)|(p2,3&p5,3)|(p2,3&p6,3)|(p3,3&p4,3)|(p3,3&p5,3)|(p3,3&p6,3)|(p4,3&p5,3)|(p4,3&p6,3)|(p5,3&p6,3)|(p0,4&p1,4)|(p0,4&p2,4)|(p0,4&p3,4)|(p0,4&p4,4)|(p0,4&p5,4)|(p0,4&p6,4)|(p1,4&p2,4)|(p1,4&p3,4)|(p1,4&p4,4)|(p1,4&p5,4)|(p1,4&p6,4)|(p2,4&p3,4)|(p2,4&p4,4)|(p2,4&p5,4)|(p2,4&p6,4)|(p3,4&p4,4)|(p3,4&p5,4)|(p3,4&p6,4)|(p4,4&p5,4)|(p4,4&p6,4)|(p5,4&p6,4)|(p0,5&p1,5)|(p0,5&p2,5)|(p0,5&p3,5)|(p0,5&p4,5)|(p0,5&p5,5)|(p0,5&p6,5)|(p1,5&p2,5)|(p1,5&p3,5)|(p1,5&p4,5)|(p1,5&p5,5)|(p1,5&p6,5)|(p2,5&p3,5)|(p2,5&p4,5)|(p2,5&p5,5)|(p2,5&p6,5)|(p3,5&p4,5)|(p3,5&p5,5)|(p3,5&p6,5)|(p4,5&p5,5)|(p4,5&p6,5)|(p5,5&p6,5)))" };

    private String createString(String[] formulas) {
        String result = "";
        for (int i = 0; i < formulas.length - 1; i++) {
            result += formulas[i] + "\n";
        }

        result += formulas[formulas.length - 1];

        return result;
    }

}
