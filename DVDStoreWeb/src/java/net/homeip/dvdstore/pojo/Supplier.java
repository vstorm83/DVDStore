/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.dao.SupplierDAO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class Supplier {
    private Long supplierId;
    private String supplierName;
    private int version;

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void populate(SupplierVO supplierVO, SupplierDAO supplierDAO)
            throws InvalidAdminInputException, DuplicateException {
        if (supplierVO == null || supplierDAO == null) {
            throw new IllegalArgumentException("supplierDAO or supplierVO is null");
        }
        if (ValidatorUtil.isEmpty(supplierVO.getSupplierName()) ||
                ValidatorUtil.isInvalidLength(supplierVO.getSupplierName(), 1, 50)) {
            throw new InvalidAdminInputException("supplierName");
        }
        Long dbSupplierId = supplierDAO.getSupplierIdBySupplierName(
                supplierVO.getSupplierName().trim());
        if (dbSupplierId != null) {
            if (supplierId == null ||
                    (dbSupplierId.longValue() != supplierId.longValue())) {
                    throw new DuplicateException("supplierName");
            }
        }
        this.supplierName = supplierVO.getSupplierName().trim();
    }
}
