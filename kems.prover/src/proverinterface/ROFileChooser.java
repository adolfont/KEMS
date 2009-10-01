/*
 * Created on 09/11/2005
 *
 */
package proverinterface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

public class ROFileChooser extends JFileChooser {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4564223634662408651L;
	private Boolean readOnly;

    public ROFileChooser(boolean readOnly) {
        this.readOnly = Boolean.valueOf(readOnly);
    }

    public ROFileChooser(String initialPath, boolean readOnly) {
        super(initialPath);
        this.readOnly = Boolean.valueOf(readOnly);
    }

    protected void setUI(ComponentUI newUI) {
        if (readOnly != null) {
            UIManager.put("FileChooser.readOnly", readOnly);
        }
        super.setUI(newUI);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame frame = new JFrame();
                final JButton normalButton   = new JButton("Normal");
                final JButton readOnlyButton = new JButton("Read Only");
                ActionListener action = new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        boolean readOnly = (ev.getSource() == readOnlyButton);
                        UIManager.put("FileChooser.readOnly",
                                      Boolean.valueOf(readOnly));
                        JFileChooser fc = new ROFileChooser(readOnly);
                        fc.showOpenDialog(frame);
                    }
                };
                normalButton.addActionListener(action);
                readOnlyButton.addActionListener(action);
                frame.getContentPane().add(normalButton, BorderLayout.WEST);
                frame.getContentPane().add(readOnlyButton, BorderLayout.EAST);
                frame.pack();
                frame.setLocation(200, 200);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

