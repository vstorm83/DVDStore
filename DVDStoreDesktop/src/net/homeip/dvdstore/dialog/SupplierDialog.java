/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Supplier.java
 *
 * Created on Mar 3, 2010, 3:31:55 PM
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
import net.homeip.dvdstore.listener.SupplierDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.SupplierTableModel;
import net.homeip.dvdstore.util.TextUtil;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class SupplierDialog extends javax.swing.JDialog {

    private SupplierTableModel supplierTableModel;
    private LoadDataModel<List<SupplierVO>> supplierListModel;
    private DataChangingModel changingModel;
    private SupplierDialogListener supplierDialogListener;
    private String lastTxtSupplierNameText;
    private List<Long> lastTblSupplierSelectedId;
    private List lastSortKeys;
    private DataChangingEvent dataChangingEvent;

    /** Creates new form Supplier */
    public SupplierDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setControlStatus(DISABLE_ALL);
        setupSupplierTable();

        prepareLoadData();
        prepareChangeData();

        supplierDialogListener = (SupplierDialogListener) ApplicationContextUtil.getApplicationContext().getBean("supplierDialogListener");
        addWindowListener(supplierDialogListener);
    }

    private void onChanging(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (changingModel.getStatus()) {
            case DataChangingModel.OK:
                dispatchChangedEvent();
                JOptionPane.showMessageDialog(this, "Thao tác thành công");
                return;
            case DataChangingModel.CONSTRAINT_VIOLATION:
                if (JOptionPane.showConfirmDialog(this, makeConstraintViolationMsg(),
                        "Xóa nhà cung cấp", JOptionPane.YES_NO_OPTION)
                        == JOptionPane.YES_OPTION) {
                    reDispatchChangingEvent(dataChangingEvent);
                    return;
                }
                break;
            case DataChangingModel.DUPLICATE:
                errorMsg = "Trùng dữ liệu";
                break;
            case DataChangingModel.VALIDATE_FAIL:
                errorMsg = "Tên nhà cung cấp từ 1 đến 50 ký tự";
                break;
            case DataChangingModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Lỗi kết nối Server";
                break;
            case DataChangingModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
                break;
        }
        setControlStatus(LAST_STATE);
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        }
    }

    private void onSupplierListRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (supplierListModel.getStatus()) {
            case LoadDataModel.OK:
                supplierTableModel.setRowData(supplierListModel.getData());
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
        supplierListModel = new LoadDataModel<List<SupplierVO>>();
        supplierListModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onSupplierListRetrieved(evt);
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
                tblSupplier.setEnabled(false);
                txtSupplierName.setEnabled(false);
                btnAddSupplier.setEnabled(false);
                btnDeleteSupplier.setEnabled(false);
                btnModifySupplier.setEnabled(false);
                break;
            case ADD_NEW:
                tblSupplier.setEnabled(true);
                txtSupplierName.setEnabled(true);
                txtSupplierName.setText("");
                lblSupplierId.setText("Thêm mới");
                btnAddSupplier.setEnabled(true);
                btnAddSupplier.setText("Đồng ý thêm");
                btnDeleteSupplier.setEnabled(false);
                btnModifySupplier.setEnabled(false);
                tblSupplier.clearSelection();
                break;
            case UPDATE_DELETE:
                tblSupplier.setEnabled(true);
                txtSupplierName.setEnabled(true);
                btnAddSupplier.setEnabled(true);
                btnAddSupplier.setText("Thêm mới");
                btnDeleteSupplier.setEnabled(true);
                btnModifySupplier.setEnabled(true);
                break;
            case DELETE:
                tblSupplier.setEnabled(true);
                txtSupplierName.setEnabled(false);
                txtSupplierName.setText("");
                lblSupplierId.setText("");
                btnAddSupplier.setEnabled(true);
                btnAddSupplier.setText("Thêm mới");
                btnDeleteSupplier.setEnabled(true);
                btnModifySupplier.setEnabled(false);
                break;
            default:
                restoreState();
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setupSupplierTable">
    private void setupSupplierTable() {
        supplierTableModel = new SupplierTableModel();
        tblSupplier.setModel(supplierTableModel);
        tblSupplier.getColumnModel().getColumn(0).setMaxWidth(100);
        tblSupplier.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblSupplier.setDefaultRenderer(Long.class, cellRenderer);
        tblSupplier.setDefaultRenderer(String.class, cellRenderer);
        tblSupplier.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblSupplier.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedIdx = tblSupplier.getSelectedRows();
                if (selectedIdx.length == 1) {
                    lblSupplierId.setText(String.valueOf(tblSupplier.getValueAt(
                            selectedIdx[0], 0)));
                    txtSupplierName.setText((String) tblSupplier.getValueAt(
                            selectedIdx[0], 1));
                    SupplierDialog.this.setControlStatus(UPDATE_DELETE);
                } else if (selectedIdx.length > 1) {
                    SupplierDialog.this.setControlStatus(DELETE);
                } else if (selectedIdx.length == 0) {
                    SupplierDialog.this.setControlStatus(ADD_NEW);
                }
            }
        });
        tblSupplier.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>

    public LoadDataModel<List<SupplierVO>> getSupplierListModel() {
        return supplierListModel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel lbItem = new javax.swing.JLabel();
        txtSupplierName = new javax.swing.JTextField();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        btnModifySupplier = new javax.swing.JButton();
        btnDeleteSupplier = new javax.swing.JButton();
        btnAddSupplier = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        lblSupplierId = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        scrollPaneSupplier = new javax.swing.JScrollPane();
        tblSupplier = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý nhà cung cấp");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quản lý nhà cung cấp"));

        lbItem.setText("Nhà cung cấp:");

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnModifySupplier.setText("Sửa");
        btnModifySupplier.setToolTipText("Chọn danh sách để sửa");
        btnModifySupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifySupplierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnModifySupplier, gridBagConstraints);

        btnDeleteSupplier.setText("Xóa");
        btnDeleteSupplier.setToolTipText("Chọn danh sách để xóa");
        btnDeleteSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSupplierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnDeleteSupplier, gridBagConstraints);

        btnAddSupplier.setText("Đồng ý thêm");
        btnAddSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSupplierActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnAddSupplier, gridBagConstraints);

        jLabel1.setText("Mã:");

        lblSupplierId.setText("Thêm mới");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbItem)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSupplierId)
                            .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblSupplierId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbItem))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Danh sách nhà cung cấp");

        tblSupplier.setAutoCreateRowSorter(true);
        tblSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSupplier.setFillsViewportHeight(true);
        tblSupplier.getTableHeader().setReorderingAllowed(false);
        scrollPaneSupplier.setViewportView(tblSupplier);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(56, 56, 56))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(30, 30, 30)
                .addComponent(scrollPaneSupplier, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifySupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifySupplierActionPerformed
        // TODO add your handling code here:
        dispatchChangingEvent(DataChangingEvent.MODIFY);
}//GEN-LAST:event_btnModifySupplierActionPerformed

    private void btnDeleteSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSupplierActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa nhà cung cấp ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangingEvent(DataChangingEvent.DELETE);
        }
}//GEN-LAST:event_btnDeleteSupplierActionPerformed

    private void btnAddSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSupplierActionPerformed
        // TODO add your handling code here:
        if (btnAddSupplier.getText().equals("Thêm mới")) {
            setControlStatus(ADD_NEW);
        } else {
            dispatchChangingEvent(DataChangingEvent.ADD);
        }
}//GEN-LAST:event_btnAddSupplierActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSupplier;
    private javax.swing.JButton btnDeleteSupplier;
    private javax.swing.JButton btnModifySupplier;
    private javax.swing.JLabel lblSupplierId;
    private javax.swing.JScrollPane scrollPaneSupplier;
    private javax.swing.JTable tblSupplier;
    private javax.swing.JTextField txtSupplierName;
    // End of variables declaration//GEN-END:variables

    private void dispatchChangingEvent(int action) {
        setControlStatus(DISABLE_ALL);
        dataChangingEvent = new DataChangingEvent(this);
        dataChangingEvent.setAction(action);
        dataChangingEvent.setDataChangingModel(changingModel);

        Object param;
        switch (action) {
            case DataChangingEvent.MODIFY:
                SupplierVO vo = new SupplierVO();
                vo.setSupplierId(Long.valueOf(lblSupplierId.getText()));
                vo.setSupplierName(TextUtil.normalize(txtSupplierName.getText()));
                param = vo;
                break;
            case DataChangingEvent.DELETE:
                param = getSelectedSupplierId();
                break;
            default:
                param = new SupplierVO();
                ((SupplierVO) param).setSupplierName(TextUtil.normalize(txtSupplierName.getText()));
        }

        dataChangingEvent.setParam(param);
        supplierDialogListener.onDataChanging(dataChangingEvent);
    }

    private void reDispatchChangingEvent(DataChangingEvent evt) {
        if (evt == null) {
            throw new IllegalArgumentException("can't dispatch null event");
        }
        evt.setIgnoreReference(true);
        supplierDialogListener.onDataChanging(evt);
    }

    private List<Long> getSelectedSupplierId() {
        List<Long> supplierIdList = new ArrayList<Long>();
        for (int row : tblSupplier.getSelectedRows()) {
            supplierIdList.add((Long) tblSupplier.getValueAt(row, 0));
        }
        return supplierIdList;
    }

    private void dispatchChangedEvent() {
        supplierDialogListener.onDataChanged(new DataChangedEvent(this));
    }

    private void saveState() {
        lastTblSupplierSelectedId = getSelectedSupplierId();
        lastSortKeys = tblSupplier.getRowSorter().getSortKeys();
        lastTxtSupplierNameText = txtSupplierName.getText();
    }

    private void restoreState() {
        tblSupplier.setEnabled(true);
        tblSupplier.clearSelection();

        tblSupplier.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastTblSupplierSelectedId) {
            for (int row = 0; row < tblSupplier.getRowCount(); row++) {
                if (id.longValue() == ((Long) tblSupplier.getValueAt(row, 0)).longValue()) {
                    tblSupplier.addRowSelectionInterval(row, row);
                }
            }
        }

        if (tblSupplier.getSelectedRow() == -1) {
            setControlStatus(ADD_NEW);
        }
        txtSupplierName.setText(lastTxtSupplierNameText);
    }

    private String makeConstraintViolationMsg() {
        StringBuilder supplierNameBuilder = new StringBuilder("Các nhà cung cấp: \n");
        for (String supplierName : changingModel.getViolationName()) {
            supplierNameBuilder.append(supplierName);
            supplierNameBuilder.append("\n");
        }
        supplierNameBuilder.append("còn phiếu đang cài đặt, tiếp tục xóa ?");
        return supplierNameBuilder.toString();
    }
}
