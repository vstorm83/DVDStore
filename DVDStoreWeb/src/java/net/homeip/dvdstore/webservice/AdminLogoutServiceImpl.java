/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import javax.jws.WebService;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.AdminLogoutService")
public class AdminLogoutServiceImpl implements AdminLogoutService {

    @Override
    public void logout() {
        System.out.println("aabx+++++++++++++++++++++++++++");
    }

}
