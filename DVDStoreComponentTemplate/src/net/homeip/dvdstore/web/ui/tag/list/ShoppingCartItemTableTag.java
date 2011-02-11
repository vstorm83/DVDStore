/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.list;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.list.ShoppingCartItemTable;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractListTag;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartItemTableTag extends AbstractListTag {
    private String confirmForm;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ShoppingCartItemTable(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        ShoppingCartItemTable table = (ShoppingCartItemTable)getComponent();
        table.setConfirmForm(confirmForm);
    }

    public void setConfirmForm(String confirmForm) {
        this.confirmForm = confirmForm;
    }

}
