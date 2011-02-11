/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

import net.homeip.dvdstore.dao.AdminDAO;
import net.homeip.dvdstore.pojo.Admin;
import net.homeip.dvdstore.pojo.web.vo.AdminPrincipalVO;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 *
 * @author VU VIET PHUONG
 */
public class AcegiAdminService implements UserDetailsService {

    private AdminDAO adminDAO;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        Admin admin = adminDAO.getAdminByAdminName(username);
        if (admin == null) {
            throw new UsernameNotFoundException("admin not found");
        }
        AdminPrincipalVO principal = new AdminPrincipalVO();
        principal.setUserName(admin.getAdminName());
        principal.setPassword(admin.getAdminPass());
        return principal;
    }

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }
}
