/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.delegate.panel;

import java.util.Date;
import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;
import net.homeip.dvdstore.webservice.ExportCardPanelService;

/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardPanelServiceDelegateImpl implements ExportCardPanelServiceDelegate {

    private ExportCardPanelService exportCardPanelService;

    public byte[] printExportCard(List<Long> exportCardIds) {
        try {
            return exportCardPanelService.printExportCard(exportCardIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public List<ExportCardVO> getExportCardVOs(
            String userNameSearch, Date startDateSearch, Date endDateSearch) {
        if (exportCardPanelService == null) {
            throw new IllegalStateException("exportCardPanelService has not set");
        }

        try {
            return exportCardPanelService.getExportCardVOs(
                    userNameSearch, startDateSearch, endDateSearch);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void revertExportCard(List<Long> exportCardIds) {
                if (exportCardPanelService == null) {
            throw new IllegalStateException("exportCardPanelService has not set");
        }

        try {
            exportCardPanelService.revertExportCard(exportCardIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setExportCardPanelService(ExportCardPanelService exportCardPanelService) {
        this.exportCardPanelService = exportCardPanelService;
    }
    
}
