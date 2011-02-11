/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;


/**
 *
 * @author VU VIET PHUONG
 */
public class ChatTableModel extends AbstractDSTableModel<ChatVO> {

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0 ||
                rowIndex > getRowData().size() - 1) {
            return null;
        }

        ChatVO chatVO = getRowData().get(rowIndex);
        if (columnIndex == 0) {
            return chatVO.getChatId();
        } else {
            return chatVO.getChatName();
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
        return new String[] {"Mã", "Chat nick"};
    }
}