/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class ListenerRegistry<T> {
    private List<T> listeners = new LinkedList<T>();

    public void addListener(T listener) {
        if (listener == null) {
            throw new IllegalArgumentException("can't add null listener");
        }
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    public void removeListener(T listener) {
        if (listener == null) {
            throw new IllegalArgumentException("can't add null listener");
        }
        listeners.remove(listener);
    }

    public List<T> getListeners() {
        return listeners;
    }
    
}
