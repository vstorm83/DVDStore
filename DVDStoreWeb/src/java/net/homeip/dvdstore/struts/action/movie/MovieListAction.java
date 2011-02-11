/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.movie;

import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.CompactMovieVO;
import net.homeip.dvdstore.struts.action.DSActionSupport;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.web.vo.MovieSearchResult;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieListAction extends DSActionSupport {    
    private List<CompactMovieVO> movieSearchResultList;
    private Long movCatId;
    private String movCatName="";
    private String movName;
    private String actName;
    private String firstLetter;
    private int pgIdx = 1;
    private int pgNum = 0;

    public String byCatId() throws Exception {        
        MovieSearchResult result = getMovieService().findMovieByCatId(movCatId,
                pgIdx, getConfigurationService().getPageSize());
        manipulateSearchResult(result);
        MovieCatgoryVO movCatVO = getMovieCatgoryService().getMovieCatgoryById(movCatId);
        if (movCatVO != null) {
            movCatName = movCatVO.getMovCatName();
        }
        return SUCCESS;
    }

    public String byOther() throws Exception {
        MovieSearchResult result = getMovieService().findMovie(
                movName, actName, pgIdx, getConfigurationService().getPageSize());
        manipulateSearchResult(result);
        return SUCCESS;
    }

    public String byFirstLetter() throws Exception {
        MovieSearchResult result = getMovieService().findMovieByFirstLetter(firstLetter,
                pgIdx, getConfigurationService().getPageSize());
        manipulateSearchResult(result);
        return SUCCESS;
    }

    public List<CompactMovieVO> getMovieSearchResultList() {
        return movieSearchResultList;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public void setMovCatId(Long movCatId) {
        this.movCatId = movCatId;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getActName() {
        return actName;
    }

    public Long getMovCatId() {
        return movCatId;
    }

    public String getMovName() {
        return movName;
    }

    public int getPgIdx() {
        return pgIdx;
    }

    public void setPgIdx(int pgIdx) {
        this.pgIdx = pgIdx;
    }

    public int getPgNum() {
        return pgNum;
    }

    public void setPgNum(int pgNum) {
        this.pgNum = pgNum;
    }

    public String getMovCatName() {
        return movCatName;
    }

    public void setMovCatName(String movCatName) {
        this.movCatName = movCatName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    private void manipulateSearchResult(MovieSearchResult result) {        
        movieSearchResultList = result.getMovieList();
        pgNum = result.getPageNum();
    }
}