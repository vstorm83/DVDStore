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
    name="ChatCellTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.ChatCellTag",
    description="Render an HTML cell of Chat",
    allowDynamicAttributes=true)
public class ChatCell extends UIBean {
    /**
     * The name of the default template for the ChatCellTag
     */
    final public static String TEMPLATE = "ChatCell";
    private String chatNick;

    public ChatCell(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (chatNick != null) {
            addParameter("chatNick", findString(chatNick));
        }
    }

    public String getChatNick() {
        return chatNick;
    }

    @StrutsTagAttribute(description="Set chatNick attribute", type="String",
            required=true, rtexprvalue=false, name="chatNick")
    public void setChatNick(String chatNick) {
        this.chatNick = chatNick;
    }        
}
