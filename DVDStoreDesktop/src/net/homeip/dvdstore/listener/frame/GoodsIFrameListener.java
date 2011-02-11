/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.SwingWorker;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.delegate.frame.GoodsIFrameServiceDelegate;
import net.homeip.dvdstore.listener.ActorChangeListener;
import net.homeip.dvdstore.listener.DataChangedListener;
import net.homeip.dvdstore.listener.DataChangingListener;
import net.homeip.dvdstore.listener.DirectorChangeListener;
import net.homeip.dvdstore.listener.ListenerRegistry;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitGoodsIFrameVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class GoodsIFrameListener extends InternalFrameAdapter
        implements LoadDataListener, DataChangedListener, 
        DataChangingListener, UploadListener {

    private GoodsIFrameServiceDelegate goodsIFrameServiceDelegate;

    public void onUploadPerformed(UploadEvent evt) {
        DataChangingModel uploadModel = evt.getDataChangingModel();
        byte[] fileUploaded = evt.getFileUploaded();
        String fileName = evt.getFileName();
        callUploadService(uploadModel, fileName, fileUploaded);
    }

    public void onDataChanged(DataChangedEvent evt) {
        MovieChangeEvent movieChangeEvent = new MovieChangeEvent(this);
        for (MovieChangeListener movieChangeListener : movieChangeListenerRegistry.getListeners()) {
            movieChangeListener.onMovieChange(movieChangeEvent);
        }
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();
        int action = evt.getAction();
        boolean ignoreReference = evt.isIgnoreReference();
        Object param = evt.getParam();
        Set<String> invalidProperties = validate(param);
        if (invalidProperties.size() > 0) {
            dataChangingModel.setInvalidProperties(invalidProperties);
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }

        switch (action) {
            case DataChangingEvent.ADD:
                callAddService(dataChangingModel, (GoodsMovieVO) param);
                break;
            case DataChangingEvent.MODIFY:
                callModifyService(dataChangingModel, (GoodsMovieVO)param);
                break;
            case DataChangingEvent.DELETE:
                callDeleteService(dataChangingModel, (List<Long>)param, ignoreReference);
        }
    }

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel loadDataModel = evt.getLoadDataModel();
        String searchByMovName = (String) evt.getParams().get(0);
        MovieCatgoryVO searchByMovCat = (MovieCatgoryVO) evt.getParams().get(1);
        if (loadDataModel.getData() instanceof InitGoodsIFrameVO) {
            callInitService(loadDataModel,
                    searchByMovName, searchByMovCat);
        } else {
            callLoadService(loadDataModel,
                    searchByMovName, searchByMovCat);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="callAddService">
    private void callAddService(final DataChangingModel dataChangingModel,
            final GoodsMovieVO goodsMovieVO) {
        if (goodsIFrameServiceDelegate == null) {
            throw new IllegalStateException("goodsIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    goodsIFrameServiceDelegate.addMovie(goodsMovieVO);
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

    // <editor-fold defaultstate="collapsed" desc="callModifyService">
    private void callModifyService(final DataChangingModel dataChangingModel,
            final GoodsMovieVO goodsMovieVO) {
        if (goodsIFrameServiceDelegate == null) {
            throw new IllegalStateException("goodsIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    goodsIFrameServiceDelegate.updateMovie(goodsMovieVO);
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
            final List<Long> goodsMovieIdList, final boolean ignoreReference) {
        if (goodsIFrameServiceDelegate == null) {
            throw new IllegalStateException("goodsIFrameServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    goodsIFrameServiceDelegate.deleteMovie(goodsMovieIdList, ignoreReference);
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
    
    // <editor-fold defaultstate="collapsed" desc="callInitService">
    private void callInitService(final LoadDataModel<InitGoodsIFrameVO> loadDataModel,
            final String searchByMovName, final MovieCatgoryVO searchByMovCat) {
        if (goodsIFrameServiceDelegate == null) {
            throw new IllegalStateException("goodsServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(goodsIFrameServiceDelegate.getInitGoodsIFrameVO(
                            searchByMovName, searchByMovCat));
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
    private void callLoadService(final LoadDataModel<List<GoodsMovieVO>> loadDataModel,
            final String searchByMovName, final MovieCatgoryVO searchByMovCat) {
        if (goodsIFrameServiceDelegate == null) {
            throw new IllegalStateException("goodsServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    loadDataModel.setData(goodsIFrameServiceDelegate.getGoodsMovieVOs(
                            searchByMovName, searchByMovCat));
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
    // <editor-fold defaultstate="collapsed" desc="listener registry">
    private ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry;
    private ListenerRegistry<DirectorChangeListener> directorChangeListenerRegistry;
    private ListenerRegistry<ActorChangeListener> actorChangeListenerRegistry;
    private ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry;

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {
        movieCatgoryChangeListenerRegistry.removeListener((MovieCatgoryChangeListener) e.getSource());
        directorChangeListenerRegistry.removeListener((DirectorChangeListener) e.getSource());
        actorChangeListenerRegistry.removeListener((ActorChangeListener) e.getSource());
        movieChangeListenerRegistry.removeListener((MovieChangeListener) e.getSource());
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
        movieCatgoryChangeListenerRegistry.addListener((MovieCatgoryChangeListener) e.getSource());
        directorChangeListenerRegistry.addListener((DirectorChangeListener) e.getSource());
        actorChangeListenerRegistry.addListener((ActorChangeListener) e.getSource());
        movieChangeListenerRegistry.addListener((MovieChangeListener) e.getSource());
    }

    public void setActorChangeListenerRegistry(ListenerRegistry<ActorChangeListener> actorChangeListenerRegistry) {
        this.actorChangeListenerRegistry = actorChangeListenerRegistry;
    }

    public void setDirectorChangeListenerRegistry(ListenerRegistry<DirectorChangeListener> directorChangeListenerRegistry) {
        this.directorChangeListenerRegistry = directorChangeListenerRegistry;
    }

    public void setMovieCatgoryChangeListenerRegistry(ListenerRegistry<MovieCatgoryChangeListener> movieCatgoryChangeListenerRegistry) {
        this.movieCatgoryChangeListenerRegistry = movieCatgoryChangeListenerRegistry;
    }

    public void setMovieChangeListenerRegistry(ListenerRegistry<MovieChangeListener> movieChangeListenerRegistry) {
        this.movieChangeListenerRegistry = movieChangeListenerRegistry;
    }// </editor-fold>

    private Set<String> validate(Object param) {
        Set<String> invalidProperties = new HashSet<String>();
        if (param instanceof GoodsMovieVO) {
            GoodsMovieVO mov = (GoodsMovieVO) param;
            if (ValidatorUtil.isInvalidLength(mov.getMovName(), 1, 50)) {
                invalidProperties.add("movName");
            }
            if (mov.getMovCatVO() == null) {
                invalidProperties.add("movCatVO");
            }
            if (mov.getMovPrice() < 0) {
                invalidProperties.add("movPrice");
            }
            if (mov.getMovSaleOff() < 0) {
                invalidProperties.add("movSaleOff");
            }
        }
        return invalidProperties;
    }

    public void setGoodsIFrameServiceDelegate(GoodsIFrameServiceDelegate goodsIFrameServiceDelegate) {
        this.goodsIFrameServiceDelegate = goodsIFrameServiceDelegate;
    }

    private void callUploadService(final DataChangingModel uploadModel,final String fileName,
            final byte[] fileUploaded) {
        if (goodsIFrameServiceDelegate == null) {
            throw new IllegalStateException("goodsServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    goodsIFrameServiceDelegate.upload(fileName, fileUploaded);
                } catch (ServerConnectionException ex) {
                    ex.printStackTrace();
                    return DataChangingModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    ex.printStackTrace();
                    return DataChangingModel.SERVER_ERROR;
                }
                return DataChangingModel.OK;
            }

            @Override
            protected void done() {
                try {
                    uploadModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }
}
