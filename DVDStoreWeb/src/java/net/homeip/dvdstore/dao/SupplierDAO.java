/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Supplier;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierProfitReportVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface SupplierDAO {

    public List<Supplier> findSupplier();

    public Supplier getSupplierById(long supplierId);

    public Supplier createSupplier();

    public void saveSupplier(Supplier supplier);

    public void deleteAll(List<Supplier> supplierList);

    public Long getSupplierIdBySupplierName(String supplierName);

    public List<SupplierProfitReportVO> findSupplierProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
}
