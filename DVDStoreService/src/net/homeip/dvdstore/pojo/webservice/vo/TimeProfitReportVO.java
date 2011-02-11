/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class TimeProfitReportVO extends ProfitReportVO {
    private int monthProfit;
    private int yearProfit;

    public int getMonthProfit() {
        return monthProfit;
    }

    public void setMonthProfit(int monthProfit) {
        this.monthProfit = monthProfit;
    }

    public int getYearProfit() {
        return yearProfit;
    }

    public void setYearProfit(int yearProfit) {
        this.yearProfit = yearProfit;
    }
    
}
