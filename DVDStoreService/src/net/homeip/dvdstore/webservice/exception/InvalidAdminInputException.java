/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class InvalidAdminInputException extends RuntimeException {

    public InvalidAdminInputException(Throwable cause) {
        super(cause);
    }

    public InvalidAdminInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAdminInputException(String message) {
        super(message);
    }

    public InvalidAdminInputException() {
    }

}
