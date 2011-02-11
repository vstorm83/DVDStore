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
    name="BestMovieWindowTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.window.BestMovieWindowTag",
    description="Render an HTML BestMovieWindow",
    allowDynamicAttributes=true)
public class BestMovieWindow extends AbstractWindow {

    public static final String TEMPLATE = "window/BestMovieWindow";
    public static final String TEMPLATE_CLOSE = "window/BestMovieWindow_close";

    public BestMovieWindow(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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
