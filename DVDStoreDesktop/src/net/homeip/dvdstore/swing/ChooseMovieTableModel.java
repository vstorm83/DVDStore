/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieVO;

/**
 *
 * @author VU VIET PHUONG
 */

public class ChooseMovieTableModel extends AbstractDSTableModel<ChooseMovieVO> {

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0 ||
                rowIndex > getRowData().size() - 1) {
            return null;
        }

        ChooseMovieVO chooseMovieVO = getRowData().get(rowIndex);
        if (columnIndex == 0) {
            return chooseMovieVO.getMovId();
        } else {
            return chooseMovieVO.getMovName();
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Long.class;
        } else {
            return String.class;
        }
    }

    @Override
    protected String[] getColumnNameList() {
        return new String[] {"Mã", "Tên phim"};
    }
}