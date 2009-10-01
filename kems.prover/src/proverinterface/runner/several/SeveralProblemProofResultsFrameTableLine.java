/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner.several;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import logic.problem.Problem;
import main.proofTree.ProofTree;
import main.tableau.verifier.ExtendedProof;
import proverinterface.runner.CommonResultsFrameTableLine;
import proverinterface.runner.ResultsFrameTableLine;
import util.FileUtil;

/**
 * A table line for proof results
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SeveralProblemProofResultsFrameTableLine extends
		ResultsFrameTableLine {

	private String problemName;
	private ExtendedProof extendedProof;
	private Problem problem;

	/*
	 * (non-Javadoc)
	 * 
	 * @see proverconfigurator.CommonResultsFrameTableLine#getColumnNames()
	 */
	public String[] getColumnNames() {

		List<String> l = new ArrayList<String>();
		l.add(new String("Problem file name"));
		l.add(new String("Prover configuration"));
		l.add(new String("Time spent (in s)"));
		l.add(new String("Closed?"));
		l.add(new String("Verified?"));
		l.add(new String("Problem size"));
		l.add(new String("Proof size"));
		l.add(new String("Nodes"));
		l.add(new String("Used nodes"));
		l.add(new String("% used nodes"));
		l.add(new String("Proof tree height"));

		return CommonResultsFrameTableLine.getStringArray(l);
	}
	
	

	public SeveralProblemProofResultsFrameTableLine(String problemName,
			ExtendedProof ep, Problem p) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);

		this.problemName = problemName;
		this.extendedProof = ep;
		this.problem = p;

		getValues().add(FileUtil.showNameAndPath(problemName));

		getValues().add(ep.getProverConfiguration().toString());
		
		
		/* Extended proof */
		getValues().add(nf.format(ep.getTimeSpent() / 1000.0));
		getValues().add(new Boolean(ep.isClosed()).toString());
		getValues().add(ep.getVerificationResult().toString());
		
		/* Problem */
		getValues().add(
				Long.toString(p.getSignedFormulaFactory().getComplexity()));
		
		/* Extended proof */
		getValues().add(Long.toString(ep.getSize()));
		getValues().add(Integer.toString(ep.getNodesQuantity()));
		getValues().add(
				ep.getUsedQuantity() != null ? Integer.toString(ep
						.getUsedQuantity().intValue()) : "n/a");
		getValues().add(
				ep.getUsedQuantity() != null ? nf.format(100.0
						* ep.getUsedQuantity().intValue()
						/ ep.getNodesQuantity())
						+ "%" : "n/a");
		getValues().add(
				Integer.toString(((ProofTree) ep.getProofTree()).getHeight()));

	}


	public ExtendedProof getExtendedProof() {
		return extendedProof;
	}


	public Problem getProblem() {
		return problem;
	}


	public String getProblemName() {
		return problemName;
	}


}
