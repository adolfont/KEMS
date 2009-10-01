/*
 * Created on 23/03/2005
 *
 */
package problemGenerator;

import java.io.FileWriter;
import java.util.Iterator;

import logic.problem.Problem;
import logic.signedFormulas.SignedFormula;
import main.exceptions.KEMSException;
import util.FileUtil;
import util.StringUtil;

/**
 * Abstract class for generators of problem families.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class ProblemGenerator {
    private int maxProblemSize = 1;

    private String familyName;

    public static final String NAME_SEPARATOR = "_";

    public static final String FORMAT = "Wagner Dias' format";

    /**
     * Creates a problems generator for a family
     * 
     * @param familyName -
     *            a string that cannot contain more than one ocurrence of the
     *            nameSeparator
     */
    protected ProblemGenerator(String familyName) {
        this.familyName = familyName;
        int index = familyName.indexOf(NAME_SEPARATOR);
        if (index != -1) {
            String secondPart = familyName.substring(index + 1);
            if (secondPart.indexOf(NAME_SEPARATOR) != -1) {
                throw new KEMSException(
                        "A family name cannot contain more than one ocurrence of the NAME_SEPARATOR");
            }
        }
    }

    /**
     * @return name of the family of problems
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @return Returns the maxProblemSize.
     */
    public int getMaxProblemSize() {
        return maxProblemSize;
    }

    /**
     * generates an instance of a problem in Wagner Dias Format
     * 
     * @param index
     * @return
     */
    public Problem generate(int index) {
        Problem p = new Problem(FORMAT);
        p.setName(getFamilyName() + NAME_SEPARATOR
                + StringUtil.zeroes(getMaxProblemSize(), index) + index);
        return generate(p, index);
    }

    /**
     * generates an instance of a problem.
     * 
     * @param p
     * @param index
     * @return
     */
    protected abstract Problem generate(Problem p, int index);

    /**
     * Saves a problem in a file
     * 
     * @param path
     * @param index
     * @param fileTerminator
     */
    public void saveFile(String path, int index, String fileTerminator) {
        Problem p = generate(index);
        FileUtil.createPath(path);
        p.setFilename(path + StringUtil.FILE_SEPARATOR + p.getName()
                + fileTerminator);
        save(p);
    }

    /**
     * Saves a sequence of problems in files.
     * 
     * @param path
     * @param begin
     * @param end
     * @param fileTerminator
     */
    public void saveSequence(String path, int begin, int end, int step,
            String fileTerminator) {
        maxProblemSize = end;
        for (int i = begin; i <= end; i+=step) {
            saveFile(path, i, fileTerminator);
        }
    }

    /**
     * saves a problem in a file
     * 
     * @param p
     */
    private void save(Problem p) {
        try {
            FileWriter f = new FileWriter(p.getFilename());
            Iterator<SignedFormula> it = p.getFormulas().iterator();
            while (it.hasNext()) {
                //                f.write(p.getFormulas().get(0).toString());
                f.write(it.next().toString() + StringUtil.LINE_SEPARATOR);
            }
            f.close();
        } catch (Exception e) {
            throw new KEMSException("ProblemGenerator: Unable to generate file "
                    + p.getFilename() + " for problem " + p.getName());
        }
    }
}
