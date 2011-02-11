/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.InvalidPasswordException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ChangeCredentialsServiceDelegate {

    public void updateAdminCredentials(String password,
            String newUserName, String newPassword)
            throws InvalidPasswordException, InvalidInputException;
    
}
