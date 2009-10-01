/*
 * Created on 10/11/2005
 *
 */
package proverinterface;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import util.StringUtil;

/**
 * An editor of problems that allows to load and save text files.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class LogViewer extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7412526812977593028L;

	private JTextPane editorPane;

	public static final String CLEAR = "Clear";

	public static final String CLEAR_LOG = "Clear log window";

	public static final String INCREASE_FONT = "Increase";

	public static final String DECREASE_FONT = "Decrease";

	public static final String LOGGING = "Logging";

	public static final String ENABLE_DISABLE_LOGGING = "Enable Logging";

	public static final String SAVE_LOGGING = "Save in a log.txt file";

	private FileWriter loggingFile;

	private static final String LOGGING_FILENAME = "log.txt";

	private boolean errorEnabled = true;

	private JScrollPane editorScrollPane;

	private JCheckBoxMenuItem enableLogging;

	private JCheckBoxMenuItem saveInFile;

	public static final String ERROR = "ERROR";

	public static final String INFO = "INFO";

	public static final String DEBUG = "DEBUG";

	public LogViewer(ProverInterface proverInterface, String logFileName) {
		super(ProverInterface.PROVER_NAME + " Log Viewer");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		// editorPane.setFont(new Font("Roman",0,20));

		editorPane = new JTextPane();
		editorPane.setEditable(false);
		editorPane.setFont(editorPane.getFont().deriveFont(
				editorPane.getFont().getSize() + 2.0f));
		editorPane.setFont(editorPane.getFont().deriveFont(Font.BOLD));

		editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(400, 280));
		editorScrollPane.setMinimumSize(new Dimension(100, 100));

		setContentPane(editorScrollPane);
		// editorPane.setFont(editorPane.getFont().deriveFont(20.0f));

		createMenu();

		this.setLocation(proverInterface.getLocation().x + 10, proverInterface
				.getLocation().y
				+ proverInterface.getHeight());

		pack();
		setMinimumSize(new Dimension((int) (getSize().width / 2),
				(int) (getSize().height / 2)));
		setSize((int) (getSize().width * 1.5), getSize().height);
		// setVisible(true);

		try {
			loggingFile = new FileWriter(new File(LOGGING_FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void createMenu() {
		// Where the GUI is created:
		JMenuBar menuBar;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Adds main menu option: another menu
		JMenu logmenu = new JMenu(LOGGING);
		logmenu.setMnemonic(KeyEvent.VK_L);
		logmenu.getAccessibleContext().setAccessibleDescription(
				"Options for logging");

		enableLogging = new JCheckBoxMenuItem(ENABLE_DISABLE_LOGGING);
		enableLogging.setMnemonic(KeyEvent.VK_E);
		enableLogging.getAccessibleContext().setAccessibleDescription(
				"Enables or disables information and debug logging");
		enableLogging.addActionListener(this);
		enableLogging.setSelected(true);
		logmenu.add(enableLogging);

		saveInFile = new JCheckBoxMenuItem(SAVE_LOGGING);
		saveInFile.setMnemonic(KeyEvent.VK_S);
		saveInFile.getAccessibleContext().setAccessibleDescription(
				"Saves in a logging file");
		saveInFile.addActionListener(this);
		saveInFile.setSelected(true);
		logmenu.add(saveInFile);

		menuBar.add(logmenu);

		// Adds main menu option: another menu
		JMenu tfmenu = new JMenu("Text font size");
		tfmenu.setMnemonic(KeyEvent.VK_T);
		tfmenu.getAccessibleContext().setAccessibleDescription(
				"Increases or decreases text font size");

		// a group of JMenuItems for main menu third option

		JMenuItem tfMenuItem = new JMenuItem(INCREASE_FONT, KeyEvent.VK_I);
		tfMenuItem.getAccessibleContext().setAccessibleDescription(
				"Increases text font size");
		tfmenu.add(tfMenuItem);
		tfMenuItem.addActionListener(this);

		tfMenuItem = new JMenuItem(DECREASE_FONT, KeyEvent.VK_D);
		tfMenuItem.getAccessibleContext().setAccessibleDescription(
				"Decreases text font size");
		tfmenu.add(tfMenuItem);
		tfMenuItem.addActionListener(this);

		menuBar.add(tfmenu);

		// Adds main menu option
		JMenu runMenu = new JMenu(CLEAR);
		JMenuItem runMenuItem = new JMenuItem(CLEAR_LOG);
		runMenuItem.setMnemonic(KeyEvent.VK_C);
		runMenuItem.getAccessibleContext().setAccessibleDescription(
				"Clear log window");
		runMenuItem.addActionListener(this);
		runMenu.add(runMenuItem);

		menuBar.add(runMenu);

		// having finished menu, sets menu bar
		setJMenuBar(menuBar);
		editorScrollPane.setAlignmentY(0.0f);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(CLEAR_LOG)) {
			editorPane.setText("");
			editorPane.repaint();
		} else {
			// increase or decrease font
			if (e.getActionCommand().equals(INCREASE_FONT)) {
				editorPane.setFont(editorPane.getFont().deriveFont(
						editorPane.getFont().getSize() + 4.0f));
			} else {
				if (e.getActionCommand().equals(DECREASE_FONT)) {
					if (editorPane.getFont().getSize() > 4) {
						editorPane.setFont(editorPane.getFont().deriveFont(
								editorPane.getFont().getSize() + -4.0f));
					}
				} else {
					if (e.getActionCommand().equals(SAVE_LOGGING)) {
						String message = "saving messages in logging file by user request.";
						if (!saveInFile.isSelected()) {
							saveInfoMessage("Stopped " + message);

							if (enableLogging.isSelected()) {
								writeInEditor(constructMessage(INFO, "Stopped "
										+ message));
							}

						} else {
							saveInfoMessage("Restarted " + message);

							if (enableLogging.isSelected()) {
								writeInEditor(constructMessage(INFO,
										"Restarted " + message));
							}

						}
					}
				}

			}

		}
	}

	public void writeInEditor(String text) {
		editorPane.setText(text + editorPane.getText());
	}

	private void appendMessage(String messageType, String message) {
		writeInEditor(constructMessage(messageType, message));

		if (saveInFile.isSelected()) {
			saveInfoMessage(message);
		}
	}

	public String constructMessage(String messageType, String messsage) {
		return new Timestamp(System.currentTimeMillis()) + " - " + messageType
				+ ": " + messsage + StringUtil.LINE_SEPARATOR;
	}

	private void saveInfoMessage(String message) {
		try {
			loggingFile.write(constructMessage(INFO, message));
			loggingFile.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void appendInfoMessage(String message) {
		if (enableLogging.isSelected()) {
			appendMessage(INFO, message);
		}
	}

	public void appendErrorMessage(String message) {
		if (isErrorEnabled()) {
			appendMessage(ERROR, message);
			setVisible(true);
		}
	}

	public void appendDebugMessage(String message) {
		if (enableLogging.isSelected()) {
			appendMessage(DEBUG, message);
		}
	}

	public boolean isErrorEnabled() {
		return errorEnabled;
	}

	public void setErrorEnabled(boolean errorEnabled) {
		this.errorEnabled = errorEnabled;
	}

	public void closeLogFile() {
		try {
			enableLogging.setSelected(true);
			saveInFile.setSelected(true);
			appendInfoMessage("Prover interface stopped.");
			loggingFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
