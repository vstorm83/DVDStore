/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.web.vo;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieSearchResult {
    private List movieList;
    private int pageNum;

    public List getMovieList() {
        return movieList;
    }

    public void setMovieList(List movieList) {
        this.movieList = movieList;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
}
