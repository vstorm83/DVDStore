/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.MovieCatgoryProfitReportVO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryDAOImpl extends HibernateDaoSupport implements MovieCatgoryDAO {

    @Override
    public List<MovieCatgory> findMovieCatgory() {
        return getHibernateTemplate().findByNamedQuery("findMovieCatgory");
    }

    @Override
    public MovieCatgory getMovieCatgoryById(long movCatId) {
        return (MovieCatgory)getHibernateTemplate().get(MovieCatgory.class, movCatId);
    }

    public MovieCatgory createMovieCatgory() {
        return new MovieCatgory();
    }

    public void deleteAll(List<MovieCatgory> movCatList) {
        if (movCatList == null) {
            throw new IllegalArgumentException("can't delete, movCatList is null");
        }
        getHibernateTemplate().deleteAll(movCatList);
    }

    public Long getMovCatIdByMovCatName(String movCatName) {
        List<Long> movCatIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findMovCatIdByMovCatName", "movCatName", movCatName);
        if (movCatIdList.size() == 0) {
            return null;
        }
        return movCatIdList.get(0);
    }

    public void saveMovieCatgory(MovieCatgory movCat) {
        if (movCat == null) {
            throw new IllegalArgumentException("can't save null movie catgory");
        }
        getHibernateTemplate().saveOrUpdate(movCat);
    }

    public List<MovieCatgoryProfitReportVO> findMovieCatgoryProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_movieCatgoryProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeMovieCatgoryProfitVOs(rs);
            }

            private List<MovieCatgoryProfitReportVO> makeMovieCatgoryProfitVOs(ResultSet rs) {
                List<MovieCatgoryProfitReportVO> movieCatgoryProfitReportVOs =
                        new ArrayList<MovieCatgoryProfitReportVO>();
                MovieCatgoryProfitReportVO movieCatgoryProfitReportVO;
                try {
                    while (rs.next()) {
                        movieCatgoryProfitReportVO = new MovieCatgoryProfitReportVO();
                        movieCatgoryProfitReportVO.setMovCatId(rs.getLong("MovieCatgoryId"));
                        movieCatgoryProfitReportVO.setMovCatName(rs.getString("MovieCatgoryName"));
                        movieCatgoryProfitReportVO.setSoNhap(rs.getInt("CatSN"));
                        movieCatgoryProfitReportVO.setTongNhap(rs.getDouble("CatTN"));
                        movieCatgoryProfitReportVO.setSoXuat(rs.getInt("CatSX"));
                        movieCatgoryProfitReportVO.setTongXuat(rs.getDouble("CatTX"));
                        movieCatgoryProfitReportVOs.add(movieCatgoryProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return movieCatgoryProfitReportVOs;
            }
        });
    }
}
