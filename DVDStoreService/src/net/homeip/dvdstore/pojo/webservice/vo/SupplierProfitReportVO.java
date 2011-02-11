/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierProfitReportVO {
    private Long supplierId;
    private String supplierName;
    private int soNhap;
    private double tongNhap;

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

    public int getSoNhap() {
        return soNhap;
    }

    public void setSoNhap(int soNhap) {
        this.soNhap = soNhap;
    }

    public double getTongNhap() {
        return tongNhap;
    }

    public void setTongNhap(double tongNhap) {
        this.tongNhap = tongNhap;
    }
    
}
