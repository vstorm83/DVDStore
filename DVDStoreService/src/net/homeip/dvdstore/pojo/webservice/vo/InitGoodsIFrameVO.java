/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class InitGoodsIFrameVO {
    private List<GoodsMovieVO> goodsMovieVOs;
    private List<MovieCatgoryVO> movieCatgoryVOs;
    private List<DirectorVO> directorVOs;    

    public List<DirectorVO> getDirectorVOs() {
        return directorVOs;
    }

    public void setDirectorVOs(List<DirectorVO> directorVOs) {
        this.directorVOs = directorVOs;
    }

    public List<GoodsMovieVO> getGoodsMovieVOs() {
        return goodsMovieVOs;
    }

    public void setGoodsMovieVOs(List<GoodsMovieVO> goodsMovieVOs) {
        this.goodsMovieVOs = goodsMovieVOs;
    }

    public List<MovieCatgoryVO> getMovieCatgoryVOs() {
        return movieCatgoryVOs;
    }

    public void setMovieCatgoryVOs(List<MovieCatgoryVO> movieCatgoryVOs) {
        this.movieCatgoryVOs = movieCatgoryVOs;
    }
    
}
