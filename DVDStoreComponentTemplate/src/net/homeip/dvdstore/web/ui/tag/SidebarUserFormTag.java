/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.SidebarUserForm;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class SidebarUserFormTag extends ActionUITag {
    private String userNameKey;
    private String passwordKey;
    private String registerAction;
    private String registerNamespace;
    private String loggedIn;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new SidebarUserForm(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        SidebarUserForm form = (SidebarUserForm)getComponent();
        form.setUserNameKey(userNameKey);
        form.setPasswordKey(passwordKey);
        form.setRegisterAction(registerAction);
        form.setRegisterNamespace(registerNamespace);
        form.setLoggedIn(loggedIn);
    }

    public String getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public String getUserNameKey() {
        return userNameKey;
    }

    public void setUserNameKey(String userNameKey) {
        this.userNameKey = userNameKey;
    }

    public String getRegisterAction() {
        return registerAction;
    }

    public void setRegisterAction(String registerAction) {
        this.registerAction = registerAction;
    }

    public String getRegisterNamespace() {
        return registerNamespace;
    }

    public void setRegisterNamespace(String registerNamespace) {
        this.registerNamespace = registerNamespace;
    }

    public void setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
    }

}