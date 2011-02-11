/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

import net.homeip.dvdstore.dao.ConfigurationDAO;
import net.homeip.dvdstore.pojo.ConfigurationInfo;
import net.homeip.dvdstore.pojo.web.vo.CompanyInfoVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationServiceImpl implements ConfigurationService {

    private ConfigurationDAO configurationDAO;

    @Override
    public CompanyInfoVO getCompanyInfo() {
        ConfigurationInfo conInfo = configurationDAO.getConfigurationInfo();
        CompanyInfoVO comInfoVO = new CompanyInfoVO();
        comInfoVO.setComName(conInfo.getComName());
        comInfoVO.setComAddress(conInfo.getComAddress());
        comInfoVO.setComTel(conInfo.getComTel());
        comInfoVO.setComEmail(conInfo.getComEmail());
        return comInfoVO;
    }

    @Override
    public int getPageSize() {
        return configurationDAO.getPageSize();
    }

    @Override
    public int getBestMovieNum() {
        return configurationDAO.getBestMovieNum();
    }

    @Override
    public int getNewMovieNum() {
        return configurationDAO.getNewMovieNum();
    }

    @Override
    public String getTimeZoneId() {
        return configurationDAO.getTimeZoneId();
    }

    public void setConfigurationDAO(ConfigurationDAO configurationDAO) {
        this.configurationDAO = configurationDAO;
    }
}
