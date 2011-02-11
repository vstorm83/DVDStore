/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.LoginFailException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.webservice.AdminLoginService;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdminLoginServiceDelegateImpl implements AdminLoginServiceDelegate {
    private AdminLoginService adminLoginService;

    public void login(String userName, String password)
            throws LoginFailException {
            try {
                adminLoginService.login(userName, password);
            } catch (javax.xml.ws.soap.SOAPFaultException ex) {
                ex.printStackTrace();
                throw new ServerException("Lỗi Server", ex);
            } catch (WebServiceException ex) {
                ex.printStackTrace();
                throw new ServerConnectionException("Không kết nối được với Server", ex);
            } catch (net.homeip.dvdstore.webservice.exception.LoginFailException ex) {
                ex.printStackTrace();
                throw new LoginFailException("Không đăng nhập được", ex);
            }
    }

    public void setAdminLoginService(AdminLoginService adminLoginService) {
        this.adminLoginService = adminLoginService;
    }

}
