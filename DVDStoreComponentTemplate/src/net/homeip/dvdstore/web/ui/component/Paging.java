/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="PagingTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.PagingTag",
    description="Render an HTML Paging",
    allowDynamicAttributes=true)
public class Paging extends ActionUIBean {
    /**
     * The name of the default template for the PagingTag
     */
    final public static String TEMPLATE = "Paging";

    public Paging(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
