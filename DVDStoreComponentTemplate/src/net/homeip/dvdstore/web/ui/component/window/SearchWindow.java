/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component.window;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.inject.Scope.Strategy;
import com.opensymphony.xwork2.util.ValueStack;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="SearchWindowTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.window.SearchWindowTag",
    description="Render an HTML SearchWindow",
    allowDynamicAttributes=true)
public class SearchWindow extends AbstractWindow {

    public static final String TEMPLATE = "window/SearchWindow";
    public static final String TEMPLATE_CLOSE = "window/SearchWindow_close";

    public SearchWindow(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {        
        super(stack, request, response);
    }

    @Override
    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }
}
