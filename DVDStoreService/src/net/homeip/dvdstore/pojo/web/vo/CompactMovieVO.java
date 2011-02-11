/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.web.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class CompactMovieVO {
    private Long movId;
    private String movName;
    private String movPicUrl;

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

    public String getMovPicUrl() {
        return movPicUrl;
    }

    public void setMovPicUrl(String movPicUrl) {
        this.movPicUrl = movPicUrl;
    }

    public CompactMovieVO(Long movId, String movName, String movPicUrl) {
        this.movId = movId;
        this.movName = movName;
        this.movPicUrl = movPicUrl;
    }

    public CompactMovieVO() {
    }
    
}
