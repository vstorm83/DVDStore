/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.io.Serializable;
import java.util.Date;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserVO implements Serializable {
    private Long userId;
    private String userName;
    private DeliveryInfo deliveryInfo;
    private Date dateCreated;

    public UserVO() {
        deliveryInfo = new DeliveryInfo();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }
    
}
