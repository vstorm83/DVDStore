/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.MovieCatgoryProfitReportVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface MovieCatgoryDAO {

    public List<MovieCatgory> findMovieCatgory();

    public MovieCatgory getMovieCatgoryById(long movCatId);

    public MovieCatgory createMovieCatgory();

    public void saveMovieCatgory(MovieCatgory movCat);

    public void deleteAll(List<MovieCatgory> movCatList);

    public Long getMovCatIdByMovCatName(String trim);

    public List<MovieCatgoryProfitReportVO> findMovieCatgoryProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
}
