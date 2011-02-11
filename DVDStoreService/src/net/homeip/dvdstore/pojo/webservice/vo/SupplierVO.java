/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierVO {
    private Long supplierId;
    private String supplierName;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SupplierVO other = (SupplierVO) obj;
        if (this.supplierId != other.supplierId && (this.supplierId == null || !this.supplierId.equals(other.supplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.supplierId != null ? this.supplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return supplierName;
    }
    
}
