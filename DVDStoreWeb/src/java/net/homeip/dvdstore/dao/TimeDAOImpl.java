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
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.TimeProfitReportVO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class TimeDAOImpl extends HibernateDaoSupport implements TimeDAO{

    public List<TimeProfitReportVO> findTimeProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_timeProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeTimeProfitVOs(rs);
            }

            private List<TimeProfitReportVO> makeTimeProfitVOs(ResultSet rs) {
                List<TimeProfitReportVO> timeProfitReportVOs =
                        new ArrayList<TimeProfitReportVO>();
                TimeProfitReportVO timeProfitReportVO;
                try {
                    while (rs.next()) {
                        timeProfitReportVO = new TimeProfitReportVO();
                        timeProfitReportVO.setMonthProfit(rs.getInt("MonthProfit"));
                        timeProfitReportVO.setYearProfit(rs.getInt("YearProfit"));
                        timeProfitReportVO.setSoNhap(rs.getInt("SN"));
                        timeProfitReportVO.setTongNhap(rs.getDouble("TN"));
                        timeProfitReportVO.setSoXuat(rs.getInt("SX"));
                        timeProfitReportVO.setTongXuat(rs.getDouble("TX"));
                        timeProfitReportVOs.add(timeProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return timeProfitReportVOs;
            }
        });
    }

}
