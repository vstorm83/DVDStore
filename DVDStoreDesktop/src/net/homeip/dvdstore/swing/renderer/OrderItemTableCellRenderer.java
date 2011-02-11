/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing.renderer;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderItemTableCellRenderer extends DSTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        switch (column) {
            case 4:
            case 5:
            case 6:
                setFont(getFont().deriveFont(Font.BOLD));
                break;
            default:
                setFont(getFont().deriveFont(Font.PLAIN));
        }
        return this;
    }
}
