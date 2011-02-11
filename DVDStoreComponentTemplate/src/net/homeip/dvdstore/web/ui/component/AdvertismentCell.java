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
import org.apache.struts2.views.annotations.StrutsTagAttribute;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="AdvertismentCellTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.AdvertismentCellTag",
    description="Render an HTML cell of movie",
    allowDynamicAttributes=true)
public class AdvertismentCell extends UIBean {
    /**
     * The name of the default template for the AdvertismentCellTag
     */
    final public static String TEMPLATE = "AdvertismentCell";
    private String advertismentSource;
    private String advertismentLink;

    public AdvertismentCell(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (advertismentSource != null) {
            addParameter("advertismentSource", findString(advertismentSource));
        }
        if (advertismentLink != null) {
            addParameter("advertismentLink", findString(advertismentLink));
        }
    }

    public String getAdvertismentLink() {
        return advertismentLink;
    }

    @StrutsTagAttribute(description="Set advertismentLink attribute", type="String",
            required=true, rtexprvalue=false, name="advertismentLink")
    public void setAdvertismentLink(String advertismentLink) {
        this.advertismentLink = advertismentLink;
    }

    public String getAdvertismentSource() {
        return advertismentSource;
    }

    @StrutsTagAttribute(description="Set advertismentSource attribute", type="String",
            required=true, rtexprvalue=false, name="advertismentSource")
    public void setAdvertismentSource(String advertismentSource) {
        this.advertismentSource = advertismentSource;
    }
}
