/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author VU VIET PHUONG
 */
public class ValidationModel extends BaseResult {

    private Set<String> invalidProperies = new HashSet<String>();
    public static final int VALIDATE_FAIL = 5;

    public Set<String> getInvalidProperties() {
        return invalidProperies;
    }

    public void setInvalidProperties(Set<String> properties) {
        this.invalidProperies = properties;
    }

    public void addInvalidProperty(String property) {
        if (property == null) {
            throw new IllegalStateException("can't add null property");
        }
        invalidProperies.add(property);
    }
}
