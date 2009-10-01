/*
 * Created on 14/10/2005
 *
 */
package util;

import java.io.File;
import java.io.IOException;

/**
 * Utilities for file handling.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FileUtil {

	public static boolean createPath(String path) {
		try {
			File f = new File(path);
			f.mkdirs();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String showNameAndPath(String problemName) {
		// show the name of the file where the problem is
		File f = new File(problemName);
		try {
			return (

			f.getName() + " at " + f.getCanonicalPath().subSequence(1,
					f.getCanonicalPath().length() - f.getName().length()));
		} catch (IOException e) {
			return (problemName);
		}
	}

}
