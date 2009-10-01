/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner.several;

import java.util.ArrayList;
import java.util.List;

import logic.problem.Problem;
import proverinterface.runner.CommonResultsFrameTableLine;
import proverinterface.runner.ResultsFrameTableLine;
import util.FileUtil;

/**
 * A table line for analysis results.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SeveralProblemAnalysisResultsFrameTableLine extends
		ResultsFrameTableLine {

	/*
	 * (non-Javadoc)protected
	 * 
	 * @see proverconfigurator.CommonResultsFrameTableLine#getColumnNames()
	 */
	public String[] getColumnNames() {

		List<String> l = new ArrayList<String>();
		l.add(new String("Problem file name"));
		l.add(new String("Size"));
		// l.add(new String("Size in bytes"));
		l.add(new String("Signed formulas"));
		l.add(new String("Atomic formulas"));
		l.add(new String("Composite formulas"));
		l.add(new String("Connectives"));
		l.add(new String("Formulas"));

		return CommonResultsFrameTableLine.getStringArray(l);
	}

	/**
	 * @param problem
	 */
	public SeveralProblemAnalysisResultsFrameTableLine(String problemName,
			Problem problem) {
		getValues().add(FileUtil.showNameAndPath(problemName));
		getValues().add(
				new Integer(problem.getSignedFormulaFactory().getComplexity()).toString());
		// getValues().add(new Long(problemInfo.getSizeInBytes()));
		getValues().add(new Integer(problem.getFormulas().size()).toString());
		getValues()
				.add(
						new Integer(problem.getFormulaFactory()
								.getAtomicFormulasSize()).toString());
		getValues().add(
				new Integer(problem.getFormulaFactory()
						.getCompositeFormulasSize()).toString());
		getValues().add(
				new Integer(problem.getFormulaFactory().getConnectivesSize()).toString());
		getValues().add(new Integer(problem.getFormulaFactory().getSize()).toString() );
	}

}
