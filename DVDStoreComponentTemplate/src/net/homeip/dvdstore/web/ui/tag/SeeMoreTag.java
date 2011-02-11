/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.SeeMore;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class SeeMoreTag extends AbstractUITag {
    private String action;
    private String namespace;
    private String topGap;
    private String bottomGap;
    private String seemore;

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new SeeMore(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        SeeMore seemoreComponent = (SeeMore)getComponent();
        seemoreComponent.setAction(action);
        seemoreComponent.setTopGap(topGap);
        seemoreComponent.setBottomGap(bottomGap);
        seemoreComponent.setSeemore(seemore);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBottomGap() {
        return bottomGap;
    }

    public void setBottomGap(String bottomGap) {
        this.bottomGap = bottomGap;
    }

    public String getTopGap() {
        return topGap;
    }

    public void setTopGap(String topGap) {
        this.topGap = topGap;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getSeemore() {
        return seemore;
    }

    public void setSeemore(String seemore) {
        this.seemore = seemore;
    }
}