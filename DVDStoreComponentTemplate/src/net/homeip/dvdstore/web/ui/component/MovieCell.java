/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="MovieCellTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.MovieCellTag",
    description="Render an HTML cell of movie",
    allowDynamicAttributes=true)
public class MovieCell extends ActionUIBean {
    /**
     * The name of the default template for the MovieCellTag
     */
    private String movieId;
    private String moviePictureUrl;
    private String movieName;

    public MovieCell(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return "MovieCell";
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();
        
        if (movieId != null) {
            addParameter("movieId", findString(movieId));
        }
        if (moviePictureUrl != null) {
            addParameter("moviePictureUrl", findString(moviePictureUrl));
        }
        if (movieName != null) {
            addParameter("movieName", findString(movieName));
        }
    }

    public String getMovieId() {
        return movieId;
    }

    @StrutsTagAttribute(description="MovieId attribute", type="String",
            required=true, rtexprvalue=false, name="movieId")
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    @StrutsTagAttribute(description="movie name attribute", type="String",
            required=true, rtexprvalue=false, name="movieName")
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMoviePictureUrl() {
        return moviePictureUrl;
    }

    @StrutsTagAttribute(description="Movie picture url attribute", type="String", 
            required=true, rtexprvalue=false, name="moviePictureUrl")
    public void setMoviePictureUrl(String moviePictureUrl) {
        this.moviePictureUrl = moviePictureUrl;
    }
}