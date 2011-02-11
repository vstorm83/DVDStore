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
import net.homeip.dvdstore.pojo.Supplier;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierProfitReportVO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierDAOImpl extends HibernateDaoSupport implements SupplierDAO {

    @Override
    public List<Supplier> findSupplier() {
        return getHibernateTemplate().findByNamedQuery("findSupplier");
    }

    @Override
    public Supplier getSupplierById(long supplierId) {
        return (Supplier)getHibernateTemplate().get(Supplier.class, supplierId);
    }

    public Supplier createSupplier() {
        return new Supplier();
    }

    public void deleteAll(List<Supplier> supplierList) {
        if (supplierList == null) {
            throw new IllegalArgumentException("can't delete, supplierList is null");
        }
        getHibernateTemplate().deleteAll(supplierList);
    }

    public Long getSupplierIdBySupplierName(String supplierName) {
        List<Long> supplierIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findSupplierIdBySupplierName", "supplierName", supplierName);
        if (supplierIdList.size() == 0) {
            return null;
        }
        return supplierIdList.get(0);
    }

    public void saveSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("can't save null supplier");
        }
        getHibernateTemplate().saveOrUpdate(supplier);
    }

    public List<SupplierProfitReportVO> findSupplierProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_supplierProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeSupplierProfitVOs(rs);
            }

            private List<SupplierProfitReportVO> makeSupplierProfitVOs(ResultSet rs) {
                List<SupplierProfitReportVO> supplierProfitReportVOs =
                        new ArrayList<SupplierProfitReportVO>();
                SupplierProfitReportVO supplierProfitReportVO;
                try {
                    while (rs.next()) {
                        supplierProfitReportVO = new SupplierProfitReportVO();
                        supplierProfitReportVO.setSupplierId(rs.getLong("SupplierId"));
                        supplierProfitReportVO.setSupplierName(rs.getString("SupplierName"));
                        supplierProfitReportVO.setSoNhap(rs.getInt("SupSN"));
                        supplierProfitReportVO.setTongNhap(rs.getDouble("SupTN"));
                        supplierProfitReportVOs.add(supplierProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return supplierProfitReportVOs;
            }
        });
    }
}
