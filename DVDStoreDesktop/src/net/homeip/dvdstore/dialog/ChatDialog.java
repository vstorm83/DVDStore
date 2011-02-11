/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatDialog.java
 *
 * Created on Mar 3, 2010, 3:15:47 PM
 */
package net.homeip.dvdstore.dialog;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.ChatDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.ChatTableModel;
import net.homeip.dvdstore.util.TextUtil;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ChatDialog extends javax.swing.JDialog {

    private ChatTableModel chatTableModel;
    private LoadDataModel<List<ChatVO>> chatListModel;
    private DataChangingModel changingModel;
    private ChatDialogListener chatDialogListener;
    private String lastTxtChatNameText;
    private List<Long> lastTblChatSelectedId;
    private List lastSortKeys;

    /** Creates new form ChatDialog */
    public ChatDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setControlStatus(DISABLE_ALL);
        setupChatTable();

        prepareLoadData();
        prepareChangeData();

        chatDialogListener = (ChatDialogListener) ApplicationContextUtil.getApplicationContext().getBean("chatDialogListener");
        addWindowListener(chatDialogListener);
    }

    private void onChanging(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (changingModel.getStatus()) {
            case DataChangingModel.OK:
                dispatchChangedEvent();
                JOptionPane.showMessageDialog(this, "Thao tác thành công");
                return;
            case DataChangingModel.DUPLICATE:
                errorMsg = "Trùng dữ liệu";
                break;
            case DataChangingModel.VALIDATE_FAIL:
                errorMsg = "Nick chat từ 1 đến 20 ký tự";
                break;
            case DataChangingModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Lỗi kết nối Server";
                break;
            case DataChangingModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        setControlStatus(LAST_STATE);
        JOptionPane.showMessageDialog(this, errorMsg);
    }

    private void onChatListRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (chatListModel.getStatus()) {
            case LoadDataModel.OK:                
                chatTableModel.setRowData(chatListModel.getData());
                break;
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Không kết nối được với Server";
                break;
            default:
                errorMsg = "Lỗi Server";
        }
        setControlStatus(LAST_STATE);
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        }
    }

    private void prepareLoadData() {
        chatListModel = new LoadDataModel<List<ChatVO>>();
        chatListModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onChatListRetrieved(evt);
            }
        });
    }

    private void prepareChangeData() {
        changingModel = new DataChangingModel();
        changingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onChanging(evt);
            }
        });
    }
    // <editor-fold defaultstate="collapsed" desc="setControlStatus">
    private final int DISABLE_ALL = 0;
    private final int ADD_NEW = 1;
    private final int UPDATE_DELETE = 2;
    private final int DELETE = 3;
    private final int LAST_STATE = 4;

    private void setControlStatus(int controlStatus) {
        switch (controlStatus) {
            case DISABLE_ALL:
                saveState();
                tblChat.setEnabled(false);
                txtChatName.setEnabled(false);
                btnAddChat.setEnabled(false);
                btnDeleteChat.setEnabled(false);
                btnModifyChat.setEnabled(false);
                break;
            case ADD_NEW:
                tblChat.setEnabled(true);
                txtChatName.setEnabled(true);
                txtChatName.setText("");
                lblChatId.setText("Thêm mới");
                btnAddChat.setEnabled(true);
                btnAddChat.setText("Đồng ý thêm");
                btnDeleteChat.setEnabled(false);
                btnModifyChat.setEnabled(false);
                tblChat.clearSelection();
                break;
            case UPDATE_DELETE:
                tblChat.setEnabled(true);
                txtChatName.setEnabled(true);
                btnAddChat.setEnabled(true);
                btnAddChat.setText("Thêm mới");
                btnDeleteChat.setEnabled(true);
                btnModifyChat.setEnabled(true);
                break;
            case DELETE:
                tblChat.setEnabled(true);
                txtChatName.setEnabled(false);
                txtChatName.setText("");
                lblChatId.setText("");
                btnAddChat.setEnabled(true);
                btnAddChat.setText("Thêm mới");
                btnDeleteChat.setEnabled(true);
                btnModifyChat.setEnabled(false);
                break;
            default:
                restoreState();
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setupChatTable">
    private void setupChatTable() {
        chatTableModel = new ChatTableModel();
        tblChat.setModel(chatTableModel);
        tblChat.getColumnModel().getColumn(0).setMaxWidth(100);
        tblChat.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblChat.setDefaultRenderer(Long.class, cellRenderer);
        tblChat.setDefaultRenderer(String.class, cellRenderer);
        tblChat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblChat.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedIdx = tblChat.getSelectedRows();
                if (selectedIdx.length == 1) {
                    lblChatId.setText(String.valueOf(tblChat.getValueAt(
                            selectedIdx[0], 0)));
                    txtChatName.setText((String) tblChat.getValueAt(
                            selectedIdx[0], 1));
                    ChatDialog.this.setControlStatus(UPDATE_DELETE);
                } else if (selectedIdx.length > 1) {
                    ChatDialog.this.setControlStatus(DELETE);
                } else if (selectedIdx.length == 0) {
                    ChatDialog.this.setControlStatus(ADD_NEW);
                }
            }
        });
        tblChat.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>

    public LoadDataModel<List<ChatVO>> getChatListModel() {
        return chatListModel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        scrollPaneChat = new javax.swing.JScrollPane();
        tblChat = new javax.swing.JTable();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel lbItem = new javax.swing.JLabel();
        txtChatName = new javax.swing.JTextField();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        btnModifyChat = new javax.swing.JButton();
        btnDeleteChat = new javax.swing.JButton();
        btnAddChat = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        lblChatId = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý nick chat");
        setResizable(false);

        tblChat.setAutoCreateRowSorter(true);
        tblChat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblChat.setFillsViewportHeight(true);
        tblChat.getTableHeader().setReorderingAllowed(false);
        scrollPaneChat.setViewportView(tblChat);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quản lý nick chat"));

        lbItem.setText("Nick name:");

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnModifyChat.setText("Sửa");
        btnModifyChat.setToolTipText("Chọn danh sách để sửa");
        btnModifyChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyChatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnModifyChat, gridBagConstraints);

        btnDeleteChat.setText("Xóa");
        btnDeleteChat.setToolTipText("Chọn danh sách để xóa");
        btnDeleteChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteChatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnDeleteChat, gridBagConstraints);

        btnAddChat.setText("Đồng ý thêm");
        btnAddChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddChatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnAddChat, gridBagConstraints);

        jLabel1.setText("Mã:");

        lblChatId.setText("Thêm mới");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(48, 48, 48)
                                .addComponent(lblChatId))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbItem)
                                .addGap(18, 18, 18)
                                .addComponent(txtChatName, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChatId)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtChatName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbItem))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Danh sách nick");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneChat, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(30, 30, 30)
                .addComponent(scrollPaneChat, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyChatActionPerformed
        // TODO add your handling code here:
        dispatchChangingEvent(DataChangingEvent.MODIFY);
}//GEN-LAST:event_btnModifyChatActionPerformed

    private void btnDeleteChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteChatActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa nick ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangingEvent(DataChangingEvent.DELETE);
        }
}//GEN-LAST:event_btnDeleteChatActionPerformed

    private void btnAddChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddChatActionPerformed
        // TODO add your handling code here:
        if (btnAddChat.getText().equals("Thêm mới")) {
            setControlStatus(ADD_NEW);
        } else {
            dispatchChangingEvent(DataChangingEvent.ADD);
        }
}//GEN-LAST:event_btnAddChatActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddChat;
    private javax.swing.JButton btnDeleteChat;
    private javax.swing.JButton btnModifyChat;
    private javax.swing.JLabel lblChatId;
    private javax.swing.JScrollPane scrollPaneChat;
    private javax.swing.JTable tblChat;
    private javax.swing.JTextField txtChatName;
    // End of variables declaration//GEN-END:variables

    private void dispatchChangingEvent(int action) {
        setControlStatus(DISABLE_ALL);
        DataChangingEvent dataChangingEvent = new DataChangingEvent(this);
        dataChangingEvent.setAction(action);
        dataChangingEvent.setDataChangingModel(changingModel);

        Object param;
        switch (action) {
            case DataChangingEvent.MODIFY:
                ChatVO vo = new ChatVO();
                vo.setChatId(Long.valueOf(lblChatId.getText()));
                vo.setChatName(TextUtil.normalize(txtChatName.getText()));
                param = vo;
                break;
            case DataChangingEvent.DELETE:
                param = getSelectedChatId();
                break;
            default:
                param = new ChatVO();
                ((ChatVO) param).setChatName(TextUtil.normalize(txtChatName.getText()));
        }

        dataChangingEvent.setParam(param);
        chatDialogListener.onDataChanging(dataChangingEvent);
    }

    private List<Long> getSelectedChatId() {
        List<Long> chatIdList = new ArrayList<Long>();
        for (int row : tblChat.getSelectedRows()) {
            chatIdList.add((Long) tblChat.getValueAt(row, 0));
        }
        return chatIdList;
    }

    private void dispatchChangedEvent() {
        chatDialogListener.onDataChanged(new DataChangedEvent(this));
    }

    private void saveState() {
        lastTblChatSelectedId = getSelectedChatId();
        lastSortKeys = tblChat.getRowSorter().getSortKeys();
        lastTxtChatNameText = txtChatName.getText();
    }

    private void restoreState() {
        tblChat.setEnabled(true);
        tblChat.clearSelection();

        tblChat.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastTblChatSelectedId) {
            for (int row = 0; row < tblChat.getRowCount(); row++) {
                if (id.longValue() == ((Long) tblChat.getValueAt(row, 0)).longValue()) {
                    tblChat.addRowSelectionInterval(row, row);
                }
            }
        }

        if (tblChat.getSelectedRow() == -1) {
            setControlStatus(ADD_NEW);
        }
        txtChatName.setText(lastTxtChatNameText);
    }
}
