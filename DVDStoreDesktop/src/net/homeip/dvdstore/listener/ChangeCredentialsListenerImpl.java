/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.HashSet;
import java.util.Set;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.ChangeCredentialsServiceDelegate;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.InvalidPasswordException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.listener.event.ChangeCredentialsEvent;
import net.homeip.dvdstore.model.ChangeCredentialsModel;
import net.homeip.dvdstore.util.ValidatorUtil;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ChangeCredentialsListenerImpl implements ChangeCredentialsListener {

    private ChangeCredentialsServiceDelegate changeCredentialsServiceDelegate;

    public void changeCredentialsPerformed(ChangeCredentialsEvent evt) {
        String password = evt.getPassword();
        String newUserName = evt.getNewUserName().trim();
        String newPassword = evt.getNewPassword();
        String retypePassword = evt.getRetypePassword();
        ChangeCredentialsModel changeCredentialsModel = evt.getChangeCredentialsModel();

        Set<String> invalidProperties = validate(password,
                newUserName, newPassword, retypePassword);
        if (invalidProperties.size() > 0) {
            changeCredentialsModel.setInvalidProperties(invalidProperties);
            changeCredentialsModel.setStatus(ChangeCredentialsModel.VALIDATE_FAIL);
            return;
        }

        callService(changeCredentialsModel, password, newUserName,
                newPassword);
    }

    private void callService(final ChangeCredentialsModel changeCredentialsModel,
           final String password, final String newUserName, final String newPassword) {
        if (changeCredentialsServiceDelegate == null) {
            throw new IllegalStateException("changeCredentialsServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    changeCredentialsServiceDelegate.updateAdminCredentials(password,
                            newUserName, newPassword);
                } catch (InvalidInputException ex) {
                    ex.printStackTrace();
                    changeCredentialsModel.addInvalidProperty(ex.getMessage());
                    return ChangeCredentialsModel.VALIDATE_FAIL;
                } catch (InvalidPasswordException ex) {
                    ex.printStackTrace();
                    return ChangeCredentialsModel.INVALID_PASSWORD;
                } catch (ServerConnectionException ex) {
                    ex.printStackTrace();
                    return ChangeCredentialsModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    ex.printStackTrace();
                    return ChangeCredentialsModel.SERVER_ERROR;
                }
                return ChangeCredentialsModel.OK;
            }

            @Override
            protected void done() {
                try {
                    changeCredentialsModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }

    private Set<String> validate(String password,
            String newUserName, String newPassword, String retypePassword) {
        Set<String> properties = new HashSet<String>();
        if (ValidatorUtil.isEmpty(password, false)) {
            properties.add("password");
        }
        if (ValidatorUtil.isEmpty(newUserName)) {
            properties.add("newUserName");
        }
        if (ValidatorUtil.isEmpty(newPassword, false)) {
            properties.add("newPassword");
        } else {
            if (ValidatorUtil.isEmpty(retypePassword, false)
                    || !retypePassword.equals(newPassword)) {
                properties.add("retypePassword");
            }
        }
        return properties;
    }

    public void setChangeCredentialsServiceDelegate(ChangeCredentialsServiceDelegate changeCredentialsServiceDelegate) {
        this.changeCredentialsServiceDelegate = changeCredentialsServiceDelegate;
    }
}
