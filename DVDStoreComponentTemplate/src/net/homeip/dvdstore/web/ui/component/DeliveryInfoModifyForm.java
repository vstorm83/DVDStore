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
    name="DeliveryInfoModifyFormTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.DeliveryInfoModifyFormTag",
    description="Render an HTML DeliveryInfoModifyForm",
    allowDynamicAttributes=true)
public class DeliveryInfoModifyForm extends ActionUIBean {
    /**
     * The name of the default template for the DeliveryInfoModifyFormTag
     */
    final public static String TEMPLATE = "DeliveryInfoModifyForm";
    private String firstNameKey;
    private String lastNameKey;
    private String emailKey;
    private String addressKey;
    private String telKey;

    public DeliveryInfoModifyForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

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

    public void setLastNameKey(String lastNameKey) {
        this.lastNameKey = lastNameKey;
    }

    public void setTelKey(String telKey) {
        this.telKey = telKey;
    }

}