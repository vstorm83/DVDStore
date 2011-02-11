/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao.test.persitence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import net.chrisrichardson.ormunit.hibernate.HibernatePersistenceTests;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.type.Type;
import org.junit.Test;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserPersitenceTest extends HibernatePersistenceTests {

    private User user;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();

        user = new User();
        user.setUserName("tendangnhap");
        user.setPassword("123");
        user.setDeliveryInfo(new DeliveryInfo("Vũ Việt", "Phương", "Hà Nội", "090", "emailfortest@gmail.com"));
        user.setDateCreated(new Date());
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

//    @Test
//    public void testPersitence() {
//        add();
//        query();
//        update();
//        delete(user);
//    }    
    private void add() {
        save(user);
    }

    private void update() {
        user.setUserName("conghoa");
        save(user);
    }

    private void query() {
        getHibernateTemplate().findByNamedQueryAndNamedParam("getUserByUserName",
                "userName", "tendangnhap").get(0);
        getHibernateTemplate().findByNamedQueryAndNamedParam("getUserIdByEmail",
                "email", "emailfortest@gmail.com").get(0);
        getHibernateTemplate().findByNamedQueryAndNamedParam("getUserIdByUserName",
                "userName", "tendangnhap").get(0);
        DeliveryInfo info = (DeliveryInfo) getHibernateTemplate().findByNamedQueryAndNamedParam("getDeliveryInfoByUserId",
                "userId", user.getUserId()).get(0);
        assertEquals(user.getDeliveryInfo().getAddress(), info.getAddress());
    }

    @Test
    public void testQueryByExample() {
        user = new User();
        user.setDeliveryInfo(new DeliveryInfo("việt", null, null, null, null));

        final Example example = Example.create(user).excludeZeroes().excludeProperty(
                "password").enableLike(MatchMode.ANYWHERE).ignoreCase();

        List<User> userList = getHibernateTemplate().executeFind(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                return session.createCriteria(User.class).add(example).list();
            }
        });
        for (User u : userList) {
            System.out.println(u.getUserId() + " " + u.getUserName());
        }
    }
}
