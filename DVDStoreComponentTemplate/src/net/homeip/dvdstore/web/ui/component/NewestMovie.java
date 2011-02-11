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
    name="NewestMovieTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.NewestMovieTag",
    description="Render an HTML NewestMovie",
    allowDynamicAttributes=true)
public class NewestMovie extends MovieCell {
    /**
     * The name of the default template for the NewestMovieTag
     */
    private String movieDescription;
    private String movie;

    public NewestMovie(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return "NewestMovie";
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (movieDescription != null) {
            addParameter("movieDescription", findString(movieDescription));
        }
        if (movie != null) {
            addParameter("movie", findString(movie));
        }
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    @StrutsTagAttribute(description="Movie movieDescription attribute", type="String",
            required=true, rtexprvalue=false, name="movieDescription")
    public void setMovieDescription(String movieShortDescription) {
        this.movieDescription = movieShortDescription;
    }

    public String getMovie() {
        return movie;
    }

    @StrutsTagAttribute(description="Movie movie attribute", type="String",
            required=true, rtexprvalue=false, name="movie")
    public void setMovie(String movie) {
        this.movie = movie;
    }

}