/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.delegate;

import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.InvalidPasswordException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.webservice.ChangeCredentialsService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminPasswordException;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChangeCredentialsServiceDelegateImpl implements ChangeCredentialsServiceDelegate {

    private ChangeCredentialsService changeCredentialsService;

    public void updateAdminCredentials(String password,
            String newUserName, String newPassword) throws InvalidPasswordException, InvalidInputException {
        if (changeCredentialsService == null) {
            throw new IllegalStateException("changeCredentialsService has not set");
        }

        try {
            changeCredentialsService.updateAdminCredentials(password, newUserName, newPassword);
        } catch (InvalidAdminInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex.getMessage(), ex);
        } catch (InvalidAdminPasswordException ex) {
            ex.printStackTrace();
            throw new InvalidPasswordException(ex.getMessage(), ex);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setChangeCredentialsService(ChangeCredentialsService changeCredentialsService) {
        this.changeCredentialsService = changeCredentialsService;
    }
}
