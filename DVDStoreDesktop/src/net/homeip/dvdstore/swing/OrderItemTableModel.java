/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.webservice.vo.OrderItemVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderItemTableModel extends AbstractDSTableModel<OrderItemVO> {

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên phim", "Loại phim", "Kho", "Giá", "Giảm giá",
                    "Số lượng"};
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4 ||
                columnIndex == 5 ||
                columnIndex == 6;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        OrderItemVO orderItemVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 4:
                orderItemVO.setMovPrice((Double)aValue);
                break;
            case 5:
                orderItemVO.setMovSaleOff((Double)aValue);
                break;
            case 6:
                orderItemVO.setQuantity((Integer)aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        OrderItemVO orderItemVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return orderItemVO.getMovId();
            case 1:
                return orderItemVO.getMovName();
            case 2:
                return orderItemVO.getMovCatName();
            case 3:
                return orderItemVO.getStockQuantity();
            case 4:
                return orderItemVO.getMovPrice();
            case 5:
                return orderItemVO.getMovSaleOff();
            case 6:
                return orderItemVO.getQuantity();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Long.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Integer.class;
            case 4:
                return Double.class;
            case 5:
                return Double.class;
            case 6:
                return Integer.class;
        }
        return null;
    }
}
