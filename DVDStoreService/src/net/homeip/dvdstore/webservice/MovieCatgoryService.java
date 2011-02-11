/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface MovieCatgoryService {
    /**
     * get all movie catgory
     * @return list of MovieCatgoryVO, never return null
     */
    List<MovieCatgoryVO> getMovieCatgoryList();
    /**
     * get movie catgory by id
     * @param movCatId
     * @return MovieCatgoryVO, if not found, return null
     */
    MovieCatgoryVO getMovieCatgoryById(long movCatId);

    public void addMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteMovCat(List<Long> movCatIdList, boolean ignoreReference)
            throws DBReferenceViolationException;

    public void updateMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidAdminInputException, DuplicateException;
}
