/*
 * Created on 02/11/2005
 *
 */
package proverinterface.proofviewer;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel for defining parameters for the proof viewer.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class ParametersPanel extends JPanel implements ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1278308085651542635L;

	private JTextField fullProofwidthTextField;

    private JTextField fullProofheightTextField;

    private JTextField paneWidthTextField;

    private JTextField paneHeightTextField;

    private JTextField maxLengthTextField;

    private JTextField fontSizeTextField;

    private JTextField circlesRadiusTextField;

    private JButton updateButton;

    private int step = 10;

    private JTextField stepTextField;

    private ProofViewer proofViewer;

    /**
     * Creates a parameters panel form a proof viewer
     */
    public ParametersPanel(ProofViewer proofViewer) {

        super(new GridLayout(0, 1));
        this.proofViewer = proofViewer;

        setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createTitledBorder("Parameters"), BorderFactory
                .createEmptyBorder(5, 5, 5, 5)));
        add(new JLabel("Set parameters below and click on Update size"));

        // panel
        // Full Proof Width:
        // Full Proof Height:
        // Panes Width:
        // Panes Height:
        // Max length:
        // Font size:
        // Circles radius:
        JPanel sizeDefinitionSubPanel2 = new JPanel(new GridLayout(0, 1));

        JPanel setWidthPanel = new JPanel(new GridLayout(0, 2));
        setWidthPanel.add(new JLabel("Full Proof Width: "));
        setWidthPanel.add(fullProofwidthTextField = new JTextField(""
                + (int) proofViewer.getFullViewProofPane().getMyPreferredSize().getWidth()));
        fullProofwidthTextField.addActionListener(proofViewer);
        JPanel setHeightPanel = new JPanel(new GridLayout(0, 2));
        setHeightPanel.add(new JLabel("Full Proof Height: "));
        setHeightPanel.add(fullProofheightTextField = new JTextField(""
                + (int) proofViewer.getFullViewProofPane().getMyPreferredSize().getHeight()));
        fullProofheightTextField.addActionListener(proofViewer);

        JPanel setPaneWidthPanel = new JPanel(new GridLayout(0, 2));
        setPaneWidthPanel.add(new JLabel("Panes Width: "));
        setPaneWidthPanel.add(paneWidthTextField = new JTextField(""
                + (int) proofViewer.getPaneSize().getWidth()));
        paneWidthTextField.addActionListener(proofViewer);
        JPanel setPaneHeightPanel = new JPanel(new GridLayout(0, 2));
        setPaneHeightPanel.add(new JLabel("Panes Height: "));
        setPaneHeightPanel.add(paneHeightTextField = new JTextField(""
                + (int) proofViewer.getPaneSize().getHeight()));
        paneHeightTextField.addActionListener(proofViewer);

        JPanel setMaxLengthPanel = new JPanel(new GridLayout(0, 2));
        setMaxLengthPanel.add(new JLabel("Max string length: "));
        setMaxLengthPanel.add(maxLengthTextField = new JTextField(""
                + proofViewer.getMaxStringLength()));
        maxLengthTextField.addActionListener(proofViewer);
        JPanel setFontSizePanel = new JPanel(new GridLayout(0, 2));
        setFontSizePanel.add(new JLabel("Font size: "));
        setFontSizePanel.add(fontSizeTextField = new JTextField(""
                + proofViewer.getFontSize()));
        fontSizeTextField.addActionListener(proofViewer);
        JPanel setCirclesRadiusPanel = new JPanel(new GridLayout(0, 2));
        setCirclesRadiusPanel.add(new JLabel("Circles radius: "));
        setCirclesRadiusPanel.add(circlesRadiusTextField = new JTextField(""
                + proofViewer.getCirclesRadius()));
        circlesRadiusTextField.addActionListener(proofViewer);
        sizeDefinitionSubPanel2.add(setWidthPanel);
        sizeDefinitionSubPanel2.add(setHeightPanel);
        sizeDefinitionSubPanel2.add(setPaneWidthPanel);
        sizeDefinitionSubPanel2.add(setPaneHeightPanel);
        sizeDefinitionSubPanel2.add(setMaxLengthPanel);
        sizeDefinitionSubPanel2.add(setFontSizePanel);
        sizeDefinitionSubPanel2.add(setCirclesRadiusPanel);

        add(sizeDefinitionSubPanel2);

        // full view move step

        JPanel stepPanel = new JPanel(new GridLayout(0, 2));
        stepPanel.add(new JLabel("Full view move step: "));
        stepTextField = new JTextField("" + step);
        stepTextField.addActionListener(this);
        stepPanel.add(stepTextField);

        JPanel auxPanel = new JPanel(new GridLayout(0, 1));
        auxPanel.add(stepPanel);

        sizeDefinitionSubPanel2.add(auxPanel);

        // update button
        JPanel sizeDefinitionSubPanelButton = new JPanel(new FlowLayout(
                FlowLayout.CENTER));
        updateButton = new JButton("Update");
        sizeDefinitionSubPanelButton.add(updateButton);
        updateButton.addActionListener(this);
        add(sizeDefinitionSubPanelButton);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        // por enquanto só um
        try {
            proofViewer.actionPerformed(e);
            int possibleStep = Integer.parseInt(stepTextField.getText());
            setStep(possibleStep);
        } catch (Exception exc) {
            // do nothing
            stepTextField.setText("" + step);
        }

    }

    //getters and setters

    public JTextField getFontSizeTextField() {
        return fontSizeTextField;
    }

    public JTextField getFullProofHeightTextField() {
        return fullProofheightTextField;
    }

    public JTextField getMaxLengthTextField() {
        return maxLengthTextField;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JTextField getFullProofWidthTextField() {
        return fullProofwidthTextField;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public JTextField getCirclesRadiusTextField() {
        return circlesRadiusTextField;
    }

    public JTextField getPaneWidthTextField() {
        return paneWidthTextField;
    }

    public JTextField getPaneHeightTextField() {
        return paneHeightTextField;
    }

}
