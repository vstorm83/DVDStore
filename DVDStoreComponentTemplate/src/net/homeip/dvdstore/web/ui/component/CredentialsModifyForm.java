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
    name="CredentialsModifyFormTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.CredentialsModifyFormTag",
    description="Render an HTML CredentialsModifyForm",
    allowDynamicAttributes=true)
public class CredentialsModifyForm extends ActionUIBean {
    /**
     * The name of the default template for the CredentialsModifyFormTag
     */
    final public static String TEMPLATE = "CredentialsModifyForm";
    private String userNameKey;
    private String userNameNoteKey;
    private String passwordKey;
    private String newPasswordKey;
    private String passwordNoteKey;    
    private String retypeNewPasswordKey;

    public CredentialsModifyForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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
        if (newPasswordKey != null) {
            addParameter("newPasswordKey", findString(newPasswordKey));
        }
        if (retypeNewPasswordKey != null) {
            addParameter("retypeNewPasswordKey", findString(retypeNewPasswordKey));
        }
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