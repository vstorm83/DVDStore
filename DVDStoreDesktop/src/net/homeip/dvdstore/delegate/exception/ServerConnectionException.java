/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class ServerConnectionException extends RuntimeException {

    public ServerConnectionException(Throwable cause) {
        super(cause);
    }

    public ServerConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerConnectionException(String message) {
        super(message);
    }

    public ServerConnectionException() {
    }

}
