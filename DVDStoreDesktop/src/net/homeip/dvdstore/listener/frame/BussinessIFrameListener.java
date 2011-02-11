/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.delegate.frame.BussinessIFrameServiceDelegate;
import net.homeip.dvdstore.listener.JMSUserChangeListenerRegistry;
import net.homeip.dvdstore.listener.ListenerRegistry;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.UserChangeListener;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.webservice.exception.NotEnoughMovieException;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class BussinessIFrameListener extends InternalFrameAdapter
        implements LoadDataListener, ChangeOrderListener, OrderCompletedListener {

    private BussinessIFrameServiceDelegate bussinessIFrameServiceDelegate;

    public void onChangeOrderPerformed(ChangeOrderEvent evt) {
        DataChangingModel orderChangingModel = evt.getOrderChangingModel();
        int action = evt.getAction();
        switch (action) {
            case ChangeOrderEvent.DELETE_ORDER:
                callDeleteOrderService(orderChangingModel, (List<Long>) evt.getParams());
                break;
            case ChangeOrderEvent.DELETE_ORDER_ITEM:
                Long orderId = (Long) ((List) evt.getParams()).get(0);
                List<Long> orderItemIdList = (List<Long>) ((List) evt.getParams()).get(1);
                callDeleteOrderItemService(orderChangingModel, orderId, orderItemIdList);
                break;
            case ChangeOrderEvent.UPDATE_ORDER:
                callUpdateOrderService(orderChangingModel, (OrderVO) evt.getParams());
                break;
            case ChangeOrderEvent.ADD_ORDER_ITEM:                                
                callAddOrderItemService(orderChangingModel, (Long) ((List) evt.getParams()).get(0),
                        (List<Long>) ((List) evt.getParams()).get(1));
                break;
            case ChangeOrderEvent.COMPLETE_ORDER:
                callCompleteOrderService(orderChangingModel,
                        (List<Long>)evt.getParams(), evt.isIgnoreQuantity());
        }
    }

    public void onOrderCompleted(OrderCompletedEvent evt) {
        MovieChangeEvent movieChangeEvent = new MovieChangeEvent(evt.getSource());
        for (MovieChangeListener movieChangeListener : movieChangeListenerRegistry.getListeners()) {
            movieChangeListener.onMovieChange(movieChangeEvent);
        }
    }

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel<List<OrderVO>> loadDataModel = evt.getLoadDataModel();
        callLoadService(loadDataModel);
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<OrderVO>> loadDataModel) {
        if (bussinessIFrameServiceDelegate == null) {
            throw new IllegalStateException("bussinessIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(bussinessIFrameServiceDelegate.getOrderVOs());
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

    // <editor-fold defaultstate="collapsed" desc="callDeleteOrderService">
    private void callDeleteOrderService(final DataChangingModel orderChangingModel,
            final List<Long> orderIdList) {
        if (bussinessIFrameServiceDelegate == null) {
            throw new IllegalStateException("bussinessIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    bussinessIFrameServiceDelegate.deleteOrder(orderIdList);
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
                    orderChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callDeleteOrderItemService">
    private void callDeleteOrderItemService(final DataChangingModel orderChangingModel,
            final Long orderId, final List<Long> orderItemIdList) {
        if (bussinessIFrameServiceDelegate == null) {
            throw new IllegalStateException("bussinessIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    bussinessIFrameServiceDelegate.deleteOrderItem(orderId, orderItemIdList);
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
                    orderChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callUpdateService">
    private void callUpdateOrderService(final DataChangingModel orderChangingModel,
            final OrderVO orderVO) {
        if (bussinessIFrameServiceDelegate == null) {
            throw new IllegalStateException("bussinessIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    bussinessIFrameServiceDelegate.updateOrder(orderVO);
                } catch (InvalidInputException ex) {
                    orderChangingModel.addInvalidProperty(ex.getMessage());
                    return DataChangingModel.VALIDATE_FAIL;
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
                    orderChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callAddOrderItemService">
    private void callAddOrderItemService(final DataChangingModel orderChangingModel,
            final Long orderId, final List<Long> movIds) {
        if (bussinessIFrameServiceDelegate == null) {
            throw new IllegalStateException("bussinessIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    bussinessIFrameServiceDelegate.addOrderItem(orderId, movIds);
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
                    orderChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="listener registry">
    private ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry;
    private ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry;
    private JMSUserChangeListenerRegistry jMSUserChangeListenerRegistry;

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        movieCatgoryChangeListenerRegistry.removeListener((MovieCatgoryChangeListener) e.getSource());
        movieChangeListenerRegistry.removeListener((MovieChangeListener) e.getSource());
        jMSUserChangeListenerRegistry.removeListener((UserChangeListener)e.getSource());
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        movieCatgoryChangeListenerRegistry.addListener((MovieCatgoryChangeListener) e.getSource());
        movieChangeListenerRegistry.addListener((MovieChangeListener) e.getSource());
        jMSUserChangeListenerRegistry.addListener((UserChangeListener)e.getSource());
    }

    public void setMovieCatgoryChangeListenerRegistry(ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry) {
        this.movieCatgoryChangeListenerRegistry = movieCatgoryChangeListenerRegistry;
    }

    public void setMovieChangeListenerRegistry(ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry) {
        this.movieChangeListenerRegistry = movieChangeListenerRegistry;
    }

    public void setjMSUserChangeListenerRegistry(JMSUserChangeListenerRegistry jMSUserChangeListenerRegistry) {
        this.jMSUserChangeListenerRegistry = jMSUserChangeListenerRegistry;
    }
    
    // </editor-fold>
    
    public void setBussinessIFrameServiceDelegate(BussinessIFrameServiceDelegate bussinessIFrameServiceDelegate) {
        this.bussinessIFrameServiceDelegate = bussinessIFrameServiceDelegate;
    }

    // <editor-fold defaultstate="collapsed" desc="callCompleteOrderService">
    private void callCompleteOrderService(final DataChangingModel orderChangingModel,
            final List<Long> orderIds, final boolean ignoreQuantity) {
        if (bussinessIFrameServiceDelegate == null) {
            throw new IllegalStateException("bussinessIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    bussinessIFrameServiceDelegate.completeOrder(orderIds, ignoreQuantity);
                } catch (NotEnoughMovieException ex) {
                    orderChangingModel.setViolationName(ex.getViolationName());
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
                    orderChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

}
