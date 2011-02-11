/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import net.homeip.dvdstore.pojo.web.vo.CompanyInfoVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ConfigurationService {
    /**
     * lấy số cell trong 1 trang gridView
     * @return int , default is 16
     */
    int getPageSize();
    /**
     * 
     * @return CompanyInfoVO, never null
     */
    CompanyInfoVO getCompanyInfo();

    public int getBestMovieNum();

    public int getNewMovieNum();

    public String getTimeZoneId();
}
