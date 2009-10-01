/*
 * Created on 02/11/2005
 *
 */
package proverinterface.proofviewer;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * [CLASS_DESCRIPTION]
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class PictureMover extends JPanel {
//    implements ActionListener {

//    private int step = 10;

//    private JTextField stepTextField;

    /**
	 * 
	 */
	private static final long serialVersionUID = -8464868894044606662L;

	public PictureMover(ProofViewer proofViewer) {
        super(new GridLayout(0, 1));

        JPanel buttonsPanel = new JPanel(new GridLayout(3,3));

        JButton[] buttons = new JButton[9];

        buttons[0] = (new JButton("left+up"));
        buttons[1] = (new JButton("up"));
        buttons[2] = (new JButton("right+up"));
        buttons[3] = (new JButton("left"));
        buttons[4] = (new JButton("center"));
        buttons[5] = (new JButton("right"));
        buttons[6] = (new JButton("left+down"));
        buttons[7] = (new JButton("down"));
        buttons[8] = (new JButton("right+down"));


        for (int i = 0; i < buttons.length; i++) {
            buttonsPanel.add(buttons[i]);
            buttons[i].addActionListener(proofViewer);
        }

        add(buttonsPanel);

//        JPanel stepPanel = new JPanel(new GridLayout(0, 2));
//        stepPanel.add(new JLabel("Step: "));
//        stepTextField = new JTextField("" + step);
//        stepTextField.addActionListener(this);
//        stepPanel.add(stepTextField);


//        JPanel updateButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        JButton updateButton = new JButton("Update");
//        updateButtonPanel.add(updateButton);
//        updateButton.addActionListener(this);

//        JPanel auxPanel = new JPanel(new GridLayout(0,1));
//        auxPanel.add(stepPanel);
//        auxPanel.add(updateButtonPanel);
        
//        add(auxPanel);
    }

//    public int getStep() {
//        return step;
//    }
//
//    public void setStep(int step) {
//        this.step = step;
//    }

//    /*
//     * (non-Javadoc)
//     * 
//     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
//     */
//    public void actionPerformed(ActionEvent e) {
//
//        // por enquanto só um
//        try {
//            int possibleStep = Integer.parseInt(stepTextField.getText());
//            setStep(possibleStep);
//        } catch (Exception exc) {
//            // do nothing
//            stepTextField.setText("" + step);
//        }
//
//    }

}
