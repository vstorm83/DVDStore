/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ChooseMovieDialogServiceDelegate {
    public List<ChooseMovieCatgoryVO> getChooseMovieCatgoryVOs();
}
