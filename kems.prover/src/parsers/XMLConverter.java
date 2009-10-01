package parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Created on 14/04/2004
 *
 */

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 * A class for creating XML files from Wagner Dias's files
 *  
 */
public class XMLConverter {

	String _source, _dest, _listOfFiles, _conversorJarFile1, _lexerName1,
			_parserName1, _conversorJarFile2, _lexerName2, _parserName2;

	public XMLConverter(String source, String dest, String listOfFiles,
			String conversorJarFile1, String lexerName1, String parserName1,
			String conversorJarFile2, String lexerName2, String parserName2) {
		_source = source;
		_dest = dest;
		_listOfFiles = listOfFiles;
		_conversorJarFile1 = conversorJarFile1;
		_lexerName1 = lexerName1;
		_parserName1 = parserName1;
		_conversorJarFile2 = conversorJarFile2;
		_lexerName2 = lexerName2;
		_parserName2 = parserName2;

	}

	public void createXMLFile(String WFile, String XMLFile) {

		ParserUser p1 = new ParserUser(new String[] { _conversorJarFile1 });

//		String s = (String) p1.parseFile(_lexerName1, _parserName1, WFile);
		p1.parseFile(_lexerName1, _parserName1, WFile);

//		ParserUser p2 = new ParserUser(new String[] { _conversorJarFile2 });
		new ParserUser(new String[] { _conversorJarFile2 });

//		p2.serialize(p2.parseString(_lexerName2, _parserName2, s), XMLFile);

	}

	public void createAll() {
		List<String> l = getSelectedFiles(_listOfFiles);
		Object[] allFilesinSourceDir = l.toArray();

		for (int i = 0; i < allFilesinSourceDir.length; i++) {
			createXMLFile(_source + File.separator
					+ (String) (allFilesinSourceDir[i]) + ".prove", _dest
					+ File.separator + (String) (allFilesinSourceDir[i])
					+ ".xml");
		}
	}

	public List<String> getSelectedFiles(String filename) {

		List<String> filenames = new ArrayList<String>();

		try {
			FileReader f = new FileReader(filename);
			BufferedReader b = new BufferedReader(f);

			String s;
			while ((s = getLine(b)) != null) {
				if (!(s.startsWith("*"))) {
					filenames.add(s);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("getSelectedFiles: File not found: " + filename);
			e.printStackTrace();
			System.exit(-1);
		}

		return filenames;

	}

	public String getLine(BufferedReader in) {
		String s;
		try {
			s = in.readLine();
		} catch (IOException e) {
			throw new RuntimeException("getLine: readLine() failed");
		}
		return s;
	}

}
