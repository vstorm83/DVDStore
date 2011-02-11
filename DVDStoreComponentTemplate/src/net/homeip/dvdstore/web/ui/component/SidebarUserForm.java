/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="SidebarUserFormTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.SidebarUserFormTag",
    description="Render an HTML SidebarUserForm",
    allowDynamicAttributes=true)
public class SidebarUserForm extends ActionUIBean {
    /**
     * The name of the default template for the SidebarUserFormTag
     */
    final public static String TEMPLATE = "SidebarUserForm";
    private String userNameKey;
    private String passwordKey;
    private String registerAction;
    private String registerNamespace;
    private String loggedIn;

    public SidebarUserForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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

        if (passwordKey != null) {
            addParameter("passwordKey", findString(passwordKey));
        }
        if (registerAction != null) {
            addParameter("registerAction", findString(registerAction));
        }
        if (registerNamespace != null) {
            addParameter("registerNamespace", findString(registerNamespace));
        }
        if (loggedIn != null) {
            addParameter("loggedIn", findValue(loggedIn, Boolean.class));
        }
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
