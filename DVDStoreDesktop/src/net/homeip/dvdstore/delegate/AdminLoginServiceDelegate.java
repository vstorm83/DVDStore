/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import net.homeip.dvdstore.delegate.exception.LoginFailException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface AdminLoginServiceDelegate {

    public void login(String userName, String password)
            throws LoginFailException;

}
