/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class ProfitReportVO {
    private int soNhap;
    private double tongNhap;
    private int soXuat;
    private double tongXuat;

    public int getSoNhap() {
        return soNhap;
    }

    public void setSoNhap(int soNhap) {
        this.soNhap = soNhap;
    }

    public int getSoXuat() {
        return soXuat;
    }

    public void setSoXuat(int soXuat) {
        this.soXuat = soXuat;
    }

    public double getTongNhap() {
        return tongNhap;
    }

    public void setTongNhap(double tongNhap) {
        this.tongNhap = tongNhap;
    }

    public double getTongXuat() {
        return tongXuat;
    }

    public void setTongXuat(double tongXuat) {
        this.tongXuat = tongXuat;
    }

}
