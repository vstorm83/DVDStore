/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo.web.vo;

import java.io.Serializable;

/**
 *
 * @author VU VIET PHUONG
 */
public class DeliveryInfo implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String tel;

    public DeliveryInfo() {
    }

    public DeliveryInfo(String firstName, String lastName, String address, String tel, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.tel = tel;
    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DeliveryInfo info = new DeliveryInfo();
        info.setFirstName(firstName);
        info.setLastName(lastName);
        info.setAddress(address);
        info.setTel(tel);
        info.setEmail(email);
        return info;
    }
}
