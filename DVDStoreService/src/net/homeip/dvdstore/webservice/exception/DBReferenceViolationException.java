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
public class DBReferenceViolationException extends RuntimeException {
    private List<String> violationName;

    public DBReferenceViolationException(Throwable cause) {
        super(cause);
    }

    public DBReferenceViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBReferenceViolationException(String message) {
        super(message);
    }

    public DBReferenceViolationException() {
    }

    public List<String> getViolationName() {
        return violationName;
    }

    public void setViolationName(List<String> violationName) {
        this.violationName = violationName;
    }
    
}
