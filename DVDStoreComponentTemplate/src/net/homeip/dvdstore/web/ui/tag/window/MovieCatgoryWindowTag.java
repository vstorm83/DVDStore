/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.window;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.window.MovieCatgoryWindow;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryWindowTag extends AbstractWindowTag{

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new MovieCatgoryWindow(stack, req, res);
    }
}