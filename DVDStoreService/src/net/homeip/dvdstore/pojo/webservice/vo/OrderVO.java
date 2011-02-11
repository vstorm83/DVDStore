/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderVO implements Serializable {
    private Long orderId;
    private String userName;
    private String firstName;
    private String lastName;
    private String address;
    private String tel;
    private String email;
    private Date startDate;
    private List<OrderItemVO> orderItemVOs;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemVO> getOrderItemVOs() {
        return orderItemVOs;
    }

    public void setOrderItemVOs(List<OrderItemVO> orderItemVOs) {
        this.orderItemVOs = orderItemVOs;
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
