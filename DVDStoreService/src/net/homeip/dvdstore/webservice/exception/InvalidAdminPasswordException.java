/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class InvalidAdminPasswordException extends RuntimeException {

    public InvalidAdminPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidAdminPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAdminPasswordException(String message) {
        super(message);
    }

    public InvalidAdminPasswordException() {
    }

}
