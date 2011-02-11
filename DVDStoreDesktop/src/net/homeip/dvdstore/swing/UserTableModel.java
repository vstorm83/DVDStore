/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserTableModel extends AbstractDSTableModel<UserVO> {

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0
                || rowIndex > getRowData().size() - 1) {
            return null;
        }

        UserVO userVO = getRowData().get(rowIndex);
        DeliveryInfo deliveryInfo =
                userVO.getDeliveryInfo() == null ? new DeliveryInfo() : userVO.getDeliveryInfo();
        switch (columnIndex) {
            case 0:
                return userVO.getUserId();
            case 1:
                return userVO.getUserName();
            case 2:
                return deliveryInfo.getFirstName();
            case 3:
                return deliveryInfo.getLastName();
            case 4:
                return deliveryInfo.getAddress();
            case 5:
                return deliveryInfo.getTel();
            case 6:
                return deliveryInfo.getEmail();
            case 7:
                return userVO.getDateCreated();
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
        return String.class;
    }

    @Override
    protected String[] getColumnNameList() {
        return new String[]{"Mã", "Tên đăng nhập", "Họ, đệm", "Tên", "Địa chỉ",
                    "Điện thoại", "Email", "Ngày đăng ký"};
    }

    public void addUser(UserVO newUser) {
        if (getRowData() == null) {
            setRowData(new ArrayList<UserVO>());
        }
        getRowData().add(newUser);
        int rowIdx = getRowData().size() - 1;
        fireTableRowsInserted(rowIdx, rowIdx);
    }

    public void updateUser(UserVO oldUserVO, UserVO newUserVO) {
        List<UserVO> userVOs = getRowData();
        if (userVOs == null) return;
        
        int rowIdx = 0;
        for (UserVO userVO : userVOs) {
//            if (userVO.getUserName().equals(oldUserVO.getUserName())) {
//                userVO.setUserName(newUserVO.getUserName());
//                userVO.setDeliveryInfo(newUserVO.getDeliveryInfo());
//                fireTableRowsUpdated(rowIdx, rowIdx);
//                return;
//            }
            if (userVO.getUserId().longValue() == oldUserVO.getUserId().longValue()) {
                userVO.setUserName(newUserVO.getUserName());
                userVO.setDeliveryInfo(newUserVO.getDeliveryInfo());
                fireTableRowsUpdated(rowIdx, rowIdx);
                return;
            }
            rowIdx++;
        }
    }
}
