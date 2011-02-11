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
import net.homeip.dvdstore.delegate.DirectorServiceDelegate;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.dialog.DirectorDialog;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.DirectorChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorDialogListener extends WindowAdapter
        implements DataChangingListener, DataChangedListener {

    private DirectorServiceDelegate directorServiceDelegate;
    private ListenerRegistry<DirectorChangeListener> directorChangeListenerRegistry;

    @Override
    public void windowOpened(WindowEvent e) {
        LoadDataModel<List<DirectorVO>> directorListModel =
                ((DirectorDialog) e.getSource()).getDirectorListModel();
        callLoadService(directorListModel, false);
    }

    public void onDataChanged(DataChangedEvent evt) {
        LoadDataModel<List<DirectorVO>> directorListModel =
                ((DirectorDialog) evt.getSource()).getDirectorListModel();
        callLoadService(directorListModel, true);
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();
        int action = evt.getAction();
        Object param = evt.getParam();

        if ((action == DataChangingEvent.ADD || action == DataChangingEvent.MODIFY)
                && validate((DirectorVO) param)) {
            dataChangingModel.addInvalidProperty("directorName");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }
        if ((action == DataChangingEvent.DELETE && validate((List<Long>) param))) {
            dataChangingModel.addInvalidProperty("directorId");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }

        switch (action) {
            case DataChangingEvent.ADD:
                callAddService(dataChangingModel, (DirectorVO) param);
                break;
            case DataChangingEvent.DELETE:
                callDeleteService(dataChangingModel, (List<Long>) param);
                break;
            case DataChangingEvent.MODIFY:
                callModifyService(dataChangingModel, (DirectorVO) param);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<DirectorVO>> movCatListModel,
            final boolean dispatchChangeEvent) {
        if (directorServiceDelegate == null) {
            throw new IllegalStateException("directorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movCatListModel.setData(directorServiceDelegate.getDirectorList());
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
                        dispatchDirectorChangeEvent();
                    }
                    movCatListModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void dispatchDirectorChangeEvent() {
                DirectorChangeEvent evt = new DirectorChangeEvent(this);
                evt.setDirectorList(movCatListModel.getData());
                for (DirectorChangeListener listener :
                        directorChangeListenerRegistry.getListeners()) {
                    listener.onDirectorChange(evt);
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callAddService">
    private void callAddService(final DataChangingModel dataChangingModel,
            final DirectorVO directorVO) {
        if (directorServiceDelegate == null) {
            throw new IllegalStateException("directorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    directorServiceDelegate.addDirector(directorVO);
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
            final List<Long> directorIdList) {
        if (directorServiceDelegate == null) {
            throw new IllegalStateException("directorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    directorServiceDelegate.deleteDirector(directorIdList);                
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
            final DirectorVO directorVO) {
        if (directorServiceDelegate == null) {
            throw new IllegalStateException("directorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    directorServiceDelegate.updateDirector(directorVO);
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

    public void setDirectorChangeListenerRegistry(ListenerRegistry<DirectorChangeListener> directorChangeListenerRegistry) {
        this.directorChangeListenerRegistry = directorChangeListenerRegistry;
    }

    public void setDirectorServiceDelegate(DirectorServiceDelegate directorServiceDelegate) {
        this.directorServiceDelegate = directorServiceDelegate;
    }

    private boolean validate(List<Long> list) {
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean validate(DirectorVO directorVO) {
        String directorName = directorVO.getDirectorName();
        if (ValidatorUtil.isEmpty(directorName)
                || ValidatorUtil.isInvalidLength(directorName, 1, 50)) {
            return true;
        }
        return false;
    }
}