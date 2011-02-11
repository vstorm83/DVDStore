/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import net.homeip.dvdstore.listener.event.DataChangedEvent;

/**
 *
 * @author VU VIET PHUONG
 */
public interface DataChangedListener {
    public void onDataChanged(DataChangedEvent evt);
}
