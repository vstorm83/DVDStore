/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminPasswordException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface ChangeCredentialsService {
    @WebMethod(action="updateAdminCredentials")
    public void updateAdminCredentials(
            @WebParam(name="password") String password,
            @WebParam(name="newUserName") String newUserName,
            @WebParam(name="newPassword") String newPassword)
            throws InvalidAdminPasswordException, InvalidAdminInputException;

}
