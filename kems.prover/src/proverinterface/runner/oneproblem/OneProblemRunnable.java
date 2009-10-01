package proverinterface.runner.oneproblem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import main.tableau.verifier.ExtendedProof;
import proverinterface.ProverConfiguration;
import proverinterface.ProverFacade;
import proverinterface.ProverInterface;
import proverinterface.runner.AbstractProblemRunnable;
import util.MemoryManager;
import util.StringUtil;

/**
 * A runnable that proves one problem and interrupts if a stop button is
 * pressed.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public abstract class OneProblemRunnable extends AbstractProblemRunnable
		implements ActionListener {

	/**
	 * 
	 */
	protected ExtendedProof ep = null;

	/**
	 * 
	 */
	protected boolean isFinished;

	/**
	 * 
	 */
	protected ProverFacade pf;

	/**
	 * 
	 */
	protected ProverInterface proverInterface;

	/**
	 * 
	 */
	protected long startTime;

	public OneProblemRunnable(ProverFacade pf, ProverInterface proverInterface) {
		this.pf = pf;
		this.proverInterface = proverInterface;

		isFinished = false;
	}

	public void run() {

		startTime = System.currentTimeMillis();

		try {

			proverInterface
					.showInfoMessage("Started proof and verify procedure for "
							+ getErrorMessageEnd());
			ep = proveProblem();
			proverInterface
					.showInfoMessage("Finished with success proof and verify procedure for "
							+ getErrorMessageEnd());
			proverInterface.showDebugMessage(createProofInformationMessage(ep,
					proverInterface.getProverConfigurator()
							.createProverConfigurationWithCurrentChoices()));

			isFinished = true;

			proverInterface.getStopFrame().dispose();
			proverInterface.showProofViewer(ep);
			// proverInterface.setIsExecutingProver(false);

		} catch (Throwable e) {
			
			e.printStackTrace();
			
			
			try {
				MemoryManager.runGC();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			if (e.getMessage()!=null && e.getMessage().equals("Java heap space")) {
				proverInterface
						.showErrorMessage("The amount of memory available was not enough to prove this problem... Finishing.");
			} else {
				proverInterface.showErrorMessage(e.getMessage());
			}

			proverInterface
					.showErrorMessage("Finished with error proof and verify procedure for "
							+ getErrorMessageEnd());

			proverInterface.getStopFrame().dispose();
			// proverInterface.setIsExecutingProver(false);
			ep = null;
			isFinished = true;
		}

		proverInterface.releaseIsExecutingProverLock();

	}

	public static String createProofInformationMessage(ExtendedProof ep,
			ProverConfiguration proverConfiguration) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		return "Additional information about proof procedure for "
				+ ep.getProblem().getName()
				+ ": "
				+ (ep.isClosed()?"closed":"open and completed") 
				+ " with "
				+ proverConfiguration
				+ " in "
				+ nf.format(ep.getTimeSpent() / 1000.0)
				+ " seconds"
				+ StringUtil.LINE_SEPARATOR
				+ "proof size: "
				+ ep.getSize()  
				+ StringUtil.LINE_SEPARATOR
				+ "number of nodes: "
				+ ep.getNodesQuantity()
				+ StringUtil.LINE_SEPARATOR
				+ "verification result: "
				+ (ep.getVerificationResult().booleanValue() ? "verified"
						: "not verfied");
	}

	/**
	 * @return an error message to include in the log viewer
	 */
	protected abstract String getErrorMessageEnd();

	/**
	 * @return the extended proof obtained
	 * @throws Exception
	 */
	protected abstract ExtendedProof proveProblem() throws Throwable;

	/*
	 * (non-Javadoc)
	 * 
	 * @see proverconfigurator.runner.IProblemRunnable#isFinished()
	 */
	public boolean isFinished() {
		return isFinished;
	}

	public void actionPerformed(ActionEvent e) {
		isFinished = true;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getTimeLimit() {
		return proverInterface.getProverConfigurator().getTimeLimit();
	}

	public ProverFacade getPf() {
		return pf;
	}

	public ProverInterface getProverInterface() {
		return proverInterface;
	}
}
