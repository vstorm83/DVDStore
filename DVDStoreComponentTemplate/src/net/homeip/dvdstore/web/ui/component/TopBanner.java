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
    name="TopBannerTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.TopBannerTag",
    description="Render an HTML cell of TopBanner",
    allowDynamicAttributes=true)
public class TopBanner extends UIBean {
    /**
     * The name of the default template for the TopBannerTag
     */
    final public static String TEMPLATE = "TopBanner";

    public TopBanner(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
