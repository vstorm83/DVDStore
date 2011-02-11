/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.web.vo.MovieSearchResult;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.MovieProfitReportVO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieDAOImpl extends HibernateDaoSupport implements MovieDAO {
    
    @Override
    public List<Movie> findNewMovie(int newMovieNum) {
        if (newMovieNum < 0) {
            throw new IllegalArgumentException("newMovieNum is negative");
        }
        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findNewMovie", "resultNum", newMovieNum);
    }

    @Override
    public Movie getNewestMovie() {
        List<Movie> movieList = getHibernateTemplate().findByNamedQuery("findNewestMovie");
        if (movieList.size() == 0) {
            return null;
        } else return movieList.get(0);
    }

    @Override
    public Movie getMovieById(long movId) {
        return (Movie) getHibernateTemplate().get(Movie.class, new Long(movId));
    }

    @Override
    public List<Movie> findBestMovie(int bestMovieNum) {
        if (bestMovieNum < 0) {
            throw new IllegalArgumentException("bestMovieNum is negative");
        }
        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findBestMovie", "resultNum", bestMovieNum);
    }

    @Override
    public MovieSearchResult findMovie(final String movName, final String actName,
            final int pgIdx, final int pgSize) {
        return (MovieSearchResult) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("{call dbo.usp_findMovie(?,?,?,?,?)}");
                callStm.setString("movName", movName);
                callStm.setString("actName", actName);
                callStm.setInt("pgIdx", pgIdx);
                callStm.setInt("pgSize", pgSize);
                callStm.setInt("pgNum", 0);
                callStm.registerOutParameter("pgNum", Types.INTEGER);

                ResultSet resultSet = callStm.executeQuery();
                MovieSearchResult result = MovieDAOImpl.this.makeMovieSearchResult(resultSet);
                result.setPageNum(callStm.getInt("pgNum"));
                return result;
            }
        });
    }

    @Override
    public MovieSearchResult findMovieByCatId(final long movCatId,
            final int pgIdx, final int pgSize) {
        return (MovieSearchResult) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall(
                        "{call dbo.usp_findMovieByCatId(?,?,?,?)}");
                callStm.setLong("movCatId", movCatId);
                callStm.setInt("pgIdx", pgIdx);
                callStm.setInt("pgSize", pgSize);
                callStm.setInt("pgNum", 0);
                callStm.registerOutParameter("pgNum", Types.INTEGER);
                ResultSet resultSet = callStm.executeQuery();

                MovieSearchResult result = MovieDAOImpl.this.makeMovieSearchResult(resultSet);
                result.setPageNum(callStm.getInt("pgNum"));
                return result;
            }
        });
    }

    public List<Movie> findMovieByDirectorId(Long directorId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findMovieByDirectorId", "directorId", directorId);
    }

    public void saveMovie(Movie mov) {
        getHibernateTemplate().saveOrUpdate(mov);
    }

    @Override
    public MovieSearchResult findMovieByFirstLetter(final String firstLetter,
            final int pgIdx, final int pgSize) {
        return (MovieSearchResult) getHibernateTemplate().execute(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall(
                        "{call dbo.usp_findMovieByFirstLetter(?,?,?,?)}");
                callStm.setString("firstLetter", firstLetter);
                callStm.setInt("pgIdx", pgIdx);
                callStm.setInt("pgSize", pgSize);
                callStm.setInt("pgNum", 0);
                callStm.registerOutParameter("pgNum", Types.INTEGER);
                ResultSet resultSet = callStm.executeQuery();

                MovieSearchResult result = MovieDAOImpl.this.makeMovieSearchResult(resultSet);
                result.setPageNum(callStm.getInt("pgNum"));
                return result;
            }
        });
    }

    public List<MovieProfitReportVO> findMovieProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_movieProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeMovieProfitVOs(rs);
            }

            private List<MovieProfitReportVO> makeMovieProfitVOs(ResultSet rs) {
                List<MovieProfitReportVO> movieProfitReportVOs =
                        new ArrayList<MovieProfitReportVO>();
                MovieProfitReportVO movieProfitReportVO;
                try {
                    while (rs.next()) {
                        movieProfitReportVO = new MovieProfitReportVO();
                        movieProfitReportVO.setMovId(rs.getLong("MovieId"));
                        movieProfitReportVO.setMovName(rs.getString("MovieName"));
                        movieProfitReportVO.setSoNhap(rs.getInt("SN"));
                        movieProfitReportVO.setTongNhap(rs.getDouble("TN"));
                        movieProfitReportVO.setSoXuat(rs.getInt("SX"));
                        movieProfitReportVO.setTongXuat(rs.getDouble("TX"));
                        movieProfitReportVOs.add(movieProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return movieProfitReportVOs;
            }
        });
    }

    public List<Movie> findMovie(String searchByMovName, MovieCatgoryVO searchByMovCat) {
        if (searchByMovName == null) {
            searchByMovName = "";
        }
        if (searchByMovCat == null) {
            return new ArrayList<Movie>();
        }
        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findMovieByNameAndMovieCatgory", new String[] {"movName", "movCatId"},
                new Object[] {"%" + searchByMovName + "%", searchByMovCat.getMovCatId()});
    }

    public int getMovieQuantity(Long movId) {
        List movies = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "getMovieQuantity", "movId", movId);
        return (Integer)movies.get(0);
    }

    public Movie createMovie() {
        return new Movie();
    }

    public void deleteAll(List<Movie> movieList) {
        if (movieList == null) {
            throw new IllegalArgumentException("can't delete null movie list");
        }
        getHibernateTemplate().deleteAll(movieList);
    }

    public Long getMovieIdByMovieName(String movName) {
        List<Long> movIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findMovieIdByMovieName", "movName", movName);
        if (movIdList.size() == 0) {
            return null;
        }
        return movIdList.get(0);
    }

    public List<Object[]> findMovieIdAndNameByCatId(Long movCatId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findMovieIdAndNameByCatId", "movCatId", movCatId);
    }

    private MovieSearchResult makeMovieSearchResult(ResultSet resultSet) throws SQLException {
        List<Movie> movieList = new ArrayList<Movie>();
        Movie movie;
        while (resultSet.next()) {
            movie = new Movie();
            movie.setMovId(resultSet.getLong("MovieId"));
            movie.setMovName(resultSet.getString("MovieName"));
            movie.setMovPicName(resultSet.getString("MoviePicName"));
            movieList.add(movie);
        }

        MovieSearchResult result = new MovieSearchResult();
        result.setMovieList(movieList);
        return result;
    }
}
