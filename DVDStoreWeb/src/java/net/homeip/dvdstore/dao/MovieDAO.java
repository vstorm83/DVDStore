/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.web.vo.MovieSearchResult;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.MovieProfitReportVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface MovieDAO {

    public Movie getMovieById(long movId);

    public List<Movie> findBestMovie(int bestMovieNum);

    public List<Movie> findNewMovie(int bestMovieNum);

    public Movie getNewestMovie();

    public MovieSearchResult findMovie(String movName, String actName, int pgIdx, int pgSize);

    public MovieSearchResult findMovieByCatId(long movCatId, int pgIdx, int pgSize);

    public MovieSearchResult findMovieByFirstLetter(String firstLetter, int pgIdx, int pgSize);

    public Iterable<Movie> findMovieByDirectorId(Long id);

    public void saveMovie(Movie mov);

    public List<Movie> findMovie(String searchByMovName, MovieCatgoryVO searchByMovCat);

    public int getMovieQuantity(Long movId);

    public Movie createMovie();

    public void deleteAll(List<Movie> movieList);

    public Long getMovieIdByMovieName(String movName);

    public List<Object[]> findMovieIdAndNameByCatId(Long movCatId);

    public List<MovieProfitReportVO> findMovieProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
    
}
