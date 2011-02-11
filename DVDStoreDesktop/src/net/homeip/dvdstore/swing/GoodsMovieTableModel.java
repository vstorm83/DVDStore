/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import java.util.Date;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class GoodsMovieTableModel extends AbstractDSTableModel<GoodsMovieVO> {

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0 ||
                rowIndex > getRowData().size() - 1) {
            return null;
        }

        GoodsMovieVO goodsMovieVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return goodsMovieVO.getMovId();
            case 1:
                return goodsMovieVO.getMovName();
            case 2:
                return goodsMovieVO.getMovCatVO().getMovCatName();
            case 3:
                return goodsMovieVO.getMovQuantity();
            case 4:
                return goodsMovieVO.getMovPrice();
            case 5:
                return goodsMovieVO.getMovSaleOff();
            case 6:
                return goodsMovieVO.getDateCreated();
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
                return Date.class;
        }
        return null;
    }

    @Override
    protected String[] getColumnNameList() {
        return new String[] {"Mã", "Tên phim", "Loại phim", "Kho", "Giá bán", "Giảm giá", "Ngày tạo"};
    }

    /**
     * Chú ý cần convert index to model, khác với getValueAt, table đã convert sẵn
     * @param modelRowIndex
     * @return List, nerver  null
     */
//    public List<GoodsMovieVO> getGoodsMovieVOs(int[] modelRowIndex) {
//        if (modelRowIndex == null) {
//            throw new IllegalArgumentException("can't get list, selectedRows index is null");
//        }
//        List<GoodsMovieVO> goodsMovieVOs = new ArrayList<GoodsMovieVO>(modelRowIndex.length);
//        for (int idx : modelRowIndex) {
//            goodsMovieVOs.add(getRowData().get(idx));
//        }
//        return goodsMovieVOs;
//    }

    /**
     * Chú ý cần convert index to model, khác với getValueAt, table đã convert sẵn
     * @param modelRowIndex
     * @return GoodsMovieVO
     */
    public GoodsMovieVO getGoodsMovieVO(int modelRowIndex) {
        if (getRowData() == null || modelRowIndex < 0 ||
                modelRowIndex > getRowData().size() - 1) {
            return null;
        }
        return getRowData().get(modelRowIndex);
    }
}
