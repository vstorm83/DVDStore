/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationInfo {

    private String comName;
    private String comAddress;
    private String comTel;
    private String comEmail;
    private int pageSize;
    private int bestMovNum;
    private int newMovNum;
    private String timeZoneId;

    public int getBestMovNum() {
        return bestMovNum;
    }

    public void setBestMovNum(int bestMovNum) {
        this.bestMovNum = bestMovNum;
    }

    public int getNewMovNum() {
        return newMovNum;
    }

    public void setNewMovNum(int newMovNum) {
        this.newMovNum = newMovNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getComAddress() {
        return comAddress;
    }

    public void setComAddress(String comAddress) {
        this.comAddress = comAddress;
    }

    public String getComEmail() {
        return comEmail;
    }

    public void setComEmail(String comEmail) {
        this.comEmail = comEmail;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComTel() {
        return comTel;
    }

    public void setComTel(String comTel) {
        this.comTel = comTel;
    }

    public void populate(ConfigurationInfoVO configurationInfoVO)
            throws InvalidAdminInputException {
        nomalize(configurationInfoVO);
        validate(configurationInfoVO);
        this.comName = configurationInfoVO.getComName();
        this.comAddress = configurationInfoVO.getComAddress();
        this.comTel = configurationInfoVO.getComTel();
        this.comEmail = configurationInfoVO.getComEmail();
        this.bestMovNum = configurationInfoVO.getBestMovNum();
        this.newMovNum = configurationInfoVO.getNewMovNum();
        this.pageSize = configurationInfoVO.getPageSize();
        this.timeZoneId = configurationInfoVO.getTimeZoneId();
    }

    private void nomalize(ConfigurationInfoVO configurationInfoVO) {
        configurationInfoVO.setComName(TextUtil.normalize(configurationInfoVO.getComName()));
        configurationInfoVO.setComAddress(TextUtil.normalize(configurationInfoVO.getComAddress()));
        configurationInfoVO.setComTel(TextUtil.normalize(configurationInfoVO.getComTel()));
        configurationInfoVO.setComEmail(TextUtil.normalize(configurationInfoVO.getComEmail()));
    }

    private void validate(ConfigurationInfoVO configurationInfoVO) {
        if (ValidatorUtil.isInvalidLength(configurationInfoVO.getComName(), 0, 100)) {
            throw new InvalidAdminInputException("comName");
        }
        if (ValidatorUtil.isInvalidLength(configurationInfoVO.getComAddress(), 0, 100)) {
            throw new InvalidAdminInputException("comAdress");
        }
        if (ValidatorUtil.isInvalidLength(configurationInfoVO.getComTel(), 0, 100)) {
            throw new InvalidAdminInputException("comTel");
        }
        if (!ValidatorUtil.isEmpty(configurationInfoVO.getComEmail())
                && ValidatorUtil.isInvalidEmail(configurationInfoVO.getComEmail())) {
            throw new InvalidAdminInputException("comEmail");
        }
        if (bestMovNum < 0) {
            throw new InvalidAdminInputException("bestMovNum");
        }
        if (newMovNum < 0) {
            throw new InvalidAdminInputException("newMovNum");
        }
        if (pageSize < 0) {
            throw new InvalidAdminInputException("pageSize");
        }
    }
}
