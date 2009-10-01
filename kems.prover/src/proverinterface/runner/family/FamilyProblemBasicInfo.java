/*
 * Created on 17/11/2005
 *
 */
package proverinterface.runner.family;

import java.io.File;

import util.StringUtil;

/**
 * Basic information about a family problem
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FamilyProblemBasicInfo {
    private String instanceName;

    private String termination;

    private String nameWithoutTermination;

    private String instanceNumber;

    private int instanceNumberAsInt;

    private String typeString = "";

    public static final String TERMINATION_SEPARATOR = ".";

    public static final String NUMBER_SEPARATOR = "_";

    private long sizeInBytes;

    private ProblemFamily family;

    /**
     * @param file
     */
    public FamilyProblemBasicInfo(ProblemFamily family, File f)
            throws Exception {
        this.family = family;

        String BASIC_MESSAGE = "This is not an accepted family file. ";

        instanceName = f.getName();

        int pointIndex = instanceName.indexOf(TERMINATION_SEPARATOR);
        if (pointIndex != -1) {
            termination = instanceName.substring(pointIndex);
            nameWithoutTermination = instanceName.substring(0, pointIndex);
            sizeInBytes = f.length();
            int underlineIndex = nameWithoutTermination
                    .indexOf(NUMBER_SEPARATOR);
            if (underlineIndex != -1) {

                String instanceNumberPossiblyWithType = nameWithoutTermination
                        .substring(underlineIndex + 1);
                int secondUnderlineIndex = instanceNumberPossiblyWithType
                        .indexOf(NUMBER_SEPARATOR);
                if (secondUnderlineIndex == -1) {
                    instanceNumber = instanceNumberPossiblyWithType;
                } else {
                    typeString = instanceNumberPossiblyWithType.substring(0,
                            secondUnderlineIndex);
                    instanceNumber = instanceNumberPossiblyWithType
                    
                            .substring(secondUnderlineIndex + 1);
                }
                family.setName(nameWithoutTermination.substring(0,
                        underlineIndex)+(!typeString.equals("")?NUMBER_SEPARATOR+typeString:""));

                if (family.getName().length() == 0) {
                    throw new Exception(BASIC_MESSAGE
                            + " Every problem must have a problem name.");
                }
                if (instanceNumber.length() == 0) {

                    throw new Exception(
                            BASIC_MESSAGE
                                    + " Every problem name must have an instance number.");

                } else {
                    try {
                        instanceNumberAsInt = Integer.parseInt(instanceNumber);
                    } catch (Exception e) {
                        throw new Exception(BASIC_MESSAGE
                                + " This problem instance number \""
                                + instanceNumber + "\" is not valid.");

                    }
                }
            } else {
                throw new Exception(BASIC_MESSAGE
                        + " Every problem file name must have a separator \""
                        + NUMBER_SEPARATOR + "\" before instance number.");
            }
        } else {
            throw new Exception(
                    BASIC_MESSAGE
                            + " Every problem file must have exactly one termination that begins with \""
                            + TERMINATION_SEPARATOR + "\".");
        }
    }

    public String getFamilyName() {
        return family.getName();
    }

    public String getInstanceName() {
        return instanceName;
    }

    public String getInstanceNumber() {
        return instanceNumber;
    }

    public String getNameWithoutTermination() {
        return nameWithoutTermination;
    }

    public String getPath() {
        return family.getPath();
    }

    public String getTermination() {
        return termination;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        String result = "";
        result += getFamilyName()
//                + (!typeString.equals("") ? NUMBER_SEPARATOR + typeString : "")
                + NUMBER_SEPARATOR + instanceNumber + " - " + sizeInBytes
                + " bytes";
        return result;
    }

    /**
     * @param info
     * @return
     */
    public boolean isSameFamily(FamilyProblemBasicInfo otherProblem) {
        return getPath().equals(otherProblem.getPath())
                && getFamilyName().equals(otherProblem.getFamilyName())
                && termination.equals(otherProblem.getTermination());
    }

    public int getInstanceNumberAsInt() {
        return instanceNumberAsInt;
    }

    /**
     * @return
     */
    public String getFileName() {
        return getPath() + StringUtil.FILE_SEPARATOR + instanceName;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

}