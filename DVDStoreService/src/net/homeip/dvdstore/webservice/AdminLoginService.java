/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import net.homeip.dvdstore.webservice.exception.LoginFailException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface AdminLoginService {
    @WebMethod(action="login")
    public void login(
            @WebParam(name="userName") String userName,
            @WebParam(name="password") String password) throws LoginFailException;
}
