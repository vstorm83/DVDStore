/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.panel;

import java.util.Calendar;
import java.util.List;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitImportCardPanelVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ImportCardPanelServiceDelegate {
    public InitImportCardPanelVO getInitImportCardPanelVO(String supplierNameSearch,
            Calendar startDateSearch, Calendar endDateSearch);
    public List<ImportCardVO> getImportCardVOs(String supplierNameSearch,
            Calendar startDateSearch, Calendar endDateSearch);

    public void addImportCard(SupplierVO supplierVO);

    public void deleteImportCard(List<Long> importCardIds);

    public void addImportCardItem(Long importCardId, List<Long> movIds);

    public void deleteImportCardItem(Long importCardId, List<Long> movIds);

    public void updateImportCardItem(ImportCardVO importCardVO) throws InvalidInputException;

    public byte[] printImportCard(List<Long> importCardIds);
}
