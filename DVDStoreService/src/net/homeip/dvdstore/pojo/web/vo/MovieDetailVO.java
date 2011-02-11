/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo.web.vo;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieDetailVO extends NewestMovieVO {
    private String movDirector;
    private List<String> movActor;
    private String movCat;
    private double movPrice;
    private double movSaleOff;

    public List<String> getMovActor() {
        return movActor;
    }

    public void setMovActor(List<String> movActor) {
        this.movActor = movActor;
    }

    public String getMovCat() {
        return movCat;
    }

    public void setMovCat(String movCat) {
        this.movCat = movCat;
    }

    public String getMovDirector() {
        return movDirector;
    }

    public void setMovDirector(String movDirector) {
        this.movDirector = movDirector;
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
    
}
