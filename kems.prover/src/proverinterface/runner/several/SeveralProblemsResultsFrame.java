/*
 * Created on 17/11/2005
 *
 */
package proverinterface.runner.several;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import proverinterface.runner.ResultsDataModel;
import proverinterface.runner.ResultsFrameTableLine;
import proverinterface.runner.family.FamilyProblemsRunnerFrame;

/**
 * Frame that shows the results for several problems.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 * 
 */
public class SeveralProblemsResultsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4519291190336301L;

	private JTable table;

	// private ResultsDataModel tableModel;

	private JScrollPane scrollPane;

	private JPanel contentPane;

	private JPanel resultsTablePane;

	private ResultsDataModel tableModel;

	private boolean resultAdded = false;

	private Boolean finished;
	
	private static int instanceCounter= 0;

	// private SeveralProblemsRunnerFrame severalProblemsRunner;

	public SeveralProblemsResultsFrame(SeveralProblemsRunnerFrame sprunner,
			List<String> problems, byte runAndAnalyseOption) {
		super(
				(runAndAnalyseOption == FamilyProblemsRunnerFrame.ANALYSE_AND_RUN_OPTION ? "Proof "
						: "Analysis ")
						+ "results for several problems #"+(++instanceCounter));
		// this.severalProblemsRunner = sprunner;
		finished = Boolean.FALSE;

		resultsTablePane = new JPanel(new GridLayout(0, 1));
		resultsTablePane.add(new JLabel("Wait a moment..."));

		setContentPane(resultsTablePane);

		// setSize(300,80);
		pack();
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setVisible(true);
		// setResizable(false);
	}

	/**
	 * @param problem
	 * @param ep
	 */
	public void addProof(ResultsFrameTableLine line) {
		boolean previousResultAdded = resultAdded;

		synchronized (finished) {
			if (!finished.booleanValue()) {//

				resultAdded = true;

				if (tableModel == null) {
					tableModel = new ResultsDataModel(line.getColumnNames());
				}
				tableModel.add(line);

				resultsTablePane = new JPanel(new BorderLayout());
				table = new JTable(tableModel);
				table.setFont(table.getFont().deriveFont(
						table.getFont().getSize() + 2.0f));

				scrollPane = new JScrollPane(table);
				scrollPane
						.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

				table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				resultsTablePane.add(scrollPane);

				table.setModel(tableModel);

				setContentPane(resultsTablePane);

				if (!previousResultAdded) {
					setVisible(false);
					pack();
					setSize((int) (this.getSize().width * 1.8), (int) (this
							.getSize().height / 2));
				}
				table.revalidate();
				if (isVisible()) {
					setVisible(true);
				}
			}
		}
	}

	public void finishRun() {
		synchronized (finished) {
			contentPane = new JPanel(new BorderLayout());
			JPanel upperContentPane = new JPanel(new GridLayout(0, 1));
			upperContentPane.add(new JLabel("Finish time: " + new Date()));

			contentPane.add(upperContentPane, BorderLayout.NORTH);
			if (!resultAdded) {
				resultsTablePane = new JPanel();
				JLabel noResults = new JLabel("No result obtained");
				resultsTablePane.add(noResults);
			} else {
				if (table != null)
					table.revalidate();
			}

			contentPane.add(resultsTablePane, BorderLayout.CENTER);

			setContentPane(contentPane);

			if (!resultAdded) {
				pack();
			}

			setVisible(true);

			finished = Boolean.TRUE;

		}
	}

}
