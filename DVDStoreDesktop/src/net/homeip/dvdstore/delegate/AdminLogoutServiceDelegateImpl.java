/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import net.homeip.dvdstore.webservice.AdminLogoutService;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdminLogoutServiceDelegateImpl implements AdminLogoutServiceDelegate {
    private AdminLogoutService adminLogoutService;

    public void logout() {
        try {
            adminLogoutService.logout();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setAdminLogoutService(AdminLogoutService adminLogoutService) {
        this.adminLogoutService = adminLogoutService;
    }

}
