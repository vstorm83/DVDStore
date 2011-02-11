/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

// <editor-fold defaultstate="collapsed" desc="import">
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
public class ReportServiceImpl implements ReportService {

    // <editor-fold defaultstate="collapsed" desc="JasperReport">
    private JasperReport movCatProfitReport;
    private JasperReport movProfitReport;
    private JasperReport directorProfitReport;
    private JasperReport actorProfitReport;
    private JasperReport supplierProfitReport;
    private JasperReport userProfitReport;
    private JasperReport timeProfitReport;
    private JasperReport timeProfitChart;
    private JasperReport exportCardReport;
    private JasperReport exportCardItemReport;
    private JasperReport importCardReport;
    private JasperReport importCardItemReport;

    {
        String path = "net/homeip/dvdstore/report/";
        try {
            movCatProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "MovieCatgoryProfitReport.jasper");
            movProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "MovieProfitReport.jasper");
            directorProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "DirectorProfitReport.jasper");
            actorProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "ActorProfitReport.jasper");
            supplierProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "SupplierProfitReport.jasper");
            userProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "UserProfitReport.jasper");
            timeProfitReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "TimeProfitReport.jasper");
            timeProfitChart = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "TimeProfitChart.jasper");
            exportCardReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "ExportCardReport.jasper");
            exportCardItemReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "ExportCardItemReport.jasper");
            importCardReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "ImportCardReport.jasper");
            importCardItemReport = (JasperReport) JRLoader.loadObjectFromLocation(
                    path + "ImportCardItemReport.jasper");
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }
    // </editor-fold>
    //service
    private ConfigurationService configurationService;

    public byte[] renderReport(int reportType, List data,
            DateSearchVO startDate, DateSearchVO endDate) {
        JasperReport jasperReport;
        Map params = null;
        switch (reportType) {
            case MOVIE_CATGORY:
                jasperReport = movCatProfitReport;
                break;
            case MOVIE:
                jasperReport = movProfitReport;
                break;
            case DIRECTOR:
                jasperReport = directorProfitReport;
                break;
            case ACTOR:
                jasperReport = actorProfitReport;
                break;
            case SUPPLIER:
                jasperReport = supplierProfitReport;
                break;
            case USER:
                jasperReport = userProfitReport;
                break;
            case TIME:
                jasperReport = timeProfitReport;
                break;
            case TIME_CHART:
                jasperReport = timeProfitChart;
                break;
            case EXPORT_CARD:
                jasperReport = exportCardReport;
                params = new HashMap();
                params.put("subReport", exportCardItemReport);
                params.put("comName", configurationService.getCompanyInfo().getComName());
                params.put("REPORT_LOCALE", new Locale("vi", "VN"));
                break;
            default:
                jasperReport = importCardReport;
                params = new HashMap();
                params.put("subReport", importCardItemReport);
                params.put("comName", configurationService.getCompanyInfo().getComName());
                params.put("REPORT_LOCALE", new Locale("vi", "VN"));

        }
        params = params == null?makeParams(startDate, endDate):params;
        return fillData(jasperReport, params, data);
    }

    private byte[] fillData(JasperReport jasReport, Map params, List list) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            JasperFillManager.fillReportToStream(jasReport,
                    outStream, params,
                    new JRBeanCollectionDataSource(list));
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return outStream.toByteArray();
    }

    private Map makeParams(DateSearchVO startDate, DateSearchVO endDate) {
        Map params = new HashMap();
        params.put("startDate", startDate.toDate(true));
        params.put("endDate", endDate.toDate(false));
        params.put("REPORT_LOCALE", new Locale("vi", "VN"));
        return params;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
