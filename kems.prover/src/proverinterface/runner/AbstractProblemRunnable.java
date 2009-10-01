package proverinterface.runner;


/**
 * An interface for the classes that run one or more problems
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public abstract class AbstractProblemRunnable implements Runnable {

	public static final String USER_INTERRUPTION_MESSAGE = "Interrupted by the user.";

	/**
	 * @return true whe the problem is proof is finished
	 */
	public abstract boolean isFinished();

	/**
	 * @return time the prover started to prover current problem
	 */
	public abstract long getStartTime();

	/**
	 * @return time limit to prove a problem
	 */
	public abstract long getTimeLimit();

	public void interruptWithTimeLimit() {
		if (!isFinished()) {
			throw new RuntimeException(showHumanReadableTime(getTimeLimit()) + " time limit exceeded");
		}
	}

	private String showHumanReadableTime(long timeLimit) {
		long seconds =  (timeLimit/1000);
		long miliseconds = timeLimit- seconds*1000;
		long minutes = seconds/60;
		seconds = seconds - minutes*60;
		
		String result = "";
		
		if (minutes>0){
			result += minutes + " minute(s) ";
		}
		
		if (seconds>0){
			result += seconds + " second(s) ";
		}

		if (miliseconds>0){
			result += miliseconds + " milisecond(s) ";
		}

		
		return result;
	}
	
	

}
