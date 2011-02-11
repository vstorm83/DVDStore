/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.mapping;

import net.chrisrichardson.ormunit.hibernate.HibernateORMappingTests;
import net.homeip.dvdstore.pojo.Director;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorMappingTest extends HibernateORMappingTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    @Test
    public void testMapUser() {
        assertClassMapping(Director.class, "Director");
        assertAllPropertiesMapped();
    }
}