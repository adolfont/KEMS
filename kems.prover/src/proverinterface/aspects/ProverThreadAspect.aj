/*
 * Created on 09/12/2005
 *  
 */
package proverinterface.aspects;

import main.proofTree.IProofTree;
import proverinterface.runner.AbstractProblemRunnable;

/**
 * An aspect that captures the thread used by the prover and interrupts it when
 * the user asks to do so.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public privileged aspect ProverThreadAspect {

	private AbstractProblemRunnable currentThread;

	public pointcut runOperation(AbstractProblemRunnable t) : 
        execution(public void AbstractProblemRunnable.run()) && target(t);

	void around(AbstractProblemRunnable t) : runOperation(t) {
		currentThread = t;

		proceed(t);
		currentThread = null;
		// tt.cancel();

	}

	// private long lastTime = -1;

	void around(): execution (public * IProofTree.addLast(*))
   {
		// first adds a node to the proof tree
		proceed();

		// if (lastTime != currentThread.getStartTime()) {
		// lastTime = currentThread.getStartTime();
		// System.err.println("Start time: "
		// + new Timestamp(lastTime).toString());
		// }

		// if it is executing an IProblemRunnable thread
		if (currentThread != null) {
			// if it has been declared finished
			if (currentThread.isFinished()) {
				// throws an Exception that will interrupt the prover
				throw new RuntimeException(
						AbstractProblemRunnable.USER_INTERRUPTION_MESSAGE);
			}
			// verificador de tempo decorrido, para poder interromper quando
			// passar de limite
			else {
				if ((System.currentTimeMillis() - currentThread.getStartTime()) > currentThread
						.getTimeLimit()) {

//					 System.err.println("End time: "
//					 + new Timestamp(System.currentTimeMillis()).toString());
//
					currentThread.interruptWithTimeLimit();
					// throw new RuntimeException(
					// AbstractProblemRunnable.TIME_LIMIT_EXCEEDED_MESSAGE);

				}
			}

		}

	}

	// faz o mesmo mas não consigo capturar a exceção no lugar certo
	// Timer timer = new Timer();
	//
	// class MyTimerTask extends TimerTask {
	//
	// private AbstractProblemRunnable cpr;
	//
	// public MyTimerTask(AbstractProblemRunnable thread) {
	// this.cpr = thread;
	// }
	//
	// public void run() {
	// System.err.println("VAI TERMINAR : "
	// + new Timestamp(System.currentTimeMillis()));
	//
	// cpr.interruptWithTimeLimit();
	// }
	//
	// }
	//
	// MyTimerTask tt = new MyTimerTask(currentThread);
	//
	// timer.schedule(tt, currentThread.getTimeLimit());

}
