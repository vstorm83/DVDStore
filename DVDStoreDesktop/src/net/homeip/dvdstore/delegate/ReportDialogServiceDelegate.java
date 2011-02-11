/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ReportDialogServiceDelegate {
    public byte[] reportMovieCatgoryProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportMovieProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportDirectorProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportActorProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportSupplierProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportUserProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportTimeProfit(DateSearchVO startDate, DateSearchVO endDate);

    public byte[] reportTimeProfitByChart(DateSearchVO startDate, DateSearchVO endDate);
}
