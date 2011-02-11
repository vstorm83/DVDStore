/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.SearchForm;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class SearchFormTag extends ActionUITag {
    private String movieNameKey;
    private String actorNameKey;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new SearchForm(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        SearchForm form = (SearchForm)getComponent();
        form.setMovieNameKey(movieNameKey);
        form.setActorNameKey(actorNameKey);
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