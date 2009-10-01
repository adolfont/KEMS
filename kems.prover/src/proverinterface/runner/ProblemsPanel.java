/*
 * Created on 17/11/2005
 *
 */
package proverinterface.runner;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import proverinterface.runner.family.ProblemFamily;

/**
 * A panel with problem file names - used by SeveralProblemsRunnerFrame and
 * ProblemFamilyRunnerFrame
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class ProblemsPanel extends JPanel {

	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -2318067371807577219L;

	private JList problemsList;

	private JFrame parent;

	private JLabel chosenProblemsLabel;

	public ProblemsPanel(JFrame parent) {
		super(new BorderLayout());
		this.parent = parent;

		add(chosenProblemsLabel = new JLabel("Chosen problems:"),
				BorderLayout.NORTH);
	}

	/**
	 * @param problems
	 */
	public void setProblems(ProblemFamily problems) {
		if (problemsList == null) {
			problemsList = new JList(problems.getArray());
			problemsList.setFocusable(false);
			add(problemsList, BorderLayout.CENTER);
		} else {
			problemsList.setListData(problems.getArray());
		}

	}

	private List<String> problemNames;

	/**
	 * @param problems
	 * @throws IOException
	 */
	public void addProblems(File[] problems) throws IOException {
		if (problemsList == null) {

			// problemsList = new JList(problems);
			// problemsList.setFocusable(false);
			// add(problemsList, BorderLayout.CENTER);

			problemNames = new ArrayList<String>();
			for (int i = 0; i < problems.length; i++) {
				problemNames.add(getRelativePathIfPossible(problems[i]));
			}
			//			
			// problemNames = Arrays.asList(problems);

			problemsList = new JList(problemNames.toArray());
			problemsList.setFocusable(false);
			add(problemsList, BorderLayout.CENTER);

		} else {
			if (problemNames == null) {
				// problemNames = Arrays.asList(problems);
				problemNames = new ArrayList<String>();
				for (int i = 0; i < problems.length; i++) {
					problemNames.add(getRelativePathIfPossible(problems[i]));
				}
				// problemsList.setListData(problems);
				problemsList.setListData(problemNames.toArray());
			} else {

				LinkedList<String> result = new LinkedList<String>();
				result.addAll(problemNames);

				for (int i = 0; i < problems.length; i++) {

					boolean found = false;
					Iterator<String> itNames = problemNames.iterator();
					while (itNames.hasNext()) {
						File filename = new File(itNames.next().toString());
						if (problems[i].getCanonicalPath().equals(
								filename.getCanonicalPath())
								|| problems[i].getPath().equals(
										filename.getPath())) {
							found = true;
							break;
						}
					}

					if (!found) {
						result.addLast(getRelativePathIfPossible(problems[i]));
						// result.addLast((File) problems[i]);
					}
				}

				problemsList.setListData(result.toArray());
				problemNames = result;
			}
		}

		chosenProblemsLabel.setVisible(true);

	}

	private String getRelativePathIfPossible(File file) {
		String absoluteFileName = file.getAbsolutePath().toString();

		if (absoluteFileName.indexOf(System.getProperty("user.dir")) != -1) {
			return absoluteFileName.substring(System.getProperty("user.dir")
					.length() + 1);
		} else {
			return absoluteFileName;
		}

	}

	public List<String> getProblemNames() {
		return problemNames;
	}

	public void clearProblemList() {
		problemNames = new ArrayList<String>();
		problemsList.setListData(problemNames.toArray());
		chosenProblemsLabel.setVisible(false);
		parent.pack();
		repaint();

	}

}
