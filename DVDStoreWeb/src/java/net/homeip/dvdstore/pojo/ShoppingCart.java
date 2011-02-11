/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.web.vo.ShoppingCartItemVO;
import net.homeip.dvdstore.pojo.web.vo.ShoppingCartVO;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.util.ValidatorUtil;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCart {

    private DeliveryInfo deliveryInfo;
    private Map<Long, ShoppingCartItem> shoppingCartItem = new HashMap<Long, ShoppingCartItem>();

    public void addItem(ShoppingCartItem item) {
        if (item == null || item.getMovie() == null) {
            throw new IllegalArgumentException("item is null or item.Movie is null");
        }
        ShoppingCartItem oldItem = shoppingCartItem.get(item.getMovie().getMovId());
        if (oldItem != null) {
            oldItem.setQuantity(oldItem.getQuantity() + 1);
        } else {
            shoppingCartItem.put(item.getMovie().getMovId(), item);
        }
    }

    public void updateShoppingCart(ShoppingCartVO shoppingCartVO)
            throws InvalidInputException {
        if (shoppingCartVO == null) {
            throw new IllegalArgumentException("shoppingCartVO is null, can't update");
        }
        DeliveryInfo info = shoppingCartVO.getDeliveryInfo();
        validateDeliveryInfo(info);
        deliveryInfo = info;
        updateItemList(shoppingCartVO.getItems());
    }

    private void updateItemList(List<ShoppingCartItemVO> items) {
        if (items == null) {
            throw new IllegalArgumentException("items is null, can't update item list");
        }
        ShoppingCartItem item;
        Iterator<ShoppingCartItem> itemIterator = shoppingCartItem.values().iterator();
        for (ShoppingCartItemVO voItem : items) {
            item = itemIterator.next();
            if (voItem.isDelete() || voItem.getQuantity() <= 0) {
                itemIterator.remove();
            } else {
                item.setQuantity(voItem.getQuantity());
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Validate">
    private void validateDeliveryInfo(DeliveryInfo deliveryInfo)
            throws InvalidInputException {
        if (deliveryInfo == null) {
            throw new IllegalArgumentException("deliveryInfo is null, can't validate");
        }
        String firstName = deliveryInfo.getFirstName();
        if (ValidatorUtil.isEmpty(firstName)
                || ValidatorUtil.isInvalidLength(firstName, 1, 20)) {
            throw new InvalidInputException("deliveryInfo.firstName");
        }
        String lastName = deliveryInfo.getLastName();
        if (ValidatorUtil.isEmpty(lastName)
                || ValidatorUtil.isInvalidLength(lastName, 1, 20)) {
            throw new InvalidInputException("deliveryInfo.lastName");
        }
        String email = deliveryInfo.getEmail();
        if (ValidatorUtil.isEmpty(email)
                || ValidatorUtil.isInvalidLength(email, 1, 50)
                || ValidatorUtil.isInvalidEmail(email.trim())) {
            throw new InvalidInputException("deliveryInfo.email");
        }
        String address = deliveryInfo.getAddress();
        if (ValidatorUtil.isEmpty(address)
                || ValidatorUtil.isInvalidLength(address, 1, 100)) {
            throw new InvalidInputException("deliveryInfo.address");
        }
        String tel = deliveryInfo.getTel();
        if (ValidatorUtil.isEmpty(tel)
                || ValidatorUtil.isInvalidLength(tel, 1, 50)) {
            throw new InvalidInputException("deliveryInfo.tel");
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter_Setter">
    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public DeliveryInfo getDeliveryInfo() {
        try {
            return (DeliveryInfo) deliveryInfo.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }

    public Map<Long, ShoppingCartItem> getShoppingCartItem() {
        return shoppingCartItem;
    }// </editor-fold>
}
