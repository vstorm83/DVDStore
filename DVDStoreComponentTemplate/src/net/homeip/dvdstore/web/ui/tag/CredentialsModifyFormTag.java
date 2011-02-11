/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.CredentialsModifyForm;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class CredentialsModifyFormTag extends ActionUITag {
    private String userNameKey;
    private String userNameNoteKey;
    private String passwordKey;
    private String newPasswordKey;
    private String passwordNoteKey;
    private String retypeNewPasswordKey;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new CredentialsModifyForm(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        CredentialsModifyForm form = (CredentialsModifyForm)getComponent();
        form.setUserNameKey(userNameKey);
        form.setUserNameNoteKey(userNameNoteKey);
        form.setPasswordKey(passwordKey);
        form.setPasswordNoteKey(passwordNoteKey);
        form.setNewPasswordKey(newPasswordKey);
        form.setRetypeNewPasswordKey(retypeNewPasswordKey);
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public void setPasswordNoteKey(String passwordNoteKey) {
        this.passwordNoteKey = passwordNoteKey;
    }

    public void setUserNameKey(String userNameKey) {
        this.userNameKey = userNameKey;
    }

    public void setUserNameNoteKey(String userNameNoteKey) {
        this.userNameNoteKey = userNameNoteKey;
    }

    public void setNewPasswordKey(String newPasswordKey) {
        this.newPasswordKey = newPasswordKey;
    }

    public void setRetypeNewPasswordKey(String retypeNewPasswordKey) {
        this.retypeNewPasswordKey = retypeNewPasswordKey;
    }
    
}