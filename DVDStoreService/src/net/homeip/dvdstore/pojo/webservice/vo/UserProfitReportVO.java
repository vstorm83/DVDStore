/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserProfitReportVO {
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private int soXuat;
    private double tongXuat;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSoXuat() {
        return soXuat;
    }

    public void setSoXuat(int soXuat) {
        this.soXuat = soXuat;
    }

    public double getTongXuat() {
        return tongXuat;
    }

    public void setTongXuat(double tongXuat) {
        this.tongXuat = tongXuat;
    }
    
}
