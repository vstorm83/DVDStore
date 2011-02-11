/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Admin;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdminDAOImpl extends HibernateDaoSupport implements AdminDAO {

    @Override
    public Admin getAdminByAdminName(String adminName) {
        List<Admin> adminList = getHibernateTemplate().
                findByNamedQueryAndNamedParam("findAdminByAdminName", "adminName", adminName);
        if (adminList.size() == 0) {
            return null;
        }
        return adminList.get(0);
    }

    @Override
    public void save(Admin admin) {
    }
}
