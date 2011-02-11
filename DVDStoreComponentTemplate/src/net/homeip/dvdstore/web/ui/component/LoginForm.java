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
    name="LoginFormTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.LoginFormTag",
    description="Render an HTML LoginForm",
    allowDynamicAttributes=true)
public class LoginForm extends ActionUIBean {
    /**
     * The name of the default template for the LoginFormTag
     */
    final public static String TEMPLATE = "LoginForm";

    public LoginForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

}
