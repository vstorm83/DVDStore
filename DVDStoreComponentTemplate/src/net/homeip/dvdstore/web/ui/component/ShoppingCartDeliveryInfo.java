/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="ShoppingCartDeliveryInfo",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.ShoppingCartDeliveryInfoTag",
    description="Render an HTML cell of ShoppingCartDeliveryInfo",
    allowDynamicAttributes=true)
public class ShoppingCartDeliveryInfo extends UIBean {
    /**
     * The name of the default template for the ShoppingCartDeliveryInfo
     */
    final public static String TEMPLATE = "ShoppingCartDeliveryInfo";
    private String addressKey;
    private String telKey;
    private String emailKey;
    private String paymentMethodKey;
    private String confirmForm;

    public ShoppingCartDeliveryInfo(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (addressKey != null) {
            addParameter("addressKey", findString(addressKey));
        }
        if (telKey != null) {
            addParameter("telKey", findString(telKey));
        }
        if (emailKey != null) {
            addParameter("emailKey", findString(emailKey));
        }
        if (paymentMethodKey != null) {
            addParameter("paymentMethodKey", findString(paymentMethodKey));
        }
        if (confirmForm != null) {
            addParameter("confirmForm", findValue(confirmForm, Boolean.class));
        }
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public void setTelKey(String telKey) {
        this.telKey = telKey;
    }

    public void setPaymentMethodKey(String paymentMethodKey) {
        this.paymentMethodKey = paymentMethodKey;
    }

    public void setConfirmForm(String confirmForm) {
        this.confirmForm = confirmForm;
    }

}