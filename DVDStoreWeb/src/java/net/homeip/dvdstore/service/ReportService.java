/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ReportService {
    public static final int MOVIE_CATGORY = 0;
    public static final int MOVIE = 1;
    public static final int DIRECTOR = 2;
    public static final int ACTOR = 3;
    public static final int SUPPLIER = 4;
    public static final int USER = 5;
    public static final int TIME = 6;
    public static final int TIME_CHART = 7;
    public static final int EXPORT_CARD = 8;
    public static final int IMPORT_CARD = 9;
    
    public byte[] renderReport(int reportType, List data, DateSearchVO startDate, DateSearchVO endDate);
}
