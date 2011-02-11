/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.ChatCell;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChatCellTag extends AbstractUITag {
    private String chatNick;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ChatCell(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        ChatCell cell = (ChatCell)getComponent();
        cell.setChatNick(chatNick);
    }

    public String getChatNick() {
        return chatNick;
    }

    public void setChatNick(String chatNick) {
        this.chatNick = chatNick;
    }

}