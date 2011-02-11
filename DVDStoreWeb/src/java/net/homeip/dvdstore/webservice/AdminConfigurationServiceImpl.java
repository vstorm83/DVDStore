/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import javax.jws.WebService;
import net.homeip.dvdstore.dao.ConfigurationDAO;
import net.homeip.dvdstore.pojo.ConfigurationInfo;
import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.AdminConfigurationService")
public class AdminConfigurationServiceImpl implements AdminConfigurationService {
    private ConfigurationDAO configurationDAO;

    public void updateConfiguration(ConfigurationInfoVO configurationInfoVO)
            throws InvalidAdminInputException {
        ConfigurationInfo configurationInfo = configurationDAO.getConfigurationInfo();
        configurationInfo.populate(configurationInfoVO);
        configurationDAO.save(configurationInfo);
    }
    
    public ConfigurationInfoVO getConfigurationInfoVO() {
        ConfigurationInfo configurationInfo = configurationDAO.getConfigurationInfo();
        ConfigurationInfoVO configurationInfoVO = new ConfigurationInfoVO();
        configurationInfoVO.setComName(configurationInfo.getComName());
        configurationInfoVO.setComAddress(configurationInfo.getComAddress());
        configurationInfoVO.setComTel(configurationInfo.getComTel());
        configurationInfoVO.setComEmail(configurationInfo.getComEmail());
        configurationInfoVO.setBestMovNum(configurationInfo.getBestMovNum());
        configurationInfoVO.setNewMovNum(configurationInfo.getNewMovNum());
        configurationInfoVO.setPageSize(configurationInfo.getPageSize());
        configurationInfoVO.setTimeZoneId(configurationInfo.getTimeZoneId());
        return configurationInfoVO;
    }

    public void setConfigurationDAO(ConfigurationDAO configurationDAO) {
        this.configurationDAO = configurationDAO;
    }

}
