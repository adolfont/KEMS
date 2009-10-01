/*
 * Created on 21/11/2005
 *
 */
package proverinterface.runner.family;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import proverinterface.ProverFacade;
import proverinterface.runner.AbstractProblemRunnable;
import proverinterface.runner.ResultsFrameTableLine;

/**
 * Class that runs a family of problems
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FamilyProblemRunnable extends AbstractProblemRunnable {

	private ProverFacade proverFacade;

	private ProblemFamily problems;

	private FamilyProblemsRunnerFrame familyRunner;

	private int index;

	private byte runAndAnalyseOption;

	private boolean finished = false;

	private Thread thread;

	private long startTime;

	public FamilyProblemRunnable(FamilyProblemsRunnerFrame familyRunner,
			ProverFacade pf, ProblemFamily problems, byte runAndAnalyseOption) {
		this.proverFacade = pf;
		this.problems = problems;
		this.familyRunner = familyRunner;
		this.runAndAnalyseOption = runAndAnalyseOption;
		index = 0;
		startTime = -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (!thread.isInterrupted() && !finished && index < problems.size()) {
			try {
				
				startTime = System.currentTimeMillis();
				List<ResultsFrameTableLine> lines = familyRunner.updateResults(proverFacade, problems
						.get(index), runAndAnalyseOption);
				if (!thread.isInterrupted()) {
					Iterator<ResultsFrameTableLine> it = lines.iterator();
					while (it.hasNext()) {
						ResultsFrameTableLine line = (ResultsFrameTableLine) it
								.next();
						familyRunner.getResultsFrame().addProof(line);
					}
				}
			} catch (Throwable e) {

				this.familyRunner
						.openMessageFrame(e, problems.get(index), null);
				// if (e instanceof RuntimeException &&
				// e.getMessage().equals(USER_INTERRUPTION_MESSAGE)){
				// System.err.println("Pode sair");
				// }
				//
				// JFrame jf = new JFrame("Error");
				// JPanel jp = new JPanel();
				// JLabel jlabel = new JLabel
				// ("\""
				// + e.getClass().getName() + ": " + e.getMessage() + "\""
				// + " while running "
				// + problems.get(index).getInstanceName()
				// + " problem. See error log for details.", JLabel.CENTER);
				// jp.add(jlabel);
				// jf.setContentPane(jp);
				// jf.setTitle("Error");
				// jf.setLocation(this.familyRunner.getLocation().x,
				// this.familyRunner.getLocation().y);
				// jf.pack();
				// jf.setVisible(true);

				// JOptionPane.showMessageDialog(familyRunner, "\""
				// + e.getClass().getName() + ": " + e.getMessage() + "\""
				// + " while running "
				// + problems.get(index).getInstanceName()
				// + " problem. See error log for details.", "Error",
				// JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			index++;
		}
		
		this.familyRunner.getProverInterface().setIsExecutingProver(false);

		finished = true;
		if (!thread.isInterrupted()) {
			familyRunner.notifyFinished();
	
			// n�o funciona!
//			JOptionPane.showMessageDialog(familyRunner, "Finished process for several problems");


			// comentado em 23-06-2006
			// show finished process frame
			JFrame jf = new JFrame("Message");
			JPanel panel = new JPanel(new GridLayout(0, 1));
			panel.add(new JLabel("Finished process for " + getFamilyName()
					+ " family", JLabel.CENTER));
			JPanel buttonPanel = new JPanel();
			JButton button = new JButton("Close");
			buttonPanel.add(button);
			panel.add(buttonPanel);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					((JFrame) ((JButton) e.getSource()).getParent().getParent()
							.getParent().getParent().getParent()).dispose();
				}
			});
			jf.setContentPane(panel);
			jf.pack();
			jf.setLocation(familyRunner.getResultsFrame().getLocation().x,
					familyRunner.getResultsFrame().getLocation().y);
			jf.setVisible(true);
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		} else {
			// System.err.println("Thread interrupted");
			thread = null;
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

	public String getFamilyName() {
		return problems.getName();
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
		return familyRunner.getProverInterface().getProverConfigurator().getTimeLimit();
	}

}
