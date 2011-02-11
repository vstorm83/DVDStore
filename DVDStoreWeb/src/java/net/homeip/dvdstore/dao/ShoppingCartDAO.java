/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.ShoppingCart;
import net.homeip.dvdstore.pojo.ShoppingCartItem;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ShoppingCartDAO {

    public ShoppingCartItem makeShoppingCartItem(Movie movie);

    public ShoppingCart getShoppingCart();

    public void saveShoppingCart(ShoppingCart shoppingCart);

    public int getShoppingCartItemCount();

    public void clearShoppingCart();

    public double getShoppingCartTotalMoney();
}
