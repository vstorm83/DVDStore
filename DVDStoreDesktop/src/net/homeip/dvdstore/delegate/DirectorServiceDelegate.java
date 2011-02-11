/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.webservice.exception.DuplicateException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface DirectorServiceDelegate {

    public List<DirectorVO> getDirectorList();

    public void addDirector(DirectorVO directorVO)
            throws InvalidInputException, DuplicateException;

    public void updateDirector(DirectorVO directorVO)
            throws InvalidInputException, DuplicateException;

    public void deleteDirector(List<Long> directorIdList);

}

