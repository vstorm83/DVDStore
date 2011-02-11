/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.web.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartItemVO {
    private String movName;
    private double movPrice;
    private double saleOff;
    private int quantity;
    private boolean delete;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(double saleOff) {
        this.saleOff = saleOff;
    }
    
}
