/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.mapping;

import net.chrisrichardson.ormunit.hibernate.HibernateORMappingTests;
import net.homeip.dvdstore.pojo.MovieCatgory;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryMappingTest extends HibernateORMappingTests {

    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    @Test
    public void testMapUser() {
        assertClassMapping(MovieCatgory.class, "MovieCatgory");
        assertAllPropertiesMapped();
    }
}