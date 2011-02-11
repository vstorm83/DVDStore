/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import net.homeip.dvdstore.model.LoginModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class LoginEvent extends EventObject {
    private String userName;
    private String password;
    private LoginModel loginModel;

    public LoginEvent(Object source) {
        super(source);
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
