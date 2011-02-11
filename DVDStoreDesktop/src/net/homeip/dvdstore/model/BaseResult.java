/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.model;

import java.util.LinkedList;
import java.util.List;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;

/**
 *
 * @author VU VIET PHUONG
 */
public class BaseResult {

    private int status;
    private List<ResultRetrievedListener> resultRetrievedListeners =
            new LinkedList<ResultRetrievedListener>();
    public static final int OK = 1;
    public static final int SERVER_ERROR = 2;
    public static final int SERVER_CONNECTION_ERROR = 3;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        ResultRetrievedEvent evt = new ResultRetrievedEvent(this);
        for (ResultRetrievedListener listener : resultRetrievedListeners) {
            listener.onResultRetrieved(evt);
        }
    }

    public void addResultRetrievedListener(ResultRetrievedListener resultRetrievedListener) {
        if (resultRetrievedListener == null) {
            throw new IllegalArgumentException("can't add null ResultRetrievedListener");
        }
        resultRetrievedListeners.add(resultRetrievedListener);
    }

    public void removeResultRetrievedListener(ResultRetrievedListener resultRetrievedListener) {
        if (resultRetrievedListener == null) {
            throw new IllegalArgumentException("can't remove null resultRetrievedListener");
        }
        resultRetrievedListeners.remove(resultRetrievedListener);
    }
}
