/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.frame;

import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.webservice.BussinessIFrameService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.NotEnoughMovieException;

/**
 *
 * @author VU VIET PHUONG
 */
public class BussinessIFrameServiceDelegateImpl implements BussinessIFrameServiceDelegate {
    private BussinessIFrameService bussinessIFrameService;

    public List<OrderVO> getOrderVOs() {
        if (bussinessIFrameService == null) {
            throw new IllegalStateException("bussinessIFrameService has not set");
        }

        try {
            return bussinessIFrameService.getOrderVOs();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteOrder(List<Long> orderIdList) {
        try {
            bussinessIFrameService.deleteOrder(orderIdList);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteOrderItem(Long orderId, List<Long> orderItemIdList) {
        try {
            bussinessIFrameService.deleteOrderItem(orderId, orderItemIdList);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addOrderItem(Long orderId, List<Long> movIds) {
        try {
            bussinessIFrameService.addOrderItem(orderId, movIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateOrder(OrderVO orderVO) throws InvalidInputException {
        try {
            bussinessIFrameService.updateOrder(orderVO);
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

    public void completeOrder(List<Long> orderIds, boolean ignoreQuantity)
            throws NotEnoughMovieException {
        try {
            bussinessIFrameService.completeOrder(orderIds, ignoreQuantity);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setBussinessIFrameService(BussinessIFrameService bussinessIFrameService) {
        this.bussinessIFrameService = bussinessIFrameService;
    }

}
