/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service.exception;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartEmptyException extends RuntimeException {

    public ShoppingCartEmptyException(Throwable cause) {
        super(cause);
    }

    public ShoppingCartEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShoppingCartEmptyException(String message) {
        super(message);
    }

    public ShoppingCartEmptyException() {
    }

}
