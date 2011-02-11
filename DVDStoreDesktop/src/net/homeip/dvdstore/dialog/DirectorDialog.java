/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DirectorDialog.java
 *
 * Created on Mar 2, 2010, 10:23:47 PM
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
import net.homeip.dvdstore.listener.DirectorDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.DirectorTableModel;
import net.homeip.dvdstore.util.TextUtil;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorDialog extends javax.swing.JDialog {

    private DirectorTableModel directorTableModel;
    private LoadDataModel<List<DirectorVO>> directorListModel;
    private DataChangingModel changingModel;
    private DirectorDialogListener directorDialogListener;
    private String lastTxtDirectorNameText;
    private List<Long> lastTblDirectorSelectedId;
    private List lastSortKeys;

    /** Creates new form DirectorDialog */
    public DirectorDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setControlStatus(DISABLE_ALL);
        setupDirectorTable();

        prepareLoadData();
        prepareChangeData();

        directorDialogListener = (DirectorDialogListener) ApplicationContextUtil.getApplicationContext().getBean("directorDialogListener");
        addWindowListener(directorDialogListener);
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
                errorMsg = "Tên đạo diễn từ 1 đến 50 ký tự";
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

    private void onDirectorListRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (directorListModel.getStatus()) {
            case LoadDataModel.OK:
                directorTableModel.setRowData(directorListModel.getData());
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
        directorListModel = new LoadDataModel<List<DirectorVO>>();
        directorListModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onDirectorListRetrieved(evt);
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
                tblDirector.setEnabled(false);
                txtDirectorName.setEnabled(false);
                btnAddDirector.setEnabled(false);
                btnDeleteDirector.setEnabled(false);
                btnModifyDirector.setEnabled(false);
                break;
            case ADD_NEW:
                tblDirector.setEnabled(true);
                txtDirectorName.setEnabled(true);
                txtDirectorName.setText("");
                lblDirectorId.setText("Thêm mới");
                btnAddDirector.setEnabled(true);
                btnAddDirector.setText("Đồng ý thêm");
                btnDeleteDirector.setEnabled(false);
                btnModifyDirector.setEnabled(false);
                tblDirector.clearSelection();
                break;
            case UPDATE_DELETE:
                tblDirector.setEnabled(true);
                txtDirectorName.setEnabled(true);
                btnAddDirector.setEnabled(true);
                btnAddDirector.setText("Thêm mới");
                btnDeleteDirector.setEnabled(true);
                btnModifyDirector.setEnabled(true);
                break;
            case DELETE:
                tblDirector.setEnabled(true);
                txtDirectorName.setEnabled(false);
                txtDirectorName.setText("");
                lblDirectorId.setText("");
                btnAddDirector.setEnabled(true);
                btnAddDirector.setText("Thêm mới");
                btnDeleteDirector.setEnabled(true);
                btnModifyDirector.setEnabled(false);
                break;
            default:
                restoreState();
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setupDirectorTable">
    private void setupDirectorTable() {
        directorTableModel = new DirectorTableModel();
        tblDirector.setModel(directorTableModel);
        tblDirector.getColumnModel().getColumn(0).setMaxWidth(100);
        tblDirector.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblDirector.setDefaultRenderer(Long.class, cellRenderer);
        tblDirector.setDefaultRenderer(String.class, cellRenderer);
        tblDirector.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblDirector.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedIdx = tblDirector.getSelectedRows();
                if (selectedIdx.length == 1) {
                    lblDirectorId.setText(String.valueOf(tblDirector.getValueAt(
                            selectedIdx[0], 0)));
                    txtDirectorName.setText((String) tblDirector.getValueAt(
                            selectedIdx[0], 1));
                    DirectorDialog.this.setControlStatus(UPDATE_DELETE);
                } else if (selectedIdx.length > 1) {
                    DirectorDialog.this.setControlStatus(DELETE);
                } else if (selectedIdx.length == 0) {
                    DirectorDialog.this.setControlStatus(ADD_NEW);
                }
            }
        });
        tblDirector.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>

    public LoadDataModel<List<DirectorVO>> getDirectorListModel() {
        return directorListModel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel lbItem = new javax.swing.JLabel();
        txtDirectorName = new javax.swing.JTextField();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        btnModifyDirector = new javax.swing.JButton();
        btnDeleteDirector = new javax.swing.JButton();
        btnAddDirector = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        lblDirectorId = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        scrollPaneMovieCatgory = new javax.swing.JScrollPane();
        tblDirector = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý đạo diễn");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quản lý đạo diễn"));

        lbItem.setText("Đạo diễn:");

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnModifyDirector.setText("Sửa");
        btnModifyDirector.setToolTipText("Chọn danh sách để sửa");
        btnModifyDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyDirectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnModifyDirector, gridBagConstraints);

        btnDeleteDirector.setText("Xóa");
        btnDeleteDirector.setToolTipText("Chọn danh sách để xóa");
        btnDeleteDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDirectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnDeleteDirector, gridBagConstraints);

        btnAddDirector.setText("Đồng ý thêm");
        btnAddDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDirectorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnAddDirector, gridBagConstraints);

        jLabel1.setText("Mã:");

        lblDirectorId.setText("Thêm mới");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(48, 48, 48)
                                .addComponent(lblDirectorId))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbItem)
                                .addGap(18, 18, 18)
                                .addComponent(txtDirectorName, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDirectorId)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDirectorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbItem))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Danh sách đạo diễn");

        tblDirector.setAutoCreateRowSorter(true);
        tblDirector.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDirector.setFillsViewportHeight(true);
        tblDirector.getTableHeader().setReorderingAllowed(false);
        scrollPaneMovieCatgory.setViewportView(tblDirector);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(scrollPaneMovieCatgory, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
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
                .addComponent(scrollPaneMovieCatgory, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyDirectorActionPerformed
        // TODO add your handling code here:
        dispatchChangingEvent(DataChangingEvent.MODIFY);
}//GEN-LAST:event_btnModifyDirectorActionPerformed

    private void btnDeleteDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDirectorActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa đạo diễn ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangingEvent(DataChangingEvent.DELETE);
        }
}//GEN-LAST:event_btnDeleteDirectorActionPerformed

    private void btnAddDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDirectorActionPerformed
        // TODO add your handling code here:
        if (btnAddDirector.getText().equals("Thêm mới")) {
            setControlStatus(ADD_NEW);
        } else {
            dispatchChangingEvent(DataChangingEvent.ADD);
        }
}//GEN-LAST:event_btnAddDirectorActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDirector;
    private javax.swing.JButton btnDeleteDirector;
    private javax.swing.JButton btnModifyDirector;
    private javax.swing.JLabel lblDirectorId;
    private javax.swing.JScrollPane scrollPaneMovieCatgory;
    private javax.swing.JTable tblDirector;
    private javax.swing.JTextField txtDirectorName;
    // End of variables declaration//GEN-END:variables

    private void dispatchChangingEvent(int action) {
        setControlStatus(DISABLE_ALL);
        DataChangingEvent dataChangingEvent = new DataChangingEvent(this);
        dataChangingEvent.setAction(action);
        dataChangingEvent.setDataChangingModel(changingModel);

        Object param;
        switch (action) {
            case DataChangingEvent.MODIFY:
                DirectorVO vo = new DirectorVO();
                vo.setDirectorId(Long.valueOf(lblDirectorId.getText()));
                vo.setDirectorName(TextUtil.normalize(txtDirectorName.getText()));
                param = vo;
                break;
            case DataChangingEvent.DELETE:
                param = getSelectedDirectorId();
                break;
            default:
                param = new DirectorVO();
                ((DirectorVO) param).setDirectorName(TextUtil.normalize(txtDirectorName.getText()));
        }

        dataChangingEvent.setParam(param);
        directorDialogListener.onDataChanging(dataChangingEvent);
    }

    private List<Long> getSelectedDirectorId() {
        List<Long> directorIdList = new ArrayList<Long>();
        for (int row : tblDirector.getSelectedRows()) {
            directorIdList.add((Long) tblDirector.getValueAt(row, 0));
        }
        return directorIdList;
    }

    private void dispatchChangedEvent() {
        directorDialogListener.onDataChanged(new DataChangedEvent(this));
    }

    private void saveState() {
        lastTblDirectorSelectedId = getSelectedDirectorId();
        lastSortKeys = tblDirector.getRowSorter().getSortKeys();
        lastTxtDirectorNameText = txtDirectorName.getText();
    }

    private void restoreState() {
        tblDirector.setEnabled(true);
        tblDirector.clearSelection();

        tblDirector.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastTblDirectorSelectedId) {
            for (int row = 0; row < tblDirector.getRowCount(); row++) {
                if (id.longValue() == ((Long) tblDirector.getValueAt(row, 0)).longValue()) {
                    tblDirector.addRowSelectionInterval(row, row);
                }
            }
        }

        if (tblDirector.getSelectedRow() == -1) {
            setControlStatus(ADD_NEW);
        }
        txtDirectorName.setText(lastTxtDirectorNameText);
    }
}
