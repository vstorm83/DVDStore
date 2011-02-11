/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.web.listener;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 * @author VU VIET PHUONG
 */
public class ConfigurationLoader implements ServletContextListener {

    public static final String DS_CONFIG_LOCATION = "/WEB-INF/DSConfig.xml";
    public static final Properties DS_CONFIG = new Properties();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        InputStream inputStream = sce.getServletContext().getResourceAsStream(DS_CONFIG_LOCATION);
        if (inputStream != null) {
            try {
                try {                    
                    DS_CONFIG.loadFromXML(inputStream);
                } finally {
                    inputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        OutputStream outStream = null;
        try {
            try {
                String path = sce.getServletContext().getRealPath(DS_CONFIG_LOCATION).replaceAll("%20", " ");
                outStream = new FileOutputStream(path);
                DS_CONFIG.storeToXML(outStream, "DVDStore Configuration");
            } finally {
                if (outStream != null) outStream.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return DS_CONFIG;
    }
}
