/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import javax.jws.WebService;
import net.homeip.dvdstore.dao.AdminDAO;
import net.homeip.dvdstore.pojo.Admin;
import net.homeip.dvdstore.pojo.web.vo.AdminPrincipalVO;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminPasswordException;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ChangeCredentialsService")
public class ChangeCredentialsServiceImpl implements ChangeCredentialsService {

    private AdminDAO adminDAO;

    @Override
    public void updateAdminCredentials(String password,
            String newUserName, String newPassword) throws InvalidAdminPasswordException, InvalidAdminInputException {
        Admin admin = (Admin) adminDAO.getAdminByAdminName(getLoggedInAdminName());
        if (admin == null) {
            throw new IllegalStateException("admin not found in database");
        }
        admin.updateCredentials(password, newUserName, newPassword);
        adminDAO.save(admin);
        renewAuthentication(newUserName);
    }

    private String getLoggedInAdminName() {
        Authentication authentication = getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    private void renewAuthentication(String newUserName) {
        Authentication authentication = getAuthentication();
        ((AdminPrincipalVO) authentication.getPrincipal()).setUserName(newUserName);
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("not yet authenticated");
        }
        return authentication;
    }

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }
}
