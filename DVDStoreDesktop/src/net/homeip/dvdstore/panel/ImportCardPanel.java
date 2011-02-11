/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ImportCardPanel.java
 *
 * Created on Mar 9, 2010, 3:17:35 PM
 */
package net.homeip.dvdstore.panel;

// <editor-fold defaultstate="collapsed" desc="import">
import datechooser.model.multiple.Period;
import datechooser.model.multiple.PeriodSet;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.dialog.ChooseMovieDialog;
import net.homeip.dvdstore.dialog.SupplierDialog;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.SupplierChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieCatgoryChangeEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.listener.event.SupplierChangeEvent;
import net.homeip.dvdstore.listener.panel.ChangeImportCardEvent;
import net.homeip.dvdstore.listener.panel.ImportCardPanelListener;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ImportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitImportCardPanelVO;
import net.homeip.dvdstore.pojo.webservice.vo.SupplierVO;
import net.homeip.dvdstore.swing.DSComboBoxModel;
import net.homeip.dvdstore.swing.ImportCardItemTableModel;
import net.homeip.dvdstore.swing.ImportCardTableModel;
import net.homeip.dvdstore.swing.editor.ImportCardEditor;
import net.homeip.dvdstore.swing.editor.ImportCardItemEditor;
import net.homeip.dvdstore.swing.renderer.ImportCardItemTableCellRenderer;
import net.homeip.dvdstore.swing.renderer.ImportCardTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ImportCardPanel extends javax.swing.JPanel
        implements MovieCatgoryChangeListener, MovieChangeListener, SupplierChangeListener {

    private ImportCardPanelListener importCardPanelListener;
    private LoadDataModel<List<ImportCardVO>> importCardPanelModel;
    private LoadDataModel<byte[]> printDataModel;
    //Chỉ dùng để init, null ngay sau khi init
    private LoadDataModel<InitImportCardPanelVO> initImportCardPanelModel;
    private DataChangingModel importCardChangingModel;
    //Đặt bộ editor ra ngoài để init dữ liệu
    private ImportCardEditor importCardEditor = new ImportCardEditor();

    /** Creates new form ImportCardPanel */
    public ImportCardPanel() {
        initComponents();
        prepareEndDateSearch();
        prepareTable();

        importCardPanelListener =
                (ImportCardPanelListener) ApplicationContextUtil.getApplicationContext().getBean(
                "importCardPanelListener");
        //Listen to MovieChangeEvent, MovieCatgoryChangeEvent, SupplierChangeEvent
        //StatisticIFrame register listener and dispatch these event to this panel

        prepareInitData();
        dispatchLoadInitDataEvent();
        prepareLoadingData();
        prepareChangingImportCard();
        preparePrintData();
    }

    // <editor-fold defaultstate="collapsed" desc="init Data">
    private void prepareInitData() {
        initImportCardPanelModel = new LoadDataModel<InitImportCardPanelVO>();
        initImportCardPanelModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadInitData(evt);
            }
        });
    }

    private void dispatchLoadInitDataEvent() {
        //Cần init data để listener có thể phân biệt data là init
        initImportCardPanelModel.setData(new InitImportCardPanelVO());
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(initImportCardPanelModel);
        List params = new ArrayList();
        params.add(supplierNameSearch);
        params.add(startDateSearch);
        params.add(endDateSearch);
        evt.setParams(params);
        importCardPanelListener.onLoadDataPerformed(evt);
    }

    private void onLoadInitData(ResultRetrievedEvent evt) {
        String errorMsg = checkError(initImportCardPanelModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            initData();
            initImportCardPanelModel = null;
        }
        restoreState();
    }

    private void initData() {
        InitImportCardPanelVO initImportCardPanelVO = initImportCardPanelModel.getData();
        importCardTableModel.setRowData(initImportCardPanelVO.getImportCardVOs());


        cbxSupplier.setModel(new DSComboBoxModel(
                initImportCardPanelVO.getSupplierVOs(), false));
        importCardEditor.setModel(new DSComboBoxModel(
                initImportCardPanelVO.getSupplierVOs(), false));
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="loadingData">
    private void prepareLoadingData() {
        importCardPanelModel = new LoadDataModel<List<ImportCardVO>>();
        importCardPanelModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onLoadImportCard(evt);
            }
        });
    }

    private void onLoadImportCard(ResultRetrievedEvent evt) {
        String errorMsg = checkError(importCardPanelModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            importCardTableModel.setRowData(importCardPanelModel.getData());
        }
        restoreState();
    }

    private void dispatchLoadDataEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(importCardPanelModel);
        List params = new ArrayList();
        params.add(supplierNameSearch);
        params.add(startDateSearch);
        params.add(endDateSearch);
        evt.setParams(params);
        importCardPanelListener.onLoadDataPerformed(evt);
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="print ImportCard">
    private void preparePrintData() {
        printDataModel = new LoadDataModel<byte[]>();
        printDataModel.setData(new byte[0]);
        printDataModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onPrintDataRetrieved(evt);
            }
        });
    }

    private void dispatchLoadReportDataEvent() {
        saveState();
        setControlStatus(DISABLE_ALL);

        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(printDataModel);
        List params = new ArrayList();
        params.add(getImportCardTableSelectedIds());
        evt.setParams(params);
        importCardPanelListener.onLoadDataPerformed(evt);
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

    // <editor-fold defaultstate="collapsed" desc="change Order">
    private void dispatchChangeImportCardEvent(int action, Object params) {
        saveState();
        setControlStatus(DISABLE_ALL);

        ChangeImportCardEvent changeImportCardEvent = new ChangeImportCardEvent(this);
        changeImportCardEvent.setAction(action);
        changeImportCardEvent.setImportCardChangingModel(importCardChangingModel);
        changeImportCardEvent.setParams(params);
        importCardPanelListener.onChangeImportCardPerformed(changeImportCardEvent);
    }

    private void prepareChangingImportCard() {
        importCardChangingModel = new DataChangingModel();
        importCardChangingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onImportCardChangingResultRetrieved(evt);
            }
        });
    }

    private void onImportCardChangingResultRetrieved(ResultRetrievedEvent evt) {
        int status = importCardChangingModel.getStatus();
        String errorMsg = checkError(status);
        //Không cần check ValidateFail do check lúc sửa bảng rồi
        if (errorMsg != null) {
            restoreState();
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            dispatchDataChangedEvent();
        }
    }

    private void dispatchDataChangedEvent() {
        DataChangedEvent dataChangedEvent = new DataChangedEvent(this);
        importCardPanelListener.onDataChanged(dataChangedEvent);
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

    public void onMovieCatgoryChange(MovieCatgoryChangeEvent evt) {
        dispatchLoadDataEvent();
    }

    public void onMovieChange(MovieChangeEvent evt) {
        dispatchLoadDataEvent();
    }

    public void onSupplierChange(SupplierChangeEvent evt) {
        dispatchLoadDataEvent();
        SupplierVO supplierVO = (SupplierVO) cbxSupplier.getSelectedItem();
        cbxSupplier.setModel(new DSComboBoxModel(evt.getSupplierList(), false));
        cbxSupplier.setSelectedItem(supplierVO);
        importCardEditor.setModel(new DSComboBoxModel(evt.getSupplierList(), false));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        scrollImportCard = new javax.swing.JScrollPane();
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        txtSupplierSearch = new javax.swing.JTextField();
        dtImportDateSearch = new datechooser.beans.DateChooserCombo();
        btnSearchImportCard = new javax.swing.JButton();
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        cbxSupplier = new javax.swing.JComboBox();
        btnAddImportCard = new javax.swing.JButton();
        btnAddImportCardItem = new javax.swing.JButton();
        btnDeleteImportCardItem = new javax.swing.JButton();
        btnDeleteImportCard = new javax.swing.JButton();
        btnOpenSupplierDialog = new javax.swing.JButton();
        btnPrintImportCard = new javax.swing.JButton();
        javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
        scrollImportCardItem = new javax.swing.JScrollPane();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(scrollImportCard, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(scrollImportCard, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setText("Nhà cung cấp:");

        jLabel2.setText("Ngày nhập:");

        dtImportDateSearch.setNothingAllowed(false);
        dtImportDateSearch.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
        dtImportDateSearch.setLocale(new java.util.Locale("vi", "", ""));
        dtImportDateSearch.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_PERIOD);

        btnSearchImportCard.setText("Tìm kiếm");
        btnSearchImportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchImportCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dtImportDateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearchImportCard))
                    .addComponent(txtSupplierSearch))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSupplierSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel2)
                    .addComponent(dtImportDateSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchImportCard))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bảng điều khiển", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel3.setText("Nhà cung cấp:");

        cbxSupplier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxSupplier.setPrototypeDisplayValue("++++++++++++++++++++++++++++++++");

        btnAddImportCard.setText("Thêm phiếu");
        btnAddImportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImportCardActionPerformed(evt);
            }
        });

        btnAddImportCardItem.setText("Thêm phim");
        btnAddImportCardItem.setEnabled(false);
        btnAddImportCardItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddImportCardItemActionPerformed(evt);
            }
        });

        btnDeleteImportCardItem.setText("Bỏ phim");
        btnDeleteImportCardItem.setEnabled(false);
        btnDeleteImportCardItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteImportCardItemActionPerformed(evt);
            }
        });

        btnDeleteImportCard.setText("Xóa phiếu");
        btnDeleteImportCard.setEnabled(false);
        btnDeleteImportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteImportCardActionPerformed(evt);
            }
        });

        btnOpenSupplierDialog.setText("...");
        btnOpenSupplierDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenSupplierDialogActionPerformed(evt);
            }
        });

        btnPrintImportCard.setText("In phiếu");
        btnPrintImportCard.setEnabled(false);
        btnPrintImportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintImportCardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAddImportCard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrintImportCard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteImportCard)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddImportCardItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteImportCardItem))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(27, 27, 27)
                        .addComponent(cbxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOpenSupplierDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpenSupplierDialog))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddImportCard)
                    .addComponent(btnPrintImportCard)
                    .addComponent(btnDeleteImportCard)
                    .addComponent(btnDeleteImportCardItem)
                    .addComponent(btnAddImportCardItem))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách phim trong phiếu nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollImportCardItem, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollImportCardItem, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, 0, 211, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private String supplierNameSearch = "";
    private Calendar startDateSearch;
    private Calendar endDateSearch;
    private void btnSearchImportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchImportCardActionPerformed
        // TODO add your handling code here:
        supplierNameSearch = txtSupplierSearch.getText();
        Period period = dtImportDateSearch.getSelectedPeriodSet().getFirstPeriod();
        startDateSearch = period.getStartDate();
        endDateSearch = period.getEndDate();
        dispatchLoadDataEvent();
    }//GEN-LAST:event_btnSearchImportCardActionPerformed

    private void btnAddImportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImportCardActionPerformed
        // TODO add your handling code here:
        SupplierVO supplierVO = (SupplierVO) cbxSupplier.getSelectedItem();
        if (supplierVO == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn nhà cung cấp");
        } else {
            dispatchChangeImportCardEvent(ChangeImportCardEvent.ADD_IMPORTCARD, supplierVO);
        }
    }//GEN-LAST:event_btnAddImportCardActionPerformed

    private void btnDeleteImportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteImportCardActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "Xóa phiếu nhập ?",
                "Xóa phiếu nhập", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dispatchChangeImportCardEvent(ChangeImportCardEvent.DELETE_IMPORTCARD,
                    getImportCardTableSelectedIds());
        }
    }//GEN-LAST:event_btnDeleteImportCardActionPerformed

    private void btnAddImportCardItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddImportCardItemActionPerformed
        // TODO add your handling code here:
        ChooseMovieDialog chooseMovieDialog = new ChooseMovieDialog(null);

        if (chooseMovieDialog.showDialog() == ChooseMovieDialog.DIALOG_OK) {
            List params = new ArrayList(2);
            params.add(getImportCardTableSelectedIds().get(0));
            params.add(chooseMovieDialog.getSelectedMovieIds());
            dispatchChangeImportCardEvent(ChangeImportCardEvent.ADD_IMPORTCARD_ITEM, params);
        }
    }//GEN-LAST:event_btnAddImportCardItemActionPerformed

    private void btnDeleteImportCardItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteImportCardItemActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Bỏ phim khỏi danh sách ?", "Bỏ phim", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            List params = new ArrayList(2);
            params.add(getImportCardTableSelectedIds().get(0));
            params.add(getImportCardItemTableSelectedIds());
            dispatchChangeImportCardEvent(ChangeImportCardEvent.DELETE_IMPORTCARD_ITEM, params);
        }
    }//GEN-LAST:event_btnDeleteImportCardItemActionPerformed

    private void btnPrintImportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintImportCardActionPerformed
        // TODO add your handling code here:
        dispatchLoadReportDataEvent();
    }//GEN-LAST:event_btnPrintImportCardActionPerformed

    private void btnOpenSupplierDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenSupplierDialogActionPerformed
        // TODO add your handling code here:
        SupplierDialog supplierDialog = new SupplierDialog(null);
        supplierDialog.setVisible(true);
    }//GEN-LAST:event_btnOpenSupplierDialogActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddImportCard;
    private javax.swing.JButton btnAddImportCardItem;
    private javax.swing.JButton btnDeleteImportCard;
    private javax.swing.JButton btnDeleteImportCardItem;
    private javax.swing.JButton btnOpenSupplierDialog;
    private javax.swing.JButton btnPrintImportCard;
    private javax.swing.JButton btnSearchImportCard;
    private javax.swing.JComboBox cbxSupplier;
    private datechooser.beans.DateChooserCombo dtImportDateSearch;
    private javax.swing.JScrollPane scrollImportCard;
    private javax.swing.JScrollPane scrollImportCardItem;
    private javax.swing.JTextField txtSupplierSearch;
    // End of variables declaration//GEN-END:variables
    // <editor-fold defaultstate="collapsed" desc="saveState-restoreState-setControlStatus">
    private List lastOrderTableSortKeys;
    private List lastOrderItemTableSortKeys;
    private List<Long> lastOrderTableSelectedIds;
    private List<Long> lastOrderItemTableSelectedIds;

    private void saveState() {
        lastOrderTableSortKeys = tblImportCard.getRowSorter().getSortKeys();
        lastOrderTableSelectedIds = getImportCardTableSelectedIds();
        lastOrderItemTableSortKeys = tblImportCardItem.getRowSorter().getSortKeys();
        lastOrderItemTableSelectedIds = getImportCardItemTableSelectedIds();
    }

    private void restoreState() {
        btnSearchImportCard.setEnabled(true);
        btnAddImportCard.setEnabled(true);
        restoreTableState(tblImportCard, lastOrderTableSortKeys, lastOrderTableSelectedIds);
        restoreTableState(tblImportCardItem, lastOrderItemTableSortKeys, lastOrderItemTableSelectedIds);
    }

    private void restoreTableState(JTable tbl, List lastSortKeys, List<Long> lastSelectedIds) {
        tbl.setEnabled(true);
        tbl.clearSelection();

        tbl.getRowSorter().setSortKeys(lastSortKeys);
        for (Long id : lastSelectedIds) {
            for (int row = 0; row < tbl.getRowCount(); row++) {
                if (id.longValue() == ((Long) tbl.getValueAt(row, 0)).longValue()) {
                    tbl.addRowSelectionInterval(row, row);
                }
            }
        }
    }
    private static final int DISABLE_ALL = 0;
    private static final int DISABLE_CONTROL = 1;
    private static final int DISABLE_DELETE_IMPORTCARD_ITEM = 2;
    private static final int ENABLE_ALL = 3;

    private void setControlStatus(int status) {
        switch (status) {
            case DISABLE_ALL:
                tblImportCard.setEnabled(false);
                tblImportCardItem.setEnabled(false);
                btnSearchImportCard.setEnabled(false);
                btnAddImportCard.setEnabled(false);
                setEnableControl(false);
                break;
            case DISABLE_CONTROL:
                setEnableControl(false);
                break;
            case DISABLE_DELETE_IMPORTCARD_ITEM:
                btnDeleteImportCardItem.setEnabled(false);
                btnAddImportCardItem.setEnabled(true);
                btnDeleteImportCard.setEnabled(true);
                btnPrintImportCard.setEnabled(true);
                break;
            case ENABLE_ALL:
                setEnableControl(true);
                btnDeleteImportCard.setEnabled(true);
                btnPrintImportCard.setEnabled(true);
        }
    }

    private void setEnableControl(boolean enabled) {
        btnDeleteImportCard.setEnabled(enabled);
        btnPrintImportCard.setEnabled(enabled);
        btnAddImportCardItem.setEnabled(enabled);
        btnDeleteImportCardItem.setEnabled(enabled);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="prepareTable">
    private JTable tblImportCard;
    private JTable tblImportCardItem;
    private ImportCardTableModel importCardTableModel;
    private ImportCardItemTableModel importCardItemTableModel;

    private void prepareTable() {
        prepareImportCardTable();
        prepareImportCardItemTable();
    }

    private void prepareImportCardTable() {
        tblImportCard = new javax.swing.JTable();
        tblImportCard.setAutoCreateRowSorter(true);
        tblImportCard.setFillsViewportHeight(true);
        tblImportCard.getTableHeader().setReorderingAllowed(false);
        scrollImportCard.setViewportView(tblImportCard);

        importCardTableModel = new ImportCardTableModel();
        tblImportCard.setModel(importCardTableModel);

        tblImportCard.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblImportCard.getColumnModel().getColumn(1).setPreferredWidth(300);
        tblImportCard.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblImportCard.setRowHeight(20);
        ImportCardTableCellRenderer cellRenderer = new ImportCardTableCellRenderer();
        tblImportCard.setDefaultRenderer(Long.class, cellRenderer);
        tblImportCard.setDefaultRenderer(String.class, cellRenderer);
        tblImportCard.setDefaultRenderer(Date.class, cellRenderer);

        tblImportCard.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblImportCard.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                if (tblImportCardItem.isEditing()) {
                    tblImportCardItem.getCellEditor().cancelCellEditing();
                }
                int[] selectedRowIndex = tblImportCard.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        importCardItemTableModel.setRowData(null);
                        setControlStatus(DISABLE_CONTROL);
                        break;
                    case 1:
                        int idx = tblImportCard.convertRowIndexToModel(tblImportCard.getSelectedRow());
                        ImportCardVO importCardVO = importCardTableModel.getImportCardVO(idx);
                        importCardItemTableModel.setRowData(importCardVO.getImportCardItemVOs());
                        setControlStatus(DISABLE_DELETE_IMPORTCARD_ITEM);
                        break;
                    default:
                        importCardItemTableModel.setRowData(null);
                        setControlStatus(DISABLE_DELETE_IMPORTCARD_ITEM);
                }
            }
        });
        tblImportCard.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastOrderTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });

        tblImportCard.setDefaultEditor(String.class, importCardEditor);
        importCardEditor.addCellEditorListener(new CellEditorListener() {

            public void editingStopped(ChangeEvent e) {
                ImportCardVO importCardVO = importCardTableModel.getImportCardVO(
                        tblImportCard.convertRowIndexToModel(importCardEditor.getRow()));
                dispatchChangeImportCardEvent(
                        ChangeImportCardEvent.UPDATE_IMPORT_CARD, importCardVO);
            }

            public void editingCanceled(ChangeEvent e) {
            }
        });
    }

    private void prepareImportCardItemTable() {
        tblImportCardItem = new javax.swing.JTable();
        tblImportCardItem.setAutoCreateRowSorter(true);
        tblImportCardItem.setFillsViewportHeight(true);
        tblImportCardItem.getTableHeader().setReorderingAllowed(false);
        scrollImportCardItem.setViewportView(tblImportCardItem);

        importCardItemTableModel = new ImportCardItemTableModel();
        tblImportCardItem.setModel(importCardItemTableModel);

        tblImportCardItem.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblImportCardItem.getColumnModel().getColumn(1).setPreferredWidth(300);
        tblImportCardItem.getColumnModel().getColumn(2).setPreferredWidth(200);
        tblImportCardItem.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblImportCardItem.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblImportCardItem.setRowHeight(20);
        ImportCardItemTableCellRenderer cellRenderer = new ImportCardItemTableCellRenderer();
        tblImportCardItem.setDefaultRenderer(Long.class, cellRenderer);
        tblImportCardItem.setDefaultRenderer(String.class, cellRenderer);
        tblImportCardItem.setDefaultRenderer(Double.class, cellRenderer);
        tblImportCardItem.setDefaultRenderer(Integer.class, cellRenderer);

        tblImportCardItem.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblImportCardItem.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedRowIndex = tblImportCardItem.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        setControlStatus(DISABLE_DELETE_IMPORTCARD_ITEM);
                        break;
                    default:
                        setControlStatus(ENABLE_ALL);
                }
            }
        });
        tblImportCardItem.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastOrderItemTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });

        final ImportCardItemEditor importCardItemEditor = new ImportCardItemEditor();
        tblImportCardItem.setDefaultEditor(Double.class, importCardItemEditor);
        tblImportCardItem.setDefaultEditor(Integer.class, importCardItemEditor);
        importCardItemEditor.addCellEditorListener(new CellEditorListener() {

            public void editingStopped(ChangeEvent e) {
                ImportCardVO importCardVO = importCardTableModel.getImportCardVO(
                        tblImportCard.convertRowIndexToModel(tblImportCard.getSelectedRow()));
                dispatchChangeImportCardEvent(ChangeImportCardEvent.UPDATE_IMPORT_CARD, importCardVO);
            }

            public void editingCanceled(ChangeEvent e) {
            }
        });
    }// </editor-fold>  

    private void prepareEndDateSearch() {
        startDateSearch = new GregorianCalendar();
        startDateSearch.set(GregorianCalendar.DATE, 1);
        endDateSearch = new GregorianCalendar();
        endDateSearch.set(GregorianCalendar.DATE, endDateSearch.getActualMaximum(GregorianCalendar.DATE));
        dtImportDateSearch.setSelection(new PeriodSet(
                new Period[]{new Period(startDateSearch, endDateSearch)}));
    }

    private List<Long> getImportCardTableSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblImportCard.getSelectedRows()) {
            selectedIds.add((Long) tblImportCard.getValueAt(idx, 0));
        }
        return selectedIds;
    }

    private List<Long> getImportCardItemTableSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblImportCardItem.getSelectedRows()) {
            selectedIds.add((Long) tblImportCardItem.getValueAt(idx, 0));
        }
        return selectedIds;
    }
}
