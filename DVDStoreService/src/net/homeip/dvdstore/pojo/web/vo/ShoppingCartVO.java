/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.web.vo;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartVO {    
    private DeliveryInfo deliveryInfo;
    private List<ShoppingCartItemVO> items;

    public List<ShoppingCartItemVO> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItemVO> items) {
        this.items = items;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

}
