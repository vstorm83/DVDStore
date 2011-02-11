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
    name="MovieCatgoryWindowTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.window.MovieCatgoryWindowTag",
    description="Render an HTML MovieCatgoryWindow",
    allowDynamicAttributes=true)
public class MovieCatgoryWindow extends AbstractWindow {

    public static final String TEMPLATE = "window/MovieCatgoryWindow";
    public static final String TEMPLATE_CLOSE = "window/MovieCatgoryWindow_close";

    public MovieCatgoryWindow(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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