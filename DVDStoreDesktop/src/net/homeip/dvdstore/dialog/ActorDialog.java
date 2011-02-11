/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ActorDialog.java
 *
 * Created on Mar 3, 2010, 3:06:44 PM
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
import net.homeip.dvdstore.listener.ActorDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.swing.ActorTableModel;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.util.TextUtil;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class ActorDialog extends javax.swing.JDialog {

    private ActorTableModel actorTableModel;
    private LoadDataModel<List<ActorVO>> actorListModel;
    private DataChangingModel changingModel;
    private ActorDialogListener actorDialogListener;
    private String lastTxtActorNameText;
    private List<Long> lastTblActorSelectedId;
    private List lastSortKeys;

    /** Creates new form ActorDialog */
    public ActorDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();
        btnChooseActors.setVisible(false);

        setControlStatus(DISABLE_ALL);
        setupActorTable();

        prepareLoadData();
        prepareChangeData();

        actorDialogListener =
                (ActorDialogListener) ApplicationContextUtil.getApplicationContext().getBean(
                "actorDialogListener");
        addWindowListener(actorDialogListener);
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
                errorMsg = "Tên diễn viên từ 1 đến 50 ký tự";
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

    private void onActorListRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (actorListModel.getStatus()) {
            case LoadDataModel.OK:
                actorTableModel.setRowData(actorListModel.getData());
                break;
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Không kết nối được với Server";
                break;
            default:
                errorMsg = "Lỗi Server";
        }
        setControlStatus(LAST_STATE);
        preChooseActor();
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        }
    }

    private void prepareLoadData() {
        actorListModel = new LoadDataModel<List<ActorVO>>();
        actorListModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onActorListRetrieved(evt);
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
                tblActor.setEnabled(false);
                txtActorName.setEnabled(false);
                btnAddActor.setEnabled(false);
                btnDeleteActor.setEnabled(false);
                btnModifyActor.setEnabled(false);
                break;
            case ADD_NEW:
                tblActor.setEnabled(true);
                txtActorName.setEnabled(true);
                txtActorName.setText("");
                lblActorId.setText("Thêm mới");
                btnAddActor.setEnabled(true);
                btnAddActor.setText("Đồng ý thêm");
                btnDeleteActor.setEnabled(false);
                btnModifyActor.setEnabled(false);
                tblActor.clearSelection();
                break;
            case UPDATE_DELETE:
                tblActor.setEnabled(true);
                txtActorName.setEnabled(true);
                btnAddActor.setEnabled(true);
                btnAddActor.setText("Thêm mới");
                btnDeleteActor.setEnabled(true);
                btnModifyActor.setEnabled(true);
                break;
            case DELETE:
                tblActor.setEnabled(true);
                txtActorName.setEnabled(false);
                txtActorName.setText("");
                lblActorId.setText("");
                btnAddActor.setEnabled(true);
                btnAddActor.setText("Thêm mới");
                btnDeleteActor.setEnabled(true);
                btnModifyActor.setEnabled(false);
                break;
            default:
                restoreState();
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setupActorTable">
    private void setupActorTable() {
        actorTableModel = new ActorTableModel();
        tblActor.setModel(actorTableModel);
        tblActor.getColumnModel().getColumn(0).setMaxWidth(100);
        tblActor.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblActor.setDefaultRenderer(Long.class, cellRenderer);
        tblActor.setDefaultRenderer(String.class, cellRenderer);
        tblActor.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblActor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedIdx = tblActor.getSelectedRows();
                if (selectedIdx.length == 1) {
                    lblActorId.setText(String.valueOf(tblActor.getValueAt(
                            selectedIdx[0], 0)));
                    txtActorName.setText((String) tblActor.getValueAt(
                            selectedIdx[0], 1));
                    ActorDialog.this.setControlStatus(UPDATE_DELETE);
                } else if (selectedIdx.length > 1) {
                    ActorDialog.this.setControlStatus(DELETE);
                } else if (selectedIdx.length == 0) {
                    ActorDialog.this.setControlStatus(ADD_NEW);
                }
            }
        });
        tblActor.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>

    public LoadDataModel<List<ActorVO>> getActorListModel() {
        return actorListModel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel lbItem = new javax.swing.JLabel();
        txtActorName = new javax.swing.JTextField();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        btnModifyActor = new javax.swing.JButton();
        btnDeleteActor = new javax.swing.JButton();
        btnAddActor = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        lblActorId = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        scrollPaneActor = new javax.swing.JScrollPane();
        tblActor = new javax.swing.JTable();
        btnChooseActors = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý diễn viên");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quản lý diễn viên"));

        lbItem.setText("Diễn viên:");

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnModifyActor.setText("Sửa");
        btnModifyActor.setToolTipText("Chọn danh sách để sửa");
        btnModifyActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyActorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnModifyActor, gridBagConstraints);

        btnDeleteActor.setText("Xóa");
        btnDeleteActor.setToolTipText("Chọn danh sách để xóa");
        btnDeleteActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnDeleteActor, gridBagConstraints);

        btnAddActor.setText("Đồng ý thêm");
        btnAddActor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnAddActor, gridBagConstraints);

        jLabel1.setText("Mã:");

        lblActorId.setText("Thêm mới");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(48, 48, 48)
                                .addComponent(lblActorId))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbItem)
                                .addGap(18, 18, 18)
                                .addComponent(txtActorName, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblActorId)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtActorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbItem))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18));
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Danh sách diễn viên");

        tblActor.setAutoCreateRowSorter(true);
        tblActor.setModel(new javax.swing.table.DefaultTableModel(
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
        tblActor.setFillsViewportHeight(true);
        tblActor.getTableHeader().setReorderingAllowed(false);
        scrollPaneActor.setViewportView(tblActor);

        btnChooseActors.setText("Chọn");
        btnChooseActors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActorsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(271, Short.MAX_VALUE)
                        .addComponent(btnChooseActors))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPaneActor, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(btnChooseActors)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneActor, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModifyActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyActorActionPerformed
        // TODO add your handling code here:
        dispatchChangingEvent(DataChangingEvent.MODIFY);
}//GEN-LAST:event_btnModifyActorActionPerformed

    private void btnDeleteActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActorActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa diễn viên ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangingEvent(DataChangingEvent.DELETE);
        }
}//GEN-LAST:event_btnDeleteActorActionPerformed

    private void btnAddActorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActorActionPerformed
        // TODO add your handling code here:
        if (btnAddActor.getText().equals("Thêm mới")) {
            setControlStatus(ADD_NEW);
        } else {
            dispatchChangingEvent(DataChangingEvent.ADD);
        }
}//GEN-LAST:event_btnAddActorActionPerformed
// <editor-fold defaultstate="collapsed" desc="chooseActors">
    private int chooseActorsAction = DIALOG_CLOSE;
    private List<ActorVO> choosenActorVOs;
    private List<ActorVO> preChoosenActorVOs;
    public static final int DIALOG_CLOSE = 0;
    public static final int DIALOG_OK = 1;

    public List<ActorVO> getChoosenActorVOs() {
        return choosenActorVOs;
    }

    public int showActorDialog(List<ActorVO> actorVOs) {
        if (actorVOs != null) {
            preChoosenActorVOs = actorVOs;
        }
        btnChooseActors.setVisible(true);
        setVisible(true);
        return chooseActorsAction;
    }// </editor-fold>

    private void btnChooseActorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActorsActionPerformed
        // TODO add your handling code here:
        choosenActorVOs = new ArrayList<ActorVO>(tblActor.getSelectedRows().length);
        ActorVO actorVO;
        for (int idx : tblActor.getSelectedRows()) {
            actorVO = new ActorVO();
            actorVO.setActorId((Long) tblActor.getValueAt(idx, 0));
            actorVO.setActorName((String) tblActor.getValueAt(idx, 1));
            choosenActorVOs.add(actorVO);
        }
        chooseActorsAction = DIALOG_OK;
        dispose();
    }//GEN-LAST:event_btnChooseActorsActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddActor;
    private javax.swing.JButton btnChooseActors;
    private javax.swing.JButton btnDeleteActor;
    private javax.swing.JButton btnModifyActor;
    private javax.swing.JLabel lblActorId;
    private javax.swing.JScrollPane scrollPaneActor;
    private javax.swing.JTable tblActor;
    private javax.swing.JTextField txtActorName;
    // End of variables declaration//GEN-END:variables

    private void dispatchChangingEvent(int action) {
        setControlStatus(DISABLE_ALL);
        DataChangingEvent dataChangingEvent = new DataChangingEvent(this);
        dataChangingEvent.setAction(action);
        dataChangingEvent.setDataChangingModel(changingModel);

        Object param;
        switch (action) {
            case DataChangingEvent.MODIFY:
                ActorVO vo = new ActorVO();
                vo.setActorId(Long.valueOf(lblActorId.getText()));
                vo.setActorName(TextUtil.normalize(txtActorName.getText()));
                param = vo;
                break;
            case DataChangingEvent.DELETE:
                param = getSelectedActorId();
                break;
            default:
                param = new ActorVO();
                ((ActorVO) param).setActorName(TextUtil.normalize(txtActorName.getText()));
        }

        dataChangingEvent.setParam(param);
        actorDialogListener.onDataChanging(dataChangingEvent);
    }

    private List<Long> getSelectedActorId() {
        List<Long> actorIdList = new ArrayList<Long>();
        for (int row : tblActor.getSelectedRows()) {
            actorIdList.add((Long) tblActor.getValueAt(row, 0));
        }
        return actorIdList;
    }

    private void dispatchChangedEvent() {
        actorDialogListener.onDataChanged(new DataChangedEvent(this));
    }

    private void saveState() {
        lastTblActorSelectedId = getSelectedActorId();
        lastSortKeys = tblActor.getRowSorter().getSortKeys();
        lastTxtActorNameText = txtActorName.getText();
    }

    private void restoreState() {
        tblActor.setEnabled(true);
        tblActor.clearSelection();

        tblActor.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastTblActorSelectedId) {
            for (int row = 0; row < tblActor.getRowCount(); row++) {
                if (id.longValue() == ((Long) tblActor.getValueAt(row, 0)).longValue()) {
                    tblActor.addRowSelectionInterval(row, row);
                }
            }
        }

        if (tblActor.getSelectedRow() == -1) {
            setControlStatus(ADD_NEW);
        }
        txtActorName.setText(lastTxtActorNameText);
    }

    private void preChooseActor() {
        if (preChoosenActorVOs != null) {
            for (ActorVO actorVO : preChoosenActorVOs) {
                for (int row = 0; row < tblActor.getRowCount(); row++) {
                    if (actorVO.getActorId().longValue()
                            == ((Long) tblActor.getValueAt(row, 0)).longValue()) {
                        tblActor.addRowSelectionInterval(row, row);
                    }
                }
            }
        }
    }
}
