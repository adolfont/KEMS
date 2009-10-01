/*
 * Created on 17/11/2005
 *
 */
package proverinterface.runner.family;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import proverinterface.runner.ResultsDataModel;
import proverinterface.runner.ResultsFrameTableLine;


/**
 * Frame the shows the results for a problem family.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class FamilyResultsFrame extends JFrame implements WindowListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1285126645665259472L;

	private JTable table;

    //    private ResultsDataModel tableModel;

    private JScrollPane scrollPane;

    private JPanel contentPane;

    private JPanel resultsTablePane;

    private ResultsDataModel tableModel;

//    private String strategyName;

//    private byte runAndAnalyseOption;

    private ProblemFamily problemsFamily;

    private boolean resultAdded = false;

    private Boolean finished;

    private FamilyProblemsRunnerFrame problemFamilyRunner;

    public FamilyResultsFrame(FamilyProblemsRunnerFrame pfrunner, ProblemFamily problems, String strategyName,
            byte runAndAnalyseOption) {
        super(
                (runAndAnalyseOption == FamilyProblemsRunnerFrame.ANALYSE_AND_RUN_OPTION ? "Proof "
                        : "Analysis ")
                        + "results for " + problems.getName() + " family");
        this.problemFamilyRunner = pfrunner;
        this.problemsFamily = problems;
        finished = Boolean.FALSE;

//        this.strategyName = strategyName;
//        this.runAndAnalyseOption = runAndAnalyseOption;
        resultsTablePane = new JPanel();
        resultsTablePane.add(new JLabel("Wait a moment..."));

        setContentPane(resultsTablePane);

        setSize(400, 150);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        addWindowListener(this);
    }

    /**
     * @param problem
     * @param ep
     */
    public void addProof(ResultsFrameTableLine line) {
        synchronized (finished) {
            if (!finished.booleanValue()) {

                resultAdded = true;

                if (tableModel == null) {
                    tableModel = new ResultsDataModel(line.getColumnNames());
                }
                tableModel.add(line);

                resultsTablePane = new JPanel(new BorderLayout());

                table = new JTable(tableModel);

                scrollPane = new JScrollPane(table);
                scrollPane
                        .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                //        table.setPreferredScrollableViewportSize(new Dimension(800,
                // 100));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                //        table.setPreferredSize(new Dimension(1600,100));
                //        table.setSize(1600, 100);

                resultsTablePane.add(scrollPane);

                table.setModel(tableModel);
                table.invalidate();
                //        table.repaint();
                //        scrollPane.repaint();

                setContentPane(resultsTablePane);

                //        Dimension size = this.getSize();
                pack();
                //        this.setSize(size);
                //      repaint();
                //        System.err
                //                .println("DEVERIA TER EXIBIDO "
                //                        + problem.getNameWithoutTermination() + " "
                //                        + ep.getTimeSpent());
            }
        }
    }

    public void finishRun() {
        synchronized (finished) {
            contentPane = new JPanel(new BorderLayout());
            JPanel upperContentPane = new JPanel(new GridLayout(0, 1));
//            if (runAndAnalyseOption == ProblemFamilyRunner.ANALYSE_AND_RUN_OPTION) {
//                upperContentPane.add(new JLabel("Strategy: " + strategyName));
//            }
            upperContentPane.add(new JLabel("Family path: "
                    + problemsFamily.getPath()));
            upperContentPane.add(new JLabel("Finish time: " + new Date()));

            contentPane.add(upperContentPane, BorderLayout.NORTH);
            if (!resultAdded) {
                resultsTablePane = new JPanel();
                resultsTablePane.add(new JLabel("No result obtained"));
            }
            contentPane.add(resultsTablePane, BorderLayout.CENTER);

            setContentPane(contentPane);
            pack();
            finished = Boolean.TRUE;
        }
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
     */
    public void windowActivated(WindowEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
     */
    public void windowClosed(WindowEvent e) {
        problemFamilyRunner.notifyFinished();
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
     */
    public void windowClosing(WindowEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
     */
    public void windowDeactivated(WindowEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
     */
    public void windowDeiconified(WindowEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
     */
    public void windowIconified(WindowEvent e) {
    }

    /* (non-Javadoc)
     * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
     */
    public void windowOpened(WindowEvent e) {
    }

}
