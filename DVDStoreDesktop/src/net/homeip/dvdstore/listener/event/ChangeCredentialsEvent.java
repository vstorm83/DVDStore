/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import net.homeip.dvdstore.model.ChangeCredentialsModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChangeCredentialsEvent extends EventObject {
    private String password;
    private String newUserName;
    private String newPassword;
    private String retypePassword;
    private ChangeCredentialsModel changeCredentialsModel;

    public ChangeCredentialsModel getChangeCredentialsModel() {
        return changeCredentialsModel;
    }

    public void setChangeCredentialsModel(ChangeCredentialsModel changeCredentialsModel) {
        this.changeCredentialsModel = changeCredentialsModel;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }

    public ChangeCredentialsEvent(Object source) {
        super(source);
    }
    
}
