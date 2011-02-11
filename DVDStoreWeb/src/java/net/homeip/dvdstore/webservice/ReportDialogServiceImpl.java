/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import javax.jws.WebService;
import net.homeip.dvdstore.dao.ActorDAO;
import net.homeip.dvdstore.dao.DirectorDAO;
import net.homeip.dvdstore.dao.MovieCatgoryDAO;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.dao.SupplierDAO;
import net.homeip.dvdstore.dao.TimeDAO;
import net.homeip.dvdstore.dao.UserDAO;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.service.ReportService;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ReportDialogService")
public class ReportDialogServiceImpl implements ReportDialogService {
    private ReportService reportService;
    private MovieCatgoryDAO movieCatgoryDAO;
    private MovieDAO movieDAO;
    private DirectorDAO directorDAO;
    private ActorDAO actorDAO;
    private SupplierDAO supplierDAO;
    private UserDAO userDAO;
    private TimeDAO timeDAO;

    public byte[] reportMovieCatgoryProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.MOVIE_CATGORY,
                movieCatgoryDAO.findMovieCatgoryProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportMovieProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.MOVIE,
               movieDAO.findMovieProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportActorProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.ACTOR,
               actorDAO.findActorProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportDirectorProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.DIRECTOR,
               directorDAO.findDirectorProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportSupplierProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.SUPPLIER,
               supplierDAO.findSupplierProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportTimeProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.TIME,
               timeDAO.findTimeProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportTimeProfitByChart(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.TIME_CHART,
               timeDAO.findTimeProfitVOs(startDate, endDate), startDate, endDate);
    }

    public byte[] reportUserProfit(DateSearchVO startDate, DateSearchVO endDate) {
        return reportService.renderReport(reportService.USER,
               userDAO.findUserProfitVOs(startDate, endDate), startDate, endDate);
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public void setDirectorDAO(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    public void setActorDAO(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    public void setSupplierDAO(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public void setTimeDAO(TimeDAO timeDAO) {
        this.timeDAO = timeDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setReportService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void setMovieCatgoryDAO(MovieCatgoryDAO movieCatgoryDAO) {
        this.movieCatgoryDAO = movieCatgoryDAO;
    }
}
