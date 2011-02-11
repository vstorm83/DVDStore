/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo.web.vo;


import net.homeip.dvdstore.pojo.ShoppingCart;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserPrincipalVO implements UserDetails {
    private Long userId;
    private String userName;
    private String password;
    private DeliveryInfo deliveryInfo;
    private ShoppingCart shoppingCart;

    @Override
    public GrantedAuthority[] getAuthorities() {
        return new GrantedAuthority[] {new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return "PERMIT_Customer";
            }

            @Override
            public int compareTo(Object o) {
                return 0;
            }
        }};
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getUserId() {
        return userId;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public DeliveryInfo getDeliveryInfo() {
        try {
            return (DeliveryInfo) deliveryInfo.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }       
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
