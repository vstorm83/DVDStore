/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.webservice.vo.ImportCardItemVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardItemTableModel extends AbstractDSTableModel<ImportCardItemVO> {

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên phim", "Loại phim", "Giá", "Số lượng"};
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3 ||
                columnIndex == 4;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ImportCardItemVO importCardItemVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 3:
                importCardItemVO.setMovPrice((Double)aValue);
                break;
            case 4:
                importCardItemVO.setQuantity((Integer)aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        ImportCardItemVO importCardItemVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return importCardItemVO.getMovId();
            case 1:
                return importCardItemVO.getMovName();
            case 2:
                return importCardItemVO.getMovCatName();
            case 3:
                return importCardItemVO.getMovPrice();
            case 4:
                return importCardItemVO.getQuantity();
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
                return Double.class;
            case 4:
                return Integer.class;
        }
        return null;
    }
}