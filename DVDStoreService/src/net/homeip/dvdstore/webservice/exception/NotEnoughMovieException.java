/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice.exception;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class NotEnoughMovieException extends RuntimeException {
    private List<String> violationName;

    public NotEnoughMovieException(Throwable cause) {
        super(cause);
    }

    public NotEnoughMovieException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughMovieException(String message) {
        super(message);
    }

    public NotEnoughMovieException() {
    }

    public List<String> getViolationName() {
        return violationName;
    }

    public void setViolationName(List<String> violationName) {
        this.violationName = violationName;
    }
}
