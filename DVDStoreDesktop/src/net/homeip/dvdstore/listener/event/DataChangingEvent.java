/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import net.homeip.dvdstore.model.DataChangingModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class DataChangingEvent extends EventObject {
    private int action;
    private boolean ignoreReference;
    private Object param;
    private DataChangingModel dataChangingModel;
    public static final int ADD = 0;
    public static final int MODIFY = 1;
    public static final int DELETE = 2;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public DataChangingModel getDataChangingModel() {
        return dataChangingModel;
    }

    public void setDataChangingModel(DataChangingModel dataChangingModel) {
        this.dataChangingModel = dataChangingModel;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public boolean isIgnoreReference() {
        return ignoreReference;
    }

    public void setIgnoreReference(boolean ignoreReference) {
        this.ignoreReference = ignoreReference;
    }

    public DataChangingEvent(Object source) {
        super(source);
    }
    
}
