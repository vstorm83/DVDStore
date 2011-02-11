/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.CompactMovieVO;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.web.vo.MovieDetailVO;
import net.homeip.dvdstore.pojo.web.vo.MovieSearchResult;
import net.homeip.dvdstore.pojo.web.vo.NewestMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface MovieService {

    /**
     * get best movie list
     * @return list of MovieVO, never return null
     */
    List<CompactMovieVO> findBestMovie(int bestMovieNum);

    /**
     * get new movie list, period is retrieved in configuration
     * @return list of MovieVO, never return null
     */
    List<CompactMovieVO> findNewMovie(int newMovieNum);

    /**
     * get newest movie
     * @return NewestMovieVO, if there is no movie, return null
     */
    NewestMovieVO findNewestMovie();

    /**
     * get list of movie
     * @param movCatId
     * @return list of CompactMovieVO, never null, pageNum default 1
     */
    MovieSearchResult findMovieByCatId(long movCatId, int pgIdx, int pgSize);

    /**
     * get list of movie
     * @param movName
     * @param actName
     * @return list of CompactMovieVO, never null, pageNum default 1
     */
    MovieSearchResult findMovie(String movName, String actName, int pgIdx, int pgSize);

    /**
     * list of movie
     * @param ftl (FirstLetter of movie name)
     * @return never null, pageNum default 1
     */
    MovieSearchResult findMovieByFirstLetter(String firstLetter, int pgIdx, int pgSize);

    public MovieDetailVO getMovieDetailVO(Long movId);

    public List<GoodsMovieVO> findGoodsMovieVOs(String searchByMovName,
            MovieCatgoryVO searchByMovCat);

    public void addMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteMovie(List<Long> goodsMovieIdList, boolean ignoreReference)
            throws DBReferenceViolationException;

    public void updateMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException;
}
