/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import net.homeip.dvdstore.delegate.AdminLogoutServiceDelegate;
import net.homeip.dvdstore.listener.event.LogoutEvent;

/**
 *
 * @author VU VIET PHUONG
 */
public class LogoutListenerImpl implements LogoutListener {
    private AdminLogoutServiceDelegate adminLogoutServiceDelegate;

    public void logoutPerformed(LogoutEvent logoutEvent) {
        adminLogoutServiceDelegate.logout();
    }

    public void setAdminLogoutServiceDelegate(AdminLogoutServiceDelegate adminLogoutServiceDelegate) {
        this.adminLogoutServiceDelegate = adminLogoutServiceDelegate;
    }

}
