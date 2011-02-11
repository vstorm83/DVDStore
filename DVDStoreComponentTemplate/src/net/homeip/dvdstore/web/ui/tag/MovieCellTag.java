/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.MovieCell;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCellTag extends ActionUITag {
    private String movieId;
    private String moviePictureUrl;
    private String movieName;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new MovieCell(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        MovieCell cell = (MovieCell)getComponent();
        cell.setMovieId(movieId);
        cell.setMovieName(movieName);
        cell.setMoviePictureUrl(moviePictureUrl);
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePictureUrl() {
        return moviePictureUrl;
    }

    public void setMoviePictureUrl(String moviePictureUrl) {
        this.moviePictureUrl = moviePictureUrl;
    }
}