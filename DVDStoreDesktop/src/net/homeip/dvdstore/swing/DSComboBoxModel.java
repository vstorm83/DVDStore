/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.swing;

import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class DSComboBoxModel extends DefaultComboBoxModel {

    public DSComboBoxModel() {
    }

    public DSComboBoxModel(List items, boolean firstEmpty) {
        super(items==null?new Object[0]:items.toArray());
        if (firstEmpty) {
            this.insertElementAt(null, 0);
            this.setSelectedItem(null);
        }
    }
    
}
