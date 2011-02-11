/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class SendOrderFailException extends RuntimeException {

    public SendOrderFailException(Throwable cause) {
        super(cause);
    }

    public SendOrderFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendOrderFailException(String message) {
        super(message);
    }

    public SendOrderFailException() {
    }

}
