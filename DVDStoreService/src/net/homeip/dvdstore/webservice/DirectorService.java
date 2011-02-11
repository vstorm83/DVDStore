/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface DirectorService {

    List<DirectorVO> getDirectorList();

    DirectorVO getDirectorById(long directorId);

    public void addDirector(DirectorVO directorVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteDirector(List<Long> directorIdList);

    public void updateDirector(DirectorVO directorVO)
            throws InvalidAdminInputException, DuplicateException;
}
