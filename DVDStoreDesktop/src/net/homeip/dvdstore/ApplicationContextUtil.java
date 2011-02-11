package net.homeip.dvdstore;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author VU VIET PHUONG
 */
public class ApplicationContextUtil {
    private static ApplicationContext context = null;

    static {
        try {
            context = new ClassPathXmlApplicationContext(new String[] {
                "Service.xml", "Listener.xml", "JMS.xml"
            });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
