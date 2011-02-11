/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.webservice.vo.ExportCardItemVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardItemTableModel extends AbstractDSTableModel<ExportCardItemVO> {

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên phim", "Loại phim", "Giá", "Giảm giá",
                    "Số lượng"};
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        ExportCardItemVO exportCardItemVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return exportCardItemVO.getMovId();
            case 1:
                return exportCardItemVO.getMovName();
            case 2:
                return exportCardItemVO.getMovCatName();
            case 3:
                return exportCardItemVO.getMovPrice();
            case 4:
                return exportCardItemVO.getMovSaleOff();
            case 5:
                return exportCardItemVO.getQuantity();
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
                return Double.class;
            case 5:
                return Integer.class;
        }
        return null;
    }
}