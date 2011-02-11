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
    name="ChatWindowTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.window.ChatWindowTag",
    description="Render an HTML ChatWindow",
    allowDynamicAttributes=true)
public class ChatWindow extends AbstractWindow {

    public static final String TEMPLATE = "window/ChatWindow";
    public static final String TEMPLATE_CLOSE = "window/ChatWindow_close";

    public ChatWindow(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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
