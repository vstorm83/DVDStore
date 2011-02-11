/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="RegisterFormTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.RegisterFormTag",
    description="Render an HTML RegisterForm",
    allowDynamicAttributes=true)
public class RegisterForm extends ActionUIBean {
    /**
     * The name of the default template for the RegisterFormTag
     */
    final public static String TEMPLATE = "RegisterForm";
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

    public RegisterForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (userNameKey != null) {
            addParameter("userNameKey", findString(userNameKey));
        }
        if (userNameNoteKey != null) {
            addParameter("userNameNote", TextProviderHelper.getText(userNameNoteKey, "", stack));
        }
        if (passwordKey != null) {
            addParameter("passwordKey", findString(passwordKey));
        }
        if (passwordNoteKey != null) {
            addParameter("passwordNote", TextProviderHelper.getText(passwordNoteKey, "", stack));
        }
        if (retypePasswordKey != null) {
            addParameter("retypePasswordKey", findString(retypePasswordKey));
        }
        if (firstNameKey != null) {
            addParameter("firstNameKey", findString(firstNameKey));
        }
        if (lastNameKey != null) {
            addParameter("lastNameKey", findString(lastNameKey));
        }
        if (emailKey != null) {
            addParameter("emailKey", findString(emailKey));
        }
        if (addressKey != null) {
            addParameter("addressKey", findString(addressKey));
        }
        if (telKey != null) {
            addParameter("telKey", findString(telKey));
        }
        if (securityCodeKey != null) {
            addParameter("securityCodeKey", findString(securityCodeKey));
        }
        if (inputSecurityCodeKey != null) {
            addParameter("inputSecurityCodeKey", findString(inputSecurityCodeKey));
        }
        if (submitButtonLabelKey != null) {
            addParameter("submitButtonLabel", TextProviderHelper.getText(submitButtonLabelKey, "", stack));
        }
        if (resetButtonLabelKey != null) {
            addParameter("resetButtonLabel", TextProviderHelper.getText(resetButtonLabelKey, "", stack));
        }
        if (registerHeaderKey != null) {
            addParameter("registerHeader", TextProviderHelper.getText(registerHeaderKey, "", stack));
        }
        if (smallRegisterHeaderKey != null) {
            addParameter("smallRegisterHeader", TextProviderHelper.getText(smallRegisterHeaderKey, "", stack));
        }
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

    public void setResetButtonLabelKey(String resetButtonLabelKey) {
        this.resetButtonLabelKey = resetButtonLabelKey;
    }

    public void setSubmitButtonLabelKey(String submitButtonLabelKey) {
        this.submitButtonLabelKey = submitButtonLabelKey;
    }

    public void setRegisterHeaderKey(String registerHeaderKey) {
        this.registerHeaderKey = registerHeaderKey;
    }

    public void setSmallRegisterHeaderKey(String smallRegisterHeaderKey) {
        this.smallRegisterHeaderKey = smallRegisterHeaderKey;
    }

}