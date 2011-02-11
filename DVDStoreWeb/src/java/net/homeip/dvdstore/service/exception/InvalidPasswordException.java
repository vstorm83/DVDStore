/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException() {
    }

}
