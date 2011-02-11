/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action;

import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.CompactMovieVO;
import net.homeip.dvdstore.pojo.web.vo.NewestMovieVO;

/**
 *
 * @author VU VIET PHUONG
 */

public class HomeAction extends DSActionSupport {
    private NewestMovieVO newestMovie;
    private List<CompactMovieVO> newMovieList;
    
    @Override
    public String execute() throws Exception {
        prepareNewMovieList();
        prepareNewestMovie();
        return SUCCESS;
    }

    private void prepareNewMovieList() {
        if (getMovieService() == null) {
            throw new IllegalStateException("movieServie is null");
        }
        
        newMovieList = getMovieService().findNewMovie(getConfigurationService().getNewMovieNum());
    }

    private void prepareNewestMovie() {
        if (getMovieService() == null) {
            throw new IllegalStateException("movieServie is null");
        }

        newestMovie = getMovieService().findNewestMovie();
    }

    public List<CompactMovieVO> getNewMovieList() {
        return newMovieList;
    }

    public void setNewMovieList(List<CompactMovieVO> newMovieList) {
        this.newMovieList = newMovieList;
    }

    public NewestMovieVO getNewestMovie() {
        return newestMovie;
    }

    public void setNewestMovie(NewestMovieVO newestMovie) {
        this.newestMovie = newestMovie;
    }    
}