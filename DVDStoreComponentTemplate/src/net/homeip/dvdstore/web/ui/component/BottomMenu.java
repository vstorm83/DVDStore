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
    name="BottomMenuTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.BottomMenuTag",
    description="Render an HTML BottomMenu",
    allowDynamicAttributes=true)
public class BottomMenu extends UIBean {
    /**
     * The name of the default template for the BottomMenuTag
     */
    final public static String TEMPLATE = "BottomMenu";
    private String info;

    public BottomMenu(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (info != null) {
            addParameter("info", findString(completeExpressionIfAltSyntax(info)));
        }
    }

    public String getInfo() {
        return info;
    }

    @StrutsTagAttribute(description="Set info attribute", type="String",
            required=false, rtexprvalue=false, name="info")
    public void setInfo(String info) {
        this.info = info;
    }
}

