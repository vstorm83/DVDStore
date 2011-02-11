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
import net.homeip.dvdstore.pojo.Actor;
import net.homeip.dvdstore.pojo.webservice.vo.ActorProfitReportVO;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class ActorDAOImpl extends HibernateDaoSupport implements ActorDAO {

    @Override
    public List<Actor> findActor() {
        return getHibernateTemplate().findByNamedQuery("findActor");
    }

    @Override
    public Actor getActorById(long actorId) {
        return (Actor)getHibernateTemplate().get(Actor.class, actorId);
    }

    public Actor createActor() {
        return new Actor();
    }

    public void deleteAll(List<Actor> actorList) {
        if (actorList == null) {
            throw new IllegalArgumentException("can't delete, actorList is null");
        }
        getHibernateTemplate().deleteAll(actorList);
    }

    public Long getActorIdByActorName(String actorName) {
        List<Long> actorIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findActorIdByActorName", "actorName", actorName);
        if (actorIdList.size() == 0) {
            return null;
        }
        return actorIdList.get(0);
    }

    public void saveActor(Actor actor) {
        if (actor == null) {
            throw new IllegalArgumentException("can't save null actor");
        }
        getHibernateTemplate().saveOrUpdate(actor);
    }

    public List<ActorProfitReportVO> findActorProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_actorProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeActorProfitVOs(rs);
            }

            private List<ActorProfitReportVO> makeActorProfitVOs(ResultSet rs) {
                List<ActorProfitReportVO> actorProfitReportVOs =
                        new ArrayList<ActorProfitReportVO>();
                ActorProfitReportVO actorProfitReportVO;
                try {
                    while (rs.next()) {
                        actorProfitReportVO = new ActorProfitReportVO();
                        actorProfitReportVO.setActorId(rs.getLong("ActorId"));
                        actorProfitReportVO.setActorName(rs.getString("ActorName"));
                        actorProfitReportVO.setSoNhap(rs.getInt("ActSN"));
                        actorProfitReportVO.setTongNhap(rs.getDouble("ActTN"));
                        actorProfitReportVO.setSoXuat(rs.getInt("ActSX"));
                        actorProfitReportVO.setTongXuat(rs.getDouble("ActTX"));
                        actorProfitReportVOs.add(actorProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return actorProfitReportVOs;
            }
        });
    }
}
