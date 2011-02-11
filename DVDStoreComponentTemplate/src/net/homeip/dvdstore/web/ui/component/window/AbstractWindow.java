/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component.window;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.ClosingUIBean;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class AbstractWindow extends ClosingUIBean {    
    private String itemTitle;

    public AbstractWindow(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (itemTitle != null) {
            addParameter("itemTitle", TextProviderHelper.getText(itemTitle,
                    findString(stripExpressionIfAltSyntax(itemTitle)), getStack()));
            
        }
    }

    public String getItemTitle() {
        return itemTitle;
    }

    @StrutsTagAttribute(description="Item Title", type="String", 
        defaultValue="", name="itemTitle", rtexprvalue=false, required=false)
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}