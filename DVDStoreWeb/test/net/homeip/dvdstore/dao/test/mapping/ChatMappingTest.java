/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.mapping;

import net.chrisrichardson.ormunit.hibernate.HibernateORMappingTests;
import net.homeip.dvdstore.pojo.Chat;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChatMappingTest extends HibernateORMappingTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    @Test
    public void testMapUser() {
        assertClassMapping(Chat.class, "Chat");
        assertAllPropertiesMapped();
    }
}