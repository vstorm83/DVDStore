/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import net.homeip.dvdstore.pojo.Admin;

/**
 *
 * @author VU VIET PHUONG
 */
public interface AdminDAO {

    public Admin getAdminByAdminName(String adminName);

    public void save(Admin admin);

}
