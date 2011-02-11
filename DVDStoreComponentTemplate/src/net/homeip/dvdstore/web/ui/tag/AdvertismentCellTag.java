/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.AdvertismentCell;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdvertismentCellTag extends AbstractUITag {
    private String advertismentSource;
    private String advertismentLink;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new AdvertismentCell(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        AdvertismentCell cell = (AdvertismentCell)getComponent();
        cell.setAdvertismentSource(advertismentSource);
        cell.setAdvertismentLink(advertismentLink);
    }

    public String getAdvertismentLink() {
        return advertismentLink;
    }

    public void setAdvertismentLink(String advertismentLink) {
        this.advertismentLink = advertismentLink;
    }

    public String getAdvertismentSource() {
        return advertismentSource;
    }

    public void setAdvertismentSource(String advertismentSource) {
        this.advertismentSource = advertismentSource;
    }

}