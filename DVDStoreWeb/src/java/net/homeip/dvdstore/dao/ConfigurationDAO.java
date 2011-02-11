/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import net.homeip.dvdstore.pojo.ConfigurationInfo;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ConfigurationDAO {
//    public static final String UPLOAD_DIR="http://localhost:8080/DVDStoreWeb/uploads/";
    public static final String APSOLUTE_UPLOAD_DIR="http://localhost:8080/DVDStoreWeb/uploads/";
    public static final String UPLOAD_DIR="uploads/";
    
    public ConfigurationInfo getConfigurationInfo();

    public int getPageSize();

    public int getBestMovieNum();

    public int getNewMovieNum();

    public String getTimeZoneId();

    public void save(ConfigurationInfo configurationInfo);

}
