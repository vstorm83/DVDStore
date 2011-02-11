/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.ShoppingCartDeliveryInfo;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartDeliveryInfoTag extends AbstractUITag {
    private String addressKey;
    private String telKey;
    private String emailKey;
    private String paymentMethodKey;
    private String confirmForm;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ShoppingCartDeliveryInfo(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        ShoppingCartDeliveryInfo info = (ShoppingCartDeliveryInfo)getComponent();
        info.setAddressKey(addressKey);
        info.setTelKey(telKey);
        info.setEmailKey(emailKey);
        info.setPaymentMethodKey(paymentMethodKey);
        info.setConfirmForm(confirmForm);
    }

    public String getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getPaymentMethodKey() {
        return paymentMethodKey;
    }

    public void setPaymentMethodKey(String paymentMethodKey) {
        this.paymentMethodKey = paymentMethodKey;
    }

    public String getTelKey() {
        return telKey;
    }

    public void setTelKey(String telKey) {
        this.telKey = telKey;
    }

    public void setConfirmForm(String confirmForm) {
        this.confirmForm = confirmForm;
    }

}
