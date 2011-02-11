/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException(Throwable cause) {
        super(cause);
    }

    public EmailDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDuplicateException(String message) {
        super(message);
    }

    public EmailDuplicateException() {
    }

}
