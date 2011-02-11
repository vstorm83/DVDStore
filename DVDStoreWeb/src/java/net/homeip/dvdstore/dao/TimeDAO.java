/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.TimeProfitReportVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface TimeDAO {
    public List<TimeProfitReportVO> findTimeProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
}
