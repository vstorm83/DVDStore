/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component.window;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="LoginWindowTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.window.LoginWindowTag",
    description="Render an HTML LoginWindow",
    allowDynamicAttributes=true)
public class LoginWindow extends AbstractWindow {

    public static final String TEMPLATE = "window/LoginWindow";
    public static final String TEMPLATE_CLOSE = "window/LoginWindow_close";

    public LoginWindow(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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
