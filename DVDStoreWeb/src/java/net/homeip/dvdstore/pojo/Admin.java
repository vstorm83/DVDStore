/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminPasswordException;

/**
 *
 * @author VU VIET PHUONG
 */
public class Admin {
    private Long adminId;
    private String adminName;
    private String adminPass;
    private int version;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPass() {
        return adminPass;
    }

    public void setAdminPass(String adminPass) {
        this.adminPass = adminPass;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void updateCredentials(String password, String newUserName,
            String newPassword) throws InvalidAdminPasswordException, InvalidAdminInputException {
        validateInput(password, newUserName, newPassword);
        validatePassword(password);
        populateCredentials(newUserName, newPassword);
    }

    private void validateInput(String password, String newUserName, String newPassword) {
        if (ValidatorUtil.isEmpty(password, false)) {
            throw new InvalidAdminInputException("password");
        }
        if (ValidatorUtil.isEmpty(newUserName)) {
            throw new InvalidAdminInputException("newUserName");
        }
        if (ValidatorUtil.isEmpty(newPassword, false)) {
            throw new InvalidAdminInputException("newPassword");
        }
    }

    private void validatePassword(String password) {
        if (!password.equals(this.adminPass)) {
            throw new InvalidAdminPasswordException("password not match");
        }
    }

    private void populateCredentials(String newUserName, String newPassword) {
        this.adminName = newUserName.trim();
        this.adminPass = newPassword;
    }
    
}
