/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;


/**
 *
 * @author VU VIET PHUONG
 */
public class ActorTableModel extends AbstractDSTableModel<ActorVO> {

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getRowData() == null || rowIndex < 0 ||
                rowIndex > getRowData().size() - 1) {
            return null;
        }

        ActorVO actorVO = getRowData().get(rowIndex);
        if (columnIndex == 0) {
            return actorVO.getActorId();
        } else {
            return actorVO.getActorName();
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
        return new String[] {"Mã", "Diễn viên"};
    }
}