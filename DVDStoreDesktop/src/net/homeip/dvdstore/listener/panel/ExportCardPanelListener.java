/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener.panel;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.Date;
import java.util.List;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.delegate.panel.ExportCardPanelServiceDelegate;
import net.homeip.dvdstore.listener.DataChangedListener;
import net.homeip.dvdstore.listener.DataChangingListener;
import net.homeip.dvdstore.listener.ListenerRegistry;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.frame.LoadDataListener;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardPanelListener
        implements DataChangingListener, DataChangedListener, LoadDataListener {

    private ExportCardPanelServiceDelegate exportCardPanelServiceDelegate;
    private ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry;

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel loadDataModel = evt.getLoadDataModel();
        if (loadDataModel.getData() instanceof byte[]) {
            List<Long> exportCardIds = (List<Long>) evt.getParams().get(0);
            callPrintExportCardService(loadDataModel, exportCardIds);
        } else {
            String userNameSearch = (String) evt.getParams().get(0);
            Date startDateSearch = (Date) evt.getParams().get(1);
            Date endDateSearch = (Date) evt.getParams().get(2);
            callLoadService(loadDataModel, userNameSearch, startDateSearch, endDateSearch);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<ExportCardVO>> loadDataModel,
            final String userNameSearch, final Date startDateSearch, final Date endDateSearch) {
        if (exportCardPanelServiceDelegate == null) {
            throw new IllegalStateException("exportCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(
                            exportCardPanelServiceDelegate.getExportCardVOs(
                            userNameSearch, startDateSearch, endDateSearch));
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
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="call PrintExportCard">
    private void callPrintExportCardService(final LoadDataModel loadDataModel,
            final List<Long> exportCardIds) {
        if (exportCardPanelServiceDelegate == null) {
            throw new IllegalStateException("exportCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(exportCardPanelServiceDelegate.printExportCard(exportCardIds));
                } catch (ServerConnectionException ex) {
                    return DataChangingModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    return DataChangingModel.SERVER_ERROR;
                }
                return DataChangingModel.OK;
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
    }// </editor-fold>

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel exportCardRevertingModel = evt.getDataChangingModel();
        List<Long> exportCardIds = (List<Long>) evt.getParam();
        callRevertExportCardService(exportCardRevertingModel, exportCardIds);
    }

    // <editor-fold defaultstate="collapsed" desc="revert ExportCard">
    private void callRevertExportCardService(final DataChangingModel exportCardRevertingModel,
            final List<Long> exportCardIds) {
        if (exportCardPanelServiceDelegate == null) {
            throw new IllegalStateException("exportCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    exportCardPanelServiceDelegate.revertExportCard(exportCardIds);
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
                    exportCardRevertingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    public void onDataChanged(DataChangedEvent evt) {
        MovieChangeEvent movieChangeEvent = new MovieChangeEvent(evt.getSource());
        for (MovieChangeListener movieChangeListener : movieChangeListenerRegistry.getListeners()) {
            movieChangeListener.onMovieChange(movieChangeEvent);
        }
    }

    public void setExportCardPanelServiceDelegate(ExportCardPanelServiceDelegate exportCardPanelServiceDelegate) {
        this.exportCardPanelServiceDelegate = exportCardPanelServiceDelegate;
    }

    public void setMovieChangeListenerRegistry(ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry) {
        this.movieChangeListenerRegistry = movieChangeListenerRegistry;
    }
}
