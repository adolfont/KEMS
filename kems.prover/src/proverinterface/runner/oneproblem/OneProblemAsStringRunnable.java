package proverinterface.runner.oneproblem;

import main.tableau.verifier.ExtendedProof;
import proverinterface.ProblemEditor;
import proverinterface.ProverFacade;
import proverinterface.ProverInterface;

public class OneProblemAsStringRunnable extends OneProblemRunnable {
	
	private String problem;

	public OneProblemAsStringRunnable(String problem,
			ProverFacade pf, ProverInterface proverInterface) {
		super(pf, proverInterface);

		this.problem = problem;
	}
	
	public ExtendedProof proveProblem() throws Throwable{
		return pf.proveAndVerifyString(problem, proverInterface
				.getProverConfigurator()
				.createProverConfigurationWithCurrentChoices());
	}
	
	protected String getErrorMessageEnd() {
         return  ProblemEditor.CURRENT_PROBLEM +" with current prover configuration: "
		+ proverInterface.getProverConfigurator()
				.createProverConfigurationWithCurrentChoices().toString();
	}


}
