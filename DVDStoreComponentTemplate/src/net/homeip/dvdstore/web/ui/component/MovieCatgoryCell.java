/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="MovieCatgoryCellTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.MovieCatgoryCellTag",
    description="Render an HTML cell of Movie Catgory",
    allowDynamicAttributes=true)
public class MovieCatgoryCell extends ActionUIBean {
    /**
     * The name of the default template for the MovieCatgoryCellTag
     */
    final public static String TEMPLATE = "MovieCatgoryCell";
    private String movieCatgoryId;
    private String movieCatgoryName;

    public MovieCatgoryCell(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (movieCatgoryId != null) {
            addParameter("movieCatgoryId", findString(movieCatgoryId));
        }
        if (movieCatgoryName != null) {
            addParameter("movieCatgoryName", findString(movieCatgoryName));
        }
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