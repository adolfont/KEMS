package swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class SwingTest extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingTest(){
		setTitle("ADOLFO");
		
		
		JPanel panel = new JPanel();
	      TableModel dataModel = new AbstractTableModel() {
	          /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public int getColumnCount() { return 10; }
	          public int getRowCount() { return 10;}
	          public Object getValueAt(int row, int col) { return new Integer(row*col); }
	      };
	      JTable table = new JTable(dataModel);
		
//		JScrollPane sp = new JScrollPane(table);

			panel.add(table.getTableHeader());
			panel.add(table);
		setContentPane(panel);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.gtk.MetalLookAndFeel");
		} catch (Exception e) {
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SwingTest();
			}
		});

	}

}
