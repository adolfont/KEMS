/*
 * Created on 15/03/2005
 *
 */
package util;

/**
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 *
 */
public class StringUtil {
    
    public static String FILE_SEPARATOR = System.getProperty("file.separator"); 
    public static String LINE_SEPARATOR = System.getProperty("line.separator"); 
    
    /** 
     * @param baseSize
     * @param thisProblemSize
     * @return a (possibly empty) sequence of zeroes
     */
    public static String zeroes(int baseSize, int thisProblemSize) {
        int digitsBigger = 0;
        for (int i = 0; baseSize > 0; i++) {
            baseSize = baseSize / 10;
            digitsBigger = i;
        }
        int digitsThis = 0;
        for (int i = 0; thisProblemSize > 0; i++) {
            thisProblemSize = thisProblemSize / 10;
            digitsThis = i;
        }
        String result = "";
        for (int j = 0; j < (digitsBigger - digitsThis); j++) {
            result = result + "0";
        }
        return result;
    }


}
