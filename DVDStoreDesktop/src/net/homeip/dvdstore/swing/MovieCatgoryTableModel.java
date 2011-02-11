/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryTableModel extends AbstractDSTableModel<MovieCatgoryVO> {

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0 ||
                rowIndex > getRowData().size() - 1) {
            return null;
        }
        
        MovieCatgoryVO movCatVO = getRowData().get(rowIndex);
        if (columnIndex == 0) {
            return movCatVO.getMovCatId();
        } else {
            return movCatVO.getMovCatName();
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
        return new String[] {"Mã", "Loại phim"};
    }
}
