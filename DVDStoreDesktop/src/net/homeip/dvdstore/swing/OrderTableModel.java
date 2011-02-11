/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderTableModel extends AbstractDSTableModel<OrderVO> {

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên đăng nhập", "Họ, đệm", "Tên", "Địa chỉ", "Điện thoại",
                    "Email", "Ngày"};
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        OrderVO orderVO = getRowData().get(rowIndex);
        String val = (String)aValue;
        switch (columnIndex) {
            case 4:
                orderVO.setAddress(val);
                break;
            case 5:
                orderVO.setTel(val);
                break;
            case 6:
                orderVO.setEmail(val);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        OrderVO orderVO = getRowData().get(rowIndex);
        switch (columnIndex) {
            case 0:
                return orderVO.getOrderId();
            case 1:
                return orderVO.getUserName();
            case 2:
                return orderVO.getFirstName();
            case 3:
                return orderVO.getLastName();
            case 4:
                return orderVO.getAddress();
            case 5:
                return orderVO.getTel();
            case 6:
                return orderVO.getEmail();
            case 7:
                return orderVO.getStartDate();
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
        }
        return null;
    }

    /**
     * Chú ý cần convert index to model, khác với getValueAt, table đã convert sẵn
     * @param modelRowIndex
     * @return OrderVO
     */
    public OrderVO getOrderVO(int modelRowIndex) {
        if (getRowData() == null || modelRowIndex < 0
                || modelRowIndex > getRowData().size() - 1) {
            return null;
        }
        return getRowData().get(modelRowIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4 ||
                columnIndex == 5 ||
                columnIndex == 6;
    }

    public void addOrder(OrderVO newOrder) {
        if (getRowData() == null) {
            setRowData(new ArrayList<OrderVO>());
        }
        getRowData().add(newOrder);
        int idx = getRowData().size() - 1;
        fireTableRowsInserted(idx, idx);
    }

    public void updateUserInOrder(UserVO oldUserVO, UserVO newUserVO) {
        List<OrderVO> orderVOs = getRowData();
        if (orderVOs == null) return;

        int rowIdx = 0;
        for (OrderVO orderVO : orderVOs) {
            if (orderVO.getUserName().equals(oldUserVO.getUserName())) {
                orderVO.setUserName(newUserVO.getUserName());
                fireTableCellUpdated(rowIdx, 1);
            }
            rowIdx++;
        }
    }

}
