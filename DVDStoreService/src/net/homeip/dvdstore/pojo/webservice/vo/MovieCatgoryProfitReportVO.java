/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryProfitReportVO extends ProfitReportVO {
    private Long movCatId;
    private String movCatName;

    public Long getMovCatId() {
        return movCatId;
    }

    public void setMovCatId(Long movCatId) {
        this.movCatId = movCatId;
    }

    public String getMovCatName() {
        return movCatName;
    }

    public void setMovCatName(String movCatName) {
        this.movCatName = movCatName;
    }

}
