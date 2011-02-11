/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdminResponse extends ActionSupport {
    Logger logger = Logger.getLogger(AdminResponse.class);

    @Override
    public String execute() {
        logger.trace("AdminResponse");
        return "adminLogout";
    }
    
}
