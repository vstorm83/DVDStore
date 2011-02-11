/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.panel;

import java.util.Date;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ExportCardPanelServiceDelegate {

    public List<ExportCardVO> getExportCardVOs(
            String userNameSearch, Date startDateSearch, Date endDateSearch);

    public void revertExportCard(List<Long> exportCardIds);

    public byte[] printExportCard(List<Long> exportCardIds);
}
