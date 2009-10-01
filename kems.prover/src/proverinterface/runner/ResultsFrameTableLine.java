/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner;

import java.util.Vector;

/**
 * A line of the JTable used in the results frame
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public abstract class ResultsFrameTableLine {

    protected ResultsFrameTableLine(){}
    
    private Vector<String> values = new Vector<String>();

    public abstract String[] getColumnNames();

    public Object get(int index) {
        return getValues().get(index);
    }
    
    public Vector<String> getValues(){
        return values;
    }


}