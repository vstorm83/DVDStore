/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.MovieCatgoryServiceDelegate;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.dialog.MovieCatgoryDialog;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.MovieCatgoryChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.util.ValidatorUtil;
// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryDialogListener extends WindowAdapter
        implements DataChangingListener, DataChangedListener {

    private MovieCatgoryServiceDelegate movieCatgoryServiceDelegate;
    private ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry;

    @Override
    public void windowOpened(WindowEvent e) {
        LoadDataModel<List<MovieCatgoryVO>> movieCatgoryListModel =
                ((MovieCatgoryDialog) e.getSource()).getMovCatListModel();
        callLoadService(movieCatgoryListModel, false);
    }

    public void onDataChanged(DataChangedEvent evt) {
        LoadDataModel<List<MovieCatgoryVO>> movieCatgoryListModel =
                ((MovieCatgoryDialog) evt.getSource()).getMovCatListModel();
        callLoadService(movieCatgoryListModel, true);
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();
        int action = evt.getAction();
        boolean ignoreReference = evt.isIgnoreReference();
        Object param = evt.getParam();

        if ((action == DataChangingEvent.ADD || action == DataChangingEvent.MODIFY)
                && validate((MovieCatgoryVO) param)) {
            dataChangingModel.addInvalidProperty("movCatName");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }
        if ((action == DataChangingEvent.DELETE && validate((List<Long>) param))) {
            dataChangingModel.addInvalidProperty("movCatId");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }
        
        switch (action) {
            case DataChangingEvent.ADD:
                callAddService(dataChangingModel, (MovieCatgoryVO) param);
                break;
            case DataChangingEvent.DELETE:
                callDeleteService(dataChangingModel, (List<Long>) param, ignoreReference);
                break;
            case DataChangingEvent.MODIFY:
                callModifyService(dataChangingModel, (MovieCatgoryVO) param);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<MovieCatgoryVO>> movCatListModel,
            final boolean dispatchChangeEvent) {
        if (movieCatgoryServiceDelegate == null) {
            throw new IllegalStateException("movieCatgoryServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movCatListModel.setData(movieCatgoryServiceDelegate.getMovieCatgoryList());
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
                    if (dispatchChangeEvent) {
                        dispatchMovCatChangeEvent();
                    }
                    movCatListModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void dispatchMovCatChangeEvent() {
                MovieCatgoryChangeEvent evt = new MovieCatgoryChangeEvent(this);
                evt.setMovCatList(movCatListModel.getData());
                for (MovieCatgoryChangeListener listener :
                        movieCatgoryChangeListenerRegistry.getListeners()) {
                    listener.onMovieCatgoryChange(evt);
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callAddService">
    private void callAddService(final DataChangingModel dataChangingModel,
            final MovieCatgoryVO movieCatgoryVO) {
        if (movieCatgoryServiceDelegate == null) {
            throw new IllegalStateException("movieCatgoryServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movieCatgoryServiceDelegate.addMovCat(movieCatgoryVO);
                } catch (InvalidInputException ex) {
                    dataChangingModel.addInvalidProperty(ex.getMessage());
                    return DataChangingModel.VALIDATE_FAIL;
                } catch (DuplicateException ex) {
                    dataChangingModel.setDuplicateProperty(ex.getMessage());
                    return DataChangingModel.DUPLICATE;
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
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callDeleteService">
    private void callDeleteService(final DataChangingModel dataChangingModel,
            final List<Long> movCatIdList, final boolean ignoreReference) {
        if (movieCatgoryServiceDelegate == null) {
            throw new IllegalStateException("movieCatgoryServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movieCatgoryServiceDelegate.deleteMovCat(movCatIdList, ignoreReference);
                } catch (DBReferenceViolationException ex) {
                    dataChangingModel.setViolationName(ex.getViolationName());
                    return DataChangingModel.CONSTRAINT_VIOLATION;
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
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callModifyService">
    private void callModifyService(final DataChangingModel dataChangingModel,
            final MovieCatgoryVO movieCatgoryVO) {
        if (movieCatgoryServiceDelegate == null) {
            throw new IllegalStateException("movieCatgoryServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movieCatgoryServiceDelegate.updateMovCat(movieCatgoryVO);
                } catch (InvalidInputException ex) {
                    dataChangingModel.addInvalidProperty(ex.getMessage());
                    return DataChangingModel.VALIDATE_FAIL;
                } catch (DuplicateException ex) {
                    dataChangingModel.setDuplicateProperty(ex.getMessage());
                    return DataChangingModel.DUPLICATE;
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
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    public void setMovieCatgoryChangeListenerRegistry(ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry) {
        this.movieCatgoryChangeListenerRegistry = movieCatgoryChangeListenerRegistry;
    }

    public void setMovieCatgoryServiceDelegate(MovieCatgoryServiceDelegate movieCatgoryServiceDelegate) {
        this.movieCatgoryServiceDelegate = movieCatgoryServiceDelegate;
    }

    private boolean validate(List<Long> list) {
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean validate(MovieCatgoryVO movieCatgoryVO) {
        String movCatName = movieCatgoryVO.getMovCatName();
        if (ValidatorUtil.isEmpty(movCatName)
                || ValidatorUtil.isInvalidLength(movCatName, 1, 50)) {
            return true;
        }
        return false;
    }
}
