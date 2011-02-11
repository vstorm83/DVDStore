/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import net.homeip.dvdstore.listener.event.DataChangingEvent;

/**
 *
 * @author VU VIET PHUONG
 */
public interface DataChangingListener {
    public void onDataChanging(DataChangingEvent evt);
}
