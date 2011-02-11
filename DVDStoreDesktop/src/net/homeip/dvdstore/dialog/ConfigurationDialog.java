/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfigurationDiaglog.java
 *
 * Created on Mar 10, 2010, 8:50:46 AM
 */
package net.homeip.dvdstore.dialog;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.Set;
import java.util.TimeZone;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.ConfigurationDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ConfigurationInfoVO;

// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ConfigurationDialog extends javax.swing.JDialog {

    private LoadDataModel<ConfigurationInfoVO> configurationDialogModel;
    private DataChangingModel configurationChangingModel;
    private ConfigurationDialogListener configurationDialogListener;

    /** Creates new form ConfigurationDiaglog */
    public ConfigurationDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        prepareTimeZoneComboBox();

        prepareLoadData();
        prepareChangeData();

        configurationDialogListener =
                (ConfigurationDialogListener) ApplicationContextUtil.getApplicationContext().getBean(
                "configurationDialogListener");
        addWindowListener(configurationDialogListener);
    }

    // <editor-fold defaultstate="collapsed" desc="loadData">
    private void prepareLoadData() {
        configurationDialogModel = new LoadDataModel<ConfigurationInfoVO>();
        configurationDialogModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onConfigurationInfoVORetrieved(evt);
            }
        });
    }

    private void onConfigurationInfoVORetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (configurationDialogModel.getStatus()) {
            case LoadDataModel.OK:
                setFormData(configurationDialogModel.getData());
                break;
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Không kết nối được với Server";
                break;
            default:
                errorMsg = "Lỗi Server";
        }
        btnUpdateConfiguration.setEnabled(true);
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="change data">
    private void prepareChangeData() {
        configurationChangingModel = new DataChangingModel();
        configurationChangingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onChanging(evt);
            }
        });
    }

    private void dispatchChangingEvent(ConfigurationInfoVO param) {
        btnUpdateConfiguration.setEnabled(false);
        DataChangingEvent evt = new DataChangingEvent(this);
        evt.setDataChangingModel(configurationChangingModel);
        evt.setParam(param);
        configurationDialogListener.onDataChanging(evt);
    }

    private void onChanging(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (configurationChangingModel.getStatus()) {
            case DataChangingModel.OK:
                dispatchChangedEvent();
                JOptionPane.showMessageDialog(this, "Thao tác thành công");
                return;
            case DataChangingModel.VALIDATE_FAIL:
                errorMsg = makeErrorMsg(configurationChangingModel.getInvalidProperties());
                break;
            case DataChangingModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Lỗi kết nối Server";
                break;
            case DataChangingModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        btnUpdateConfiguration.setEnabled(true);
        JOptionPane.showMessageDialog(this, errorMsg);
    }

    private void dispatchChangedEvent() {
        configurationDialogListener.onDataChanged(new DataChangedEvent(this));
    }// </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        txtComName = new javax.swing.JTextField();
        txtComAddress = new javax.swing.JTextField();
        txtComTel = new javax.swing.JTextField();
        txtComEmail = new javax.swing.JTextField();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel7 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel8 = new javax.swing.JLabel();
        spinPageSize = new javax.swing.JSpinner();
        cbxTimeZone = new javax.swing.JComboBox();
        spinBestMovieNum = new javax.swing.JSpinner();
        spinNewMovieNum = new javax.swing.JSpinner();
        btnUpdateConfiguration = new javax.swing.JButton();
        btnCloseDialog = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cài đật");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tên công ty"));

        jLabel1.setText("Tên công ty:");

        jLabel2.setText("Địa chỉ:");

        jLabel3.setText("Điện thoại:");

        jLabel4.setText("Email:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtComName, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(txtComTel, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(txtComEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addComponent(txtComAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
                .addGap(74, 74, 74))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtComName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtComEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông tin khác"));

        jLabel5.setText("Số phim/Trang:");

        jLabel6.setText("Số phim bán chạy:");

        jLabel7.setText("Số phim mới:");
        jLabel7.setPreferredSize(new java.awt.Dimension(73, 14));

        jLabel8.setText("Múi giờ:");

        spinPageSize.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        cbxTimeZone.setPrototypeDisplayValue("++++++++++");

        spinBestMovieNum.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        spinNewMovieNum.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinBestMovieNum)
                    .addComponent(spinPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(cbxTimeZone, 0, 87, Short.MAX_VALUE)
                        .addGap(35, 35, 35))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(spinNewMovieNum, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel5)
                    .addComponent(spinPageSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinNewMovieNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel6)
                    .addComponent(spinBestMovieNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(cbxTimeZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnUpdateConfiguration.setText("Cập nhật");
        btnUpdateConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateConfigurationActionPerformed(evt);
            }
        });

        btnCloseDialog.setText("Đóng cửa sổ");
        btnCloseDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseDialogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addComponent(btnUpdateConfiguration)
                        .addGap(40, 40, 40)
                        .addComponent(btnCloseDialog))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateConfiguration)
                    .addComponent(btnCloseDialog))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-530)/2, (screenSize.height-405)/2, 530, 405);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseDialogActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseDialogActionPerformed

    private void btnUpdateConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateConfigurationActionPerformed
        // TODO add your handling code here:
        dispatchChangingEvent(getFormData());
    }//GEN-LAST:event_btnUpdateConfigurationActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCloseDialog;
    private javax.swing.JButton btnUpdateConfiguration;
    private javax.swing.JComboBox cbxTimeZone;
    private javax.swing.JSpinner spinBestMovieNum;
    private javax.swing.JSpinner spinNewMovieNum;
    private javax.swing.JSpinner spinPageSize;
    private javax.swing.JTextField txtComAddress;
    private javax.swing.JTextField txtComEmail;
    private javax.swing.JTextField txtComName;
    private javax.swing.JTextField txtComTel;
    // End of variables declaration//GEN-END:variables

    public LoadDataModel<ConfigurationInfoVO> getConfigurationDialogModel() {
        return configurationDialogModel;
    }

    private void prepareTimeZoneComboBox() {
        cbxTimeZone.setModel(new DefaultComboBoxModel(TimeZone.getAvailableIDs()));
    }

    // <editor-fold defaultstate="collapsed" desc="get-setFormData">
    private void setFormData(ConfigurationInfoVO configurationVO) {
        if (configurationVO == null) {
            throw new IllegalArgumentException("can't set form with null data");
        }
        txtComName.setText(configurationVO.getComName());
        txtComAddress.setText(configurationVO.getComAddress());
        txtComTel.setText(configurationVO.getComTel());
        txtComEmail.setText(configurationVO.getComEmail());
        spinPageSize.setValue(configurationVO.getPageSize());
        spinBestMovieNum.setValue(configurationVO.getBestMovNum());
        spinNewMovieNum.setValue(configurationVO.getNewMovNum());
        cbxTimeZone.setSelectedItem(configurationVO.getTimeZoneId());
    }

    private ConfigurationInfoVO getFormData() {
        ConfigurationInfoVO configurationInfoVO = new ConfigurationInfoVO();
        configurationInfoVO.setComName(txtComName.getText());
        configurationInfoVO.setComAddress(txtComAddress.getText());
        configurationInfoVO.setComTel(txtComTel.getText());
        configurationInfoVO.setComEmail(txtComEmail.getText());
        configurationInfoVO.setBestMovNum((Integer) spinBestMovieNum.getValue());
        configurationInfoVO.setNewMovNum((Integer) spinNewMovieNum.getValue());
        configurationInfoVO.setPageSize((Integer) spinPageSize.getValue());
        configurationInfoVO.setTimeZoneId((String) cbxTimeZone.getSelectedItem());
        return configurationInfoVO;
    }// </editor-fold>

    private String makeErrorMsg(Set<String> invalidProperties) {
        StringBuilder errorMsgBuilder = new StringBuilder();
        for (String property : invalidProperties) {
            if (property.equals("comName")) {
                errorMsgBuilder.append("Tên công ty không vượt quá 100 ký tự\n");
            } else if (property.equals("comAddress")) {
                errorMsgBuilder.append("Địa chỉ không vượt quá 100 ký tự\n");
            } else if (property.equals("comTel")) {
                errorMsgBuilder.append("Điện thoại không vượt quá 100 ký tự\n");
            } else if (property.equals("comEmail")) {
                errorMsgBuilder.append("Email sai định dạng");
            }
        }
        String errorMsg = errorMsgBuilder.toString();
        return errorMsg.equals("")?null:errorMsg;
    }
}
