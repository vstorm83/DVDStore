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
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.webservice.ActorService;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class ActorServiceDelegateImpl implements ActorServiceDelegate {

    private ActorService actorService;

    public List<ActorVO> getActorList() {
        if (actorService == null) {
            throw new IllegalStateException("actorService has not set");
        }

        try {
            return actorService.getActorList();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void addActor(ActorVO actorVO)
            throws InvalidInputException, DuplicateException {
        try {
            actorService.addActor(actorVO);
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

    public void deleteActor(List<Long> movCatIdList) {
        try {
            actorService.deleteActor(movCatIdList);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateActor(ActorVO actorVO)
            throws InvalidInputException, DuplicateException {
        try {
            actorService.updateActor(actorVO);
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

    public void setActorService(ActorService actorService) {
        this.actorService = actorService;
    }
}