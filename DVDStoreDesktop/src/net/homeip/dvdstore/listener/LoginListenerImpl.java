/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener;

import net.homeip.dvdstore.listener.event.LoginEvent;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.AdminLoginServiceDelegate;
import net.homeip.dvdstore.delegate.exception.LoginFailException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.model.LoginModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class LoginListenerImpl implements LoginListener {

    private AdminLoginServiceDelegate adminLoginServiceDelegate;

    public void loginPerformed(LoginEvent loginEvent) {
        LoginModel loginModel = loginEvent.getLoginModel();
        if (loginModel == null) {
            throw new IllegalStateException("loginModel is null");
        }

        String userName = loginEvent.getUserName() == null ? "" : loginEvent.getUserName().trim();
        String password = loginEvent.getPassword() == null ? "" : loginEvent.getPassword();

        if (validate(userName, password)) {
            loginModel.setStatus(LoginModel.LOGIN_FAIL);
            return;
        }

        callService(loginModel, userName, password);
    }

    private boolean validate(String userName, String password) {
        if (userName.equals("") || password.equals("")) {
            return true;
        }
        return false;
    }

    private void callService(final LoginModel loginModel,
            final String userName, final String password) {
        if (adminLoginServiceDelegate == null) {
            throw new IllegalStateException("serviceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    adminLoginServiceDelegate.login(userName, password);
                } catch (ServerConnectionException ex) {
                    ex.printStackTrace();
                    return LoginModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    ex.printStackTrace();
                    return LoginModel.SERVER_ERROR;
                } catch (LoginFailException ex) {
                    ex.printStackTrace();
                    return LoginModel.LOGIN_FAIL;
                }
                return LoginModel.OK;
            }

            @Override
            protected void done() {
                try {
                    loginModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        };
        swingWorker.execute();
    }

    public void setAdminLoginServiceDelegate(AdminLoginServiceDelegate adminLoginServiceDelegate) {
        this.adminLoginServiceDelegate = adminLoginServiceDelegate;
    }
}
