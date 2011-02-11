/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserIFrame.java
 *
 * Created on Feb 24, 2010, 10:03:57 AM
 */
package net.homeip.dvdstore.frame;

// <editor-fold defaultstate="collapsed" desc="import">
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.UserChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.listener.event.UserChangeEvent;
import net.homeip.dvdstore.listener.frame.UserIFrameListener;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.UserTableModel;
import net.homeip.dvdstore.util.TextUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class UserIFrame extends javax.swing.JInternalFrame
        implements UserChangeListener, MessageListener {

    private LoadDataModel<List<UserVO>> loadDataModel;
    private LoadDataModel<byte[]> printDataModel;
    private DataChangingModel userDeletingModel;
    private DataChangingEvent userDeletingEvent;
    private UserIFrameListener userIFrameListener;
    private SimpleMessageListenerContainer userRegisterMessageListenerContainer;

    /** Creates new form UserIFrame */
    public UserIFrame() {
        initComponents();
        prepareEndDateSearch();
        prepareTable();

        userIFrameListener =
                (UserIFrameListener) ApplicationContextUtil.getApplicationContext().getBean(
                "userIFrameListener");
        prepareListenerRegistry();

        prepareLoadData();
        dispatchLoadDataEvent();
        preparePrintData();

        prepareDeletingUser();
        prepareListenToMessage();
    }

    // <editor-fold defaultstate="collapsed" desc="load Data">
    private void prepareLoadData() {
        loadDataModel = new LoadDataModel<List<UserVO>>();
        loadDataModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadDataResultRetrieved(evt);
            }
        });
    }

    private void dispatchLoadDataEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(loadDataModel);
        List params = new ArrayList();
        params.add(userNameSearch);
        params.add(firstNameSearch);
        params.add(lastNameSearch);
        params.add(addressSearch);
        params.add(telSearch);
        params.add(emailSearch);
        params.add(startDateSearch);
        params.add(endDateSearch);
        evt.setParams(params);
        userIFrameListener.onLoadDataPerformed(evt);
    }

    private void onLoadDataResultRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = checkError(loadDataModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            userTableModel.setRowData(loadDataModel.getData());
        }
        restoreState();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="print ExportCard">
    private void preparePrintData() {
        printDataModel = new LoadDataModel<byte[]>();
        printDataModel.setData(new byte[0]);
        printDataModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onPrintDataRetrieved(evt);
            }
        });
    }

    private void dispatchLoadPrintDataEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(printDataModel);
        List params = new ArrayList();
        params.add(getUserTableSelectedIds());
        evt.setParams(params);
        userIFrameListener.onLoadDataPerformed(evt);
    }

    private void onPrintDataRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = checkError(printDataModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            showReport(printDataModel.getData());
        }
        restoreState();
    }

    private void showReport(byte[] reportData) {
        try {
            JasperViewer.viewReport(new ByteArrayInputStream(reportData), false, false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="delete User">
    private void prepareDeletingUser() {
        userDeletingModel = new DataChangingModel();
        userDeletingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onUserDeletingResultRetrieved(evt);
            }
        });
    }

    private void dispatchDeleteUserEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        userDeletingEvent = new DataChangingEvent(this);
        userDeletingEvent.setDataChangingModel(userDeletingModel);
        userDeletingEvent.setParam(getUserTableSelectedIds());
        userIFrameListener.onDataChanging(userDeletingEvent);
    }

    private void onUserDeletingResultRetrieved(ResultRetrievedEvent evt) {
        int status = userDeletingModel.getStatus();
        String errorMsg = checkError(status);
        if (errorMsg != null) {
            restoreState();
            JOptionPane.showMessageDialog(this, errorMsg);
        } else if (status == DataChangingModel.CONSTRAINT_VIOLATION) {
            if (JOptionPane.showConfirmDialog(this,
                    makeDBReferenceViolationMsg(userDeletingModel.getViolationName()),
                    "Xóa thông tin khách hàng", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                reDispatchDeleteUserEvent(userDeletingEvent);
            } else {
                restoreState();
            }
        } else {
            dispatchLoadDataEvent();
            dispatchUserDeletedEvent();
        }
    }

    private void dispatchUserDeletedEvent() {
        DataChangedEvent dataChangedEvent = new DataChangedEvent(this);
        userIFrameListener.onDataChanged(dataChangedEvent);
    }

    private void reDispatchDeleteUserEvent(DataChangingEvent evt) {
        evt.setIgnoreReference(true);
        userIFrameListener.onDataChanging(evt);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="check error">
    private String checkError(int status) {
        String errorMsg = null;
        switch (status) {
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Lỗi kết nối Server";
                break;
            case LoadDataModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        return errorMsg;
    }
    // </editor-fold>

    public void onUserChange(UserChangeEvent evt) {
        userTableModel.updateUser(evt.getOldUserVO(), evt.getNewUserVO());
    }

    // <editor-fold defaultstate="collapsed" desc="prepareListenToMessage">
    private void prepareListenToMessage() {
        addInternalFrameListener(new InternalFrameAdapter() {

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                userRegisterMessageListenerContainer.shutdown();
            }

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                try {
                    userRegisterMessageListenerContainer =
                            (SimpleMessageListenerContainer) ApplicationContextUtil.getApplicationContext().getBean(
                            "userRegisterMessageListenerContainer");
                    userRegisterMessageListenerContainer.setMessageListener(UserIFrame.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void onMessage(Message msg) {
        try {
            final UserVO newUser = (UserVO) ((ObjectMessage) msg).getObject();
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    if (TextUtil.containsIgnoreCase(newUser.getUserName(), userNameSearch)
                            && TextUtil.containsIgnoreCase(newUser.getDeliveryInfo().getFirstName(), firstNameSearch)
                            && TextUtil.containsIgnoreCase(newUser.getDeliveryInfo().getLastName(), lastNameSearch)
                            && TextUtil.containsIgnoreCase(newUser.getDeliveryInfo().getAddress(), addressSearch)
                            && TextUtil.containsIgnoreCase(newUser.getDeliveryInfo().getTel(), telSearch)
                            && TextUtil.containsIgnoreCase(newUser.getDeliveryInfo().getEmail(), emailSearch)
                            && newUser.getDateCreated().compareTo(startDateSearch.getTime()) >= 0
                            && newUser.getDateCreated().compareTo(endDateSearch.getTime()) <= 0
                            ) {
                        userTableModel.addUser(newUser);
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }// </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel userIFrameHeaderPane = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        javax.swing.JLabel lblTieuDeUser = new javax.swing.JLabel();
        javax.swing.JTabbedPane userTabbedPane = new javax.swing.JTabbedPane();
        javax.swing.JSplitPane userSplitPane = new javax.swing.JSplitPane();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JPanel userPaneNorth = new javax.swing.JPanel();
        scrollUser = new javax.swing.JScrollPane();
        javax.swing.JPanel userControlButtonPane = new javax.swing.JPanel();
        btnSearchUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        btnTradeHistory = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        javax.swing.JTabbedPane userSearchFormTabbedPane = new javax.swing.JTabbedPane();
        javax.swing.JPanel userSearchFormPane = new javax.swing.JPanel();
        javax.swing.JLabel lblUserName = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();
        javax.swing.JLabel lblTel = new javax.swing.JLabel();
        javax.swing.JLabel lblEmail = new javax.swing.JLabel();
        javax.swing.JLabel jLabel9 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtFirstName = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtTel = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        dtDateCreated = new datechooser.beans.DateChooserCombo();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("DVDStoreDesktop"); // NOI18N
        setTitle(bundle.getString("UserIFrame.title")); // NOI18N

        userIFrameHeaderPane.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/goctrai.gif"))); // NOI18N
        userIFrameHeaderPane.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gocfai.gif"))); // NOI18N
        userIFrameHeaderPane.add(jLabel2, java.awt.BorderLayout.EAST);

        lblTieuDeUser.setBackground(new java.awt.Color(255, 255, 255));
        lblTieuDeUser.setFont(new java.awt.Font("Times New Roman", 3, 24));
        lblTieuDeUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieuDeUser.setText(bundle.getString("UserIFrame.lblTieuDe.text")); // NOI18N
        lblTieuDeUser.setOpaque(true);
        userIFrameHeaderPane.add(lblTieuDeUser, java.awt.BorderLayout.CENTER);

        getContentPane().add(userIFrameHeaderPane, java.awt.BorderLayout.NORTH);

        userSplitPane.setDividerLocation(300);
        userSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 53));

        userPaneNorth.setPreferredSize(new java.awt.Dimension(100, 49));
        userPaneNorth.setLayout(new java.awt.BorderLayout());
        userPaneNorth.add(scrollUser, java.awt.BorderLayout.CENTER);

        userControlButtonPane.setBackground(new java.awt.Color(255, 255, 255));

        btnSearchUser.setText(bundle.getString("UserIFrame.btnSearchUser.text")); // NOI18N
        btnSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchUserActionPerformed(evt);
            }
        });

        btnDeleteUser.setText(bundle.getString("UserIFrame.btnDeleteUser.text")); // NOI18N
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        btnTradeHistory.setText(bundle.getString("UserIFrame.btnTradeHistory.text")); // NOI18N
        btnTradeHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTradeHistoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout userControlButtonPaneLayout = new javax.swing.GroupLayout(userControlButtonPane);
        userControlButtonPane.setLayout(userControlButtonPaneLayout);
        userControlButtonPaneLayout.setHorizontalGroup(
            userControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userControlButtonPaneLayout.createSequentialGroup()
                .addContainerGap(558, Short.MAX_VALUE)
                .addComponent(btnSearchUser)
                .addGap(18, 18, 18)
                .addComponent(btnTradeHistory)
                .addGap(18, 18, 18)
                .addComponent(btnDeleteUser)
                .addGap(28, 28, 28))
        );
        userControlButtonPaneLayout.setVerticalGroup(
            userControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userControlButtonPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(userControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(btnTradeHistory)
                        .addComponent(btnSearchUser))
                    .addComponent(btnDeleteUser))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userPaneNorth.add(userControlButtonPane, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setViewportView(userPaneNorth);

        userSplitPane.setTopComponent(jScrollPane1);

        jScrollPane3.setPreferredSize(new java.awt.Dimension(100, 100));

        lblUserName.setText(bundle.getString("UserIFrame.lblUserName.text")); // NOI18N

        jLabel4.setText(bundle.getString("UserIFrame.jLabel4.text")); // NOI18N

        jLabel5.setText(bundle.getString("UserIFrame.jLabel5.text")); // NOI18N

        jLabel6.setText(bundle.getString("UserIFrame.jLabel6.text")); // NOI18N

        lblTel.setText(bundle.getString("UserIFrame.lblTel.text")); // NOI18N

        lblEmail.setText(bundle.getString("UserIFrame.lblEmail.text")); // NOI18N

        jLabel9.setText(bundle.getString("UserIFrame.jLabel9.text")); // NOI18N

        dtDateCreated.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dtDateCreated.setNothingAllowed(false);
    dtDateCreated.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
    dtDateCreated.setLocale(new java.util.Locale("vi", "", ""));
    dtDateCreated.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_PERIOD);

    javax.swing.GroupLayout userSearchFormPaneLayout = new javax.swing.GroupLayout(userSearchFormPane);
    userSearchFormPane.setLayout(userSearchFormPaneLayout);
    userSearchFormPaneLayout.setHorizontalGroup(
        userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, userSearchFormPaneLayout.createSequentialGroup()
            .addContainerGap(89, Short.MAX_VALUE)
            .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(userSearchFormPaneLayout.createSequentialGroup()
                    .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblEmail)
                        .addComponent(lblTel))
                    .addGap(62, 62, 62)
                    .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtTel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                        .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)))
                .addComponent(lblUserName))
            .addGap(75, 75, 75)
            .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel6)
                .addComponent(jLabel4)
                .addComponent(jLabel9))
            .addGap(30, 30, 30)
            .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(userSearchFormPaneLayout.createSequentialGroup()
                    .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel5)
                    .addGap(10, 10, 10)
                    .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(txtAddress)
                .addComponent(dtDateCreated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(134, 134, 134))
    );
    userSearchFormPaneLayout.setVerticalGroup(
        userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(userSearchFormPaneLayout.createSequentialGroup()
            .addGap(24, 24, 24)
            .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(userSearchFormPaneLayout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmail))
                    .addGap(41, 41, 41)
                    .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTel)
                        .addComponent(jLabel9)))
                .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUserName)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(userSearchFormPaneLayout.createSequentialGroup()
                    .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGap(32, 32, 32)
                    .addGroup(userSearchFormPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addGap(41, 41, 41)
                    .addComponent(dtDateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(42, Short.MAX_VALUE))
    );

    userSearchFormTabbedPane.addTab(bundle.getString("UserIFrame.userSearchFormPane.TabConstraints.tabTitle"), userSearchFormPane); // NOI18N

    jScrollPane3.setViewportView(userSearchFormTabbedPane);

    userSplitPane.setBottomComponent(jScrollPane3);

    userTabbedPane.addTab(bundle.getString("UserIFrame.userSplitPane.TabConstraints.tabTitle"), userSplitPane); // NOI18N

    getContentPane().add(userTabbedPane, java.awt.BorderLayout.CENTER);

    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    setBounds((screenSize.width-988)/2, (screenSize.height-658)/2, 988, 658);
    }// </editor-fold>//GEN-END:initComponents
    private String userNameSearch;
    private String firstNameSearch;
    private String lastNameSearch;
    private String addressSearch;
    private String telSearch;
    private String emailSearch;
    private Calendar startDateSearch;
    private Calendar endDateSearch;
    private void btnSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchUserActionPerformed
        // TODO add your handling code here:
        userNameSearch = txtUserName.getText();
        firstNameSearch = txtFirstName.getText();
        lastNameSearch = txtLastName.getText();
        addressSearch = txtAddress.getText();
        telSearch = txtTel.getText();
        emailSearch = txtEmail.getText();
        Period period = dtDateCreated.getSelectedPeriodSet().getFirstPeriod();
        startDateSearch = period.getStartDate();
        endDateSearch = period.getEndDate();
        dispatchLoadDataEvent();
    }//GEN-LAST:event_btnSearchUserActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa thông tin khách hàng ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchDeleteUserEvent();
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnTradeHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTradeHistoryActionPerformed
        // TODO add your handling code here:
        dispatchLoadPrintDataEvent();
    }//GEN-LAST:event_btnTradeHistoryActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnSearchUser;
    private javax.swing.JButton btnTradeHistory;
    private datechooser.beans.DateChooserCombo dtDateCreated;
    private javax.swing.JScrollPane scrollUser;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtTel;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

    private void prepareListenerRegistry() {
        addInternalFrameListener(userIFrameListener);
    }
    // <editor-fold defaultstate="collapsed" desc="saveState-restoreState-setControlStatus">
    private List lastUserTableSortKeys;
    private List<Long> lastUserTableSelectedIds;

    private void saveState() {
        lastUserTableSortKeys = tblUser.getRowSorter().getSortKeys();
        lastUserTableSelectedIds = getUserTableSelectedIds();
    }

    private void restoreState() {
        restoreTableState(tblUser, lastUserTableSortKeys, lastUserTableSelectedIds);
    }

    private void restoreTableState(JTable tbl, List lastSortKeys, List<Long> lastSelectedIds) {
        tbl.setEnabled(true);
        btnSearchUser.setEnabled(true);
        tbl.clearSelection();

        tbl.getRowSorter().setSortKeys(lastSortKeys);
        if (lastSelectedIds != null) {
            for (Long id : lastSelectedIds) {
                for (int row = 0; row < tbl.getRowCount(); row++) {
                    if (id.longValue() == ((Long) tbl.getValueAt(row, 0)).longValue()) {
                        tbl.addRowSelectionInterval(row, row);
                    }
                }
            }
        }
    }
    private static final int DISABLE_ALL = 0;
    private static final int DISABLE_CONTROL = 1;
    private static final int ENABLE_CONTROL = 2;

    private void setControlStatus(int status) {
        switch (status) {
            case DISABLE_ALL:
                tblUser.setEnabled(false);
                btnSearchUser.setEnabled(false);
                setEnableControl(false);
                break;
            case DISABLE_CONTROL:
                setEnableControl(false);
            case ENABLE_CONTROL:
                setEnableControl(true);
                break;
        }
    }

    private void setEnableControl(boolean enabled) {
        btnTradeHistory.setEnabled(enabled);
        btnDeleteUser.setEnabled(enabled);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="prepareTable">
    private JTable tblUser;
    private UserTableModel userTableModel;

    private void prepareTable() {
        prepareUserTable();
    }

    private void prepareUserTable() {
        tblUser = new javax.swing.JTable();
        tblUser.setAutoCreateRowSorter(true);
        tblUser.setFillsViewportHeight(true);
        tblUser.getTableHeader().setReorderingAllowed(false);
        scrollUser.setViewportView(tblUser);

        userTableModel = new UserTableModel();
        tblUser.setModel(userTableModel);

        tblUser.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblUser.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblUser.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblUser.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblUser.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblUser.getColumnModel().getColumn(5).setPreferredWidth(200);
        tblUser.getColumnModel().getColumn(6).setPreferredWidth(180);
        tblUser.getColumnModel().getColumn(7).setPreferredWidth(70);
        tblUser.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblUser.setDefaultRenderer(Long.class, cellRenderer);
        tblUser.setDefaultRenderer(String.class, cellRenderer);
        tblUser.setDefaultRenderer(Date.class, cellRenderer);

        tblUser.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblUser.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedRowIndex = tblUser.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        setControlStatus(DISABLE_CONTROL);
                        break;
                    default:
                        setControlStatus(ENABLE_CONTROL);
                }
            }
        });
        tblUser.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastUserTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }
// </editor-fold>

    private List<Long> getUserTableSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblUser.getSelectedRows()) {
            selectedIds.add((Long) tblUser.getValueAt(idx, 0));
        }
        return selectedIds;
    }

    private void prepareEndDateSearch() {
        startDateSearch = new GregorianCalendar();
        startDateSearch.set(GregorianCalendar.DATE, 1);
        endDateSearch = new GregorianCalendar();
        endDateSearch.set(GregorianCalendar.DATE, endDateSearch.getActualMaximum(GregorianCalendar.DATE));
        dtDateCreated.setSelection(new PeriodSet(
                new Period[]{new Period(startDateSearch, endDateSearch)}));
    }

    private String makeDBReferenceViolationMsg(List<String> violationNames) {
        if (violationNames == null) {
            return null;
        }
        StringBuilder msgBuilder = new StringBuilder("Các user:");
        for (String violationName : violationNames) {
            msgBuilder.append(violationName);
            msgBuilder.append("\n");
        }
        msgBuilder.append("Còn đơn hàng, tiếp tục xóa ?");
        return msgBuilder.toString();
    }
}
