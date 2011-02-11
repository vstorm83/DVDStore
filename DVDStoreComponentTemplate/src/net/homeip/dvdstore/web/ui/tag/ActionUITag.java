/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import net.homeip.dvdstore.web.ui.component.ActionUIBean;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class ActionUITag extends AbstractUITag {
    private String action;
    private String namespace;


    @Override
    protected void populateParams() {
        super.populateParams();

        ActionUIBean cell = (ActionUIBean)getComponent();
        cell.setAction(action);
        cell.setNamespace(namespace);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
