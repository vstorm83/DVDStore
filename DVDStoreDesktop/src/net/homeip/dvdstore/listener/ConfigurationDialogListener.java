/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.ConfigurationServiceDelegate;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.dialog.ConfigurationDialog;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.util.ValidatorUtil;
// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationDialogListener extends WindowAdapter
        implements DataChangingListener, DataChangedListener {
        private ConfigurationServiceDelegate configurationServiceDelegate;
        
    @Override
    public void windowOpened(WindowEvent e) {
        LoadDataModel<ConfigurationInfoVO> congfigurationDialogModel =
                ((ConfigurationDialog) e.getSource()).getConfigurationDialogModel();
        callLoadService(congfigurationDialogModel);
    }

    public void onDataChanged(DataChangedEvent evt) {
        LoadDataModel<ConfigurationInfoVO> congfigurationDialogModel =
                ((ConfigurationDialog) evt.getSource()).getConfigurationDialogModel();
        callLoadService(congfigurationDialogModel);
    }

    public void onDataChanging(DataChangingEvent evt) {
        DataChangingModel dataChangingModel = evt.getDataChangingModel();        
        ConfigurationInfoVO configurationInfoVO = (ConfigurationInfoVO)evt.getParam();
        Set<String> invalidProperties = validate(configurationInfoVO);
        if (invalidProperties.size() > 0) {
            dataChangingModel.setInvalidProperties(invalidProperties);
            dataChangingModel.setStatus(DataChangingModel.VALIDATE_FAIL);
            return;
        }

        callUpdateService(dataChangingModel, configurationInfoVO);
    }

    private void callLoadService(final LoadDataModel<ConfigurationInfoVO> congfigurationDialogModel) {
        if (configurationServiceDelegate == null) {
            throw new IllegalStateException("configurationServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    congfigurationDialogModel.setData(
                            configurationServiceDelegate.getConfigurationInfoVO());
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
                    congfigurationDialogModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }

    public void setConfigurationServiceDelegate(ConfigurationServiceDelegate configurationServiceDelegate) {
        this.configurationServiceDelegate = configurationServiceDelegate;
    }

    private void callUpdateService(final DataChangingModel dataChangingModel,
            final ConfigurationInfoVO configurationInfoVO) {
        if (configurationServiceDelegate == null) {
            throw new IllegalStateException("configurationServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    configurationServiceDelegate.updateConfiguration(configurationInfoVO);
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
                    dataChangingModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        swingWorker.execute();
    }

    private Set<String> validate(ConfigurationInfoVO configurationInfoVO) {
        String comName = TextUtil.normalize(configurationInfoVO.getComName());
        String comAddress = TextUtil.normalize(configurationInfoVO.getComAddress());
        String comTel = TextUtil.normalize(configurationInfoVO.getComTel());
        String comEmail = TextUtil.normalize(configurationInfoVO.getComEmail());

        Set<String> invalidProperties = new HashSet<String>();
        if (ValidatorUtil.isInvalidLength(comName, 0, 100)) {
            invalidProperties.add("comName");
        }
        if (ValidatorUtil.isInvalidLength(comAddress, 0, 100)) {
            invalidProperties.add("comAddress");
        }
        if (ValidatorUtil.isInvalidLength(comTel, 0, 100)) {
            invalidProperties.add("comTel");
        }
        if (!ValidatorUtil.isEmpty(comEmail) &&
                ValidatorUtil.isInvalidEmail(comEmail)) {
            invalidProperties.add("comEmail");
        }
        return invalidProperties;
    }

}
