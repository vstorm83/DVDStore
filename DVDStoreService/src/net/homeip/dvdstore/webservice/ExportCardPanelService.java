/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface ExportCardPanelService {

    public List<ExportCardVO> getExportCardVOs(
            String userNameSearch, Date startDateSearch, Date endDateSearch);

    public List<ExportCardVO> findExportCardVOs(List<Long> exportCardIds);
    
    public void revertExportCard(List<Long> exportCardIds);

    public byte[] printExportCard(List<Long> exportCardIds);
}
