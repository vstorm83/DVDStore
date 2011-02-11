/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.movie;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.homeip.dvdstore.struts.action.DSActionSupport;
import net.homeip.dvdstore.pojo.web.vo.MovieDetailVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieDetailAction extends DSActionSupport 
        implements ModelDriven<MovieDetailVO>, Preparable {
    private MovieDetailVO movieDetailVO;
    private long movId;

    @Override
    public String execute() {        
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        movieDetailVO = getMovieService().getMovieDetailVO(movId);
    }
    
    @Override
    public MovieDetailVO getModel() {
        return movieDetailVO;
    }

    public void setMovId(long movId) {
        this.movId = movId;
    }

}
