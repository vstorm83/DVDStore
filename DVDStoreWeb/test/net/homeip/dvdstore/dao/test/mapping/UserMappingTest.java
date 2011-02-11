/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.mapping;

import net.chrisrichardson.ormunit.hibernate.HibernateORMappingTests;
import net.homeip.dvdstore.pojo.User;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserMappingTest extends HibernateORMappingTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    @Test
    public void testMapUser() {
        assertClassMapping(User.class, "DSUser");
        assertProperty("userId", "UserId");
        assertProperty("userName", "UserName");
        assertProperty("password", "Pass");
        assertComponentProperty("deliveryInfo.firstName");
        assertComponentProperty("deliveryInfo.lastName");
        assertComponentProperty("deliveryInfo.address");
        assertComponentProperty("deliveryInfo.tel");
        assertComponentProperty("deliveryInfo.email");
        assertProperty("lock", "Lock");
        assertProperty("dateCreated", "DateCreated");
        assertProperty("version", "Version");
    }
}