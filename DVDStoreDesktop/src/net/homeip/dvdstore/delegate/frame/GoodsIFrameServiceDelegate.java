/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.frame;

import java.io.File;
import java.util.List;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitGoodsIFrameVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface GoodsIFrameServiceDelegate {

    public InitGoodsIFrameVO getInitGoodsIFrameVO(String searchByMovName,
            MovieCatgoryVO searchByMovCat);

    public List<GoodsMovieVO> getGoodsMovieVOs(String searchByMovName,
            MovieCatgoryVO searchByMovCat);

    public void addMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidInputException, DuplicateException;;

    public void deleteMovie(List<Long> goodsMovieIdList,
            boolean ignoreReference) throws DBReferenceViolationException;

    public void updateMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidInputException, DuplicateException;;

    public void upload(String fileName, byte[] fileUploaded);
}
