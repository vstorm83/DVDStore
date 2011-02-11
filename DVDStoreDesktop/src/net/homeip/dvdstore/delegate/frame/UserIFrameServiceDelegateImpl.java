/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.Calendar;
import java.util.List;
import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import net.homeip.dvdstore.webservice.UserIFrameService;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;

// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class UserIFrameServiceDelegateImpl implements UserIFrameServiceDelegate {
    private UserIFrameService userIFrameService;

    public byte[] printTradeHistory(List<Long> userIds) {
        try {
            return userIFrameService.printTradeHistory(userIds);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public List<UserVO> getUserVOs(String userNameSearch, String firstNameSearch,
            String lastNameSearch, String addressSearch, String telSearch, String emailSearch,
            Calendar startDateSearch, Calendar endDateSearch) {
        if (userIFrameService == null) {
            throw new IllegalStateException("userIFrameService has not set");
        }

        try {
            return userIFrameService.getUserVOs(userNameSearch,
                            firstNameSearch, lastNameSearch, addressSearch,
                            telSearch, emailSearch, startDateSearch.getTime(), endDateSearch.getTime());
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void deleteUser(List<Long> userIds, boolean ignoreReference)
            throws DBReferenceViolationException {
        try {
            userIFrameService.deleteUser(userIds, ignoreReference);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setUserIFrameService(UserIFrameService userIFrameService) {
        this.userIFrameService = userIFrameService;
    }

}
