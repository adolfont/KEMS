/*
 * Created on 15/03/2005
 *
 */
package rulesNew;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for rulesNew");
        //$JUnit-BEGIN$
        suite.addTestSuite(RuleTest_TOP_BOTTOM.class);
        suite.addTestSuite(KERuleTest.class);
        suite.addTestSuite(GettersTest.class);
        suite.addTestSuite(RuleTest.class);
        suite.addTestSuite(RuleTest_BIIMPLIES.class);
        //$JUnit-END$
        return suite;
    }
}
