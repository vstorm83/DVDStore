/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener;

import java.util.List;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.ChooseMovieDialogServiceDelegate;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.frame.LoadDataListener;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChooseMovieDialogListener implements LoadDataListener {
    private ChooseMovieDialogServiceDelegate chooseMovieDialogServiceDelegate;

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel<List<ChooseMovieCatgoryVO>> loadDataModel = evt.getLoadDataModel();
        callLoadService(loadDataModel);
    }

    private void callLoadService(final LoadDataModel<List<ChooseMovieCatgoryVO>> loadDataModel) {
        if (chooseMovieDialogServiceDelegate == null) {
            throw new IllegalStateException("chooseMovieDialogServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(chooseMovieDialogServiceDelegate.getChooseMovieCatgoryVOs());
                } catch (ServerConnectionException ex) {
                    ex.printStackTrace();
                    return LoadDataModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    ex.printStackTrace();
                    return LoadDataModel.SERVER_ERROR;
                }
                return LoadDataModel.OK;
            }

            @Override
            protected void done() {
                try {
                    loadDataModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }

    public void setChooseMovieDialogServiceDelegate(ChooseMovieDialogServiceDelegate chooseMovieDialogServiceDelegate) {
        this.chooseMovieDialogServiceDelegate = chooseMovieDialogServiceDelegate;
    }

}
