/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.web.vo;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdminPrincipalVO implements UserDetails {
    private String userName;
    private String password;

    @Override
    public GrantedAuthority[] getAuthorities() {
        return new GrantedAuthority[] {new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return "PERMIT_Admin";
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
