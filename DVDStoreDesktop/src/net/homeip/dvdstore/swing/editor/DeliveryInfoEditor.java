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
import net.homeip.dvdstore.util.ValidatorUtil;

/**
 *
 * @author VU VIET PHUONG
 */
public class DeliveryInfoEditor extends AbstractCellEditor implements TableCellEditor {

    private JTextField txtDeliveryInfo;
    //4-address, 5-tel, 6-email
    private int row;
    private int column;

    public DeliveryInfoEditor() {
        txtDeliveryInfo = new JTextField();
    }

    public Object getCellEditorValue() {
        return TextUtil.normalize(txtDeliveryInfo.getText());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        txtDeliveryInfo.setText((String) value);
        this.row = row;
        this.column = column;
        return txtDeliveryInfo;
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
        String value = (String)getCellEditorValue();
        switch (column) {
            case 4:                
                if (ValidatorUtil.isEmpty(value)
                        || ValidatorUtil.isInvalidLength(value, 1, 100)) {
                    errorMsg = "Địa chỉ từ 1 đến 100 ký tự";
                }
                break;
            case 5:                
                if (ValidatorUtil.isEmpty((String)getCellEditorValue())
                        || ValidatorUtil.isInvalidLength(value, 1, 50)) {
                    errorMsg = "Điện thoại từ 1 đến 50 ký tự";
                }
                break;
            case 6:                
                if (ValidatorUtil.isEmpty(value)
                        || ValidatorUtil.isInvalidLength(value, 1, 50)
                        || ValidatorUtil.isInvalidEmail(value)) {
                    errorMsg = "Chưa nhập email, hoặc email không hợp lệ";
                }                
        }
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
}
