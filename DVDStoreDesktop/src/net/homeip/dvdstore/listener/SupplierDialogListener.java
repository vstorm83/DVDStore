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
import net.homeip.dvdstore.delegate.SupplierServiceDelegate;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.dialog.SupplierDialog;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.SupplierChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierDialogListener extends WindowAdapter
        implements DataChangingListener, DataChangedListener {

    private SupplierServiceDelegate supplierServiceDelegate;
    private ListenerRegistry<SupplierChangeListener> supplierChangeListenerRegistry;

    @Override
    public void windowOpened(WindowEvent e) {
        LoadDataModel<List<SupplierVO>> supplierListModel =
                ((SupplierDialog) e.getSource()).getSupplierListModel();
        callLoadService(supplierListModel, false);
    }

    public void onDataChanged(DataChangedEvent evt) {
        LoadDataModel<List<SupplierVO>> supplierListModel =
                ((SupplierDialog) evt.getSource()).getSupplierListModel();
        callLoadService(supplierListModel, true);
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();
        int action = evt.getAction();
        boolean ignoreReference = evt.isIgnoreReference();
        Object param = evt.getParam();

        if ((action == DataChangingEvent.ADD || action == DataChangingEvent.MODIFY)
                && validate((SupplierVO) param)) {
            dataChangingModel.addInvalidProperty("supplierName");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }
        if ((action == DataChangingEvent.DELETE && validate((List<Long>) param))) {
            dataChangingModel.addInvalidProperty("supplierId");
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }

        switch (action) {
            case DataChangingEvent.ADD:
                callAddService(dataChangingModel, (SupplierVO) param);
                break;
            case DataChangingEvent.DELETE:
                callDeleteService(dataChangingModel, (List<Long>) param, ignoreReference);
                break;
            case DataChangingEvent.MODIFY:
                callModifyService(dataChangingModel, (SupplierVO) param);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<SupplierVO>> movCatListModel,
            final boolean dispatchChangeEvent) {
        if (supplierServiceDelegate == null) {
            throw new IllegalStateException("supplierServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    movCatListModel.setData(supplierServiceDelegate.getSupplierList());
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
                        dispatchSupplierChangeEvent();
                    }
                    movCatListModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private void dispatchSupplierChangeEvent() {
                SupplierChangeEvent evt = new SupplierChangeEvent(this);
                evt.setSupplierList(movCatListModel.getData());
                for (SupplierChangeListener listener :
                        supplierChangeListenerRegistry.getListeners()) {
                    listener.onSupplierChange(evt);
                }
            }
        };
        swingWorker.execute();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="callAddService">
    private void callAddService(final DataChangingModel dataChangingModel,
            final SupplierVO supplierVO) {
        if (supplierServiceDelegate == null) {
            throw new IllegalStateException("supplierServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    supplierServiceDelegate.addSupplier(supplierVO);
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
            final List<Long> supplierIdList, final boolean ignoreReference) {
        if (supplierServiceDelegate == null) {
            throw new IllegalStateException("supplierServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    supplierServiceDelegate.deleteSupplier(supplierIdList, ignoreReference);
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
            final SupplierVO supplierVO) {
        if (supplierServiceDelegate == null) {
            throw new IllegalStateException("supplierServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    supplierServiceDelegate.updateSupplier(supplierVO);
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

    public void setSupplierChangeListenerRegistry(ListenerRegistry<SupplierChangeListener> supplierChangeListenerRegistry) {
        this.supplierChangeListenerRegistry = supplierChangeListenerRegistry;
    }

    public void setSupplierServiceDelegate(SupplierServiceDelegate supplierServiceDelegate) {
        this.supplierServiceDelegate = supplierServiceDelegate;
    }

    private boolean validate(List<Long> list) {
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    private boolean validate(SupplierVO supplierVO) {
        String supplierName = supplierVO.getSupplierName();
        if (ValidatorUtil.isEmpty(supplierName)
                || ValidatorUtil.isInvalidLength(supplierName, 1, 50)) {
            return true;
        }
        return false;
    }
}
