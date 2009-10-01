/*
 * Created on 09/11/2005
 *
 */
package proverinterface.runner.family;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import logic.problem.Problem;
import main.exceptions.KEMSException;
import main.tableau.verifier.ExtendedProof;
import proverinterface.ProverConfiguration;
import proverinterface.ProverFacade;
import proverinterface.ProverInterface;
import proverinterface.ROFileChooser;
import proverinterface.runner.AbstractProblemRunnable;
import proverinterface.runner.ProblemsPanel;
import proverinterface.runner.ResultsFrameTableLine;

/**
 * Interface object used to select and run a problem family.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class FamilyProblemsRunnerFrame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 966859768654590816L;

	private static final String OPEN = "Choose one or more instances of a problem family";

	private static final String RUN_CHOSEN_FAMILY = "Run chosen family";

	private static final String ANALYSE_CHOSEN_FAMILY = "Analyse chosen family";

	public static final String RUN = "Run";

	public static final String STOP = "Stop";

	private String familyPath;

	private TextFieldStringPanel tf;

	private ProblemsPanel problemList;

	private boolean familyChosen;

	private JMenuItem runMenuItem, analyseMenuItem;

	private ProblemFamily problems;

	private ProverInterface proverInterface;

	private JPanel resultsPanel;

	private FamilyResultsFrame resultsFrame;

//	private boolean resultsChanged = false;

	public static final byte ANALYSE_OPTION = 0;

	public static final byte ANALYSE_AND_RUN_OPTION = 1;

	private FamilyProblemRunnable problemsRunner;

	private JButton stopButton;

	public FamilyProblemsRunnerFrame(ProverInterface proverInterface) {
		super("Family Runner");
		familyChosen = false;

		JPanel panel = new JPanel(new BorderLayout());
		this.proverInterface = proverInterface;

		// choose file
//		GridBagLayout gdb = new GridBagLayout();
		// JPanel chooseFile = new JPanel(gdb);
		JPanel chooseFile = new JPanel(new GridLayout(2, 1));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 4;

		tf = new TextFieldStringPanel("Family path: ", familyPath, 30);
		tf.addActionListener(this);
		tf.setVisible(true);
		// chooseFile.add(tf, c);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 4;
		JButton openButton = new JButton(OPEN);
		openButton
				.setToolTipText("You can to choose one or more files containing instances of a problem family");
		openButton.addActionListener(this);
		chooseFile.add(openButton);
		chooseFile.add(tf);
		panel.add(chooseFile, BorderLayout.NORTH);

		resultsPanel = new JPanel(new GridLayout(0, 1));
		panel.add(resultsPanel, BorderLayout.SOUTH);

		createMenu();

		setContentPane(panel);
		this.setLocation(proverInterface.getLocation().x+10,proverInterface.getLocation().y+10);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void createMenu() {
		// Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu(RUN);
		menu.setMnemonic(KeyEvent.VK_U);
		menu.getAccessibleContext().setAccessibleDescription(
				"Run or analyse problem families");
		menuBar.add(menu);

		runMenuItem = new JMenuItem(RUN_CHOSEN_FAMILY);
		runMenuItem.setMnemonic(KeyEvent.VK_R);
		runMenuItem.getAccessibleContext().setAccessibleDescription(
				"Runs chosen family");
		runMenuItem.addActionListener(this);
		runMenuItem.setEnabled(familyChosen);
		menu.add(runMenuItem);

		analyseMenuItem = new JMenuItem(ANALYSE_CHOSEN_FAMILY);
		analyseMenuItem.setMnemonic(KeyEvent.VK_A);
		analyseMenuItem.getAccessibleContext().setAccessibleDescription(
				"Analyse chosen family");
		analyseMenuItem.addActionListener(this);
		analyseMenuItem.setEnabled(familyChosen);
		menu.add(analyseMenuItem);

		setJMenuBar(menuBar);

		// botao para para o processo de prova
		stopButton = new JButton(STOP);
		stopButton.setVisible(false);
		menuBar.add(stopButton);
		stopButton.addActionListener(this);
		stopButton
				.setToolTipText("Stops the current family proving/analysing process");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(OPEN)) {
			openFileChooser();
		}
		if (e.getActionCommand().equals(STOP)) {
			stopRunningFamily();
		}
		try {
			if (e.getActionCommand().equals(RUN_CHOSEN_FAMILY)) {
				if (!proverInterface.isExecutingProver()) {
					proverInterface.setIsExecutingProver(true);
					runAndAnalyseProblemFamily(ANALYSE_AND_RUN_OPTION);
				} else {
					proverInterface.showIsExecutingMessage();
				}

			}
			if (e.getActionCommand().equals(ANALYSE_CHOSEN_FAMILY)) {
				if (!proverInterface.isExecutingProver()) {
					proverInterface.setIsExecutingProver(true);
					runAndAnalyseProblemFamily(ANALYSE_OPTION);
				} else {
					proverInterface.showIsExecutingMessage();
				}
			}
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(this, exception.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			proverInterface.setIsExecutingProver(false);
		}

	}

	private void runAndAnalyseProblemFamily(byte runaAndAnalyseOption)
			throws Exception {
		if (problemsRunner == null || problemsRunner.isFinished()) {
			ProverFacade pf = new ProverFacade();

			resultsFrame = new FamilyResultsFrame(this, problems,
					proverInterface.getProverConfigurator().getStrategyName(),
					runaAndAnalyseOption);

			resultsFrame.setLocation(this.getLocation().x + 400, this
					.getLocation().y);
			problemsRunner = new FamilyProblemRunnable(this, pf, problems,
					runaAndAnalyseOption);
			problemsRunner.start();
			stopButton.setVisible(true);

		} else {
			throw new Exception(
					"There is a family being runned. Try again after that family finishes.");
		}

	}

	/**
	 * 
	 */
	private void openFileChooser() {
		// Create a file chooser
		UIManager.put("FileChooser.readOnly", Boolean.valueOf(true));

		String initialPath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "problems";
		final ROFileChooser fc = new ROFileChooser(initialPath, true);
		fc.setMultiSelectionEnabled(true);
		fc.setApproveButtonText("Choose");
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDragEnabled(false);

		// In response to a button click:
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File[] files = fc.getSelectedFiles();

			try {
				reconfigureFamilyRunner(files);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void reconfigureFamilyRunner(File[] files) throws Exception {
		// FamilyProblemBasicInfo[] problems = null;

		try {
			problems = getFamilyProblems(files);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		tf.setTextFieldText(problems.getPath());

		if (problemList == null) {
			problemList = new ProblemsPanel(this);
			getContentPane().add(problemList, BorderLayout.CENTER);
		}
		problemList.setProblems(problems);
		familyChosen = true;
		runMenuItem.setEnabled(true);
		analyseMenuItem.setEnabled(true);

		pack();
		repaint();

	}

	private ProblemFamily getFamilyProblems(File[] files) throws Exception {
		class ProblemFilter implements FilenameFilter {
			private String familyName, numberSeparator, termination;

//			public ProblemFilter(String familyName, String numberSeparator,
//					String termination) {
//				this.familyName = familyName;
//				this.numberSeparator = numberSeparator;
//				this.termination = termination;
//			}

			public boolean accept(File dir, String name) {
				return name.endsWith(termination)
						&& name.startsWith(familyName + numberSeparator);
			}
		}

		String path = files[0].getParentFile().toString();

		ProblemFamily problems = new ProblemFamily(path);

		FamilyProblemBasicInfo familyProblemInfo = new FamilyProblemBasicInfo(
				problems, files[0]);

//		FilenameFilter filter = new ProblemFilter(familyProblemInfo
//				.getFamilyName(), FamilyProblemBasicInfo.NUMBER_SEPARATOR,
//				familyProblemInfo.getTermination());
		problems.setSize(files.length);
		problems.add(familyProblemInfo);

		String oldFamilyName = problems.getName();
		for (int i = 1; i < files.length; i++) {

			problems.add(new FamilyProblemBasicInfo(problems, files[i]));

			if (!oldFamilyName.equals(problems.getName())) {
				throw new KEMSException("Problems of different families ("
						+ oldFamilyName + " and " + problems.getName()
						+ ") in the same selection.");
			}
		}

		return problems;
	}

	/**
	 * @param ep
	 */
	public List<ResultsFrameTableLine> updateResults(ProverFacade proverFacade,
			FamilyProblemBasicInfo problemInfo, byte runAndAnalyseOption)
			throws Throwable {
		List<ResultsFrameTableLine> result = new ArrayList<ResultsFrameTableLine>();

//		resultsChanged = true;

		ResultsFrameTableLine line = null;
		Problem p = proverFacade.analyse(problemInfo.getFileName(),
				proverInterface.getProverConfigurator()
						.createProverConfigurationWithCurrentChoices());
		if (runAndAnalyseOption == FamilyProblemsRunnerFrame.ANALYSE_OPTION) {
			line = new FamilyProblemAnalysisResultsFrameTableLine(problemInfo,
					p);
			result.add(line);
		} else {
			// tries with all prover configurations
			Vector<ProverConfiguration> proverConfigurations = proverInterface
					.getProverConfigurator().createProverConfigurationVector();

			Iterator<ProverConfiguration> it = proverConfigurations.iterator();
			while (it.hasNext()) {
				ProverConfiguration pc = (ProverConfiguration) it.next();
				ExtendedProof ep = null;
				try {
					ep = proverFacade.proveAndVerifyFile(problemInfo
							.getFileName(), pc);
				} catch (Throwable e) {

					openMessageFrame(e, problemInfo, pc);
					e.printStackTrace();
					if (e instanceof RuntimeException
							&& e
									.getMessage()
									.equals(
											AbstractProblemRunnable.USER_INTERRUPTION_MESSAGE)) {
						problemsRunner.interrupt();
						break;
					}

					// JOptionPane.showMessageDialog(this, "\""
					// + e.getClass().getName() + ": " + e.getMessage() + "\""
					// + " while running "
					// + problemInfo.getInstanceName()
					// + " problem. See error log for details.", "Error",
					// JOptionPane.ERROR_MESSAGE);
				}
				if (ep != null) {
					line = new FamilyProblemProofResultsFrameTableLine(
							problemInfo, ep, p);
					result.add(line);
				}
			}

		}

		return result;
	}

	/**
	 * @param e
	 * @param problemInfo
	 */
	public void openMessageFrame(Throwable e,
			FamilyProblemBasicInfo problemInfo, ProverConfiguration pc) {
		JFrame jf = new JFrame("Error");
		JPanel jp = new JPanel(new GridLayout(0, 1));
		JLabel jlabel1 = new JLabel("\"" + e.getClass().getName() + ": "
				+ e.getMessage() + "\"" + " while running "
				+ problemInfo.getInstanceName() + " problem.", JLabel.CENTER);
		jp.add(jlabel1);
		if (pc != null) {
			JLabel jlabel1_1 = new JLabel("Prover configuration: "
					+ pc.toString(), JLabel.CENTER);
			jp.add(jlabel1_1);
		}
		JLabel jlabel2 = new JLabel("See error log for details.", JLabel.CENTER);
		jp.add(jlabel2);
		jf.setContentPane(jp);
		jf.setTitle("Error");
		jf.setLocation(this.resultsFrame.getLocation().x, this.resultsFrame
				.getLocation().y);
		jf.pack();
		jf.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void stopRunningFamily() {
		if (problemsRunner != null) {
			notifyFinished();
		}

	}

	/**
	 * 
	 */
	public void notifyFinished() {
		synchronized (problemsRunner) {
			stopButton.setVisible(false);
			problemsRunner.interrupt();
			resultsFrame.finishRun();
		}
	}

	public FamilyResultsFrame getResultsFrame() {
		return resultsFrame;
	}

	public ProverInterface getProverInterface() {
		return proverInterface;
	}

}
