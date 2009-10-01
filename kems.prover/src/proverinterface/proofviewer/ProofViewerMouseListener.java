/*
 * Created on 07/11/2005
 *
 */
package proverinterface.proofviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A mouse listener for the proof viewer
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ProofViewerMouseListener implements MouseListener, ActionListener {

    private JPopupMenu popup;

    private static final String SHOW_CIRCLES = "show circles";

    private static final String SHOW_NUMBERS = "show numbers";

    private static final String SHOW_MARK_USED = "show sign marking used formulas";

    private static final String OPEN_PARAMETERS = "change parameters";

    private ProofViewer proofViewer;

    private JFrame parametersFrame = null;

    private JCheckBoxMenuItem circlesOption;

    private JCheckBoxMenuItem numbersOption;

    private JCheckBoxMenuItem markUsedOption;

    /**
     * @param pane
     */
    public ProofViewerMouseListener(ProofViewer proofViewer) {
        this.proofViewer = proofViewer;

        popup = new JPopupMenu("Full View options");

        circlesOption = new JCheckBoxMenuItem(SHOW_CIRCLES);
        popup.add(circlesOption);
        circlesOption.addActionListener(this);

        numbersOption = new JCheckBoxMenuItem(SHOW_NUMBERS);
        popup.add(numbersOption);
        numbersOption.addActionListener(this);

        markUsedOption = new JCheckBoxMenuItem(SHOW_MARK_USED);
        popup.add(markUsedOption);
        markUsedOption.addActionListener(this);

        JMenuItem openParameters = new JMenuItem(OPEN_PARAMETERS);
        popup.add(openParameters);
        openParameters.addActionListener(this);

        //        JRadioButtonMenuItem circlesOption2 = new JRadioButtonMenuItem("show
        // circles");
        //        popup.add(circlesOption2);
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(SHOW_CIRCLES)) {
            proofViewer.getFullViewProofPane().setShowCircles(
                    ((JCheckBoxMenuItem) e.getSource()).getState());
        } else {
            if (e.getActionCommand().equals(SHOW_NUMBERS)) {
                proofViewer.getFullViewProofPane().setShowNumbers(
                        ((JCheckBoxMenuItem) e.getSource()).getState());
            } else {
                if (e.getActionCommand().equals(SHOW_MARK_USED)) {
                    proofViewer.getFullViewProofPane().setMarkUsed(
                            ((JCheckBoxMenuItem) e.getSource()).getState());
                } else {
                    if (e.getActionCommand().equals(OPEN_PARAMETERS)) {
                        if (parametersFrame == null) {
                            parametersFrame = new JFrame("Parameters");
                            parametersFrame.getContentPane().add(proofViewer
                                    .getParametersPanel());
                            parametersFrame
                                    .setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            parametersFrame.pack();
                            parametersFrame.setVisible(true);
                        } else {
                            parametersFrame.pack();
                            parametersFrame.setVisible(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param b
     * @param c
     */
    public void setCheckBoxesInitialState() {
        circlesOption.setSelected(proofViewer.getFullViewProofPane()
                .isShowingCircles());
        numbersOption.setSelected(proofViewer.getFullViewProofPane()
                .isShowingNumbers());
        markUsedOption.setSelected(proofViewer.getFullViewProofPane()
                .isMarkingUsed());
    }
}
