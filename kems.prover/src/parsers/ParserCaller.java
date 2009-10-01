/*
 * Created on May 12, 2004
 *
 */
package parsers;


/**
 * @author adolfo
 *
  */
public class ParserCaller {

	String _source, _dest, _conversorJarFile, _lexerName, _parserName;

	public ParserCaller(
		String source,
		String dest,
		String conversorJarFile,
		String lexerName,
		String parserName) {
		_source = source;
		_dest = dest;
		_conversorJarFile = conversorJarFile;
		_lexerName = lexerName;
		_parserName = parserName;
	}

//	/**
//	 * creates the destination file 
//	 */
//	public void createFile() {
//
//		ParserUser pu = new ParserUser(new String[] { _conversorJarFile });
//
//		//		SATSResult sr = (SATSResult) pu.parseFile(_lexerName, _parserName, _source);
//
//		pu.serialize(pu.parseFile(_lexerName, _parserName, _source), _dest);
//
//	}

	public static void main(String[] args) {

		if (args.length != 5) {
			System.out.println(
				"Usage: java -jar XMLConverter.jar ParserCaller <sourceFile> <destFile>  <analyser.jar>");
			System.out.println(
				"<sourceFile> - source file in SATLIB format"
					+ " <destFile> - destination file in XML format (some class) \n"
					+ " <analyser.jar> - jar file containing the classes containing the analyser\n"
					+ " <LexerClass> - name of the class for the Lexer Analyser\n"
					+ " <ParserClass> - name of the class for the Parser Analyser\n");
		} else {

			// the directory in arg[1] must exist!
//			ParserCaller pc =
				new ParserCaller(args[0], args[1], args[2], args[3], args[4]);

//			pc.createFile();
		}
	}
}
