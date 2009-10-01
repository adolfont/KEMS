/*
 * Created on 09/11/2005
 *
 */
package proverinterface.runner.several;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import logic.problem.Problem;
import main.tableau.verifier.ExtendedProof;
import proverinterface.ProverConfiguration;
import proverinterface.ProverFacade;
import proverinterface.ProverInterface;
import proverinterface.ROFileChooser;
import proverinterface.runner.AbstractProblemRunnable;
import proverinterface.runner.ProblemsPanel;
import proverinterface.runner.ResultsFrameTableLine;
import proverinterface.runner.oneproblem.OneProblemRunnable;
import util.StringUtil;

/**
 * Interface object used to select and run a problem family.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SeveralProblemsRunnerFrame extends JFrame implements
		ActionListener {

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = 966859768654590816L;

	/** menu options */

	private static final String OPEN = "Choose one or more problem instances";

	private static final String RUN_CHOSEN_PROBLEMS = "Run problems";

	private static final String ANALYSE_CHOSEN_PROBLEMS = "Analyse problems";

	public static final String RUN = "Run";

	public static final String STOP = "Stop";

	private static final String PROBLEM_LIST = "Problem List";

	private static final String LOAD_PROBLEM_LIST = "Load";

	private static final String CLEAR_PROBLEM_LIST = "Clear";

	private static final String SAVE_PROBLEM_LIST = "Save";

	private static final String RESULTS_WINDOW = "Results window";

	private static final String SHOW_CURRENT_RESULTS= "Show current results";

	/** a panel with a list of problems */
	private ProblemsPanel problemListPanel;

	/** indicates if any problem has been chosen */
	private boolean problemsChosen;

	/** a menu item to run problems, that can become enabled */
	private JMenuItem runMenuItem;

	/** a menu item to analyse problems, that can become enabled */
	private JMenuItem analyseMenuItem;

	private ProverInterface proverInterface;

	private JPanel resultsPanel;

	private SeveralProblemsResultsFrame resultsFrame;

	public static final byte ANALYSE_OPTION = 0;

	public static final byte ANALYSE_AND_RUN_OPTION = 1;


	private SeveralProblemRunnable severalProblemRunnable;

	private JButton stopButton;

	private JMenuItem saveMenuItem;

	private JMenuItem showMenuItem;

	public SeveralProblemsRunnerFrame(ProverInterface proverInterface) {
		super("Several Problems Runner");
		problemsChosen = false;

		JPanel panel = new JPanel(new BorderLayout());
		this.proverInterface = proverInterface;

		JPanel chooseFile = new JPanel(new GridLayout(2, 1));
		JButton openButton = new JButton(OPEN);
		openButton
				.setToolTipText("You can to choose one or more files containing problem instances");
		openButton.addActionListener(this);
		chooseFile.add(openButton);
		// chooseFile.add(tf);
		panel.add(chooseFile, BorderLayout.NORTH);

		resultsPanel = new JPanel(new GridLayout(0, 1));
		panel.add(resultsPanel, BorderLayout.SOUTH);

		createMenu();

		setContentPane(panel);
		this.setLocation(proverInterface.getLocation().x + 10, proverInterface
				.getLocation().y + 10);
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
				"Run or analyse problem instances");
		menuBar.add(menu);

		runMenuItem = new JMenuItem(RUN_CHOSEN_PROBLEMS);
		runMenuItem.setMnemonic(KeyEvent.VK_R);
		runMenuItem.getAccessibleContext().setAccessibleDescription(
				"Runs chosen problems");
		runMenuItem.addActionListener(this);
		runMenuItem.setEnabled(problemsChosen);
		menu.add(runMenuItem);

		analyseMenuItem = new JMenuItem(ANALYSE_CHOSEN_PROBLEMS);
		analyseMenuItem.setMnemonic(KeyEvent.VK_A);
		analyseMenuItem.getAccessibleContext().setAccessibleDescription(
				"Analyses chosen problems");
		analyseMenuItem.addActionListener(this);
		analyseMenuItem.setEnabled(problemsChosen);
		menu.add(analyseMenuItem);

		menu = new JMenu(PROBLEM_LIST);
		menu.setMnemonic(KeyEvent.VK_P);
		menu.getAccessibleContext().setAccessibleDescription(
				"Load or clear problem list");

		JMenuItem clearMenuItem = new JMenuItem(CLEAR_PROBLEM_LIST);
		clearMenuItem.setMnemonic(KeyEvent.VK_C);
		clearMenuItem.getAccessibleContext().setAccessibleDescription(
				"Clear problem list");
		clearMenuItem.addActionListener(this);
		menu.add(clearMenuItem);

		JMenuItem loadMenuItem = new JMenuItem(LOAD_PROBLEM_LIST);
		loadMenuItem.setMnemonic(KeyEvent.VK_L);
		loadMenuItem.getAccessibleContext().setAccessibleDescription(
				"Load a problem list");
		loadMenuItem.addActionListener(this);
		menu.add(loadMenuItem);
		menuBar.add(menu);

		saveMenuItem = new JMenuItem(SAVE_PROBLEM_LIST);
		saveMenuItem.setMnemonic(KeyEvent.VK_S);
		saveMenuItem.getAccessibleContext().setAccessibleDescription(
				"Save current problem list");
		saveMenuItem.addActionListener(this);
		saveMenuItem.setEnabled(false);
		menu.add(saveMenuItem);
		menuBar.add(menu);

		
		menu = new JMenu(RESULTS_WINDOW);
		menu.setMnemonic(KeyEvent.VK_E);
		menu.getAccessibleContext().setAccessibleDescription(
				"Show results window");

		showMenuItem = new JMenuItem(SHOW_CURRENT_RESULTS);
		showMenuItem.setMnemonic(KeyEvent.VK_S);
		showMenuItem.getAccessibleContext().setAccessibleDescription(
				"Show current results window");
		showMenuItem.addActionListener(this);
		showMenuItem.setEnabled(false);
		menu.add(showMenuItem);
		menuBar.add(menu);


		
		setJMenuBar(menuBar);

		// botao parar para o processo de prova
		stopButton = new JButton(STOP);
		stopButton.setVisible(false);
		menuBar.add(stopButton);
		stopButton.addActionListener(this);
		stopButton
				.setToolTipText("Stops the current proving/analysing process");

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
			stopRunningPoblems();
		}
		if (e.getActionCommand().equals(LOAD_PROBLEM_LIST)) {
			loadProblemList();
		}
		if (e.getActionCommand().equals(CLEAR_PROBLEM_LIST)) {
			clearProblemList();
		}
		if (e.getActionCommand().equals(SAVE_PROBLEM_LIST)) {
			saveProblemList();
		}
		if (e.getActionCommand().equals(SHOW_CURRENT_RESULTS)) {
			resultsFrame.setVisible(true);
		}

		try {
			if (e.getActionCommand().equals(RUN_CHOSEN_PROBLEMS)) {
				// only runs if no one else is running
				if (!proverInterface.isExecutingProver()) {
					proverInterface.setIsExecutingProver(true);
					runAndAnalyseProblems(ANALYSE_AND_RUN_OPTION);
				} else {
					proverInterface.showIsExecutingMessage();
				}
			} else if (e.getActionCommand().equals(ANALYSE_CHOSEN_PROBLEMS)) {
				if (!proverInterface.isExecutingProver()) {
					proverInterface.setIsExecutingProver(true);
					runAndAnalyseProblems(ANALYSE_OPTION);
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

	private void clearProblemList() {
		if (problemListPanel != null) {
			problemListPanel.clearProblemList();
			runMenuItem.setEnabled(false);
			analyseMenuItem.setEnabled(false);
			saveMenuItem.setEnabled(false);
		}
	}

	private void runAndAnalyseProblems(byte runAndAnalyseOption)
			throws Exception {
		if (severalProblemRunnable == null || severalProblemRunnable.isFinished()) {
			ProverFacade pf = new ProverFacade();

			resultsFrame = new SeveralProblemsResultsFrame(this,
					problemListPanel.getProblemNames(),
					runAndAnalyseOption);

			resultsFrame.setLocation(this.getLocation().x + 400, this
					.getLocation().y);
			severalProblemRunnable = new SeveralProblemRunnable(this, pf,
					problemListPanel.getProblemNames(), runAndAnalyseOption);

			severalProblemRunnable.start();

			stopButton.setVisible(true);
			showMenuItem.setEnabled(true);

		} else {
			throw new Exception(
					"There are problems being runned. Try again after those problems finish.");
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
				reconfigureSeveralRunner(files);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void reconfigureSeveralRunner(File[] files) throws Exception {
		// FamilyProblemBasicInfo[] problems = null;

		// try {
		// problems = getFamilyProblems(files);
		// } catch (Exception e) {
		// JOptionPane.showMessageDialog(this, e.getMessage(), "Error",
		// JOptionPane.ERROR_MESSAGE);
		// }
		// tf.setTextFieldText(problems.getPath());

		if (problemListPanel == null) {
			problemListPanel = new ProblemsPanel(this);
			getContentPane().add(problemListPanel, BorderLayout.CENTER);
		}
		problemListPanel.addProblems(files);
		problemsChosen = true;
		runMenuItem.setEnabled(true);
		analyseMenuItem.setEnabled(true);
		saveMenuItem.setEnabled(true);

		pack();
		repaint();

	}

	private List<ResultsFrameTableLine> analyseOneProblem(ProverFacade proverFacade,
			String problemName, SeveralProblemsResultsFrame frame) {
		List<ResultsFrameTableLine> result = new ArrayList<ResultsFrameTableLine>();

		ResultsFrameTableLine line = null;

		Problem p;
		try {
			proverInterface.showInfoMessage("Analysing " + problemName);

			p = proverFacade.analyse(problemName, proverInterface
					.getProverConfigurator().createProverConfigurationWithCurrentChoices());

			line = new SeveralProblemAnalysisResultsFrameTableLine(problemName,
					p);
			result.add(line);
			frame.addProof(line);
			proverInterface.showInfoMessage("Problem " + problemName
					+ "analysed");
			return result;

		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				proverInterface
						.showErrorMessage(ProverInterface.OUT_OF_MEMORY_MESSAGE);
			} else {
				proverInterface.showErrorMessage(e.getMessage());
			}

			proverInterface.showErrorMessage("Problem " + problemName
					+ " not analysed");
			return null;
		}
	}

	/**
	 * @param frame
	 * @param ep
	 */
	public List<ResultsFrameTableLine> updateResults(ProverFacade proverFacade, String problemName,
			byte runAndAnalyseOption, SeveralProblemsResultsFrame frame)
			throws Throwable {

		if (runAndAnalyseOption == SeveralProblemsRunnerFrame.ANALYSE_OPTION) {
			return analyseOneProblem(proverFacade, problemName, frame);
		} else {
			return runOneProblemWithConfigurations(proverFacade, problemName,
					frame);
		}

	}

	private List<ResultsFrameTableLine> runOneProblemWithConfigurations(ProverFacade proverFacade,
			String problemName, SeveralProblemsResultsFrame frame)
			throws Throwable {
		ArrayList<ResultsFrameTableLine> result = 
			 new ArrayList<ResultsFrameTableLine>();

		ResultsFrameTableLine line = null;

		// tries with all prover configurations
		Vector<ProverConfiguration> proverConfigurations = proverInterface.getProverConfigurator()
				.createProverConfigurationVector();

		Iterator<ProverConfiguration> it = proverConfigurations.iterator();

		Problem p = null;

		try {
			proverInterface
					.showInfoMessage("Starting analysis procedure for problem "
							+ problemName);
			p = proverFacade.analyse(problemName, proverInterface
					.getProverConfigurator().createProverConfigurationWithCurrentChoices());
			proverInterface
					.showInfoMessage("Finished with success analysis procedure for problem "
							+ problemName);
		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				proverInterface
						.showErrorMessage(ProverInterface.OUT_OF_MEMORY_MESSAGE);
			} else {
				proverInterface.showErrorMessage(e.getMessage());
			}
			proverInterface
					.showInfoMessage("Finished with error analysis and proof procedure for problem "
							+ problemName);

			return null;
		}
		while (it.hasNext()) {
			ProverConfiguration pc = (ProverConfiguration) it.next();
			ExtendedProof ep = null;
			try {

				proverInterface
						.showInfoMessage("Starting proof and verify procedure for problem "
								+ problemName + " with " + pc.toString());
				severalProblemRunnable.setStartTime(System.currentTimeMillis());
				ep = proverFacade.proveAndVerifyFile(problemName, pc);
				proverInterface.showDebugMessage(OneProblemRunnable
						.createProofInformationMessage(ep, pc));
				proverInterface
						.showInfoMessage("Finished with success proof and verify procedure for problem "
								+ problemName);
			} catch (Throwable e) {
				if (e instanceof OutOfMemoryError) {
					proverInterface
							.showErrorMessage(ProverInterface.OUT_OF_MEMORY_MESSAGE);
				} else {
					proverInterface.showErrorMessage(e.getMessage());
				}

				proverInterface
						.showInfoMessage("Finished with error proof and verify procedure for problem "
								+ problemName);

				if (e instanceof RuntimeException
						&& e
								.getMessage()
								.equals(
										AbstractProblemRunnable.USER_INTERRUPTION_MESSAGE)) {
					severalProblemRunnable.interrupt();
					break;
				}

			}
			if (ep != null) {
				line = new SeveralProblemProofResultsFrameTableLine(
						problemName, ep, p);
				result.add(0,line);
				frame.addProof(line);
			}
		}

		return result;
	} /*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
		 */

	public void stopRunningPoblems() {
		if (severalProblemRunnable != null) {
			notifyFinished();
		}

	}

	private void loadProblemList() {
		// Create a file chooser
		UIManager.put("FileChooser.readOnly", Boolean.valueOf(true));

		String initialPath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "problems";
		final ROFileChooser fc = new ROFileChooser(initialPath, true);
		fc.setMultiSelectionEnabled(false);
		fc.setApproveButtonText("Choose");
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDragEnabled(false);

		// In response to a button click:
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();

			try {
				reconfigureSeveralRunner(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void saveProblemList() {
		// Create a file chooser
		UIManager.put("FileChooser.readOnly", Boolean.valueOf(true));

		String initialPath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "problems";
		final ROFileChooser fc = new ROFileChooser(initialPath, true);
		fc.setMultiSelectionEnabled(false);
		fc.setApproveButtonText("Save");
		// fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setDragEnabled(false);

		// In response to a button click:
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = fc.getSelectedFile();

			if (file.exists()) {
				int result = JOptionPane.showConfirmDialog(this,
						"Are you sure you want to overwrite \""
								+ file.getName() + "\" ?", "Overwrite?",
						JOptionPane.YES_NO_OPTION);
				if (result != JOptionPane.OK_OPTION) {
					return;
				}
			}

			saveProblemListInFile(file);

		}
	}

	private void saveProblemListInFile(File file) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("### Problem list saved in "
					+ new Timestamp(System.currentTimeMillis()).toString()
					+ StringUtil.LINE_SEPARATOR);

			Iterator<String> it = problemListPanel.getProblemNames().iterator();
			while (it.hasNext()) {
				String filename = it.next().toString();
				writer.write(filename + StringUtil.LINE_SEPARATOR);
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"It was not possible to save this problem list. See error log for details.",
							"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void reconfigureSeveralRunner(File file) throws Exception {
		File[] files = verifyProblemList(file);
		if (files != null) {
			reconfigureSeveralRunner(files);
		} else {
			JOptionPane.showMessageDialog(this,
					"This is not a valid problem list.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * verifies problem list and gives filename array
	 * 
	 * @param problemList
	 * @return
	 * @throws Exception
	 */
	private File[] verifyProblemList(File problemList) throws Exception {
		List<File> result = new ArrayList<File>();
		FileReader filereader = new FileReader(problemList);
		BufferedReader reader = new BufferedReader(filereader);

		while (reader.ready()) {
			String possibleFileName = reader.readLine();
			// replaces windows file separators with current system file
			// separators
			possibleFileName = possibleFileName.replaceAll("\\\\",
					StringUtil.FILE_SEPARATOR);
			// replaces unix file separators with current system file separators
			possibleFileName = possibleFileName.replaceAll("/",
					StringUtil.FILE_SEPARATOR);
			File possibleFile = new File(possibleFileName);
			if (possibleFile.exists()) {
				result.add(possibleFile);
			}
		}

		if (result.isEmpty()) {
			return null;
		} else {
			File[] resultArray = new File[result.size()];
			for (int i = 0; i < resultArray.length; i++) {
				resultArray[i] = (File) result.get(i);
			}
			return resultArray;
		}

	}

	/**
	 * 
	 */
	public void notifyFinished() {
		synchronized (severalProblemRunnable) {
			stopButton.setVisible(false);
			showMenuItem.setEnabled(false);
			severalProblemRunnable.interrupt();
			resultsFrame.finishRun();
		}
	}

	public SeveralProblemsResultsFrame getResultsFrame() {
		return resultsFrame;
	}

	public ProverInterface getProverInterface() {
		return proverInterface;
	}

}
