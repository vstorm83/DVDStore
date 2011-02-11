/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitImportCardPanelVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface ImportCardPanelService {

    public InitImportCardPanelVO getInitImportCardPanelVO(String supplierNameSearch,
            Date startDateSearch, Date endDateSearch);

    public List<ImportCardVO> getImportCardVOs(String supplierNameSearch,
            Date startDateSearch, Date endDateSearch);

    public List<ImportCardVO> findImportCardVOs(List<Long> importCardIds);

    public void addImportCard(SupplierVO supplierVO);

    public void deleteImportCard(List<Long> importCardIds);

    public void addImportCardItem(Long importCardId, List<Long> movIds);

    public void deleteImportCardItem(Long importCardId, List<Long> movIds);

    public void updateImportCardItem(ImportCardVO importCardVO) throws InvalidAdminInputException;

    public byte[] printImportCard(List<Long> importCardIds);
}
