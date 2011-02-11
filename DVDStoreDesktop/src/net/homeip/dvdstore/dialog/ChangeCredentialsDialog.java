/*
 * DoiMatKhau.java
 *
 * Created on November 29, 2008, 10:40 PM
 */
package net.homeip.dvdstore.dialog;

import java.awt.Frame;
import javax.swing.JOptionPane;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.ChangeCredentialsListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.ChangeCredentialsEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.ChangeCredentialsModel;

/**
 *
 * @author  Administrator
 */
public class ChangeCredentialsDialog extends javax.swing.JDialog
        implements ResultRetrievedListener {

    private int action = DIALOG_CLOSED;
    public static final int DIALOG_CLOSED = 0;
    public static final int DIALOG_OK = 1;
    private String newUserName;
    private ChangeCredentialsListener changeCredentialsListener;
    private ChangeCredentialsModel changeCredentialsModel;

    public void addChangeCredentialsListener(ChangeCredentialsListener changeCredentialsListener) {
        if (changeCredentialsListener == null) {
            throw new IllegalArgumentException("can't add null changeCredentialsListener");
        }
        this.changeCredentialsListener = changeCredentialsListener;
    }

    public void onResultRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = "";
        switch (changeCredentialsModel.getStatus()) {
            case ChangeCredentialsModel.OK:
                finishChangeCredentials();
                return;
            case ChangeCredentialsModel.VALIDATE_FAIL:
                errorMsg = getInputErrorMessage();
                break;
            case ChangeCredentialsModel.INVALID_PASSWORD:
                errorMsg = "Mật khẩu cũ không đúng";
                break;
            case ChangeCredentialsModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Không kết nối được tới Server";
                break;
            case ChangeCredentialsModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        setEnableChangeCredentials(true);
        JOptionPane.showMessageDialog(this, errorMsg);
    }

    private String getInputErrorMessage() {
        StringBuilder msgBuild = new StringBuilder();
        for (String property : changeCredentialsModel.getInvalidProperties()) {
            if (property.equals("password")) {
                msgBuild.append("Chưa nhập mật khẩu\n");
            }
            if (property.equals("newUserName")) {
                msgBuild.append("Chưa nhập tên đăng nhập mới\n");
            }
            if (property.equals("newPassword")) {
                msgBuild.append("Chưa nhập mật khẩu mới\n");
            }
            if (property.equals("retypePassword")) {
                msgBuild.append("Mật khẩu nhập lại không đúng\n");
            }
        }
        return msgBuild.toString();
    }
    
    private void finishChangeCredentials() {
        this.newUserName = txtTenDangNhapMoi.getText();
        this.action = DIALOG_OK;
        JOptionPane.showMessageDialog(this, "Sửa mật khẩu thành công");
        dispose();
    }       

    public ChangeCredentialsDialog(Frame owner) {
        super(owner, true);
        initComponents();
        addChangeCredentialsListener((ChangeCredentialsListener)ApplicationContextUtil.
                getApplicationContext().getBean("changeCredentialsListener"));
        changeCredentialsModel = new ChangeCredentialsModel();
        changeCredentialsModel.addResultRetrievedListener(this);
    }

    public int showDialog() {
        setVisible(true);
        return action;
    }

    private void dispatchChangeCredentialsEvent() {
        if (changeCredentialsListener == null) {
            throw new IllegalStateException("changeCredentialsListener has not set");
        }
        ChangeCredentialsEvent evt = new ChangeCredentialsEvent(this);
        evt.setChangeCredentialsModel(changeCredentialsModel);
        evt.setNewPassword(new String(txtMatKhauMoi.getPassword()));
        evt.setNewUserName(txtTenDangNhapMoi.getText());
        evt.setPassword(new String(txtMatKhau.getPassword()));
        evt.setRetypePassword(new String(txtNhapLai.getPassword()));

        changeCredentialsListener.changeCredentialsPerformed(evt);
    }
    
    public String getNewUserName() {
        return newUserName;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        txtTenDangNhapMoi = new javax.swing.JTextField();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        txtNhapLai = new javax.swing.JPasswordField();
        btnDongYSua = new javax.swing.JButton();
        btnNhapLai = new javax.swing.JButton();
        btnDongCuaSo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Password");
        setModal(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin đăng nhập cũ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setText("Mật khẩu:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 12));
        jLabel6.setForeground(new java.awt.Color(0, 51, 204));
        jLabel6.setText("Bạn cần nhập chính xác thông tin đăng nhập cũ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(47, 47, 47)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông tin đăng nhập mới", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(255, 102, 0))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel3.setText("Tên đăng nhập:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel4.setText("Mật khẩu mới:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel5.setText("Nhập lại:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenDangNhapMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtNhapLai, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                    .addComponent(jLabel5))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenDangNhapMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNhapLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnDongYSua.setText("Đồng ý sửa");
        btnDongYSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongYSuaActionPerformed(evt);
            }
        });

        btnNhapLai.setText("Nhập lại");
        btnNhapLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapLaiActionPerformed(evt);
            }
        });

        btnDongCuaSo.setText("Đóng cửa sổ");
        btnDongCuaSo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongCuaSoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(btnDongYSua)
                        .addGap(57, 57, 57)
                        .addComponent(btnNhapLai)
                        .addGap(45, 45, 45)
                        .addComponent(btnDongCuaSo))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDongYSua)
                    .addComponent(btnNhapLai)
                    .addComponent(btnDongCuaSo))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-617)/2, (screenSize.height-223)/2, 617, 223);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDongCuaSoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongCuaSoActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnDongCuaSoActionPerformed

    private void btnNhapLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapLaiActionPerformed
        // TODO add your handling code here:
        txtMatKhau.setText("");
        txtTenDangNhapMoi.setText("");
        txtMatKhauMoi.setText("");
        txtNhapLai.setText("");
    }//GEN-LAST:event_btnNhapLaiActionPerformed

    private void btnDongYSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongYSuaActionPerformed
        // TODO add your handling code here:
        setEnableChangeCredentials(false);
        dispatchChangeCredentialsEvent();
    }//GEN-LAST:event_btnDongYSuaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDongCuaSo;
    private javax.swing.JButton btnDongYSua;
    private javax.swing.JButton btnNhapLai;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtNhapLai;
    private javax.swing.JTextField txtTenDangNhapMoi;
    // End of variables declaration//GEN-END:variables

    private void setEnableChangeCredentials(boolean enabled) {
        btnDongYSua.setEnabled(enabled);
    }    
}
