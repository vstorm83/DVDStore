/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.DeliveryInfoModifyForm;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class DeliveryInfoModifyFormTag extends ActionUITag {
    private String firstNameKey;
    private String lastNameKey;
    private String emailKey;
    private String addressKey;
    private String telKey;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new DeliveryInfoModifyForm(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        DeliveryInfoModifyForm form = (DeliveryInfoModifyForm)getComponent();
        form.setFirstNameKey(firstNameKey);
        form.setLastNameKey(lastNameKey);
        form.setEmailKey(emailKey);
        form.setAddressKey(addressKey);
        form.setTelKey(telKey);
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