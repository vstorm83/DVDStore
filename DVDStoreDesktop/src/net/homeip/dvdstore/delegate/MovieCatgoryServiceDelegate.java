/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface MovieCatgoryServiceDelegate {

    public List<MovieCatgoryVO> getMovieCatgoryList();

    public void addMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidInputException, DuplicateException;

    public void updateMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidInputException, DuplicateException;

    public void deleteMovCat(List<Long> movCatIdList, boolean ignoreReference)
            throws DBReferenceViolationException;

}
