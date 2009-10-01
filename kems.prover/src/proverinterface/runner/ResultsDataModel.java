/*
 * Created on 25/11/2005
 *
 */
package proverinterface.runner;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * [CLASS_DESCRIPTION] 
 *
 * @author Adolfo Gustavo Serra Seca Neto
 *
 */
public class ResultsDataModel implements TableModel {

    private List<ResultsFrameTableLine> lines;

    private String[] columnNames;

    public ResultsDataModel(String[] columnNames) {
        this.columnNames = columnNames;
        lines = new ArrayList<ResultsFrameTableLine>();
    }

    public void add(ResultsFrameTableLine line) {
        lines.add(0,line);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return lines.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
	public Class<?> getColumnClass(int columnIndex) {
        return ((ResultsFrameTableLine) lines.get(0)).get(columnIndex)
                .getClass();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((ResultsFrameTableLine) lines.get(rowIndex))
                .get(columnIndex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int,
     *      int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
     */
    public void addTableModelListener(TableModelListener l) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
     */
    public void removeTableModelListener(TableModelListener l) {
    }
}
