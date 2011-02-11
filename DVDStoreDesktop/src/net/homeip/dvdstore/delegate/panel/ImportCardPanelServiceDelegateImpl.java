/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.delegate.panel;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.Calendar;
import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitImportCardPanelVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.webservice.ImportCardPanelService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardPanelServiceDelegateImpl implements ImportCardPanelServiceDelegate {

    private ImportCardPanelService importCardPanelService;

    public byte[] printImportCard(List<Long> importCardIds) {
        try {
            return importCardPanelService.printImportCard(importCardIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateImportCardItem(ImportCardVO importCardVO) throws InvalidInputException {
        try {
            importCardPanelService.updateImportCardItem(importCardVO);
        } catch (InvalidAdminInputException ex) {
            throw new InvalidInputException(ex.getMessage(), ex);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteImportCardItem(Long importCardId, List<Long> movIds) {
        if (importCardPanelService == null) {
            throw new IllegalStateException("importCardPanelService has not set");
        }

        try {
            importCardPanelService.deleteImportCardItem(importCardId, movIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addImportCardItem(Long importCardId, List<Long> movIds) {
        if (importCardPanelService == null) {
            throw new IllegalStateException("importCardPanelService has not set");
        }

        try {
            importCardPanelService.addImportCardItem(importCardId, movIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteImportCard(List<Long> importCardIds) {
        if (importCardPanelService == null) {
            throw new IllegalStateException("importCardPanelService has not set");
        }

        try {
            importCardPanelService.deleteImportCard(importCardIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public List<ImportCardVO> getImportCardVOs(String supplierNameSearch,
            Calendar startDateSearch, Calendar endDateSearch) {
        if (importCardPanelService == null) {
            throw new IllegalStateException("importCardPanelService has not set");
        }

        try {
            return importCardPanelService.getImportCardVOs(supplierNameSearch,
                    startDateSearch.getTime(), endDateSearch.getTime());
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public InitImportCardPanelVO getInitImportCardPanelVO(String supplierNameSearch,
            Calendar startDateSearch, Calendar endDateSearch) {
        if (importCardPanelService == null) {
            throw new IllegalStateException("importCardPanelService has not set");
        }

        try {
            return importCardPanelService.getInitImportCardPanelVO(supplierNameSearch,
                    startDateSearch.getTime(), endDateSearch.getTime());
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addImportCard(SupplierVO supplierVO) {
        try {
            importCardPanelService.addImportCard(supplierVO);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setImportCardPanelService(ImportCardPanelService importCardPanelService) {
        this.importCardPanelService = importCardPanelService;
    }
    
}
