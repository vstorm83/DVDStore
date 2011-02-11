/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action;

import net.homeip.dvdstore.pojo.web.vo.CompanyInfoVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ContactAction extends DSActionSupport {
    private CompanyInfoVO comInfo;

    @Override
    public String execute() throws Exception {
        prepareCompanyInfo();
        return SUCCESS;
    }

    private void prepareCompanyInfo() {
        comInfo = getConfigurationService().getCompanyInfo();
    }

    public CompanyInfoVO getComInfo() {
        return comInfo;
    }    
}