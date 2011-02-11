/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.MovieCatgoryCell;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryCellTag extends ActionUITag {
    private String movieCatgoryId;
    private String movieCatgoryName;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new MovieCatgoryCell(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        MovieCatgoryCell cell = (MovieCatgoryCell)getComponent();
        cell.setMovieCatgoryId(movieCatgoryId);
        cell.setMovieCatgoryName(movieCatgoryName);
    }

    public String getMovieCatgoryId() {
        return movieCatgoryId;
    }

    public void setMovieCatgoryId(String movieCatgoryId) {
        this.movieCatgoryId = movieCatgoryId;
    }

    public String getMovieCatgoryName() {
        return movieCatgoryName;
    }

    public void setMovieCatgoryName(String movieCatgoryName) {
        this.movieCatgoryName = movieCatgoryName;
    }

}
