/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationInfoVO {

    private String comName;
    private String comAddress;
    private String comTel;
    private String comEmail;
    private int pageSize;
    private int bestMovNum;
    private int newMovNum;
    private String timeZoneId;

    public int getBestMovNum() {
        return bestMovNum;
    }

    public void setBestMovNum(int bestMovNum) {
        this.bestMovNum = bestMovNum;
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }

    public String getComEmail() {
        return comEmail;
    }

    public void setComEmail(String comEmail) {
        this.comEmail = comEmail;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel;
    }

    public int getNewMovNum() {
        return newMovNum;
    }

    public void setNewMovNum(int newMovNum) {
        this.newMovNum = newMovNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
