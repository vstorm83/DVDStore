/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChooseMovieDialog.java
 *
 * Created on Mar 8, 2010, 10:43:18 AM
 */
package net.homeip.dvdstore.dialog;

// <editor-fold defaultstate="collapsed" desc="import">
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.ChooseMovieDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieCatgoryVO;
import net.homeip.dvdstore.swing.ChooseMovieTableModel;
import net.homeip.dvdstore.swing.DSComboBoxModel;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ChooseMovieDialog extends javax.swing.JDialog {

    private ChooseMovieDialogListener chooseMovieDialogListener;
    private LoadDataModel<List<ChooseMovieCatgoryVO>> loadDataModel;
    private ChooseMovieTableModel chooseMovieTableModel;
    private JTable tblChooseMovie;
    private int action = DIALOG_CLOSE;
    public static final int DIALOG_CLOSE = 0;
    public static final int DIALOG_OK = 1;

    /** Creates new form ChooseMovieDialog */
    public ChooseMovieDialog(Frame parent) {
        super(parent, true);
        initComponents();
        prepareChooseMovieTable();

        chooseMovieDialogListener =
                (ChooseMovieDialogListener) ApplicationContextUtil.getApplicationContext().getBean(
                "chooseMovieDialogListener");

        prepareLoadData();
        dispatchLoadDataEvent();
    }

    public int showDialog() {
        action = DIALOG_CLOSE;
        setVisible(true);
        return action;
    }

    // <editor-fold defaultstate="collapsed" desc="loadData">
    private void dispatchLoadDataEvent() {
        setEnableControl(false);
        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(loadDataModel);
        chooseMovieDialogListener.onLoadDataPerformed(evt);
    }

    private void prepareLoadData() {
        loadDataModel = new LoadDataModel<List<ChooseMovieCatgoryVO>>();
        loadDataModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadDataResultRetrieved(evt);
            }
        });
    }

    private void onLoadDataResultRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = checkError(loadDataModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            cbxMovieCatgory.setModel(new DSComboBoxModel(loadDataModel.getData(), false));
            if (cbxMovieCatgory.getItemCount() > 0) {
                cbxMovieCatgory.setSelectedIndex(0);
            }
        }
        setEnableControl(true);
    }// </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        cbxMovieCatgory = new javax.swing.JComboBox();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        scrMovie = new javax.swing.JScrollPane();
        btnChooseMovie = new javax.swing.JButton();
        btnCloseDialog = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chọn phim");
        setResizable(false);

        jLabel1.setText("Loại phim:");

        cbxMovieCatgory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxMovieCatgory.setPrototypeDisplayValue("++++++++++++++++++++++++++");
        cbxMovieCatgory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxMovieCatgoryActionPerformed(evt);
            }
        });

        jLabel2.setText("Phim:");

        btnChooseMovie.setText("Chọn phim");
        btnChooseMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseMovieActionPerformed(evt);
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
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbxMovieCatgory, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(32, 32, 32)
                                .addComponent(scrMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(btnChooseMovie)
                        .addGap(40, 40, 40)
                        .addComponent(btnCloseDialog)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxMovieCatgory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(scrMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChooseMovie)
                    .addComponent(btnCloseDialog))
                .addGap(49, 49, 49))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-405)/2, (screenSize.height-311)/2, 405, 311);
    }// </editor-fold>//GEN-END:initComponents

    private void cbxMovieCatgoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxMovieCatgoryActionPerformed
        // TODO add your handling code here:
        ChooseMovieCatgoryVO chooseMovieCatgoryVO = (ChooseMovieCatgoryVO) cbxMovieCatgory.getSelectedItem();
        chooseMovieTableModel.setRowData(chooseMovieCatgoryVO.getChooseMovieVOs());
    }//GEN-LAST:event_cbxMovieCatgoryActionPerformed

    private void btnCloseDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseDialogActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseDialogActionPerformed

    private void btnChooseMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseMovieActionPerformed
        // TODO add your handling code here:
        if (getSelectedMovieIds().size() == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn phim");
            return;
        }
        action = DIALOG_OK;
        dispose();
    }//GEN-LAST:event_btnChooseMovieActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChooseMovie;
    private javax.swing.JButton btnCloseDialog;
    private javax.swing.JComboBox cbxMovieCatgory;
    private javax.swing.JScrollPane scrMovie;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="prepare table">
    private void prepareChooseMovieTable() {
        tblChooseMovie = new javax.swing.JTable();
        tblChooseMovie.setAutoCreateRowSorter(true);
        tblChooseMovie.setFillsViewportHeight(true);
        tblChooseMovie.getTableHeader().setReorderingAllowed(false);
        scrMovie.setViewportView(tblChooseMovie);

        chooseMovieTableModel = new ChooseMovieTableModel();
        tblChooseMovie.setModel(chooseMovieTableModel);

        tblChooseMovie.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblChooseMovie.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblChooseMovie.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblChooseMovie.setDefaultRenderer(Long.class, cellRenderer);
        tblChooseMovie.setDefaultRenderer(String.class, cellRenderer);
    }// </editor-fold>

    public List<Long> getSelectedMovieIds() {
        List<Long> movieIdList = new ArrayList<Long>();
        for (int row : tblChooseMovie.getSelectedRows()) {
            movieIdList.add((Long) tblChooseMovie.getValueAt(row, 0));
        }
        return movieIdList;
    }
    
    private String checkError(int status) {
        String errorMsg = null;
        switch (status) {
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Không kết nối được với Server";
                break;
            case LoadDataModel.SERVER_ERROR:
                errorMsg = "Lỗi Server";
        }
        return errorMsg;
    }

    private void setEnableControl(boolean enabled) {
        cbxMovieCatgory.setEnabled(enabled);
        tblChooseMovie.setEnabled(enabled);
        btnChooseMovie.setEnabled(enabled);
    }

}
