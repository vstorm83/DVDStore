/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.NewestMovie;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class NewestMovieTag extends MovieCellTag {
    private String movieDescription;
    private String movie;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new NewestMovie(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        NewestMovie cell = (NewestMovie)getComponent();
        cell.setMovieDescription(movieDescription);
        cell.setMovie(movie);
    }

    public String getMovieShortDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

}
