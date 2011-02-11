/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ConfigurationServiceDelegate {
    public ConfigurationInfoVO getConfigurationInfoVO();

    public void updateConfiguration(ConfigurationInfoVO configurationInfoVO)
            throws InvalidInputException;
}
