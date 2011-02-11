/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.swing;

import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

/**
 *
 * @author Administrator
 */
public class ImagePreviewer extends JLabel {

    public ImagePreviewer(final JFileChooser chooser) {
        setPreferredSize(new Dimension(100, 100));
        setBorder(BorderFactory.createEtchedBorder());

        chooser.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
                    if (chooser.getSelectedFile() != null) {
                        ImageIcon icon = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
                        if (icon.getIconWidth() > 100) {
                            icon = new ImageIcon(icon.getImage().getScaledInstance(100, -1, Image.SCALE_DEFAULT));
                        }
                        setIcon(icon);
                    }
                }
            }
        });
    }
}
