/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore;

import javax.swing.JDialog;
import javax.swing.JFrame;
import net.homeip.dvdstore.frame.LoginFrame;

/**
 *
 * @author VU VIET PHUONG
 */
public class DVDStoreDesktop {

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
//                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    com.incors.plaf.alloy.AlloyLookAndFeel.setProperty("alloy.licenseCode",
                            "2010/05/30#hummer83@gmail.com#1tyfkbp#12rkxc");
                    com.incors.plaf.alloy.themes.CustomFontTheme myFontTheme =
                            new com.incors.plaf.alloy.themes.CustomFontTheme(
                            new String[]{"Tahoma", "Verdana", "Helvetica"}, 12, 10);
//                    com.incors.plaf.alloy.AlloyTheme theme = new com.incors.plaf.alloy.themes.glass.GlassTheme(myFontTheme);
                    com.incors.plaf.alloy.AlloyTheme theme =
                            new com.incors.plaf.alloy.themes.acid.AcidTheme(myFontTheme);
                    javax.swing.LookAndFeel alloyLnF = new com.incors.plaf.alloy.AlloyLookAndFeel(theme);
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    javax.swing.UIManager.setLookAndFeel(alloyLnF);

                    new LoginFrame().setVisible(true);
//                    MainFrame mainFrame = new MainFrame("");
//                    mainFrame.setVisible(true);
//                    Nhớ set lại listenToLogoutMessage();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
