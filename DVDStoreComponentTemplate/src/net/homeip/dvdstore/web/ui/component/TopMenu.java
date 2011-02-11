/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="TopMenuTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.TopMenuTag",
    description="Render an HTML TopMenu",
    allowDynamicAttributes=true)
public class TopMenu extends UIBean {
    /**
     * The name of the default template for the TopMenuTag
     */
    final public static String TEMPLATE = "TopMenu";
    private String mainAction;
    private String mainNamespace;
    private String contactAction;
    private String contactNamespace;
    private String guideAction;
    private String guideNamespace;
    private String registerAction;
    private String registerNamespace;
    private String main;
    private String contact;
    private String guide;
    private String register;

    public TopMenu(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (main != null) {
            addParameter("main", TextProviderHelper.getText(main, "", stack));
        }
        if (mainAction != null) {
            addParameter("mainAction", findString(mainAction));
        }
        if (mainNamespace != null) {
            addParameter("mainNamespace", findString(mainNamespace));
        }
        if (contact != null) {
            addParameter("contact", TextProviderHelper.getText(contact, "", stack));
        }
        if (contactAction != null) {
            addParameter("contactAction", findString(contactAction));
        }
        if (contactNamespace != null) {
            addParameter("contactNamespace", findString(contactNamespace));
        }
        if (guide != null) {
            addParameter("guide", TextProviderHelper.getText(guide, "", stack));
        }
        if (guideAction != null) {
            addParameter("guideAction", findString(guideAction));
        }
        if (guideNamespace != null) {
            addParameter("guideNamespace", findString(guideNamespace));
        }
        if (register != null) {
            addParameter("register", TextProviderHelper.getText(register, "", stack));
        }
        if (registerAction != null) {
            addParameter("registerAction", findString(registerAction));
        }
        if (registerNamespace != null) {
            addParameter("registerNamespace", findString(registerNamespace));
        }
    }

    public String getContact() {
        return contact;
    }

    @StrutsTagAttribute(description="Set contact attribute", type="String",
            required=true, rtexprvalue=false, name="contact")
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactAction() {
        return contactAction;
    }

    @StrutsTagAttribute(description="Set contactAction attribute", type="String",
            required=false, rtexprvalue=false, name="contactAction", defaultValue="Contact")
    public void setContactAction(String contactAction) {
        this.contactAction = contactAction;
    }

    public String getContactNamespace() {
        return contactNamespace;
    }

    @StrutsTagAttribute(description="Set contactNamespace attribute", type="String",
            required=false, rtexprvalue=false, name="contactNamespace", defaultValue="/")
    public void setContactNamespace(String contactNamespace) {
        this.contactNamespace = contactNamespace;
    }

    public String getGuide() {
        return guide;
    }

    @StrutsTagAttribute(description="Set guide attribute", type="String",
            required=true, rtexprvalue=false, name="guide")
    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getGuideAction() {
        return guideAction;
    }

    @StrutsTagAttribute(description="Set guideAction attribute", type="String",
            required=false, rtexprvalue=false, name="guideAction", defaultValue="Guide")
    public void setGuideAction(String guideAction) {
        this.guideAction = guideAction;
    }

    public String getGuideNamespace() {
        return guideNamespace;
    }

    @StrutsTagAttribute(description="Set guideNamespace attribute", type="String",
            required=false, rtexprvalue=false, name="guideNamespace", defaultValue="/")
    public void setGuideNamespace(String guideNamespace) {
        this.guideNamespace = guideNamespace;
    }

    public String getMain() {
        return main;
    }

    @StrutsTagAttribute(description="Set main attribute", type="String",
            required=true, rtexprvalue=false, name="main")
    public void setMain(String main) {
        this.main = main;
    }

    public String getMainAction() {
        return mainAction;
    }

    @StrutsTagAttribute(description="Set mainAction attribute", type="String",
            required=false, rtexprvalue=false, name="mainAction", defaultValue="Main")
    public void setMainAction(String mainAction) {
        this.mainAction = mainAction;
    }

    public String getMainNamespace() {
        return mainNamespace;
    }

    @StrutsTagAttribute(description="Set mainNamespace attribute", type="String",
            required=false, rtexprvalue=false, name="mainNamespace", defaultValue="/")
    public void setMainNamespace(String mainNamespace) {
        this.mainNamespace = mainNamespace;
    }

    public String getRegister() {
        return register;
    }

    @StrutsTagAttribute(description="Set register attribute", type="String",
            required=true, rtexprvalue=false, name="register")
    public void setRegister(String register) {
        this.register = register;
    }

    public String getRegisterAction() {
        return registerAction;
    }

    @StrutsTagAttribute(description="Set registerAction attribute", type="String",
            required=false, rtexprvalue=false, name="registerAction", defaultValue="Register")
    public void setRegisterAction(String registerAction) {
        this.registerAction = registerAction;
    }

    public String getRegisterNamespace() {
        return registerNamespace;
    }

    @StrutsTagAttribute(description="Set registerNamespace attribute", type="String",
            required=false, rtexprvalue=false, name="registerNamespace", defaultValue="/user")
    public void setRegisterNamespace(String registerNamespace) {
        this.registerNamespace = registerNamespace;
    }

}

