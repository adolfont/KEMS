/*
 * Created on 23/03/2005
 *
 */
package problemGenerator;

import junit.framework.TestCase;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * 
 */
public class ProblemGeneratorTest extends TestCase {

    public void testProblemGenerator() {

        ProblemGenerator p = new UProblemGenerator();

//        System.out.println(p.generate(30));
        
        p
        .saveFile(
                "/home/kurumin/TableauProver/problems/generated/pelletier/U/",
                15, ".prove");
        p
        .saveSequence(
                "/home/kurumin/TableauProver/problems/generated/pelletier/U/",
                1, 20, ".prove");
        
        p=new UPrimeProblemGenerator ();
//        System.out.println(p.generate(1));
//        System.out.println(p.generate(2));
//        System.out.println(p.generate(3));

        p
        .saveSequence(
                "/home/kurumin/TableauProver/problems/generated/pelletier/UPrime/",
                1, 20, ".prove");
        
        p = new SquareTseitinProblemGenerator();
      System.out.println(p.generate(1));
//      System.out.println(p.generate(2));
//      System.out.println(p.generate(3));

      p
      .saveSequence(
              "/home/kurumin/TableauProver/problems/generated/pelletier/SquareTseitin/",
              1, 10, ".prove");


    }
    
}
