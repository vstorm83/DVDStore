/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardItemVO {
    private Long movId;
    private String movName;
    private String movCatName;
    private double movPrice;
    private double movSaleOff;
    private int quantity;

    public String getMovCatName() {
        return movCatName;
    }

    public void setMovCatName(String movCatName) {
        this.movCatName = movCatName;
    }

    public Long getMovId() {
        return movId;
    }

    public void setMovId(Long movId) {
        this.movId = movId;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public double getMovPrice() {
        return movPrice;
    }

    public void setMovPrice(double movPrice) {
        this.movPrice = movPrice;
    }

    public double getMovSaleOff() {
        return movSaleOff;
    }

    public void setMovSaleOff(double movSaleOff) {
        this.movSaleOff = movSaleOff;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
