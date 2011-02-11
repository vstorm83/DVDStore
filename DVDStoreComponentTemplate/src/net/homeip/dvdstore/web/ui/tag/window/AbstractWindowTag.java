/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.window;

import net.homeip.dvdstore.web.ui.component.window.AbstractWindow;
import org.apache.struts2.views.jsp.ui.AbstractClosingTag;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class AbstractWindowTag extends AbstractClosingTag {
    private String itemTitle;

    @Override
    protected void populateParams() {
        super.populateParams();

        AbstractWindow itemWrapper = (AbstractWindow)getComponent();        
        itemWrapper.setItemTitle(itemTitle);
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
}