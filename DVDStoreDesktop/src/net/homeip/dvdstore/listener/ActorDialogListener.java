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
import net.homeip.dvdstore.delegate.ActorServiceDelegate;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.dialog.ActorDialog;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ActorChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ActorDialogListener extends WindowAdapter
        implements DataChangingListener, DataChangedListener {

    private ActorServiceDelegate actorServiceDelegate;
    private ListenerRegistry<ActorChangeListener> actorChangeListenerRegistry;

    @Override
    public void windowOpened(WindowEvent e) {
        LoadDataModel<List<ActorVO>> actorListModel =
                ((ActorDialog) e.getSource()).getActorListModel();
        callLoadService(actorListModel, false);
    }

    public void onDataChanged(DataChangedEvent evt) {
        LoadDataModel<List<ActorVO>> actorListModel =
                ((ActorDialog) evt.getSource()).getActorListModel();
        callLoadService(actorListModel, true);
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();
        int action = evt.getAction();
        Object param = evt.getParam();

        if ((action == DataChangingEvent.ADD || action == DataChangingEvent.MODIFY)
                && validate((ActorVO) param)) {
            dataChangingModel.addInvalidProperty("actorName");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }
        if ((action == DataChangingEvent.DELETE && validate((List<Long>) param))) {
            dataChangingModel.addInvalidProperty("actorId");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }

        switch (action) {
            case DataChangingEvent.ADD:
                callAddService(dataChangingModel, (ActorVO) param);
                break;
            case DataChangingEvent.DELETE:
                callDeleteService(dataChangingModel, (List<Long>) param);
                break;
            case DataChangingEvent.MODIFY:
                callModifyService(dataChangingModel, (ActorVO) param);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<ActorVO>> movCatListModel,
            final boolean dispatchChangeEvent) {
        if (actorServiceDelegate == null) {
            throw new IllegalStateException("actorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movCatListModel.setData(actorServiceDelegate.getActorList());
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
                        dispatchActorChangeEvent();
                    }
                    movCatListModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void dispatchActorChangeEvent() {
                ActorChangeEvent evt = new ActorChangeEvent(this);
                evt.setActorList(movCatListModel.getData());
                for (ActorChangeListener listener :
                        actorChangeListenerRegistry.getListeners()) {
                    listener.onActorChange(evt);
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callAddService">
    private void callAddService(final DataChangingModel dataChangingModel,
            final ActorVO actorVO) {
        if (actorServiceDelegate == null) {
            throw new IllegalStateException("actorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    actorServiceDelegate.addActor(actorVO);
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
            final List<Long> actorIdList) {
        if (actorServiceDelegate == null) {
            throw new IllegalStateException("actorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    actorServiceDelegate.deleteActor(actorIdList);
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
            final ActorVO actorVO) {
        if (actorServiceDelegate == null) {
            throw new IllegalStateException("actorServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    actorServiceDelegate.updateActor(actorVO);
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

    public void setActorChangeListenerRegistry(ListenerRegistry<ActorChangeListener> actorChangeListenerRegistry) {
        this.actorChangeListenerRegistry = actorChangeListenerRegistry;
    }

    public void setActorServiceDelegate(ActorServiceDelegate actorServiceDelegate) {
        this.actorServiceDelegate = actorServiceDelegate;
    }

    private boolean validate(List<Long> list) {
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean validate(ActorVO actorVO) {
        String actorName = actorVO.getActorName();
        if (ValidatorUtil.isEmpty(actorName)
                || ValidatorUtil.isInvalidLength(actorName, 1, 50)) {
            return true;
        }
        return false;
    }
}