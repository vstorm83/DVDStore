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
@StrutsTag(name="MovieGridTag",
tldTagClass="net.homeip.dvdstore.web.ui.tag.list.MovieGridTag",
description="Render a MovieGrid")
public class MovieGrid extends SimpleListUIBean {
    final public static String TEMPLATE = "list/MovieGrid";

    public MovieGrid(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}