/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao;

import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.ShoppingCart;
import net.homeip.dvdstore.pojo.ShoppingCartItem;
import net.homeip.dvdstore.pojo.web.vo.UserPrincipalVO;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartDAOImpl implements ShoppingCartDAO {

    @Override
    public ShoppingCart getShoppingCart() {
        UserPrincipalVO principal = getUserPrincipalVO();
        if (principal == null) {
            throw new IllegalStateException("user is not authenticated, can't get shopping cart");
        }
        ShoppingCart shoppingCart = principal.getShoppingCart();
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setDeliveryInfo(principal.getDeliveryInfo());
            principal.setShoppingCart(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public int getShoppingCartItemCount() {
        if (getUserPrincipalVO() == null) {
            return 0;
        }
        ShoppingCart shoppingCart = getShoppingCart();
        int total = 0;
        for (ShoppingCartItem shoppingCartItem : shoppingCart.getShoppingCartItem().values()) {
            total += shoppingCartItem.getQuantity();
        }
        return total;
    }

    @Override
    public void clearShoppingCart() {
        getShoppingCart().getShoppingCartItem().clear();
    }

    @Override
    public double getShoppingCartTotalMoney() {
        ShoppingCart shoppingCart = getShoppingCart();
        double total = 0;
        for (ShoppingCartItem item : shoppingCart.getShoppingCartItem().values()) {
            total += (item.getMovie().getMovPrice()
                    - item.getMovie().getMovSaleOff() / 100) * item.getQuantity();
        }
        return total;
    }

    @Override
    public ShoppingCartItem makeShoppingCartItem(Movie movie) {
        if (movie == null) {
            throw new IllegalArgumentException("movie is null, can't make shoppingCartItem");
        }

        Movie mv = makeShoppingCartMovie(movie);
        ShoppingCartItem item = new ShoppingCartItem();
        item.setMovie(mv);
        item.setQuantity(1);
        return item;
    }

    @Override
    public void saveShoppingCart(ShoppingCart shoppingCart) {
    }

    private UserPrincipalVO getUserPrincipalVO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (UserPrincipalVO) authentication.getPrincipal();
    }

    private Movie makeShoppingCartMovie(Movie movie) {
        Movie mv = new Movie();
        mv.setMovId(movie.getMovId());
        mv.setMovName(movie.getMovName());
        mv.setMovCat(movie.getMovCat());
        mv.setMovPrice(movie.getMovPrice());
        mv.setMovSaleOff(movie.getMovSaleOff());
        return mv;
    }
}
