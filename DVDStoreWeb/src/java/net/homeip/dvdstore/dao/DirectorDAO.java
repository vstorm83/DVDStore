/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Director;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorProfitReportVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface DirectorDAO {

    public List<Director> findDirector();

    public Director getDirectorById(long directorId);

    public Director createDirector();

    public void saveDirector(Director director);

    public void deleteAll(List<Director> directorList);

    public Long getDirectorIdByDirectorName(String directorName);

    public List<DirectorProfitReportVO> findDirectorProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
}
