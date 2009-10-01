package proverinterface.commandline;

import java.sql.Time;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import logic.problem.Problem;
import main.tableau.verifier.ExtendedProof;

import org.apache.log4j.Logger;

import proverinterface.ProverConfiguration;
import proverinterface.ProverFacade;
import proverinterface.runner.AbstractProblemRunnable;
import util.MemoryManager;

/**
 * A problem runnable for the command-line runner.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class CommandLineRunnable extends AbstractProblemRunnable {

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(CommandLineRunnable.class);

	private String problemFilename;

	private ProverConfiguration proverConfiguration;

	private boolean finished;

	private long startTime;

	private long timeLimit;

	private ExtendedProof proof;

	private Problem problem;

	private String message;

	private static Set<ProverConfiguration> finishedProverConfigurations;

	static {
		finishedProverConfigurations = new HashSet<ProverConfiguration>();
	}

	public CommandLineRunnable(String problemFilename, ProverConfiguration pc) {
		this.problemFilename = problemFilename;
		this.proverConfiguration = pc;
		timeLimit = pc.getTimeLimit();
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public long getTimeLimit() {
		return timeLimit;
	}

	public void run() {
		startTime = System.currentTimeMillis();
		finished = false;

		ProverFacade pf = new ProverFacade(40, 1000);
		try {
			logger.debug(new Time(System.currentTimeMillis())
					+ " - Starting to prove " + problemFilename + " with "
					+ proverConfiguration);

			logger
					.debug("Time limit: "
							+ ((proverConfiguration.getTimeLimit() - CommandLineRunner.TIME_LIMIT_INCREASE) / CommandLineRunner.TIME_UNIT)
							+ " " + CommandLineRunner.TIME_UNIT_NAME + "(s)");

			try {

				problem = pf.analyse(problemFilename, proverConfiguration);
			} catch (Throwable e) {
				e.printStackTrace();
				throw new ParseException("Problems while parsing "
						+ problemFilename, 0);
			}

			if (setContainsEqualBased(finishedProverConfigurations,
					proverConfiguration)) {
				throw new RuntimeException(
						"This prover configuration has not finished a smaller problem");
			}

			proof = null;
			MemoryManager.runGC(40, 1000);

			proof = pf.proveAndVerifyFile(problemFilename, proverConfiguration);
			finished = true;
			message = "Success";
		} catch (Throwable e) {
			// CommandLineRunner.println("Error while trying to prove " +
			// problemFilename
			// + " with " + proverConfiguration + " : " + e.getMessage());
			logger.debug("Error", e);
			message = "Error: " + e.getMessage();

			// System.err.println("FPC bef: "+ finishedProverConfigurations);
			if (!setContainsEqualBased(finishedProverConfigurations,
					proverConfiguration)) {
				finishedProverConfigurations.add(proverConfiguration.clone());
			}
			// System.err.println("Fpc: "+finishedProverConfigurations);
			finished = true;
		}
	}

	public boolean setContainsEqualBased(Set<ProverConfiguration> set,
			ProverConfiguration pc) {
		Iterator<ProverConfiguration> it = set.iterator();

		while (it.hasNext()) {
			if (it.next().equals(pc)) {
				return true;
			}
		}

		return false;
	}

	public ExtendedProof getProof() {
		return proof;
	}

	public Problem getProblem() {
		return problem;
	}

	public String getMessage() {
		return message;
	}

}
