/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.model;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class DataChangingModel extends ValidationModel {
    public static final int DUPLICATE = 7;
    public static final int CONSTRAINT_VIOLATION = 8;
    private String duplicateProperty;
    private List<String> violationName;

    public String getDuplicateProperty() {
        return duplicateProperty;
    }

    public void setDuplicateProperty(String duplicateProperty) {
        this.duplicateProperty = duplicateProperty;
    }

    public List<String> getViolationName() {
        return violationName;
    }

    public void setViolationName(List<String> violationName) {
        this.violationName = violationName;
    }

}
