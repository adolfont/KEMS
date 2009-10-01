/*
 * Created on 09/11/2005
 *
 */
package proverinterface.runner.family;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel for a label and a textfield for an int value.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class TextFieldIntPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1972879381071297516L;

	private JTextField textfield;

    private int defaultValue;

    public TextFieldIntPanel(String label, int defaultValue) {
        super(new GridLayout(0, 2));
        this.defaultValue = defaultValue;
        add(new JLabel(label));
        add(textfield = new JTextField("" + defaultValue));
    }

    public int getValue() {
        int value;

        try {
            value = Integer.parseInt(textfield.getText());
        } catch (Exception e) {
            value = defaultValue;
        }

        textfield.setText("" + value);
        textfield.repaint();

        return value;

    }

    public void addActionListener (ActionListener listener){
        textfield.addActionListener(listener);
    }

}
