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
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.webservice.MovieCatgoryService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryServiceDelegateImpl implements MovieCatgoryServiceDelegate {

    private MovieCatgoryService movieCatgoryService;

    public List<MovieCatgoryVO> getMovieCatgoryList() {
        if (movieCatgoryService == null) {
            throw new IllegalStateException("movieCatgoryService has not set");
        }

        try {
            return movieCatgoryService.getMovieCatgoryList();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidInputException, DuplicateException {
        try {
            movieCatgoryService.addMovCat(movieCatgoryVO);
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

    public void deleteMovCat(List<Long> movCatIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        try {
            movieCatgoryService.deleteMovCat(movCatIdList, ignoreReference);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidInputException, DuplicateException {
        try {
            movieCatgoryService.updateMovCat(movieCatgoryVO);
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

    public void setMovieCatgoryService(MovieCatgoryService movieCatgoryService) {
        this.movieCatgoryService = movieCatgoryService;
    }
}
