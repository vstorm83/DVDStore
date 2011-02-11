/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.panel;

import java.util.EventObject;
import net.homeip.dvdstore.model.DataChangingModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChangeImportCardEvent extends EventObject {
    private DataChangingModel importCardChangingModel;
    private Object params;
    private int action;
    public static final int DELETE_IMPORTCARD = 0;
    public static final int ADD_IMPORTCARD = 1;
    public static final int UPDATE_IMPORT_CARD = 2;
    public static final int DELETE_IMPORTCARD_ITEM = 3;
    public static final int ADD_IMPORTCARD_ITEM = 4;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public DataChangingModel getImportCardChangingModel() {
        return importCardChangingModel;
    }

    public void setImportCardChangingModel(DataChangingModel importCardChangingModel) {
        this.importCardChangingModel = importCardChangingModel;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public ChangeImportCardEvent(Object source) {
        super(source);
    }
    
}
