/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class LoginFailException extends RuntimeException {

    public LoginFailException(Throwable cause) {
        super(cause);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(String message) {
        super(message);
    }

    public LoginFailException() {
    }

}
