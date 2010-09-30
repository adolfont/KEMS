/*
 * Created on 08/11/2005
 *
 */
package proverinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import main.tableau.IProof;
import main.tableau.verifier.ExtendedProof;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import proverinterface.commandline.CommandLineRunner;
import proverinterface.proofviewer.ProofViewer;
import proverinterface.runner.family.FamilyProblemsRunnerFrame;
import proverinterface.runner.oneproblem.OneProblemAsStringRunnable;
import proverinterface.runner.oneproblem.OneProblemInFileRunnable;
import proverinterface.runner.oneproblem.OneProblemRunnable;
import proverinterface.runner.several.SeveralProblemsRunnerFrame;
import util.StringUtil;

/**
 * A graphical interface for the KE multi-strategy tableau prover.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ProverInterface extends JFrame implements ActionListener {

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -3394952312771716351L;

	/**
	 * logger
	 */
	public static Logger logger = Logger.getLogger(ProverInterface.class);

	private static final String DEFAULT_VERSION = "1.1";
	private static final String DEFAULT_RELASE_DATE = "Sep 30th, 2010";
	private static final String DEFAULT_SITE = "http://www.dainf.ct.utfpr.edu.br/~adolfo/KEMS";

	// lock file name
	private static final String LOCK_FILENAME = ".lock";

	public static final String PROVER_NAME = "KEMS";

	// action menu options
	private static final String EDITOR_OPTION = "Editor";

	private static final String FAMILY_OPTION = "Family - Choose and Run a Problem Family";

	private static final String SEVERAL_OPTION = "Several - Choose and Run Several Problems";

	private static final String INSTANCE_OPTION = "Instance - Choose, Run and View Proof of a Problem Instance";

	private static final String CONFIGURE_OPTION = "Prover";

	private static final String LOGGER_OPTION = "Logger";

	private static final String ABOUT_OPTION = "About " + PROVER_NAME;

	private static final String CONTENTS_OPTION = "Help Contents";

	// window labels
	private static final String PROVER_FULL_NAME = "KE Multi-Strategy Tableau Prover";

	public static final String OUT_OF_MEMORY_MESSAGE = "The amount of memory available was not enough to perform some operation.";

	/**
	 * A frame that allows the user to run/analyse a problem family
	 */
	private FamilyProblemsRunnerFrame familyRunner;

	/**
	 * A frame that allows the user to run several problem instances
	 */
	private SeveralProblemsRunnerFrame severalRunner;

	/**
	 * A frame that allows the user to edit and run one problem
	 */
	private ProblemEditor problemEditor;

	/**
	 * A frame that allows the user to edit and run one problem
	 */
	private LogViewer logViewer;

	/**
	 * A frame that allows the user to configure the prover
	 */
	private ProverConfigurator proverConfigurator;

	/** KEMS properties */
	private Properties properties;

	/** a lock file */
	private File _lockFile;

	/** a frame that contains a button to stop running a problem */
	private JFrame stopFrame;

	/** a button to stop running a problem */
	private JButton stopButton;

	/** a flag to indicate if the prover is executing/running some problem */
	private boolean isExecutingProver;

	/** a lock to the "isExecutingProver" variable */
	private Object isExecutingLock;

	/**
	 * Creates a prover interface
	 */
	public ProverInterface() {
		super();

		try {
			logViewer = new LogViewer(this, "log.txt");
			loadProperties();
			checkLock();

			proverConfigurator = new ProverConfigurator();

			isExecutingProver = false;
			isExecutingLock = new Object();

			setTitle(PROVER_NAME + " - " + PROVER_FULL_NAME);

			this.setJMenuBar(createMenuBar());

			drawsMainWindow();
			logViewer.appendInfoMessage("Prover interface started.");
		} catch (Throwable e) {
			if (e instanceof OutOfMemoryError) {
				showErrorMessage(OUT_OF_MEMORY_MESSAGE);
			} else {
				showErrorMessage(e.getMessage());
			}

		}

	}

	/**
	 * Creates main menu bar and its sub menus
	 * 
	 * @return a menu bar with sub menus
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		createFirstSubMenu(menuBar);
		createSecondSubMenu(menuBar);
		// createThirdSubMenu(menuBar);
		createFourthSubMenu(menuBar);

		return menuBar;
	}

	private void createFirstSubMenu(JMenuBar menuBar) {
		JMenu menu;
		JMenuItem menuItem;
		// Build the first menu.
		menu = new JMenu("Problem");
		menu.setMnemonic(KeyEvent.VK_P);
		menu.getAccessibleContext().setAccessibleDescription(
				"Configure the problem(s) to be run by KEMS");
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem(EDITOR_OPTION, KeyEvent.VK_E);
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Opens the problem editor");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		// menuItem = new JMenuItem(FAMILY_OPTION, KeyEvent.VK_F);
		// menuItem.getAccessibleContext().setAccessibleDescription(
		// "Opens the family problem chooser");
		// menu.add(menuItem);
		// menuItem.addActionListener(this);

		menuItem = new JMenuItem(INSTANCE_OPTION, KeyEvent.VK_I);
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Opens the problem instance chooser");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem(SEVERAL_OPTION, KeyEvent.VK_S);
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Opens the several problems chooser");
		menu.add(menuItem);
		menuItem.addActionListener(this);
	}

	private void createSecondSubMenu(JMenuBar menuBar) {
		JMenu menu;
		JMenuItem menuItem;
		// Build second menu in the menu bar.
		menu = new JMenu("Configure");
		menu.setMnemonic(KeyEvent.VK_C);
		menu.getAccessibleContext().setAccessibleDescription(
				"Configures the prover");

		menuItem = new JMenuItem(CONFIGURE_OPTION, KeyEvent.VK_P);
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Configures the prover");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem(LOGGER_OPTION, KeyEvent.VK_L);
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Show log viewer");
		menu.add(menuItem);
		menuItem.addActionListener(this);//

		menuBar.add(menu);
	}

	// private void createThirdSubMenu(JMenuBar menuBar) {
	// JMenu menu;
	// JMenuItem menuItem;
	// menu = new JMenu("Show");
	// menu.setMnemonic(KeyEvent.VK_S);
	// menu.getAccessibleContext().setAccessibleDescription(
	// "Information about the prover");
	//
	// menuItem = new JMenuItem(LOGGER_OPTION, KeyEvent.VK_L);
	// menuItem.getAccessibleContext().setAccessibleDescription(
	// "Show log viewer");
	// menu.add(menuItem);
	// menuItem.addActionListener(this);//
	//
	// menuBar.add(menu);
	// }

	private void createFourthSubMenu(JMenuBar menuBar) {
		JMenu menu;
		JMenuItem menuItem;
		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menu.getAccessibleContext().setAccessibleDescription(
				"Help about the prover");

//		menuItem = new JMenuItem(CONTENTS_OPTION, KeyEvent.VK_I);
//		menuItem.getAccessibleContext().setAccessibleDescription(
//				"Important information about the prover");
//		menu.add(menuItem);
//		menuItem.addActionListener(this);

		menuItem = new JMenuItem(ABOUT_OPTION, KeyEvent.VK_A);
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Information about the prover");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuBar.add(menu);
	}

	/**
	 * Verifies lock and shows message
	 */
	private void checkLock() {

		if (properties.getProperty("check.lock", "false").equals("true")) {

			logger.debug("Verifying lock file existence");

			_lockFile = new File(LOCK_FILENAME);

			// if there is a lock file, show an error message
			if (_lockFile.isFile()) {
				JOptionPane.showMessageDialog(null, "A lock file ( "
						+ _lockFile.getAbsolutePath() + " ) was found!"
						+ System.getProperty("line.separator")
						+ "If there is no other KEMS instance running, "
						+ "remove this lock file and restart the system.",
						"KEMS Initialization Error", JOptionPane.ERROR_MESSAGE);
				logger.error("Lock file found");
				System.exit(0);
			} else {
				try {
					new FileWriter(LOCK_FILENAME);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,
							"Unable to create lock file!",
							"KEMS Initialization Error",
							JOptionPane.ERROR_MESSAGE);
					logger.error("Problem opening lock file", e);
				}
			}
		}

	}

	/**
	 * Loads a properties file.
	 */
	private void loadProperties() {
		String propertiesFilename = "application.properties";

		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFilename));
		} catch (FileNotFoundException e) {
			logger.error("Properties file \"" + propertiesFilename
					+ "\" not found in " + System.getProperty("user.dir"));
			showDebugMessage("Properties file \"" + propertiesFilename
					+ "\" not found in " + System.getProperty("user.dir"));
			properties = System.getProperties();
		} catch (IOException e) {
			logger.error("Problem reading properties file: \""
					+ propertiesFilename + "\".");
			showDebugMessage("Problem reading properties file: \""
					+ propertiesFilename + "\".");
			properties = System.getProperties();
		}

	}

	/**
	 * Draws the main window.
	 */
	private void drawsMainWindow() {
		// Make sure we have nice window decorations.
		JFrame.setDefaultLookAndFeelDecorated(true);
		// this.pack();
		JPanel panel = new JPanel(new GridLayout(3, 1));
		JLabel label = new JLabel("  " + PROVER_NAME + "  ", JLabel.CENTER);
		label.setFont(label.getFont().deriveFont(40.0f));

		panel.setBackground(Color.YELLOW);
		label.setForeground(Color.BLUE);
		panel.add(new JLabel());
		panel.add(label);
		panel.add(new JLabel("   Version: "
				+ properties.getProperty("version", DEFAULT_VERSION)
				+ "    Release date: "
				+ properties.getProperty("release.date", DEFAULT_RELASE_DATE)
				+ "   Site: " + properties.getProperty("site", DEFAULT_SITE)
				+ "   ", JLabel.CENTER));

		// coloca imagem - mas n�o animada
		// File f = new File("/home/kurumin/kems/lib/ext/ampulheta.gif");
		// try {
		// BufferedImage bi = ImageIO.read(f);
		// ImageIcon i = new ImageIcon(bi);
		// panel.add(new JButton(i));
		//					
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }

		this.setContentPane(panel);

		this.pack();

		Rectangle maximumBounds = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		double x = (maximumBounds.getWidth() / 2) - (getWidth() / 2);
		double y = (maximumBounds.getHeight() / 2) - (getHeight() / 2);
		this.setLocation((int) x + 10, (int) y - 100);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		// remove lock file when window is closed
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				if (properties.getProperty("check.lock", "false")
						.equals("true")) {

					boolean result = _lockFile.delete();
					if (result) {
						logger.debug("Lock file successfully removed!");
					} else {
						logger.error("Could not remove lock file: "
								+ _lockFile.getAbsolutePath()
								+ "    Please do it manually.");
					}
					logViewer.closeLogFile();

				}
			}
		});

		createsStopFrame();

	}

	private void createsStopFrame() {
		stopFrame = new JFrame(PROVER_NAME + " Prover Stopper");
		stopFrame.setLayout(new GridLayout(2, 1));
		stopFrame.add(new JLabel("Running the prover..."));
		stopFrame.add(stopButton = new JButton("Stop"));
		stopFrame.setLocation(this.getLocation().x + 10,
				this.getLocation().y + 10);
		stopFrame.pack();
		stopFrame.setSize((int) (stopFrame.getSize().getWidth() * 2),
				(int) stopFrame.getSize().getHeight());
		stopFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		stopFrame.setVisible(false);
		stopFrame.setResizable(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (EDITOR_OPTION.equals(e.getActionCommand())) {
			openEditor();
		}
		if (INSTANCE_OPTION.equals(e.getActionCommand())) {
			chooseFileAndOpenProofViewer();
		}
		if (FAMILY_OPTION.equals(e.getActionCommand())) {
			openFamilyRunner();
		}
		if (CONFIGURE_OPTION.equals(e.getActionCommand())) {
			openProverConfigurator();
		}
		if (ABOUT_OPTION.equals(e.getActionCommand())) {
			openAboutFrame();
		}
		if (CONTENTS_OPTION.equals(e.getActionCommand())) {
			openContentsFrame();
		}
		if (SEVERAL_OPTION.equals(e.getActionCommand())) {
			openSeveralRunner();
		}
		if (LOGGER_OPTION.equals(e.getActionCommand())) {
			openLogViewer();
		}

	}

	public void openLogViewer() {
		// logViewer.pack();
		logViewer.setVisible(true);
	}

	/**
	 * If an internet connection is available, it opens the system home page.
	 * Otherwise, shows only a link to that page.
	 */
	private void openAboutFrame() {
		JEditorPane about = null;
		String defaultLink = DEFAULT_SITE;
//		try {
//			about = new JEditorPane(defaultLink);
//			about.setPreferredSize(new Dimension(800, 600));
//		} catch (IOException e) {
			about = new JEditorPane();
			about.setFont(about.getFont().deriveFont(14.0f));
			about.setFont(about.getFont().deriveFont(Font.BOLD));
			String aboutString = "This is the " + PROVER_FULL_NAME + ","
					+ StringUtil.LINE_SEPARATOR;

			aboutString += "a KE-tableau based prover for classical and "
					+ "paraconsistent " + StringUtil.LINE_SEPARATOR
					+ "propositional logics that allows one "
					+ "to use different strategies."
					+ StringUtil.LINE_SEPARATOR+ StringUtil.LINE_SEPARATOR;
			aboutString += "See " + defaultLink + " for more information.";
			about.setText(aboutString);
			about.setPreferredSize(new Dimension(580, 200));
//	}
		about.setEditable(false);
		JFrame aboutFrame = new JFrame("About");
		JScrollPane aboutScroller = new JScrollPane(about);
		aboutFrame.setContentPane(aboutScroller);
		aboutFrame.pack();
		aboutFrame.setLocation(this.getLocation().x + 10,
				this.getLocation().y + 10);
		aboutFrame.setVisible(true);

	}

	private void openContentsFrame() {
		JEditorPane contents = null;

		String propertiesLink = properties.getProperty("help.link");

		String secondOptionLink = "file:///" + System.getProperty("user.dir")
				+ StringUtil.FILE_SEPARATOR + "KEMS_help.html";
		try {
			contents = new JEditorPane(propertiesLink);
			contents.setPreferredSize(new Dimension(800, 600));
		} catch (IOException e1) {
			try {
				contents = new JEditorPane(secondOptionLink);
				contents.setPreferredSize(new Dimension(800, 600));
			} catch (IOException e2) {
				contents = new JEditorPane();
				contents.setFont(contents.getFont().deriveFont(14.0f));
				contents.setFont(contents.getFont().deriveFont(Font.BOLD));
				String helpString = "The " + PROVER_FULL_NAME + " help"
						+ " page is not available now."
						+ StringUtil.LINE_SEPARATOR;

				helpString += "See " + propertiesLink
						+ " for more information.";
				contents.setText(helpString);
				contents.setPreferredSize(new Dimension(580, 80));
			}
		}

		contents.setEditable(false);
		JFrame aboutFrame = new JFrame("Help");
		JScrollPane aboutScroller = new JScrollPane(contents);
		aboutFrame.setContentPane(aboutScroller);
		aboutFrame.pack();
		aboutFrame.setLocation(this.getLocation().x + 10,
				this.getLocation().y + 10);
		aboutFrame.setVisible(true);

	}

	/**
	 * Opens the window that allows the user to configure the prover.
	 */
	private void openProverConfigurator() {
		proverConfigurator.pack();
		proverConfigurator.setVisible(true);
	}

	/**
	 * Opens the window that allows the user to run or analyse a problem family
	 */
	private void openFamilyRunner() {
		if (familyRunner == null) {
			familyRunner = new FamilyProblemsRunnerFrame(this);
		} else {
			familyRunner.pack();
			familyRunner.setVisible(true);
		}
	}

	/**
	 * Opens the window that allows the user to run or analyse several problems
	 */
	private void openSeveralRunner() {
		if (severalRunner == null) {
			severalRunner = new SeveralProblemsRunnerFrame(this);
		} else {
			severalRunner.pack();
			severalRunner.setVisible(true);
		}
	}

	/**
	 * Opens the window that allows the user to load/edit a problem.
	 */
	private void openEditor() {
		if (problemEditor == null) {
			problemEditor = new ProblemEditor(this);
			problemEditor.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			problemEditor.setLocation(100, 100);
			problemEditor.pack();
			problemEditor.setVisible(true);
		} else {
			problemEditor.restore();
		}
	}

	/**
	 * Opens a window that allows the user to choose a file that will be run by
	 * the prover
	 */
	public void chooseFileAndOpenProofViewer() {
		// Only does it if no other frame is running the prover
		synchronized (isExecutingLock) {
			if (!isExecutingProver) {
				// Obs.: As the file chooser is modal, we can set
				// isExecutingProver in the beginning
				isExecutingProver = true;

				String filename = null;

				UIManager.put("FileChooser.readOnly", Boolean.valueOf(true));

				JFrame jf = new JFrame("Open File");
				ROFileChooser rofc = new ROFileChooser(getProblemsDir(), true);
				rofc.setApproveButtonText("Run");
				int result = rofc.showOpenDialog(jf);
				if (result == ROFileChooser.APPROVE_OPTION) {
					File fc = rofc.getSelectedFile();
					filename = fc.getAbsolutePath();
					openProofViewerForFile(filename);
				} else {
					isExecutingProver = false;
				}
			} else {
				showIsExecutingMessage();
			}
		}

	}

	/**
	 * Opens a proof viewer either for a string containing a problem description
	 * 
	 * @param problemString
	 */
	public void openProofViewerForString(String problemString) {
		synchronized (isExecutingLock) {
			if (!isExecutingProver) {
				isExecutingProver = true;
				// isExecutingProver has to be set to false
				executeProver(false, problemString);
			} else {
				showIsExecutingMessage();
			}
		}
	}

	/**
	 * Opens a proof viewer either for a file containing a problem description
	 * 
	 * @param filename
	 */
	public void openProofViewerForFile(String filenameOrString) {
		executeProver(true, filenameOrString);
	}

	/**
	 * Shows a proof in the proof viewer
	 * 
	 * @param proof
	 *            - the proof to be shown
	 */
	public void showProofViewer(IProof proof) {

		if (proof != null) {
			ProofViewer proofViewer = new ProofViewer(this);
			proofViewer.setValuation(proverConfigurator
					.createValuation(((ExtendedProof) proof)
							.getProverConfiguration().getStrategyName()));

			proofViewer.setProof(proof);
			proofViewer.showOn();
		}
	}

	/**
	 * @return directory with problem files
	 */
	public String getProblemsDir() {
		return System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "problems";
	}

	/**
	 * Proves and verify a file or a string containing a problem
	 * 
	 * @param fileOrString
	 * @param filenameOrString
	 * @return a proof
	 */
	public void executeProver(boolean fileOrString, String filenameOrString) {

		// Emerson
		// executeProver(false, problemString);
		// System.out.println("Este código quando o KEMS vai \"resolver\" um problema");
		// # fim Emerson

		// shows stop frame
		stopFrame.setVisible(true);

		ProverFacade pf = new ProverFacade();

		// prepares to run the prover for one problem

		OneProblemRunnable oneProblemRunnable = null;
		if (fileOrString) {
			oneProblemRunnable = new OneProblemInFileRunnable(filenameOrString,
					pf, this);
		} else {
			oneProblemRunnable = new OneProblemAsStringRunnable(
					filenameOrString, pf, this);
		}

		// enables stop button to stop proof
		stopButton.addActionListener(oneProblemRunnable);

		// actually runs the prover
		new Thread(oneProblemRunnable).start();

	}

	public Window getStopFrame() {
		return stopFrame;
	}

	public void showIsExecutingMessage() {
		JOptionPane.showMessageDialog(this,
				"There is already one problem being executed!", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void releaseIsExecutingProverLock() {
		synchronized (isExecutingLock) {
			isExecutingProver = false;
		}

	}

	public boolean isExecutingProver() {
		synchronized (isExecutingLock) {
			return isExecutingProver;
		}
	}

	public void setIsExecutingProver(boolean b) {
		synchronized (isExecutingLock) {
			isExecutingProver = b;
		}

	}

	/**
	 * @return a prover configurator
	 */
	public ProverConfigurator getProverConfigurator() {
		return proverConfigurator;
	}

	public void showErrorMessage(String errorMessage) {
		logViewer.appendErrorMessage(errorMessage);
	}

	public void showInfoMessage(String errorMessage) {
		logViewer.appendInfoMessage(errorMessage);
	}

	public void showDebugMessage(String errorMessage) {
		logViewer.appendDebugMessage(errorMessage);
	}

	/**
	 * Main method
	 * 
	 * @param args
	 * @throws Throwable
	 */
	public static void main(String[] args) throws Throwable {

		File f = new File("log4j.properties");

		if (f.exists()) {
			// System.err.println("log4j.properties exists");
			Properties log4jProperties = new Properties();
			log4jProperties.load(new FileInputStream(f));
			PropertyConfigurator.configure(log4jProperties);
		}

		logger.info("Starting KEMS");
		logger.info("Free memory (bytes)"+Runtime.getRuntime().freeMemory());

		if (args.length > 0) {
			CommandLineRunner.main(args);
			logger.info("Finishing KEMS");
			System.exit(0);
		}

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.gtk.MetalLookAndFeel");
		} catch (Exception e) {
			// nothing to do
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new ProverInterface();
				} catch (Throwable e) {
					logger.error("Finishing KEMS", e);
				}
			}
		});

	}
}