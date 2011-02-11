/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import net.homeip.dvdstore.pojo.web.vo.ShoppingCartVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface PlaceOrderService {
    int getShoppingCartItemCount();

    public ShoppingCartVO getShoppingCart();

    public String sendOrder();

    public void addItem(long movId);

    public void updateShoppingCart(ShoppingCartVO shoppingCartVO);
    
}
