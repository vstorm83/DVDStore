/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardVO {
    private Long exportCardId;
    private String userName;
    private String firstName;
    private String lastName;
    private String address;
    private String tel;
    private String email;
    private Date startDate;
    private Date endDate;
    private List<ExportCardItemVO> exportCardItemVOs;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getExportCardId() {
        return exportCardId;
    }

    public void setExportCardId(Long exportCardId) {
        this.exportCardId = exportCardId;
    }

    public List<ExportCardItemVO> getExportCardItemVOs() {
        return exportCardItemVOs;
    }

    public void setExportCardItemVOs(List<ExportCardItemVO> exportCardItemVOs) {
        this.exportCardItemVOs = exportCardItemVOs;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
