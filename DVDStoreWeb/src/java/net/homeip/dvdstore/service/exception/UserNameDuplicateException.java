/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserNameDuplicateException extends RuntimeException {

    public UserNameDuplicateException(Throwable cause) {
        super(cause);
    }

    public UserNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameDuplicateException(String message) {
        super(message);
    }

    public UserNameDuplicateException() {
    }    
}
