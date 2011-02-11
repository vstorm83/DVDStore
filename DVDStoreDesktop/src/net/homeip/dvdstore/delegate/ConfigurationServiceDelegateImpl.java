/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.delegate;

import javax.xml.ws.WebServiceException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;
import net.homeip.dvdstore.webservice.AdminConfigurationService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationServiceDelegateImpl implements ConfigurationServiceDelegate {

    private AdminConfigurationService adminConfigurationService;

    public ConfigurationInfoVO getConfigurationInfoVO() {
        if (adminConfigurationService == null) {
            throw new IllegalStateException("adminConfigurationService has not set");
        }

        try {
            return adminConfigurationService.getConfigurationInfoVO();
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void updateConfiguration(ConfigurationInfoVO configurationInfoVO)
            throws InvalidInputException {
        if (adminConfigurationService == null) {
            throw new IllegalStateException("adminConfigurationService has not set");
        }

        try {
            adminConfigurationService.updateConfiguration(configurationInfoVO);
        } catch (InvalidAdminInputException ex) {
            ex.printStackTrace();
            throw new InvalidInputException(ex.getMessage(), ex);
        } catch (javax.xml.ws.soap.SOAPFaultException ex) {
            ex.printStackTrace();
            throw new ServerException(ex.getMessage(), ex);
        } catch (WebServiceException ex) {
            ex.printStackTrace();
            throw new ServerConnectionException(ex.getMessage(), ex);
        }
    }

    public void setAdminConfigurationService(AdminConfigurationService adminConfigurationService) {
        this.adminConfigurationService = adminConfigurationService;
    }
}
