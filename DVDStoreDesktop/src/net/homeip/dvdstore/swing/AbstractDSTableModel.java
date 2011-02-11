/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class AbstractDSTableModel<T> extends AbstractTableModel {
    private List<T> rowData;
    private String[] columnNameList;

    public AbstractDSTableModel() {
        columnNameList = getColumnNameList();
    }

    public int getColumnCount() {
        if (columnNameList == null) {
            return 0;
        }
        return columnNameList.length;
    }

    @Override
    public String getColumnName(int column) {
        if (columnNameList == null ||
                column < 0 || column > columnNameList.length) {
            return null;
        }
        return columnNameList[column];
    }

    public int getRowCount() {
        if (rowData == null) {
            return 0;
        }
        return rowData.size();
    }

    public List<T> getRowData() {
        return rowData;
    }

    public void setRowData(List<T> rowData) {
        this.rowData = rowData;
        fireTableDataChanged();
    }
   
    protected abstract String[] getColumnNameList();
}
