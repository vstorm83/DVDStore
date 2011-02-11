/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReportDialog.java
 *
 * Created on Mar 12, 2010, 12:47:55 AM
 */
package net.homeip.dvdstore.dialog;

// <editor-fold defaultstate="collapsed" desc="import">
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.ReportDialogListener;
import net.homeip.dvdstore.listener.ResultRetrievedListener;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.event.ResultRetrievedEvent;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.view.JasperViewer;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ReportDialog extends javax.swing.JDialog {

    private DateSearchVO startDate;
    private DateSearchVO endDate;
    private LoadDataModel<byte[]> reportModel;
    private ReportDialogListener reportDialogListener;

    /** Creates new form ReportDialog */
    public ReportDialog(java.awt.Frame parent) {
        super(parent, false);
        initComponents();
        prepareSpin();
        prepareReportList();

        reportDialogListener =
                (ReportDialogListener) ApplicationContextUtil.getApplicationContext().getBean(
                "reportDialogListener");
        prepareLoadData();
    }

    // <editor-fold defaultstate="collapsed" desc="load Report">
    private void prepareLoadData() {
        reportModel = new LoadDataModel<byte[]>();
        reportModel.addResultRetrievedListener(new ResultRetrievedListener() {

            public void onResultRetrieved(ResultRetrievedEvent evt) {
                onReportDataRetrieved(evt);
            }
        });
    }

    private void dispatchLoadReportDataEvent() {
        btnReport.setEnabled(false);
        LoadDataEvent evt = new LoadDataEvent(this);
        evt.setLoadDataModel(reportModel);
        List params = new ArrayList();
        params.add(lstReport.getSelectedIndex());
        params.add(startDate);
        params.add(endDate);
        evt.setParams(params);
        reportDialogListener.onLoadDataPerformed(evt);
    }

    private void onReportDataRetrieved(ResultRetrievedEvent evt) {
        String errorMsg = null;
        switch (reportModel.getStatus()) {
            case LoadDataModel.OK:
                showReport(reportModel.getData());
                break;
            case LoadDataModel.SERVER_CONNECTION_ERROR:
                errorMsg = "Không kết nối được với Server";
                break;
            default:
                errorMsg = "Lỗi Server";
        }
        btnReport.setEnabled(true);
        if (errorMsg != null) {
            JOptionPane.showMessageDialog(this, errorMsg);
        }
    }
    // </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        lstReport = new javax.swing.JList();
        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        spinStartMonth = new javax.swing.JSpinner();
        spinEndMonth = new javax.swing.JSpinner();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        spinStartYear = new javax.swing.JSpinner();
        spinEndYear = new javax.swing.JSpinner();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        btnReport = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Báo cáo");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Danh sách báo cáo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        lstReport.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "  Báo cáo doanh thu theo phim", "  Báo cáo doanh thu theo loại phim", "  Báo cáo doanh thu theo đạo diễn", "  Báo cáo doanh thu theo diễn viên", "  Báo cáo tình hình nhập hàng", "  Báo cáo doanh thu theo khách hàng", "  Báo cáo doanh thu theo tháng", "  Biều đồ doanh thu theo tháng" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstReport);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thời gian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel1.setText("Tháng bắt đầu:");

        jLabel2.setText("Tháng kết thúc:");

        spinStartMonth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        spinEndMonth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 12, 1));

        jLabel3.setText("Năm:");

        spinStartYear.setModel(new javax.swing.SpinnerNumberModel(2009, 2009, 9999, 1));

        spinEndYear.setModel(new javax.swing.SpinnerNumberModel(2009, 2009, 9999, 1));

        jLabel4.setText("Năm:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spinEndMonth)
                    .addComponent(spinStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spinEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(58, 58, 58))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinStartYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(jLabel1)
                                .addComponent(spinStartMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(jLabel2)
                                .addComponent(spinEndMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(spinEndYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        btnReport.setText("Báo cáo");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        btnClose.setText("Đóng cửa sổ");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(btnReport)
                .addGap(27, 27, 27)
                .addComponent(btnClose)
                .addGap(93, 93, 93))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReport)
                    .addComponent(btnClose))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-402)/2, (screenSize.height-463)/2, 402, 463);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        // TODO add your handling code here:
        startDate.setMonth((Integer) spinStartMonth.getValue());
        startDate.setYear((Integer) spinStartYear.getValue());
        endDate.setMonth((Integer) spinEndMonth.getValue());
        endDate.setYear((Integer) spinEndYear.getValue());

        if (endDate.compareTo(startDate) == -1) {
            JOptionPane.showMessageDialog(this,
                    "Thời gian kết thúc cần bằng hoặc sau thời gian bắt đầu");
            return;
        }
        dispatchLoadReportDataEvent();
    }//GEN-LAST:event_btnReportActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnReport;
    private javax.swing.JList lstReport;
    private javax.swing.JSpinner spinEndMonth;
    private javax.swing.JSpinner spinEndYear;
    private javax.swing.JSpinner spinStartMonth;
    private javax.swing.JSpinner spinStartYear;
    // End of variables declaration//GEN-END:variables

    private void prepareSpin() {
        GregorianCalendar today = new GregorianCalendar();
        spinStartMonth.setValue(today.get(Calendar.MONTH) + 1);
        spinEndMonth.setValue(today.get(Calendar.MONTH) + 1);
        spinStartYear.setValue(today.get(Calendar.YEAR));
        spinEndYear.setValue(today.get(Calendar.YEAR));
        startDate = new DateSearchVO();
        endDate = new DateSearchVO();
    }

    private void prepareReportList() {
        lstReport.setFixedCellHeight(20);
        lstReport.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                int selectedIdx = lstReport.getSelectedIndex();
                switch (selectedIdx) {
                    case -1:
                        btnReport.setEnabled(false);
                        break;
                    default:
                        btnReport.setEnabled(true);
                }
            }
        });
        lstReport.setSelectedIndex(0);
    }

    private void showReport(byte[] reportData) {
        try {
            JasperViewer.viewReport(new ByteArrayInputStream(reportData), false, false);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
}
