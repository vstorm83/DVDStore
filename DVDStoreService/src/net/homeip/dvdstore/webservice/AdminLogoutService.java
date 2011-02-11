/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface AdminLogoutService {
    @WebMethod(action="logout")
    public void logout();
}