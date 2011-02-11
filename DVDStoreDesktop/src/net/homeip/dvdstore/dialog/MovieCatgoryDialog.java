/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MovieCatgoryDialog.java
 *
 * Created on Mar 1, 2010, 3:37:12 PM
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
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.MovieCatgoryDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.MovieCatgoryTableModel;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryDialog extends javax.swing.JDialog {

    private MovieCatgoryTableModel movCatTableModel;
    private LoadDataModel<List<MovieCatgoryVO>> movCatListModel;
    private DataChangingModel changingModel;
    private MovieCatgoryDialogListener movieCatgoryDialogListener;
    private String lastTxtMovCatNameText;
    private List<Long> lastTblMovCatSelectedId;
    private List lastSortKeys;

    /** Creates new form MovieCatgoryDialog */
    public MovieCatgoryDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        setControlStatus(DISABLE_ALL);
        setupMovieCatgoryTable();

        prepareLoadData();
        prepareChangeData();

        movieCatgoryDialogListener = (MovieCatgoryDialogListener) ApplicationContextUtil.getApplicationContext().getBean("movieCatgoryDialogListener");
        addWindowListener(movieCatgoryDialogListener);
    }

    private void onChanging(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (changingModel.getStatus()) {
            case DataChangingModel.OK:
                dispatchChangedEvent();
                JOptionPane.showMessageDialog(this, "Thao tác thành công");
                return;
            case DataChangingModel.CONSTRAINT_VIOLATION:
                errorMsg = makeDBReferenceViolationMsg();
                break;
            case DataChangingModel.DUPLICATE:
                errorMsg = "Trùng dữ liệu";
                break;
            case DataChangingModel.VALIDATE_FAIL:
                errorMsg = "Tên loại phim từ 1 đến 50 ký tự";
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

    private void onMovCatListRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (movCatListModel.getStatus()) {
            case LoadDataModel.OK:
                movCatTableModel.setRowData(movCatListModel.getData());
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
        movCatListModel = new LoadDataModel<List<MovieCatgoryVO>>();
        movCatListModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onMovCatListRetrieved(evt);
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
                tblMovCat.setEnabled(false);
                txtMovCatName.setEnabled(false);
                btnAddMovCat.setEnabled(false);
                btnDeleteMovCat.setEnabled(false);
                btnModifyMovCat.setEnabled(false);
                break;
            case ADD_NEW:
                tblMovCat.setEnabled(true);
                txtMovCatName.setEnabled(true);
                txtMovCatName.setText("");
                lblMovCatId.setText("Thêm mới");
                btnAddMovCat.setEnabled(true);
                btnAddMovCat.setText("Đồng ý thêm");
                btnDeleteMovCat.setEnabled(false);
                btnModifyMovCat.setEnabled(false);
                tblMovCat.clearSelection();
                break;
            case UPDATE_DELETE:
                tblMovCat.setEnabled(true);
                txtMovCatName.setEnabled(true);
                btnAddMovCat.setEnabled(true);
                btnAddMovCat.setText("Thêm mới");
                btnDeleteMovCat.setEnabled(true);
                btnModifyMovCat.setEnabled(true);
                break;
            case DELETE:
                tblMovCat.setEnabled(true);
                txtMovCatName.setEnabled(false);
                txtMovCatName.setText("");
                lblMovCatId.setText("");
                btnAddMovCat.setEnabled(true);
                btnAddMovCat.setText("Thêm mới");
                btnDeleteMovCat.setEnabled(true);
                btnModifyMovCat.setEnabled(false);
                break;
            default:
                restoreState();
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setupMovieCatgoryTable">
    private void setupMovieCatgoryTable() {
        movCatTableModel = new MovieCatgoryTableModel();
        tblMovCat.setModel(movCatTableModel);
        tblMovCat.getColumnModel().getColumn(0).setMaxWidth(100);
        tblMovCat.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblMovCat.setDefaultRenderer(Long.class, cellRenderer);
        tblMovCat.setDefaultRenderer(String.class, cellRenderer);
        tblMovCat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblMovCat.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                int[] selectedIdx = tblMovCat.getSelectedRows();
                if (selectedIdx.length == 1) {
                    lblMovCatId.setText(String.valueOf(tblMovCat.getValueAt(
                            selectedIdx[0], 0)));
                    txtMovCatName.setText((String) tblMovCat.getValueAt(
                            selectedIdx[0], 1));
                    MovieCatgoryDialog.this.setControlStatus(UPDATE_DELETE);
                } else if (selectedIdx.length > 1) {
                    MovieCatgoryDialog.this.setControlStatus(DELETE);
                } else if (selectedIdx.length == 0) {
                    MovieCatgoryDialog.this.setControlStatus(ADD_NEW);
                }
            }
        });

        tblMovCat.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>

    public LoadDataModel<List<MovieCatgoryVO>> getMovCatListModel() {
        return movCatListModel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel lbItem = new javax.swing.JLabel();
        txtMovCatName = new javax.swing.JTextField();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        btnModifyMovCat = new javax.swing.JButton();
        btnDeleteMovCat = new javax.swing.JButton();
        btnAddMovCat = new javax.swing.JButton();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        lblMovCatId = new javax.swing.JLabel();
        scrollPaneMovieCatgory = new javax.swing.JScrollPane();
        tblMovCat = new javax.swing.JTable();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản lý loại phim");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Quản lý loại phim"));

        lbItem.setText("Loại phim:");

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnModifyMovCat.setText("Sửa");
        btnModifyMovCat.setToolTipText("Chọn danh sách để sửa");
        btnModifyMovCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModifyMovCatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnModifyMovCat, gridBagConstraints);

        btnDeleteMovCat.setText("Xóa");
        btnDeleteMovCat.setToolTipText("Chọn danh sách để xóa");
        btnDeleteMovCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMovCatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnDeleteMovCat, gridBagConstraints);

        btnAddMovCat.setText("Đồng ý thêm");
        btnAddMovCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMovCatActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(btnAddMovCat, gridBagConstraints);

        jLabel1.setText("Mã:");

        lblMovCatId.setText("Thêm mới");

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
                                .addComponent(lblMovCatId))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbItem)
                                .addGap(18, 18, 18)
                                .addComponent(txtMovCatName, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMovCatId)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMovCatName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbItem))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        tblMovCat.setAutoCreateRowSorter(true);
        tblMovCat.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMovCat.setFillsViewportHeight(true);
        tblMovCat.getTableHeader().setReorderingAllowed(false);
        scrollPaneMovieCatgory.setViewportView(tblMovCat);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 204));
        jLabel3.setText("Danh sách loại phim");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollPaneMovieCatgory, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(77, 77, 77))))
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

    private void btnAddMovCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMovCatActionPerformed
        // TODO add your handling code here:
        if (btnAddMovCat.getText().equals("Thêm mới")) {
            setControlStatus(ADD_NEW);
        } else {
            dispatchChangingEvent(DataChangingEvent.ADD);
        }
    }//GEN-LAST:event_btnAddMovCatActionPerformed

    private void btnModifyMovCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModifyMovCatActionPerformed
        // TODO add your handling code here:
        dispatchChangingEvent(DataChangingEvent.MODIFY);
    }//GEN-LAST:event_btnModifyMovCatActionPerformed

    private void btnDeleteMovCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMovCatActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Xóa loại phim ?", "Xóa", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchChangingEvent(DataChangingEvent.DELETE);
        }
    }//GEN-LAST:event_btnDeleteMovCatActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMovCat;
    private javax.swing.JButton btnDeleteMovCat;
    private javax.swing.JButton btnModifyMovCat;
    private javax.swing.JLabel lblMovCatId;
    private javax.swing.JScrollPane scrollPaneMovieCatgory;
    private javax.swing.JTable tblMovCat;
    private javax.swing.JTextField txtMovCatName;
    // End of variables declaration//GEN-END:variables

    private String makeDBReferenceViolationMsg() {
        StringBuilder movCatNameBuilder = new StringBuilder("Các loại phim: \n");
        for (String movCatName : changingModel.getViolationName()) {
            movCatNameBuilder.append(movCatName);
            movCatNameBuilder.append("\n");
        }
        movCatNameBuilder.append("còn phim đang cài đặt, không thể xóa");
        return movCatNameBuilder.toString();
    }

    private void dispatchChangingEvent(int action) {
        setControlStatus(DISABLE_ALL);
        DataChangingEvent dataChangingEvent = new DataChangingEvent(this);
        dataChangingEvent.setAction(action);
        dataChangingEvent.setDataChangingModel(changingModel);

        Object param;
        switch (action) {
            case DataChangingEvent.MODIFY:
                MovieCatgoryVO vo = new MovieCatgoryVO();
                vo.setMovCatId(Long.valueOf(lblMovCatId.getText()));
                vo.setMovCatName(TextUtil.normalize(txtMovCatName.getText()));
                param = vo;
                break;
            case DataChangingEvent.DELETE:
                param = getSelectedMovCatId();
                break;
            default:
                param = new MovieCatgoryVO();
                ((MovieCatgoryVO) param).setMovCatName(TextUtil.normalize(txtMovCatName.getText()));
        }

        dataChangingEvent.setParam(param);
        movieCatgoryDialogListener.onDataChanging(dataChangingEvent);
    }

    private List<Long> getSelectedMovCatId() {
        List<Long> movCatIdList = new ArrayList<Long>();
        for (int row : tblMovCat.getSelectedRows()) {
            movCatIdList.add((Long) tblMovCat.getValueAt(row, 0));
        }
        return movCatIdList;
    }

    private void dispatchChangedEvent() {
        movieCatgoryDialogListener.onDataChanged(new DataChangedEvent(this));
    }

    private void saveState() {
        lastTblMovCatSelectedId = getSelectedMovCatId();
        lastSortKeys = tblMovCat.getRowSorter().getSortKeys();
        lastTxtMovCatNameText = txtMovCatName.getText();
    }

    private void restoreState() {
        tblMovCat.setEnabled(true);
        tblMovCat.clearSelection();

        tblMovCat.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastTblMovCatSelectedId) {
            for (int row = 0; row < tblMovCat.getRowCount(); row++) {
                if (id.longValue() == ((Long) tblMovCat.getValueAt(row, 0)).longValue()) {
                    tblMovCat.addRowSelectionInterval(row, row);
                }
            }
        }

        if (tblMovCat.getSelectedRow() == -1) {
            setControlStatus(ADD_NEW);
        }
        txtMovCatName.setText(lastTxtMovCatNameText);
    }
}
