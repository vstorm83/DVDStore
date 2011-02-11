/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import java.util.Date;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardTableModel extends AbstractDSTableModel<ExportCardVO> {

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên đăng nhập", "Họ, đệm", "Tên", "Địa chỉ", "Điện thoại",
                    "Email", "Ngày đặt", "Ngày giao"};
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        ExportCardVO exportCardVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return exportCardVO.getExportCardId();
            case 1:
                return exportCardVO.getUserName();
            case 2:
                return exportCardVO.getFirstName();
            case 3:
                return exportCardVO.getLastName();
            case 4:
                return exportCardVO.getAddress();
            case 5:
                return exportCardVO.getTel();
            case 6:
                return exportCardVO.getEmail();
            case 7:
                return exportCardVO.getStartDate();
            case 8:
                return exportCardVO.getEndDate();
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
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            case 7:
                return Date.class;
            case 8 :
                return Date.class;
        }
        return null;
    }

    /**
     * Chú ý cần convert index to model, khác với getValueAt, table đã convert sẵn
     * @param modelRowIndex
     * @return ExportCardVO
     */
    public ExportCardVO getExportCardVO(int modelRowIndex) {
        if (getRowData() == null || modelRowIndex < 0
                || modelRowIndex > getRowData().size() - 1) {
            return null;
        }
        return getRowData().get(modelRowIndex);
    }

    public void updateUserInExportCard(UserVO oldUserVO, UserVO newUserVO) {
        List<ExportCardVO> exportCardVOs = getRowData();
        if (exportCardVOs == null) return;
        
        int rowIdx = 0;
        for (ExportCardVO exportCardVO : exportCardVOs) {
            if (exportCardVO.getUserName().equals(oldUserVO.getUserName())) {
                exportCardVO.setUserName(newUserVO.getUserName());
                fireTableCellUpdated(rowIdx, 1);
            }
            rowIdx++;
        }
    }
}