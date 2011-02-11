/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.list;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.list.MovieCatgoryList;
import net.homeip.dvdstore.web.ui.tag.support.AbstractSimpleListTag;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryListTag extends AbstractSimpleListTag {

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new MovieCatgoryList(stack, req, res);
    }
}
