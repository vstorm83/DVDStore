/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.delegate;

import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.webservice.SupplierService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierServiceDelegateImpl implements SupplierServiceDelegate {

    private SupplierService supplierService;

    public List<SupplierVO> getSupplierList() {
        if (supplierService == null) {
            throw new IllegalStateException("supplierService has not set");
        }

        try {
            return supplierService.getSupplierList();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addSupplier(SupplierVO supplierVO)
            throws InvalidInputException, DuplicateException {
        try {
            supplierService.addSupplier(supplierVO);
        } catch (InvalidAdminInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex.getMessage(), ex);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteSupplier(List<Long> movCatIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        try {
            supplierService.deleteSupplier(movCatIdList, ignoreReference);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateSupplier(SupplierVO supplierVO)
            throws InvalidInputException, DuplicateException {
        try {
            supplierService.updateSupplier(supplierVO);
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

    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }
}
