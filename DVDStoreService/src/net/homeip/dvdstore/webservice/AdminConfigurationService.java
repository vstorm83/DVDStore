/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface AdminConfigurationService {

    public ConfigurationInfoVO getConfigurationInfoVO();

    public void updateConfiguration(ConfigurationInfoVO configurationInfoVO)
            throws InvalidAdminInputException;

}
