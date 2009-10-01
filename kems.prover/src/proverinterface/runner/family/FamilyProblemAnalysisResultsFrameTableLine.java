/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner.family;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import proverinterface.runner.CommonResultsFrameTableLine;

import logic.problem.Problem;

/**
 * A table line for analysis results. 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class FamilyProblemAnalysisResultsFrameTableLine extends CommonResultsFrameTableLine {
    
    /* (non-Javadoc)
     * @see proverconfigurator.CommonResultsFrameTableLine#getColumnNames()
     */
    public String[] getColumnNames() {
        String[] columnNames =super.getColumnNames();
        
        List<String> l = new ArrayList<String>(Arrays.asList(columnNames));
        l.add(new String("Size"));
//        l.add(new String("Size in bytes"));
        l.add(new String("Signed formulas"));
        l.add(new String("Atomic formulas"));
        l.add(new String("Composite formulas"));
        l.add(new String("Connectives"));
        l.add(new String("Formulas"));
        
        return getStringArray(l);
    }

    /**
     * @param problem
     */
    public FamilyProblemAnalysisResultsFrameTableLine(FamilyProblemBasicInfo problemInfo, Problem problem) {
        super(problemInfo);
        getValues().add(new Integer(problem.getSignedFormulaFactory().getComplexity()).toString());
//        getValues().add(new Long(problemInfo.getSizeInBytes()));
        getValues().add(new Integer(problem.getFormulas().size()).toString());
        getValues().add(new Integer(problem.getFormulaFactory().getAtomicFormulasSize()).toString());
        getValues().add(new Integer(problem.getFormulaFactory().getCompositeFormulasSize()).toString());
        getValues().add(new Integer(problem.getFormulaFactory().getConnectivesSize()).toString());
        getValues().add(new Integer(problem.getFormulaFactory().getSize()).toString());
    }

}
