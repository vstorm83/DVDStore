/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.webservice.ReportDialogService;

/**
 *
 * @author VU VIET PHUONG
 */
public class ReportDialogServiceDelegateImpl implements ReportDialogServiceDelegate {
    private ReportDialogService reportDialogService;
    
    public byte[] reportActorProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportActorProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportDirectorProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportDirectorProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportMovieCatgoryProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportMovieCatgoryProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportMovieProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportMovieProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportSupplierProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportSupplierProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportTimeProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportTimeProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportTimeProfitByChart(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportTimeProfitByChart(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public byte[] reportUserProfit(DateSearchVO startDate, DateSearchVO endDate) {
        if (reportDialogService == null) {
            throw new IllegalStateException("reportDialogService has not set");
        }

        try {
            return reportDialogService.reportUserProfit(startDate, endDate);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setReportDialogService(ReportDialogService reportDialogService) {
        this.reportDialogService = reportDialogService;
    }

}
