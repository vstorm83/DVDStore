/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.webservice.DirectorService;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorServiceDelegateImpl implements DirectorServiceDelegate {

    private DirectorService directorService;

    public List<DirectorVO> getDirectorList() {
        if (directorService == null) {
            throw new IllegalStateException("directorService has not set");
        }

        try {
            return directorService.getDirectorList();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addDirector(DirectorVO directorVO)
            throws InvalidInputException, DuplicateException {
        try {
            directorService.addDirector(directorVO);
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

    public void deleteDirector(List<Long> movCatIdList) {
        try {
            directorService.deleteDirector(movCatIdList);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateDirector(DirectorVO directorVO)
            throws InvalidInputException, DuplicateException {
        try {
            directorService.updateDirector(directorVO);
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

    public void setDirectorService(DirectorService directorService) {
        this.directorService = directorService;
    }
}