/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.frame;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.homeip.dvdstore.listener.JMSUserChangeListenerRegistry;
import net.homeip.dvdstore.listener.ListenerRegistry;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.SupplierChangeListener;
import net.homeip.dvdstore.listener.UserChangeListener;

/**
 *
 * @author VU VIET PHUONG
 */
public class StatisticIFrameListener extends InternalFrameAdapter {
    // <editor-fold defaultstate="collapsed" desc="listener registry">
    private ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry;
    private ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry;
    private ListenerRegistry<SupplierChangeListener> supplierChangeListenerRegistry;
    private JMSUserChangeListenerRegistry jMSUserChangeListenerRegistry;

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        movieCatgoryChangeListenerRegistry.removeListener((MovieCatgoryChangeListener) e.getSource());
        movieChangeListenerRegistry.removeListener((MovieChangeListener) e.getSource());
        supplierChangeListenerRegistry.removeListener((SupplierChangeListener)e.getSource());
        jMSUserChangeListenerRegistry.removeListener((UserChangeListener)e.getSource());
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        movieCatgoryChangeListenerRegistry.addListener((MovieCatgoryChangeListener) e.getSource());
        movieChangeListenerRegistry.addListener((MovieChangeListener) e.getSource());
        supplierChangeListenerRegistry.addListener((SupplierChangeListener)e.getSource());
        jMSUserChangeListenerRegistry.addListener((UserChangeListener)e.getSource());
    }

    public void setMovieCatgoryChangeListenerRegistry(ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry) {
        this.movieCatgoryChangeListenerRegistry = movieCatgoryChangeListenerRegistry;
    }

    public void setMovieChangeListenerRegistry(ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry) {
        this.movieChangeListenerRegistry = movieChangeListenerRegistry;
    }

    public void setSupplierChangeListenerRegistry(ListenerRegistry<SupplierChangeListener> supplierChangeListenerRegistry) {
        this.supplierChangeListenerRegistry = supplierChangeListenerRegistry;
    }

    public void setjMSUserChangeListenerRegistry(JMSUserChangeListenerRegistry jMSUserChangeListenerRegistry) {
        this.jMSUserChangeListenerRegistry = jMSUserChangeListenerRegistry;
    }
    
    // </editor-fold>
}
