
import junit.framework.TestCase;
import classicalLogic.ClassicalConnectives;
import classicalLogic.ClassicalRuleStructures;
import classicalLogic.ClassicalSignatureFactory;

/*
 * Created on 06/04/2005
 *
 */

/**
 * Tests classical rule structures.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 */
public class ClassicalRuleStructuresTest extends TestCase {

    public void testConstructor() {
        ClassicalSignatureFactory cs = ClassicalSignatureFactory.getInstance();
        ClassicalRuleStructures crs = new ClassicalRuleStructures(cs
                .getNormalBXSignature());

        System.out.println(crs.getRules(ClassicalConnectives.AND));

        crs = new ClassicalRuleStructures(cs.getClausalSignature());
        System.out.println(crs.getRules(ClassicalConnectives.TOP));

        crs = new ClassicalRuleStructures(cs.getNormalSignature());
        System.out.println(crs.getRules(ClassicalConnectives.TOP));

        crs = new ClassicalRuleStructures(cs.getNormalBSignature());
        System.out.println(crs.getRules(ClassicalConnectives.TOP));

        crs = new ClassicalRuleStructures(cs.getNormalBXSignature());
        System.out.println(crs.getRules(ClassicalConnectives.TOP));
        System.out.println(crs.getRules(ClassicalConnectives.TOP));
    }

}
