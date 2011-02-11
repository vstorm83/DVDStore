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
    name="SearchFormTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.SearchFormTag",
    description="Render an HTML SearchForm",
    allowDynamicAttributes=true)
public class SearchForm extends ActionUIBean {
    /**
     * The name of the default template for the SearchFormTag
     */
    final public static String TEMPLATE = "SearchForm";
    private String movieNameKey;
    private String actorNameKey;

    public SearchForm(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (movieNameKey != null) {
            addParameter("movieNameKey", findString(movieNameKey));
        }
       
        if (actorNameKey != null) {
            addParameter("actorNameKey", findString(actorNameKey));
        }
    }

    public String getActorNameKey() {
        return actorNameKey;
    }

    public void setActorNameKey(String actorNameKey) {
        this.actorNameKey = actorNameKey;
    }

    public String getMovieNameKey() {
        return movieNameKey;
    }

    public void setMovieNameKey(String movieNameKey) {
        this.movieNameKey = movieNameKey;
    }
    
}