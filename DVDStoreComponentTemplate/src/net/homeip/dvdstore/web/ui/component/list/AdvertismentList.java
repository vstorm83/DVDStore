/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component.list;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(name="AdvertismentListTag",
tldTagClass="net.homeip.dvdstore.web.ui.tag.list.AdvertismentListTag",
description="Render a AdvertismentList")
public class AdvertismentList extends SimpleListUIBean {
    final public static String TEMPLATE = "list/AdvertismentList";

    public AdvertismentList(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}