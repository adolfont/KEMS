/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import proverinterface.runner.family.FamilyProblemBasicInfo;




/**
 * A class that contains the common features of proof and analysis table lines.
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public abstract class CommonResultsFrameTableLine extends ResultsFrameTableLine {

    
    private static final String[] columnNames ={
            "Instance number"            
    };
    
    /**
     * @param problem
     */
    public CommonResultsFrameTableLine(FamilyProblemBasicInfo problem) {
        getValues().add(Integer.toString(problem.getInstanceNumberAsInt()));
    }

    /* (non-Javadoc)
     * @see proverconfigurator.ResultsFrameTableLine#getColumnNames()
     */
    public String[] getColumnNames() {
        return columnNames;
    }

    public static String[] getStringArray(List<String> l){
        String[] result = new String[l.size()];
        Iterator<String> it = l.iterator();
        int i=0;
        while (it.hasNext()){
            result[i++]= (String) it.next();
        }

        return result;
    }

    protected List<String> getColumnNamesAsList(){
        return new ArrayList<String>(Arrays.asList(getColumnNames()));
    }

}
