/*
 * Created on May 12, 2004
 *
 */
package converters;

import junit.framework.TestCase;

/**
 * @author adolfo
 *
  */
public class ParserCallerTest extends TestCase {

	/**
	 * Constructor for ParserCallerTest.
	 * @param arg0
	 */
	public ParserCallerTest(String arg0) {
		super(arg0);
	}

	public void testParserCaller() {
		ParserCaller pc = new ParserCaller ("teste.sat","teste.xml","file:/home/adolfo/workspace/TPExe/lib/SATH.jar","SATHLexer", "SATHParser");
		
		pc.createFile();
		
	}
	
	public void testMain() {
		String[] args = {"teste.sat","teste.xml","file:/home/adolfo/workspace/TPExe/lib/SATH.jar","SATHLexer", "SATHParser"};
		ParserCaller.main(args);
	}

}
