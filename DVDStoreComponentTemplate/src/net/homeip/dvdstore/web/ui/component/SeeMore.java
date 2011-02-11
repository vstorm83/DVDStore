/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="SeeMoreTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.SeeMoreTag",
    description="Render an HTML cell of SeeMore",
    allowDynamicAttributes=true)
public class SeeMore extends ActionUIBean {
    /**
     * The name of the default template for the SeeMoreTag
     */
    final public static String TEMPLATE = "SeeMore";    
    private String topGap;
    private String bottomGap;
    private String seemore;

    public SeeMore(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (topGap != null) {
            addParameter("topGap", findValue(topGap, Integer.class));
        }
        if (bottomGap != null) {
            addParameter("bottomGap", findValue(bottomGap, Integer.class));
        }
        if (seemore != null) {
            addParameter("seemore", TextProviderHelper.getText(seemore, "Seemore", stack));
        }
    }
    
    public String getTopGap() {
        return topGap;
    }

    @StrutsTagAttribute(description="Set top gap", type="Integer",
            required=false, rtexprvalue=false, name="topGap", defaultValue="5")
    public void setTopGap(String topGap) {
        this.topGap = topGap;
    }

    public String getBottomGap() {
        return bottomGap;
    }

    @StrutsTagAttribute(description="Set bottom gap", type="Integer",
            required=false, rtexprvalue=false, name="bottomGap", defaultValue="5")
    public void setBottomGap(String bottomGap) {
        this.bottomGap = bottomGap;
    }    

    public String getSeemore() {
        return seemore;
    }

    @StrutsTagAttribute(description="Set seemore text", type="String",
            required=true, rtexprvalue=false, name="seemore", defaultValue="Seemore")
    public void setSeemore(String seemore) {
        this.seemore = seemore;
    }
}
