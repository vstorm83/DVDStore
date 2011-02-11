/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class DuplicateException extends RuntimeException {

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException() {
    }

}
