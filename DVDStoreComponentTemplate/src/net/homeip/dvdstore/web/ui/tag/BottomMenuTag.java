/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.BottomMenu;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class BottomMenuTag extends AbstractUITag {
    private String info;

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new BottomMenu(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        BottomMenu menu = (BottomMenu)getComponent();
        menu.setInfo(info);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
