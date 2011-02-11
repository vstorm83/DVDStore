/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import net.homeip.dvdstore.listener.event.LogoutEvent;

/**
 *
 * @author VU VIET PHUONG
 */
public interface LogoutListener {
    public void logoutPerformed(LogoutEvent logoutEvent);
}
