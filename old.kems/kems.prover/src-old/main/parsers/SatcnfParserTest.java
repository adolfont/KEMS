package parsers;
import java.io.IOException;

import junit.framework.TestCase;
import parser.ParserUser;
import problem.Problem;

/*
 * Created on 20/10/2004
 *
 */

/** Class that tests the parser for the SAT CNF format.
 * See examples in this folder.
 * @author adolfo
 *
 */
public class SatcnfParserTest extends TestCase {

	/**
	 * Constructor for SatcnfParserTest.
	 * @param arg0
	 */
	public SatcnfParserTest(String arg0) {
		super(arg0);
	}

	public void testCreateParserForCNF() throws IOException {
		// TODO pode ser colocado aqui ou no VM arguments!!!
//		System.setProperty("workspace.home","/mnt/hdb/Meus documentos/adolfo/usp/workspace2/");

		ParserUser pc =
			new ParserUser(
			// TODO porque aqui pode?
				new String[] { "file:$WORKSPACE/TPExe/lib/satcnf.jar" });
		Object result =
			pc.parseFile(
				"satcnf.satcnfLexer",
				"satcnf.satcnfParser",
				System.getProperty("workspace.home")+"TableauProver4/tests/parsers/teste.cnf");
				
		Problem p = (Problem) result;
		p.setName("teste.cnf");
		p.setFilename(System.getProperty("workspace.home")+"Rules/tests/parsers/teste.cnf");
		
//		System.out.println(result);
//		
	}
	
	public static void main(String[] args) {
		// TODO You have to put it as a VM argument: -Dworkspace.home=xxx
		System.out.println(System.getProperty("workspace.home"));
	}

}


