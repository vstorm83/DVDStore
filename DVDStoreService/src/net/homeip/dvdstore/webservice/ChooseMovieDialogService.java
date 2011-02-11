/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface ChooseMovieDialogService {

    public List<ChooseMovieCatgoryVO> getChooseMovieCatgoryVOs();

}
