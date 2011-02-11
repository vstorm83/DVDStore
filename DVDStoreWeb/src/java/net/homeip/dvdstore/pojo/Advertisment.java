/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import java.util.Date;

/**
 *
 * @author VU VIET PHUONG
 */
public class Advertisment {
    private Long adId;
    private String adPicName;
    private String adLink;
    private Date endDate;
    private int version;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    public String getAdPicName() {
        return adPicName;
    }

    public void setAdPicName(String adPicName) {
        this.adPicName = adPicName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
}
