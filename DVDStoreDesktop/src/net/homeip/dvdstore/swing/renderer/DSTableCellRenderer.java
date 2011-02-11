/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing.renderer;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author VU VIET PHUONG
 */
public class DSTableCellRenderer extends DefaultTableCellRenderer {
    private static final Locale DSLOCALE = new Locale("vi", "VN");

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (row % 2 != 0) {
            setBackground(new Color(238, 234, 234));
        } else {
            setBackground(Color.WHITE);
        }
        
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (value instanceof Long || value instanceof Double ||
                value instanceof Integer) {
            setHorizontalAlignment(SwingConstants.CENTER);
        } else if (value instanceof String) {
            setHorizontalAlignment(SwingConstants.LEFT);
        } else if (value instanceof Date) {
            setHorizontalAlignment(SwingConstants.CENTER);
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, DSLOCALE);
            setText(dateFormat.format((Date)value));
        }

        return this;
    }
}