/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import java.util.List;
import net.homeip.dvdstore.model.LoadDataModel;

/**
 *
 * @author VU VIET PHUONG
 */
public class LoadDataEvent<T> extends EventObject {
    private LoadDataModel<T> loadDataModel;
    private List params;

    public LoadDataModel<T> getLoadDataModel() {
        return loadDataModel;
    }

    public void setLoadDataModel(LoadDataModel<T> loadDataModel) {
        this.loadDataModel = loadDataModel;
    }

    public List getParams() {
        return params;
    }

    public void setParams(List params) {
        this.params = params;
    }

    public LoadDataEvent(Object source) {
        super(source);
    }
    
}
