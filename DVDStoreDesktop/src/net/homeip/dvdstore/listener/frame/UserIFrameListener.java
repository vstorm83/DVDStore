/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.Calendar;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.delegate.frame.UserIFrameServiceDelegate;
import net.homeip.dvdstore.listener.DataChangedListener;
import net.homeip.dvdstore.listener.DataChangingListener;
import net.homeip.dvdstore.listener.JMSUserChangeListenerRegistry;
import net.homeip.dvdstore.listener.ListenerRegistry;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.UserChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class UserIFrameListener extends InternalFrameAdapter
        implements DataChangingListener, DataChangedListener,
        LoadDataListener {

    private UserIFrameServiceDelegate userIFrameServiceDelegate;

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel loadDataModel = evt.getLoadDataModel();
        if (loadDataModel.getData() instanceof byte[]) {
            List<Long> userIds = (List<Long>) evt.getParams().get(0);
            callPrintTradeHistoryService(loadDataModel, userIds);
        } else {
            List params = evt.getParams();
            String userNameSearch = (String) params.get(0);
            String firstNameSearch = (String) params.get(1);
            String lastNameSearch = (String) params.get(2);
            String addressSearch = (String) params.get(3);
            String telSearch = (String) params.get(4);
            String emailSearch = (String) params.get(5);
            Calendar startDateSearch = (Calendar) params.get(6);
            Calendar endDateSearch = (Calendar) params.get(7);
            callLoadService(loadDataModel, userNameSearch, firstNameSearch, lastNameSearch, addressSearch,
                    telSearch, emailSearch, startDateSearch, endDateSearch);
        }
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel userDeletingModel = evt.getDataChangingModel();
        boolean ignoreReference = evt.isIgnoreReference();
        Object param = evt.getParam();
        callDeleteService(userDeletingModel, (List<Long>) param, ignoreReference);
    }

    public void onDataChanged(DataChangedEvent evt) {
        MovieChangeEvent movieChangeEvent = new MovieChangeEvent(evt.getSource());
        for (MovieChangeListener movieChangeListener : movieChangeListenerRegistry.getListeners()) {
            movieChangeListener.onMovieChange(movieChangeEvent);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<UserVO>> loadDataModel,
            final String userNameSearch, final String firstNameSearch, final String lastNameSearch,
            final String addressSearch, final String telSearch,
            final String emailSearch, final Calendar startDateSearch, final Calendar endDateSearch) {
        if (userIFrameServiceDelegate == null) {
            throw new IllegalStateException("userIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(
                            userIFrameServiceDelegate.getUserVOs(userNameSearch,
                            firstNameSearch, lastNameSearch, addressSearch,
                            telSearch, emailSearch, startDateSearch, endDateSearch));
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="call PrintExportCard">
    private void callPrintTradeHistoryService(final LoadDataModel loadDataModel,
            final List<Long> userIds) {
        if (userIFrameServiceDelegate == null) {
            throw new IllegalStateException("userIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(userIFrameServiceDelegate.printTradeHistory(userIds));
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

    // <editor-fold defaultstate="collapsed" desc="delete User">
    private void callDeleteService(final DataChangingModel userDeletingModel,
            final List<Long> userIds, final boolean ignoreReference) {
        if (userIFrameServiceDelegate == null) {
            throw new IllegalStateException("userIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    userIFrameServiceDelegate.deleteUser(userIds, ignoreReference);
                } catch (DBReferenceViolationException ex) {
                    userDeletingModel.setViolationName(ex.getViolationName());
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
                    userDeletingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="listener registry">
    private ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry;
    private JMSUserChangeListenerRegistry jMSUserChangeListenerRegistry;

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {        
        jMSUserChangeListenerRegistry.removeListener((UserChangeListener)e.getSource());
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {        
        jMSUserChangeListenerRegistry.addListener((UserChangeListener)e.getSource());
    }

    public void setMovieChangeListenerRegistry(ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry) {
        this.movieChangeListenerRegistry = movieChangeListenerRegistry;
    }

    public void setjMSUserChangeListenerRegistry(JMSUserChangeListenerRegistry jMSUserChangeListenerRegistry) {
        this.jMSUserChangeListenerRegistry = jMSUserChangeListenerRegistry;
    }
    // </editor-fold>

    public void setUserIFrameServiceDelegate(UserIFrameServiceDelegate userIFrameServiceDelegate) {
        this.userIFrameServiceDelegate = userIFrameServiceDelegate;
    }
}
