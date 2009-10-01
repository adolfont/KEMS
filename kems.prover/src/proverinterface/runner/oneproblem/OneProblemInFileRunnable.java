package proverinterface.runner.oneproblem;

import main.tableau.verifier.ExtendedProof;
import proverinterface.ProverFacade;
import proverinterface.ProverInterface;

public class OneProblemInFileRunnable extends OneProblemRunnable {
	
	private String filename;

	public OneProblemInFileRunnable(String filename,
			ProverFacade pf, ProverInterface proverInterface) {
		super(pf, proverInterface);

		this.filename = filename;
	}
	
	public ExtendedProof proveProblem() throws Throwable{
		return pf.proveAndVerifyFile(filename, proverInterface
				.getProverConfigurator()
				.createProverConfigurationWithCurrentChoices());
	}
	
	protected String getErrorMessageEnd() {
         return  "file with current prover configuration: "
		+ proverInterface.getProverConfigurator()
				.createProverConfigurationWithCurrentChoices().toString();
	}


}
