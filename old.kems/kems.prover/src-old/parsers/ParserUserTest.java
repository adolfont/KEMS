/*
 * Created on 11/11/2003, 11:49:04 
 *
 */

import parser.ParserUser;
import junit.framework.TestCase;

/**
 * @author Adolfo Neto
 *
 * A class that tests the ParserUser class.
 */
public class ParserUserTest extends TestCase {

	String cwJar =
		"file:/home/adolfo/workspace/TPExe/analysers/output/conversor-wagner-SATLIB/cw.jar";
	String pclJar =
		"file:/home/adolfo/workspace/TPExe/analysers/input/satlib-sat-s/pcl.jar";

	/**
	 * Constructor for ParserUserTest.
	 * @param arg0
	 */
	public ParserUserTest(String arg0) {
		super(arg0);
	}

	public void testCreation() {

		ParserUser p = new ParserUser(new String[] { cwJar });

		String s =
			(String) p.parseFile(
				"ConversorWagnerSATLIBLexer",
				"ConversorWagnerSATLIBParser",
				"/home/adolfo/workspace/TableauProver/newcases/gamma1.prove");

		//		System.out.println(s);
	}

	public void testMain_cwSAT() {

		String args[] =
			{
				"file:",
				cwJar,
				"ConversorWagnerSATLIBLexer",
				"ConversorWagnerSATLIBParser",
				"/home/adolfo/workspace/TableauProver/newcases/phpn6.prove",
				"phpn6.String.xml" };

		ParserUser.main(args);
	}

	public void testMain_SATS() {

		String args[] =
			{  pclJar,
				"file:/home/adolfo/workspace/TPExe/analysers/output/satlib-sat-s/SATS.jar",
				"SATSLexer",
				"SATSParser",
				"/home/adolfo/workspace/TPExe/cases/phpn6.wsat",
				"phpn6.xml" };

		ParserUser.main(args);
	}

	public void test2Creations() {

		ParserUser p = new ParserUser(new String[] { cwJar });

		String s =
			(String) p.parseFile(
				"ConversorWagnerSATLIBLexer",
				"ConversorWagnerSATLIBParser",
				"/home/adolfo/workspace/TableauProver/newcases/phpn6.prove");

		ParserUser q =
			new ParserUser(
				new String[] {
					pclJar,
					"file:/home/adolfo/workspace/TPExe/analysers/output/satlib-sat-s/SATS.jar" });

		Object o = q.parseString("SATSLexer", "SATSParser", s);
		//		System.out.println(o.toString());

	}


}
