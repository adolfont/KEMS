/*
 * Created on 22/11/2005
 *
 */
package proverinterface.runner.family;

import java.util.ArrayList;
import java.util.List;

/**
 * A family of problems
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ProblemFamily {
    private String name;
    private String path;
    private int size;
    
    private List<FamilyProblemBasicInfo> problems;


    /**
     * @param path2
     */
    public ProblemFamily(String path2) {
        this.path = path2;
        problems = new ArrayList<FamilyProblemBasicInfo>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    /**
     * @return
     */
    public int size() {
        return size;
    }
    /**
     * @param index
     * @return
     */
    public FamilyProblemBasicInfo get(int index) {
        return (FamilyProblemBasicInfo)problems.get(index);
    }
    /**
     * @return
     */
    public Object[] getArray() {
        return problems.toArray();
    }
    /**
     * @param length
     */
    public void setSize(int length) {
        size = length;
    }
    /**
     * @param familyProblemInfo
     */
    public void add(FamilyProblemBasicInfo familyProblemInfo) {
        problems.add(familyProblemInfo);
    }
}
