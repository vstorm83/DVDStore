/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import java.awt.EventQueue;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.swing.JOptionPane;

/**
 *
 * @author VU VIET PHUONG
 */
public class JMSExceptionListener implements ExceptionListener {

    public void onException(JMSException jmse) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JOptionPane.showMessageDialog(null, "Không có kết nối Server");
                System.exit(1);
            }
        });
    }

}
