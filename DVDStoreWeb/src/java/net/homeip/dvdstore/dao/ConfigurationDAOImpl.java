/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.Properties;
import net.homeip.dvdstore.pojo.ConfigurationInfo;

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationDAOImpl implements ConfigurationDAO {
    private Properties properties;

    @Override
    public int getPageSize() {
        int pageSize = 16;
        try {
            pageSize = Integer.parseInt(properties.getProperty("pageSize"));
        } catch (Exception ex) {}
        return pageSize;
    }

    @Override
    public int getBestMovieNum() {
        int bestMovieNum = 4;
        try {
            bestMovieNum = Integer.parseInt(properties.getProperty("bestMovNum"));
        } catch (Exception ex) {}
        return bestMovieNum;
    }

    @Override
    public int getNewMovieNum() {
        int newMovieNum = 16;
        try {
            newMovieNum = Integer.parseInt(properties.getProperty("newMovNum"));
        } catch (Exception ex) {}
        return newMovieNum;
    }

    @Override
    public ConfigurationInfo getConfigurationInfo() {
        ConfigurationInfo info = new ConfigurationInfo();
        info.setComName(properties.getProperty("comName", "Công ty TNHH xuất nhập khẩu Cát Thành"));
        info.setComAddress(properties.getProperty("comAddress", "Nhà 6 ngõ 261 Khâm Thiên Hà Nội"));
        info.setComEmail(properties.getProperty("comEmail", "vstorm83@gmail.com"));
        info.setComTel(properties.getProperty("comTel", "(04)38515171"));
        info.setBestMovNum(getBestMovieNum());
        info.setNewMovNum(getNewMovieNum());
        info.setPageSize(getPageSize());
        info.setTimeZoneId(getTimeZoneId());
        return info;
    }

    @Override
    public void save(ConfigurationInfo configurationInfo) {
        properties.setProperty("comName", configurationInfo.getComName());
        properties.setProperty("comAddress", configurationInfo.getComAddress());
        properties.setProperty("comTel", configurationInfo.getComTel());
        properties.setProperty("comEmail", configurationInfo.getComEmail());
        properties.setProperty("pageSize", String.valueOf(configurationInfo.getPageSize()));
        properties.setProperty("bestMovNum", String.valueOf(configurationInfo.getBestMovNum()));
        properties.setProperty("newMovNum", String.valueOf(configurationInfo.getNewMovNum()));
        properties.setProperty("timeZoneId", configurationInfo.getTimeZoneId());
    }

    @Override
    public String getTimeZoneId() {
        return properties.getProperty("timeZoneId", "Asia/Saigon");
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
