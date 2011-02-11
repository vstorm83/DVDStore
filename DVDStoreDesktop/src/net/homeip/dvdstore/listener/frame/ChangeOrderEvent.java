/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.frame;

import java.util.EventObject;
import net.homeip.dvdstore.model.DataChangingModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChangeOrderEvent extends EventObject {
    private DataChangingModel orderChangingModel;
    private Object params;
    private boolean ignoreQuantity;
    private int action;
    public static final int DELETE_ORDER = 0;
    public static final int DELETE_ORDER_ITEM = 1;
    public static final int ADD_ORDER_ITEM = 2;
    public static final int UPDATE_ORDER = 3;
    public static final int COMPLETE_ORDER = 4;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public DataChangingModel getOrderChangingModel() {
        return orderChangingModel;
    }

    public void setOrderChangingModel(DataChangingModel orderChangingModel) {
        this.orderChangingModel = orderChangingModel;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public boolean isIgnoreQuantity() {
        return ignoreQuantity;
    }

    public void setIgnoreQuantity(boolean ignoreQuantity) {
        this.ignoreQuantity = ignoreQuantity;
    }

    public ChangeOrderEvent(Object source) {
        super(source);
    }
    
}
