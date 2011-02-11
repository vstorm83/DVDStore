/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitGoodsIFrameVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface GoodsIFrameService {

    public InitGoodsIFrameVO getInitGoodsIFrameVO(String searchByMovName, MovieCatgoryVO searchByMovCat);

    public List<GoodsMovieVO> getGoodsMovieVOs(String searchByMovName, MovieCatgoryVO searchByMovCat);

    public void addMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteMovie(List<Long> goodsMovieIdList, boolean ignoreReference)
            throws DBReferenceViolationException;;

    public void updateMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException;;

    public void upload(String fileName, byte[] fileUploaded);
}
