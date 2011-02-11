/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardEditor extends AbstractCellEditor implements TableCellEditor {

    private JComboBox cbxSupplier;
    //4-address, 5-tel, 6-email
    private int row;
    private int column;

    public ImportCardEditor() {
        cbxSupplier = new JComboBox();
    }

    public Object getCellEditorValue() {
        return cbxSupplier.getSelectedItem();
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        cbxSupplier.setSelectedItem(value);
        this.row = row;
        this.column = column;
        return cbxSupplier;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= 2;
        }
        return true;
    }

    public void setModel(ComboBoxModel comboBoxModel) {
        cbxSupplier.setModel(comboBoxModel);
    }
    
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}