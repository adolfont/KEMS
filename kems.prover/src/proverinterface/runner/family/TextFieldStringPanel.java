/*
 * Created on 09/11/2005
 *
 */
package proverinterface.runner.family;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel with a label and a text field for a string.
 * 
 * @author Adolfo Gustavo Serra Seca Neto
 *  
 */
public class TextFieldStringPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2594709935576444466L;
	private JTextField textfield;

    public TextFieldStringPanel(String label, String initialValue, int columns) {
        super(new GridBagLayout());
        add(new JLabel(label));
        add(textfield = new JTextField(initialValue, columns));
        textfield.setEditable(false);
    }

    public String getValue() {
        return textfield.getText();
    }
    
    public void addActionListener (ActionListener listener){
        textfield.addActionListener(listener);
    }

    /**
     * @param absolutePath
     */
    public void setTextFieldText(String absolutePath) {
        textfield.setText(absolutePath);
        textfield.repaint();
    }

}
