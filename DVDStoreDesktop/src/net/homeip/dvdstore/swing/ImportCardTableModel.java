/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing;

import java.util.Date;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardTableModel extends AbstractDSTableModel<ImportCardVO> {

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên nhà cung cấp", "Ngày nhập"};
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            ImportCardVO importCardVO = getRowData().get(rowIndex);
            importCardVO.setSupplierVO((SupplierVO) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        ImportCardVO importCardVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return importCardVO.getImportCardId();
            case 1:
                return importCardVO.getSupplierVO().getSupplierName();
            case 2:
                return importCardVO.getDateCreated();
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
                return Date.class;
        }
        return null;
    }

    /**
     * Chú ý cần convert index to model, khác với getValueAt, table đã convert sẵn
     * @param modelRowIndex
     * @return ImportCardVO
     */
    public ImportCardVO getImportCardVO(int modelRowIndex) {
        if (getRowData() == null || modelRowIndex < 0
                || modelRowIndex > getRowData().size() - 1) {
            return null;
        }
        return getRowData().get(modelRowIndex);
    }
}
