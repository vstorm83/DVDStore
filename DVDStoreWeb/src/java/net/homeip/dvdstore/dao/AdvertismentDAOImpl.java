/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Advertisment;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class AdvertismentDAOImpl extends HibernateDaoSupport implements AdvertismentDAO {

    @Override
    public List<Advertisment> findAdvertisment() {
        return getHibernateTemplate().findByNamedQuery("findAdvertisment");
    }

}
