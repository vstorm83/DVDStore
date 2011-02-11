/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener.panel;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.Calendar;
import java.util.List;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.delegate.panel.ImportCardPanelServiceDelegate;
import net.homeip.dvdstore.listener.DataChangedListener;
import net.homeip.dvdstore.listener.ListenerRegistry;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.frame.LoadDataListener;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitImportCardPanelVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardPanelListener
        implements LoadDataListener, ChangeImportCardListener, DataChangedListener {

    private ImportCardPanelServiceDelegate importCardPanelServiceDelegate;
    private ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry;

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel loadDataModel = evt.getLoadDataModel();
        if (loadDataModel.getData() instanceof byte[]) {
            List<Long> importCardIds = (List<Long>) evt.getParams().get(0);
            callPrintImportCardService(loadDataModel, importCardIds);
        } else {
            String supplierNameSearch = (String) evt.getParams().get(0);
            Calendar startDateSearch = (Calendar) evt.getParams().get(1);
            Calendar endDateSearch = (Calendar) evt.getParams().get(2);
            if (loadDataModel.getData() instanceof InitImportCardPanelVO) {
                callInitService(loadDataModel,
                        supplierNameSearch, startDateSearch, endDateSearch);
            } else {
                callLoadService(loadDataModel,
                        supplierNameSearch, startDateSearch, endDateSearch);
            }
        }
    }

    public void onDataChanged(DataChangedEvent evt) {
        MovieChangeEvent movieChangeEvent = new MovieChangeEvent(evt.getSource());
        for (MovieChangeListener movieChangeListener : movieChangeListenerRegistry.getListeners()) {
            movieChangeListener.onMovieChange(movieChangeEvent);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callInitService">
    private void callInitService(final LoadDataModel<InitImportCardPanelVO> loadDataModel,
            final String supplierNameSearch, final Calendar startDateSearch, final Calendar endDateSearch) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(importCardPanelServiceDelegate.getInitImportCardPanelVO(
                            supplierNameSearch, startDateSearch, endDateSearch));
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

    // <editor-fold defaultstate="collapsed" desc="callLoadService">
    private void callLoadService(final LoadDataModel<List<ImportCardVO>> loadDataModel,
            final String supplierNameSearch, final Calendar startDateSearch, final Calendar endDateSearch) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(importCardPanelServiceDelegate.getImportCardVOs(
                            supplierNameSearch, startDateSearch, endDateSearch));
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

    // <editor-fold defaultstate="collapsed" desc="change importCard">
    public void onChangeImportCardPerformed(ChangeImportCardEvent evt) {
        DataChangingModel importCardChangingModel = evt.getImportCardChangingModel();
        int action = evt.getAction();
        switch (action) {
            case ChangeImportCardEvent.ADD_IMPORTCARD:
                callAddImportCardService(importCardChangingModel, (SupplierVO) evt.getParams());
                break;
            case ChangeImportCardEvent.DELETE_IMPORTCARD:
                callDeleteImportCardService(importCardChangingModel, (List<Long>) evt.getParams());
                break;
            case ChangeImportCardEvent.UPDATE_IMPORT_CARD:
                callUpdateImportCardService(importCardChangingModel, (ImportCardVO) evt.getParams());
                break;
            case ChangeImportCardEvent.ADD_IMPORTCARD_ITEM:
                Long importCardId = (Long) ((List) evt.getParams()).get(0);
                List<Long> movIds = (List<Long>) ((List) evt.getParams()).get(1);
                callAddImportCardItemService(importCardChangingModel,
                        importCardId, movIds);
                break;
            case ChangeImportCardEvent.DELETE_IMPORTCARD_ITEM:
                callDeleteImportCardItemService(importCardChangingModel, (Long) ((List) evt.getParams()).get(0),
                        (List<Long>) ((List) evt.getParams()).get(1));
                break;
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add ImportCard">
    private void callAddImportCardService(final DataChangingModel importCardChangingModel,
            final SupplierVO supplierVO) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    importCardPanelServiceDelegate.addImportCard(supplierVO);
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
                    importCardChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="delete ImportCard">
    private void callDeleteImportCardService(final DataChangingModel importCardChangingModel,
            final List<Long> importCardIds) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    importCardPanelServiceDelegate.deleteImportCard(importCardIds);
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
                    importCardChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="add ImportCardItem">
    private void callAddImportCardItemService(final DataChangingModel importCardChangingModel,
            final Long importCardId, final List<Long> movIds) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    importCardPanelServiceDelegate.addImportCardItem(importCardId, movIds);
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
                    importCardChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="delete ImportCardItem">
    private void callDeleteImportCardItemService(final DataChangingModel importCardChangingModel,
            final Long importCardId, final List<Long> movIds) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    importCardPanelServiceDelegate.deleteImportCardItem(importCardId, movIds);
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
                    importCardChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="update ImportCard">
    private void callUpdateImportCardService(final DataChangingModel importCardChangingModel,
            final ImportCardVO importCardVO) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    importCardPanelServiceDelegate.updateImportCardItem(importCardVO);
                    //Không catch validate fail exception do client đã validate rồi
                    //Nếu có exception thì là lỗi nghiêm trọng
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
                    importCardChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="call PrintImportCard">
    private void callPrintImportCardService(final LoadDataModel loadDataModel,
            final List<Long> importCardIds) {
        if (importCardPanelServiceDelegate == null) {
            throw new IllegalStateException("importCardPanelServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(importCardPanelServiceDelegate.printImportCard(importCardIds));
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

    public void setImportCardPanelServiceDelegate(ImportCardPanelServiceDelegate importCardPanelServiceDelegate) {
        this.importCardPanelServiceDelegate = importCardPanelServiceDelegate;
    }

    public void setMovieChangeListenerRegistry(ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry) {
        this.movieChangeListenerRegistry = movieChangeListenerRegistry;
    }
}
