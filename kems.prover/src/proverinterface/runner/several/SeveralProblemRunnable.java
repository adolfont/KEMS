/*
 * Created on 21/11/2005
 *
 */
package proverinterface.runner.several;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import proverinterface.ProverFacade;
import proverinterface.ProverInterface;
import proverinterface.runner.AbstractProblemRunnable;

/**
 * Class that runs a family of problems
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SeveralProblemRunnable extends AbstractProblemRunnable {

	private ProverFacade proverFacade;

	private SeveralProblemsRunnerFrame severalRunner;

	private int index;

	private byte runAndAnalyseOption;

	private boolean finished = false;

	private Thread thread;

	private List<String> problems;

	private long startTime;

	public SeveralProblemRunnable(SeveralProblemsRunnerFrame severalRunner,
			ProverFacade pf, List<String> problems, byte runAndAnalyseOption) {
		this.proverFacade = pf;
		this.problems = problems;
		this.severalRunner = severalRunner;
		this.runAndAnalyseOption = runAndAnalyseOption;
		index = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		Thread.currentThread().setName("Several Problems Runnable");

		try {
			severalRunner
					.getProverInterface()
					.showInfoMessage(
							"Starting "
									+ ((runAndAnalyseOption == SeveralProblemsRunnerFrame.ANALYSE_AND_RUN_OPTION) ? "run"
											: "analyse") + " procedure");

			while (!thread.isInterrupted() && !finished
					&& index < problems.size()) {
				try {
//					startTime = System.currentTimeMillis();
					severalRunner.updateResults(proverFacade, problems.get(
							index).toString(), runAndAnalyseOption,
							severalRunner.getResultsFrame());
				} catch (Throwable e) {
					severalRunner.getProverInterface().showErrorMessage(
							e.getMessage());
				}
				index++;
			}
			finished = true;
			severalRunner.getProverInterface().setIsExecutingProver(false);
			if (!thread.isInterrupted()) {
				severalRunner.notifyFinished();

				// comentado em 23-06-2006
				// show finished process frame
				JFrame jf = new JFrame("Message");
				JPanel panel = new JPanel(new GridLayout(0, 1));
				panel.add(new JLabel("Finished process for several problems",
						JLabel.CENTER));
				JPanel buttonPanel = new JPanel();
				JButton button = new JButton("Close");
				buttonPanel.add(button);
				panel.add(buttonPanel);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						((JFrame) ((JButton) e.getSource()).getParent()
								.getParent().getParent().getParent()
								.getParent()).dispose();
					}
				});
				jf.setContentPane(panel);
				jf.pack();
				jf.setLocation(
						severalRunner.getResultsFrame().getLocation().x + 10,
						severalRunner.getResultsFrame().getLocation().y + 50);
				jf.setVisible(true);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			} else {
				// System.err.println("Thread interrupted");
				thread = null;
			}
		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				severalRunner.getProverInterface().showErrorMessage(
						ProverInterface.OUT_OF_MEMORY_MESSAGE);
			} else {
				severalRunner.getProverInterface().showErrorMessage(
						e.getMessage());
			}

			severalRunner
					.getProverInterface()
					.showErrorMessage(
							"Finished with error  "
									+ ((runAndAnalyseOption == SeveralProblemsRunnerFrame.ANALYSE_AND_RUN_OPTION) ? "run"
											: "analyse") + " procedure");
		} finally {
			severalRunner
					.getProverInterface()
					.showInfoMessage(
							"Finished  "
									+ ((runAndAnalyseOption == SeveralProblemsRunnerFrame.ANALYSE_AND_RUN_OPTION) ? "run"
											: "analyse") + " procedure");
		}
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * @return
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * 
	 */
	public void interrupt() {
		if (thread != null) {
			thread.interrupt();
		}
		finished = true;
	}

	/**
	 * @param b
	 */
	public void setFinished(boolean b) {
		finished = b;

	}

	public long getStartTime() {
		return startTime;
	}

	public long getTimeLimit() {
		return severalRunner.getProverInterface().getProverConfigurator()
				.getTimeLimit();
	}

	public void setStartTime(long l) {
		startTime = l;
	}


}
