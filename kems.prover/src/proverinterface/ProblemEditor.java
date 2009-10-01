/*
 * Created on 10/11/2005
 *
 */
package proverinterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import main.exceptions.KEMSException;

/**
 * An editor of problems that allows to load and save text files.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ProblemEditor extends JFrame implements ActionListener, WindowListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7412526812977593028L;

	private ProverInterface proverConfigurator;

	private JTextPane editorPane;

	public static final String LOAD = "Load";

	public static final String SAVE_AS = "Save As...";

	public static final String SAVE = "Save";

	public static final String RUN = "Run";

	public static final String RUN_THIS_PROBLEM = "Run this problem";

	public static final String INCREASE_FONT = "Increase";

	public static final String DECREASE_FONT = "Decrease";

	public static final String CURRENT_PROBLEM = "problem description in Problem Editor window";

	private File editingFile;

	private JMenuItem saveMenuItem;

	private boolean textChanged = false;

	private String filename = null;

	public ProblemEditor(ProverInterface proverConfigurator) {
		super("Problem Editor");
		this.proverConfigurator = proverConfigurator;

		// editorPane.setFont(new Font("Roman",0,20));

		editorPane = new JTextPane();
		editorPane.setEditable(false);
		// editorPane.setText("Here you can edit a problem...");
		editorPane.setText("");
		editorPane.setEditable(true);
		editorPane.addKeyListener(this);

		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(400, 280));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));

		setContentPane(editorScrollPane);
		// editorPane.setFont(editorPane.getFont().deriveFont(20.0f));

		createMenu();

		addWindowListener(this);

	}

	public void createMenu() {
		// Where the GUI is created:
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first main menu option menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("Works with files");
		menuBar.add(menu);

		// a group of JMenuItems for first main menu option
		menuItem = new JMenuItem(LOAD, KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Loads a problem");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem(SAVE_AS, KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Saves a problem with a new name");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		menuItem = new JMenuItem(SAVE, KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Saves a problem");
		menu.add(menuItem);
		menuItem.addActionListener(this);

		saveMenuItem = menuItem;
		saveMenuItem.setEnabled(false);

		// Adds main menu option: another menu
		JMenu tfmenu = new JMenu("Text font size");
		tfmenu.setMnemonic(KeyEvent.VK_T);
		tfmenu.getAccessibleContext().setAccessibleDescription("Increases or decreases text font size");

		// a group of JMenuItems for main menu third option
		JMenuItem tfMenuItem = new JMenuItem(INCREASE_FONT, KeyEvent.VK_I);
		tfMenuItem.getAccessibleContext().setAccessibleDescription("Increases text font size");
		tfmenu.add(tfMenuItem);
		tfMenuItem.addActionListener(this);

		tfMenuItem = new JMenuItem(DECREASE_FONT, KeyEvent.VK_D);
		tfMenuItem.getAccessibleContext().setAccessibleDescription("Decreases text font size");
		tfmenu.add(tfMenuItem);
		tfMenuItem.addActionListener(this);

		menuBar.add(tfmenu);

		// Adds main menu option
		JMenu runMenu = new JMenu(RUN);
		JMenuItem runMenuItem = new JMenuItem(RUN_THIS_PROBLEM);
		runMenuItem.setMnemonic(KeyEvent.VK_R);
		runMenuItem.getAccessibleContext().setAccessibleDescription("Runs the problem in the editor");
		runMenuItem.addActionListener(this);
		runMenu.add(runMenuItem);

		menuBar.add(runMenu);

		// having finished menu, sets menu bar
		setJMenuBar(menuBar);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals(LOAD)) {
			String fileContent = loadFile();
			if (fileContent != null) {
				editorPane.setText(fileContent);
				editorPane.repaint();
				saveMenuItem.setEnabled(true);
			}

		} else {
			if (e.getActionCommand().equals(SAVE_AS)) {
				saveFileAs();
			} else {
				if (e.getActionCommand().equals(SAVE)) {
					saveFile();
				} else {
					if (e.getActionCommand().equals(RUN_THIS_PROBLEM)) {
						proverConfigurator.openProofViewerForString(editorPane.getText());
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
							}

						}

					}
				}
			}
		}

	}

	/**
	 * 
	 */
	private boolean saveFileAs() {
		UIManager.put("FileChooser.readOnly", Boolean.valueOf(true));

		JFrame jf = new JFrame("Choose Location to Save");
		ROFileChooser rofc = new ROFileChooser(proverConfigurator.getProblemsDir(), true);
		int result = rofc.showSaveDialog(jf);
		if (result == ROFileChooser.APPROVE_OPTION) {
			File fileToWrite = rofc.getSelectedFile();

			if (fileToWrite.exists()) {
				int optionResult = JOptionPane.showConfirmDialog(this,
						"Are you sure you want to overwrite \"" + fileToWrite.getName() + "\" ?", "Overwrite?",
						JOptionPane.YES_NO_OPTION);
				if (optionResult != JOptionPane.OK_OPTION) {
					return false;
				}
			}

			FileWriter fw = null;
			try {
				fw = new FileWriter(fileToWrite);
				fw.write(editorPane.getText());
				fw.flush();
				fw.close();

				textChanged = false;
				filename = fileToWrite.getAbsolutePath();
				editingFile = fileToWrite;
				saveMenuItem.setEnabled(true);
				return true;
			} catch (IOException e) {
				throw new KEMSException("Problem writing to file " + fileToWrite.getAbsolutePath() + ": "
						+ e.getMessage());
			}

		}
		return false;

	}

	private void saveFile() {

		if (editingFile.exists()) {
			int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to overwrite \""
					+ editingFile.getName() + "\" ?", "Overwrite?", JOptionPane.YES_NO_OPTION);
			if (result != JOptionPane.OK_OPTION) {
				return;
			}
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(editingFile);
			fw.write(editorPane.getText());
			fw.flush();
			fw.close();

			textChanged = false;
			filename = editingFile.getAbsolutePath();
		} catch (IOException e) {
			throw new KEMSException("Problem writing to file " + editingFile.getAbsolutePath() + ": "
					+ e.getMessage());
		}

	}

	/**
	 * 
	 */
	private String loadFile() {
		String fileContent = "";

		UIManager.put("FileChooser.readOnly", Boolean.valueOf(true));

		JFrame jf = new JFrame("Choose File to Load");
		ROFileChooser rofc = new ROFileChooser(proverConfigurator.getProblemsDir(), true);
		int result = rofc.showOpenDialog(jf);
		if (result == ROFileChooser.APPROVE_OPTION) {
			editingFile = rofc.getSelectedFile();

			try {
				BufferedReader reader = new BufferedReader(new FileReader(editingFile));

				String line = null;
				boolean firstLine = true;
				try {
					do {
						line = reader.readLine();
						if (line != null) {
							if (!firstLine) {
								fileContent += System.getProperty("line.separator");
							}
							fileContent += line;
						}
						firstLine = false;

					} while (line != null);

					reader.close();
				} catch (IOException e1) {
					throw new KEMSException("Problem reading from file " + editingFile.getAbsolutePath()
							+ ": " + e1.getMessage());
				}

				filename = editingFile.getAbsolutePath();
				textChanged = false;

			} catch (FileNotFoundException e) {
				throw new KEMSException("Problem reading from file " + editingFile.getAbsolutePath() + ": "
						+ e.getMessage());
			}

		} else {
			return null;
		}

		return fileContent;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent e) {
		if (textChanged) {
			int option = JOptionPane.showConfirmDialog(this, "Save before closing?",
					"Closing problem editor", JOptionPane.YES_NO_OPTION);

			if (option == JOptionPane.YES_OPTION) {
				if (filename == null) {
					boolean result = saveFileAs();
					if (result) {
						dispose();
					}
				} else {
					saveFile();
					dispose();
				}
			} else {
				dispose();
			}
		} else {
			dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent e) {
		reinitialize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		textChanged = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * 
	 */
	public void reinitialize() {
		editorPane.setText("");
		textChanged = false;
		filename = null;
		saveMenuItem.setEnabled(false);
	}
	
	public void restore(){
		// pack();
		setVisible(true);
		setState(JFrame.NORMAL);
	}

}
