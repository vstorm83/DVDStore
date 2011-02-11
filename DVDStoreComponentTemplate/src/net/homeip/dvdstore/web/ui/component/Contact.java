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

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="ContactTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.ContactTag",
    description="Render an HTML cell of Contact",
    allowDynamicAttributes=true)
public class Contact extends UIBean {
    /**
     * The name of the default template for the ContactTag
     */
    final public static String TEMPLATE = "Contact";
    private String companyNameKey;
    private String addressKey;
    private String telKey;
    private String emailKey;

    public Contact(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (companyNameKey != null) {
            addParameter("companyName", findString(completeExpressionIfAltSyntax(companyNameKey)));
        }
        if (addressKey != null) {
            addParameter("addressLabel", TextProviderHelper.getText(addressKey, "", stack));
            addParameter("address", findString(completeExpressionIfAltSyntax(addressKey)));
        }
        if (telKey != null) {
            addParameter("telLabel", TextProviderHelper.getText(telKey, "", stack));
            addParameter("tel", findString(completeExpressionIfAltSyntax(telKey)));
        }
        if (emailKey != null) {
            addParameter("emailLabel", TextProviderHelper.getText(emailKey, "", stack));
            addParameter("email", findString(completeExpressionIfAltSyntax(emailKey)));
        }
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public void setCompanyNameKey(String companyNameKey) {
        this.companyNameKey = companyNameKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public void setTelKey(String telKey) {
        this.telKey = telKey;
    }

}