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
import net.homeip.dvdstore.pojo.Director;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorProfitReportVO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorDAOImpl extends HibernateDaoSupport implements DirectorDAO {

    @Override
    public List<Director> findDirector() {
        return getHibernateTemplate().findByNamedQuery("findDirector");
    }

    @Override
    public Director getDirectorById(long directorId) {
        return (Director)getHibernateTemplate().get(Director.class, directorId);
    }

    public Director createDirector() {
        return new Director();
    }

    public void deleteAll(List<Director> directorList) {
        if (directorList == null) {
            throw new IllegalArgumentException("can't delete, directorList is null");
        }
        getHibernateTemplate().deleteAll(directorList);
    }

    public Long getDirectorIdByDirectorName(String directorName) {
        List<Long> directorIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findDirectorIdByDirectorName", "directorName", directorName);
        if (directorIdList.size() == 0) {
            return null;
        }
        return directorIdList.get(0);
    }

    public void saveDirector(Director director) {
        if (director == null) {
            throw new IllegalArgumentException("can't save null director");
        }
        getHibernateTemplate().saveOrUpdate(director);
    }

    public List<DirectorProfitReportVO> findDirectorProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_directorProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeDirectorProfitVOs(rs);
            }

            private List<DirectorProfitReportVO> makeDirectorProfitVOs(ResultSet rs) {
                List<DirectorProfitReportVO> directorProfitReportVOs =
                        new ArrayList<DirectorProfitReportVO>();
                DirectorProfitReportVO directorProfitReportVO;
                try {
                    while (rs.next()) {
                        directorProfitReportVO = new DirectorProfitReportVO();
                        directorProfitReportVO.setDirectorId(rs.getLong("DirectorId"));
                        directorProfitReportVO.setDirectorName(rs.getString("DirectorName"));
                        directorProfitReportVO.setSoNhap(rs.getInt("DirSN"));
                        directorProfitReportVO.setTongNhap(rs.getDouble("DirTN"));
                        directorProfitReportVO.setSoXuat(rs.getInt("DirSX"));
                        directorProfitReportVO.setTongXuat(rs.getDouble("DirTX"));
                        directorProfitReportVOs.add(directorProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return directorProfitReportVOs;
            }
        });
    }
    
}
