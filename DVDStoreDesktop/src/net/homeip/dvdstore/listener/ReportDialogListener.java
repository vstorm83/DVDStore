/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener;

import javax.swing.SwingWorker;
import net.homeip.dvdstore.delegate.ReportDialogServiceDelegate;
import net.homeip.dvdstore.delegate.exception.ServerConnectionException;
import net.homeip.dvdstore.delegate.exception.ServerException;
import net.homeip.dvdstore.listener.event.LoadDataEvent;
import net.homeip.dvdstore.listener.frame.LoadDataListener;
import net.homeip.dvdstore.model.LoadDataModel;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ReportDialogListener implements LoadDataListener {

    private ReportDialogServiceDelegate reportDialogServiceDelegate;

    public void onLoadDataPerformed(LoadDataEvent evt) {
        LoadDataModel<byte[]> reportDataModel = evt.getLoadDataModel();
        int reportType = (Integer) evt.getParams().get(0);
        DateSearchVO startDate = (DateSearchVO) evt.getParams().get(1);
        DateSearchVO endDate = (DateSearchVO) evt.getParams().get(2);

        callLoadReportDataService(reportDataModel, reportType, startDate, endDate);
    }

    public void setReportDialogServiceDelegate(ReportDialogServiceDelegate reportDialogServiceDelegate) {
        this.reportDialogServiceDelegate = reportDialogServiceDelegate;
    }

    private void callLoadReportDataService(final LoadDataModel<byte[]> reportDataModel,
            final int reportType, final DateSearchVO startDate, final DateSearchVO endDate) {
        if (reportDialogServiceDelegate == null) {
            throw new IllegalStateException("reportDialogServiceDelegate has not set");
        }
        SwingWorker<Integer, Void> swingWorker = new SwingWorker<Integer, Void>() {

            @Override
            protected Integer doInBackground() throws Exception {
                try {
                    byte[] reportData = loadData(reportType);
                    reportDataModel.setData(reportData);
                } catch (ServerConnectionException ex) {
                    ex.printStackTrace();
                    return LoadDataModel.SERVER_CONNECTION_ERROR;
                } catch (ServerException ex) {
                    ex.printStackTrace();
                    return LoadDataModel.SERVER_ERROR;
                }
                return LoadDataModel.OK;
            }

            @Override
            protected void done() {
                try {
                    reportDataModel.setStatus(get());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            private byte[] loadData(int reportType) {
                switch (reportType) {
                    case 0:
                        return reportDialogServiceDelegate.reportMovieProfit(startDate, endDate);
                    case 1:
                        return reportDialogServiceDelegate.reportMovieCatgoryProfit(startDate, endDate);
                    case 2:
                        return reportDialogServiceDelegate.reportDirectorProfit(startDate, endDate);
                    case 3:
                        return reportDialogServiceDelegate.reportActorProfit(startDate, endDate);
                    case 4:
                        return reportDialogServiceDelegate.reportSupplierProfit(startDate, endDate);
                    case 5:
                        return reportDialogServiceDelegate.reportUserProfit(startDate, endDate);
                    case 6:
                        return reportDialogServiceDelegate.reportTimeProfit(startDate, endDate);
                    default:
                        return reportDialogServiceDelegate.reportTimeProfitByChart(startDate, endDate);
                }
            }
        };
        swingWorker.execute();
    }
}
