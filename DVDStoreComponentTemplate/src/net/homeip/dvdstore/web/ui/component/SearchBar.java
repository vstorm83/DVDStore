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
    name="SearchBarTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.SearchBarTag",
    description="Render an HTML SearchBar",
    allowDynamicAttributes=true)
public class SearchBar extends ActionUIBean {
    /**
     * The name of the default template for the SearchBarTag
     */
    final public static String TEMPLATE = "SearchBar";

    public SearchBar(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
