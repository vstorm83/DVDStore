/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import net.homeip.dvdstore.util.TextUtil;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderItemEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField txtEditor;
    //4-movPrice, 5-saleOff, 6-quantity
    private int row;
    private int column;

    public OrderItemEditor() {
        txtEditor = new JTextField();
    }

    public Object getCellEditorValue() {
        String sValue = TextUtil.normalize(txtEditor.getText());
        switch (column) {
            case 4:
            case 5:
                return Double.valueOf(sValue);
            default:
                return Integer.valueOf(sValue);
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        txtEditor.setText(String.valueOf(value));
        this.row = row;
        this.column = column;
        return txtEditor;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (e instanceof MouseEvent) {
            return ((MouseEvent) e).getClickCount() >= 2;
        }
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        String errorMsg = null;
        String sValue = TextUtil.normalize(txtEditor.getText());

        errorMsg = validate(sValue);

        if (errorMsg != null) {
            JOptionPane.showMessageDialog(null, errorMsg);
            return false;
        }
        return super.stopCellEditing();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    private String validate(String sValue) {
        String errorMsg = null;
        switch (column) {
            case 4:
                try {
                    double price = Double.valueOf(sValue);
                    errorMsg = price < 0 ? "Giá phải là số lớn hơn hoặc bằng 0" : null;
                } catch (Exception ex) {
                    errorMsg = "Giá sai định dạng";
                }
                break;
            case 5:
                try {
                    double saleOff = Double.valueOf(sValue);
                    errorMsg = saleOff < 0 || saleOff > 100 ? "Giảm giá phải >= 0 và <= 100" : null;
                } catch (Exception ex) {
                    errorMsg = "Giảm giá sai định dạng";
                }
                break;
            case 6:
                try {
                    int quantity = Integer.valueOf(sValue);
                    errorMsg = quantity <= 0 ? "Số lượng phải > 0" : null;
                } catch (Exception ex) {
                    errorMsg = "Số lượng sai định dạng";
                }
        }
        return errorMsg;
    }
}
