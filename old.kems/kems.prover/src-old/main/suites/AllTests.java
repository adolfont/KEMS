/*
 * Created on 15/03/2005
 *
 */
package suites;

import junit.framework.Test;
import junit.framework.TestSuite;
import facade.TableauFacadeTest;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for tableau facade");
		//$JUnit-BEGIN$
		suite.addTestSuite(TableauFacadeTest.class);
		//$JUnit-END$
		return suite;
	}
}
