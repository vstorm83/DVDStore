
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.ExportCardVO;
import net.homeip.dvdstore.pojo.webservice.vo.MovieCatgoryProfitReportVO;
import net.homeip.dvdstore.service.ReportService;
import net.homeip.dvdstore.webservice.ExportCardPanelService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.ResultSetMappingDefinition;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VU VIET PHUONG
 */
public class TestJasper {

    public static String PATH = "E:\\Practice\\java\\DVDStoreTemp\\src\\net\\homeip\\dvdstore\\report\\";

    public static void main(String[] args) throws JRException, IOException {

//        JasperReport importCardReport = compileReport(PATH + "ImportCardReport.jrxml");
//        JasperReport importCardItemReport = compileReport(PATH + "ImportCardItemReport.jrxml");
//        compileReportToFile(PATH + "DirectorProfitReport.jrxml");
//        compileReportToFile(PATH + "ActorProfitReport.jrxml");
//        compileReportToFile(PATH + "SupplierProfitReport.jrxml");
//        compileReportToFile(PATH + "UserProfitReport.jrxml");
//        compileReportToFile(PATH + "TimeProfitReport.jrxml");
//        compileReportToFile(PATH + "TimeProfitChart.jrxml");
//        compileReportToFile(PATH + "MovieCatgoryProfitReport.jrxml");
//        compileReportToFile(PATH + "ExportCardItemReport.jrxml");
//        compileReportToFile(PATH + "ExportCardReport.jrxml");
//        JasperReport exportCardReport = compileReport("ExportCardReport.jrxml");
//        JasperReport exportCardItemReport = compileReport("ExportCardItemReport.jrxml");

//        Map params = new HashMap();
//        params.put("subReport", JRLoader.loadObject(PATH + "ExportCardReport.jasper"));
//        params.put("comName", "Cộng hòa");
//        params.put("startDate", new DateSearchVO(3, 2010).toDate(true));
//        params.put("endDate", new DateSearchVO(3, 2010).toDate(false));
//        params.put("REPORT_LOCALE", new Locale("vi", "VN"));

//        List list = makeList();
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        JasperFillManager.fillReportToStream(
//                JRLoader.getFileInputStream(PATH + "MovieCatgoryProfitReport.jasper"),
//                outStream,
//                params, new JRBeanCollectionDataSource(list));
//        ReportService service =
//                (ReportService) ApplicationContextUtil.getApplicationContext().getBean(
//                "reportService");
//        List<Long> list = new ArrayList<Long>();
//        for (int i = 1; i < 20; i++) {
//            list.add(Long.valueOf(i));
//        }
//        JasperViewer.viewReport(new ByteArrayInputStream(
//                service.reportActorProfit(new DateSearchVO(3, 2008), new DateSearchVO(3, 2011))), false, false);
//        JasperViewer.viewReport(new ByteArrayInputStream(
//                service.reportDirectorProfit(new DateSearchVO(3, 2008), new DateSearchVO(3, 2011))), false, false);
//        JasperViewer.viewReport(new ByteArrayInputStream(
//                service.reportSupplierProfit(new DateSearchVO(3, 2008), new DateSearchVO(3, 2011))), false, false);
//        JasperViewer.viewReport(new ByteArrayInputStream(
//                service.reportTimeProfit(new DateSearchVO(3, 2008), new DateSearchVO(3, 2011))), false, false);
//          JasperViewer.viewReport(new ByteArrayInputStream(
//                service.reportTimeProfitByChart(new DateSearchVO(3, 2010), new DateSearchVO(8, 2010))), false, false);
//        JasperViewer.viewReport(new ByteArrayInputStream(
//                service.reportUserProfit(new DateSearchVO(3, 2008), new DateSearchVO(3, 2011))), false, false);
    }

    private static JasperReport compileReport(String fileName) throws JRException {
        return JasperCompileManager.compileReport(JRLoader.getFileInputStream(PATH + fileName));
    }

//    private static List makeList() {
//        ExportCardPanelService service =
//                (ExportCardPanelService) ApplicationContextUtil.getApplicationContext().getBean(
//                "exportCardPanelService");
//        GregorianCalendar startDateSearch = new GregorianCalendar(2000, 1, 1);
//        GregorianCalendar endDateSearch = new GregorianCalendar(2012, 1, 1);
//        List<ExportCardVO> exportCardVOs = service.getExportCardVOs("",
//                startDateSearch.getTime(), endDateSearch.getTime());
//        return exportCardVOs;
//    }

    private static void compileReportToFile(String path) throws JRException {
        JasperCompileManager.compileReportToFile(path);
    }
}
