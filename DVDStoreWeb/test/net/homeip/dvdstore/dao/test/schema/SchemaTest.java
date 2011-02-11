/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao.test.schema;

import net.chrisrichardson.ormunit.hibernate.HibernateSchemaTests;
import org.junit.Test;

/**
 *
 * @author VU VIET PHUONG
 */
public class SchemaTest extends HibernateSchemaTests {
    @Override
    protected String[] getConfigLocations() {
        return new String[]{"classpath:Data.xml"};
    }

    @Test
    public void testSchema() throws Exception {
        assertDatabaseSchema();
    }
}