/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.Contact;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class ContactTag extends AbstractUITag {
    private String companyNameKey;
    private String addressKey;
    private String telKey;
    private String emailKey;
    
    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Contact(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        Contact contact = (Contact)getComponent();
        contact.setCompanyNameKey(companyNameKey);
        contact.setAddressKey(addressKey);
        contact.setTelKey(telKey);
        contact.setEmailKey(emailKey);
    }

    public String getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(String addressKey) {
        this.addressKey = addressKey;
    }

    public String getCompanyNameKey() {
        return companyNameKey;
    }

    public void setCompanyNameKey(String companyNameKey) {
        this.companyNameKey = companyNameKey;
    }

    public String getEmailKey() {
        return emailKey;
    }

    public void setEmailKey(String emailKey) {
        this.emailKey = emailKey;
    }

    public String getTelKey() {
        return telKey;
    }

    public void setTelKey(String telKey) {
        this.telKey = telKey;
    }

}