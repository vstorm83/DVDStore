/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExportCardPanel.java
 *
 * Created on Mar 8, 2010, 9:38:56 PM
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.MovieCatgoryChangeListener;
import net.homeip.dvdstore.listener.MovieChangeListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.UserChangeListener;
import net.homeip.dvdstore.listener.event.DataChangedEvent;
import net.homeip.dvdstore.listener.event.DataChangingEvent;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.MovieCatgoryChangeEvent;
import net.homeip.dvdstore.listener.event.MovieChangeEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.listener.event.UserChangeEvent;
import net.homeip.dvdstore.listener.panel.ExportCardPanelListener;
import net.homeip.dvdstore.model.DataChangingModel;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;
import net.homeip.dvdstore.swing.renderer.DSTableCellRenderer;
import net.homeip.dvdstore.swing.ExportCardItemTableModel;
import net.homeip.dvdstore.swing.ExportCardTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ExportCardPanel extends javax.swing.JPanel
        implements MovieCatgoryChangeListener, MovieChangeListener, UserChangeListener {

    private LoadDataModel<List<ExportCardVO>> loadDataModel;
    private LoadDataModel<byte[]> printDataModel;
    private DataChangingModel exportCardRevertingModel;
    private ExportCardPanelListener exportCardPanelListener;

    /** Creates new form ExportCardPanel */
    public ExportCardPanel() {
        initComponents();
        prepareEndDateSearch();
        prepareTable();

        exportCardPanelListener =
                (ExportCardPanelListener) ApplicationContextUtil.getApplicationContext().getBean(
                "exportCardPanelListener");
        //listenerRegistry được prepare bởi StatisticIFrame. Sau đó delegate lại cho panel

        prepareLoadData();
        dispatchLoadDataEvent();

        prepareRevertingExportCard();
        preparePrintData();
    }

    // <editor-fold defaultstate="collapsed" desc="load Data">
    private void prepareLoadData() {
        loadDataModel = new LoadDataModel<List<ExportCardVO>>();
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
        params.add(startDateSearch.getTime());
        params.add(endDateSearch.getTime());
        evt.setParams(params);
        exportCardPanelListener.onLoadDataPerformed(evt);
    }

    private void onLoadDataResultRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = checkError(loadDataModel.getStatus());
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            exportCardTableModel.setRowData(loadDataModel.getData());
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
        params.add(getExportCardTableSelectedIds());
        evt.setParams(params);
        exportCardPanelListener.onLoadDataPerformed(evt);
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

    // <editor-fold defaultstate="collapsed" desc="Revert ExportCard">
    private void prepareRevertingExportCard() {
        exportCardRevertingModel = new DataChangingModel();
        exportCardRevertingModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onExportCardRevertingResultRetrieved(evt);
            }
        });
    }

    private void dispatchRevertExportCardEvent(List<Long> exportCardIds) {
        saveState();
        setControlStatus(DISABLE_ALL);

        DataChangingEvent dataChangingEvent = new DataChangingEvent(this);
        dataChangingEvent.setDataChangingModel(exportCardRevertingModel);
        dataChangingEvent.setParam(exportCardIds);
        exportCardPanelListener.onDataChanging(dataChangingEvent);
    }

    private void onExportCardRevertingResultRetrieved(ResultRetrievedEvent evt) {
        int status = exportCardRevertingModel.getStatus();
        String errorMsg = checkError(status);
        if (errorMsg != null) {
            restoreState();
            JOptionPane.showMessageDialog(this, errorMsg);
        } else {
            dispatchExportCardRevertedEvent();
        }
    }

    private void dispatchExportCardRevertedEvent() {
        DataChangedEvent dataChangedEvent = new DataChangedEvent(this);
        exportCardPanelListener.onDataChanged(dataChangedEvent);
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

    public void onUserChange(UserChangeEvent evt) {
        exportCardTableModel.updateUserInExportCard(evt.getOldUserVO(), evt.getNewUserVO());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JSplitPane exportCardSplitPane = new javax.swing.JSplitPane();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JPanel exportCardListPaneNorth = new javax.swing.JPanel();
        scrollExportCard = new javax.swing.JScrollPane();
        javax.swing.JPanel exportCardListControlButtonPane = new javax.swing.JPanel();
        btnPrintExportCard = new javax.swing.JButton();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        dtEndDate = new datechooser.beans.DateChooserCombo();
        btnRevertExportCard = new javax.swing.JButton();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        btnSearchExportCard = new javax.swing.JButton();
        javax.swing.JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        javax.swing.JTabbedPane movieExportCardListTabbedPane = new javax.swing.JTabbedPane();
        scrollExportCardItem = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        exportCardSplitPane.setDividerLocation(300);
        exportCardSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 53));

        exportCardListPaneNorth.setPreferredSize(new java.awt.Dimension(100, 49));
        exportCardListPaneNorth.setLayout(new java.awt.BorderLayout());
        exportCardListPaneNorth.add(scrollExportCard, java.awt.BorderLayout.CENTER);

        exportCardListControlButtonPane.setBackground(new java.awt.Color(255, 255, 255));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("DVDStoreDesktop"); // NOI18N
        btnPrintExportCard.setText(bundle.getString("StatisticIFrame.btnPrintExportCard.text")); // NOI18N
        btnPrintExportCard.setEnabled(false);
        btnPrintExportCard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintExportCardActionPerformed(evt);
            }
        });

        jLabel3.setText(bundle.getString("StatisticIFrame.jLabel3.text")); // NOI18N

        dtEndDate.setCurrentView(new datechooser.view.appearance.AppearancesList("Swing",
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
    dtEndDate.setNothingAllowed(false);
    dtEndDate.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 12));
    dtEndDate.setLocale(new java.util.Locale("vi", "", ""));
    dtEndDate.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_PERIOD);

    btnRevertExportCard.setText(bundle.getString("StatisticIFrame.btnRevertExportCard.text")); // NOI18N
    btnRevertExportCard.setEnabled(false);
    btnRevertExportCard.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnRevertExportCardActionPerformed(evt);
        }
    });

    jLabel4.setText(bundle.getString("StatisticIFrame.jLabel4.text")); // NOI18N

    btnSearchExportCard.setText(bundle.getString("StatisticIFrame.btnSearchExportCard.text")); // NOI18N
    btnSearchExportCard.setEnabled(false);
    btnSearchExportCard.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSearchExportCardActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout exportCardListControlButtonPaneLayout = new javax.swing.GroupLayout(exportCardListControlButtonPane);
    exportCardListControlButtonPane.setLayout(exportCardListControlButtonPaneLayout);
    exportCardListControlButtonPaneLayout.setHorizontalGroup(
        exportCardListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(exportCardListControlButtonPaneLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel4)
            .addGap(2, 2, 2)
            .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(dtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(btnSearchExportCard)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnPrintExportCard)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnRevertExportCard)
            .addContainerGap(12, Short.MAX_VALUE))
    );
    exportCardListControlButtonPaneLayout.setVerticalGroup(
        exportCardListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(exportCardListControlButtonPaneLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(exportCardListControlButtonPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(jLabel4)
                .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel3)
                .addComponent(dtEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSearchExportCard)
                .addComponent(btnPrintExportCard)
                .addComponent(btnRevertExportCard))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    exportCardListPaneNorth.add(exportCardListControlButtonPane, java.awt.BorderLayout.SOUTH);

    jScrollPane1.setViewportView(exportCardListPaneNorth);

    exportCardSplitPane.setTopComponent(jScrollPane1);

    movieExportCardListTabbedPane.addTab("Phim trong phiếu xuất", scrollExportCardItem);

    jScrollPane3.setViewportView(movieExportCardListTabbedPane);

    exportCardSplitPane.setBottomComponent(jScrollPane3);

    add(exportCardSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    private String userNameSearch = "";
    private Calendar startDateSearch;
    private Calendar endDateSearch;
    private void btnSearchExportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchExportCardActionPerformed
        // TODO add your handling code here:
        userNameSearch = txtUserName.getText().trim();
        Period period = dtEndDate.getSelectedPeriodSet().getFirstPeriod();
        startDateSearch = period.getStartDate();
        endDateSearch = period.getEndDate();
        dispatchLoadDataEvent();
    }//GEN-LAST:event_btnSearchExportCardActionPerformed

    private void btnRevertExportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevertExportCardActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this,
                "Chuyển về danh sách hóa đơn ?", "Chuyển danh sách", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION) {
            dispatchRevertExportCardEvent(getExportCardTableSelectedIds());
        }
    }//GEN-LAST:event_btnRevertExportCardActionPerformed

    private void btnPrintExportCardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintExportCardActionPerformed
        // TODO add your handling code here:
        dispatchLoadPrintDataEvent();
    }//GEN-LAST:event_btnPrintExportCardActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrintExportCard;
    private javax.swing.JButton btnRevertExportCard;
    private javax.swing.JButton btnSearchExportCard;
    private datechooser.beans.DateChooserCombo dtEndDate;
    private javax.swing.JScrollPane scrollExportCard;
    private javax.swing.JScrollPane scrollExportCardItem;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
    // <editor-fold defaultstate="collapsed" desc="saveState-restoreState-setControlStatus">
    private List lastExportCardTableSortKeys;
    private List lastExportCardItemTableSortKeys;
    private List<Long> lastExportCardTableSelectedIds;

    private void saveState() {
        lastExportCardTableSortKeys = tblExportCard.getRowSorter().getSortKeys();
        lastExportCardTableSelectedIds = getExportCardTableSelectedIds();
        lastExportCardItemTableSortKeys = tblExportCardItem.getRowSorter().getSortKeys();
    }

    private void restoreState() {
        restoreTableState(tblExportCard, lastExportCardTableSortKeys, lastExportCardTableSelectedIds);
        restoreTableState(tblExportCardItem, lastExportCardItemTableSortKeys, null);
    }

    private void restoreTableState(JTable tbl, List lastSortKeys, List<Long> lastSelectedIds) {
        tbl.setEnabled(true);
        btnSearchExportCard.setEnabled(true);
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
                tblExportCard.setEnabled(false);
                tblExportCardItem.setEnabled(false);
                btnSearchExportCard.setEnabled(false);
                setEnableControl(false);
                break;
            case DISABLE_CONTROL:
                setEnableControl(false);
                break;
            case ENABLE_CONTROL:
                setEnableControl(true);
                break;
        }
    }

    private void setEnableControl(boolean enabled) {
        btnRevertExportCard.setEnabled(enabled);
        btnPrintExportCard.setEnabled(enabled);
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="prepareTable">
    private JTable tblExportCard;
    private JTable tblExportCardItem;
    private ExportCardTableModel exportCardTableModel;
    private ExportCardItemTableModel exportCardItemTableModel;

    private void prepareTable() {
        prepareExportCardTable();
        prepareExportCardItemTable();
    }

    private void prepareExportCardTable() {
        tblExportCard = new javax.swing.JTable();
        tblExportCard.setAutoCreateRowSorter(true);
        tblExportCard.setFillsViewportHeight(true);
        tblExportCard.getTableHeader().setReorderingAllowed(false);
        scrollExportCard.setViewportView(tblExportCard);

        exportCardTableModel = new ExportCardTableModel();
        tblExportCard.setModel(exportCardTableModel);

        tblExportCard.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblExportCard.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblExportCard.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblExportCard.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblExportCard.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblExportCard.getColumnModel().getColumn(5).setPreferredWidth(200);
        tblExportCard.getColumnModel().getColumn(6).setPreferredWidth(180);
        tblExportCard.getColumnModel().getColumn(7).setPreferredWidth(70);
        tblExportCard.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblExportCard.setDefaultRenderer(Long.class, cellRenderer);
        tblExportCard.setDefaultRenderer(String.class, cellRenderer);
        tblExportCard.setDefaultRenderer(Date.class, cellRenderer);

        tblExportCard.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblExportCard.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedRowIndex = tblExportCard.getSelectedRows();
                switch (selectedRowIndex.length) {
                    case 0:
                        setControlStatus(DISABLE_CONTROL);
                        exportCardItemTableModel.setRowData(null);
                        break;
                    case 1:
                        int idx = tblExportCard.convertRowIndexToModel(tblExportCard.getSelectedRow());
                        ExportCardVO exportCardVO = exportCardTableModel.getExportCardVO(idx);
                        exportCardItemTableModel.setRowData(exportCardVO.getExportCardItemVOs());
                        setControlStatus(ENABLE_CONTROL);
                        break;
                    default:
                        exportCardItemTableModel.setRowData(null);
                        setControlStatus(ENABLE_CONTROL);
                }
            }
        });
        tblExportCard.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastExportCardTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }

    private void prepareExportCardItemTable() {
        tblExportCardItem = new javax.swing.JTable();
        tblExportCardItem.setAutoCreateRowSorter(true);
        tblExportCardItem.setFillsViewportHeight(true);
        tblExportCardItem.getTableHeader().setReorderingAllowed(false);
        scrollExportCardItem.setViewportView(tblExportCardItem);

        exportCardItemTableModel = new ExportCardItemTableModel();
        tblExportCardItem.setModel(exportCardItemTableModel);

        tblExportCardItem.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblExportCardItem.getColumnModel().getColumn(1).setPreferredWidth(800);
        tblExportCardItem.getColumnModel().getColumn(2).setPreferredWidth(300);
        tblExportCardItem.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblExportCardItem.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblExportCardItem.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblExportCardItem.setRowHeight(20);
        DSTableCellRenderer cellRenderer = new DSTableCellRenderer();
        tblExportCardItem.setDefaultRenderer(Long.class, cellRenderer);
        tblExportCardItem.setDefaultRenderer(String.class, cellRenderer);
        tblExportCardItem.setDefaultRenderer(Double.class, cellRenderer);
        tblExportCardItem.setDefaultRenderer(Integer.class, cellRenderer);

        tblExportCardItem.setRowSelectionAllowed(false);
        tblExportCardItem.getRowSorter().addRowSorterListener(new RowSorterListener() {

            public void sorterChanged(RowSorterEvent e) {
                if (e.getType() == RowSorterEvent.Type.SORT_ORDER_CHANGED) {
                    lastExportCardItemTableSortKeys = e.getSource().getSortKeys();
                }
            }
        });
    }// </editor-fold>

    private void prepareEndDateSearch() {
        startDateSearch = new GregorianCalendar();
        startDateSearch.set(GregorianCalendar.DATE, 1);
        endDateSearch = new GregorianCalendar();
        endDateSearch.set(GregorianCalendar.DATE, endDateSearch.getActualMaximum(GregorianCalendar.DATE));
        dtEndDate.setSelection(new PeriodSet(
                new Period[]{new Period(startDateSearch, endDateSearch)}));
    }

    private List<Long> getExportCardTableSelectedIds() {
        List<Long> selectedIds = new ArrayList<Long>();
        for (int idx : tblExportCard.getSelectedRows()) {
            selectedIds.add((Long) tblExportCard.getValueAt(idx, 0));
        }
        return selectedIds;
    }
}
