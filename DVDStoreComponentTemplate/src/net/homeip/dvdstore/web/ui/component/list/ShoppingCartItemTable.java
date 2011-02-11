/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component.list;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.ListUIBean;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartItemTable extends ListUIBean {
    private String confirmForm;

    public ShoppingCartItemTable(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return "list/ShoppingCartItemTable";
    }

    @Override
    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (confirmForm != null) {
            addParameter("confirmForm", findValue(confirmForm, Boolean.class));
        }
    }

    public void setConfirmForm(String confirmForm) {
        this.confirmForm = confirmForm;
    }

}