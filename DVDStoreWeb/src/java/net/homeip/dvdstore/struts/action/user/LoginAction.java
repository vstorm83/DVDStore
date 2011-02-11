/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.user;

import net.homeip.dvdstore.struts.action.DSActionSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class LoginAction extends DSActionSupport {
    private String errCode;
    public static final int LOGIN_ERROR = 0;
    public static final int ACCESS_DENIED = 1;

    @Override
    public String execute() throws Exception {
        int code = -1;
        try {
            code = Integer.parseInt(errCode);
        } catch (NumberFormatException e) {}
        if (code == LOGIN_ERROR) {
            addActionError(getText("loginForm.loginFail"));
        } else if (code == ACCESS_DENIED) {
            addActionError(getText("loginForm.accessDenied"));
        } else {
            return SUCCESS;
        }
        return INPUT;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }    
}
