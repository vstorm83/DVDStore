/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.RegisterForm;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class RegisterFormTag extends ActionUITag {
    private String userNameKey;
    private String userNameNoteKey;
    private String passwordKey;
    private String passwordNoteKey;
    private String retypePasswordKey;
    private String firstNameKey;
    private String lastNameKey;
    private String emailKey;
    private String addressKey;
    private String telKey;
    private String securityCodeKey;
    private String inputSecurityCodeKey;
    private String submitButtonLabelKey;
    private String resetButtonLabelKey;
    private String registerHeaderKey;
    private String smallRegisterHeaderKey;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new RegisterForm(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        RegisterForm form = (RegisterForm)getComponent();
        form.setUserNameKey(userNameKey);
        form.setUserNameNoteKey(userNameNoteKey);
        form.setPasswordKey(passwordKey);
        form.setPasswordNoteKey(passwordNoteKey);
        form.setRetypePasswordKey(retypePasswordKey);
        form.setFirstNameKey(firstNameKey);
        form.setLastNameKey(lastNameKey);
        form.setEmailKey(emailKey);
        form.setAddressKey(addressKey);
        form.setTelKey(telKey);
        form.setSecurityCodeKey(securityCodeKey);
        form.setInputSecurityCodeKey(inputSecurityCodeKey);
        form.setSubmitButtonLabelKey(submitButtonLabelKey);
        form.setResetButtonLabelKey(resetButtonLabelKey);
        form.setRegisterHeaderKey(registerHeaderKey);
        form.setSmallRegisterHeaderKey(smallRegisterHeaderKey);
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public void setFirstNameKey(String firstNameKey) {
        this.firstNameKey = firstNameKey;
    }

    public void setInputSecurityCodeKey(String inputSecurityCodeKey) {
        this.inputSecurityCodeKey = inputSecurityCodeKey;
    }

    public void setLastNameKey(String lastNameKey) {
        this.lastNameKey = lastNameKey;
    }

    public void setPasswordKey(String passwordKey) {
        this.passwordKey = passwordKey;
    }

    public void setPasswordNoteKey(String passwordNoteKey) {
        this.passwordNoteKey = passwordNoteKey;
    }

    public void setRetypePasswordKey(String retypePasswordKey) {
        this.retypePasswordKey = retypePasswordKey;
    }

    public void setSecurityCodeKey(String securityCodeKey) {
        this.securityCodeKey = securityCodeKey;
    }

    public void setTelKey(String telKey) {
        this.telKey = telKey;
    }

    public void setUserNameKey(String userNameKey) {
        this.userNameKey = userNameKey;
    }

    public void setUserNameNoteKey(String userNameNoteKey) {
        this.userNameNoteKey = userNameNoteKey;
    }

    public void setRegisterHeaderKey(String registerHeaderKey) {
        this.registerHeaderKey = registerHeaderKey;
    }

    public void setResetButtonLabelKey(String resetButtonLabelKey) {
        this.resetButtonLabelKey = resetButtonLabelKey;
    }

    public void setSmallRegisterHeaderKey(String smallRegisterHeaderKey) {
        this.smallRegisterHeaderKey = smallRegisterHeaderKey;
    }

    public void setSubmitButtonLabelKey(String submitButtonLabelKey) {
        this.submitButtonLabelKey = submitButtonLabelKey;
    }

}