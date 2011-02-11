/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

// <editor-fold defaultstate="collapsed" desc="import">
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.ConfigurationDAO;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.InitGoodsIFrameVO;
import net.homeip.dvdstore.service.MovieService;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.GoodsIFrameService")
public class GoodsIFrameServiceImpl implements GoodsIFrameService {
    private MovieService movieService;
    private MovieCatgoryService movieCatgoryService;
    private DirectorService directorService;

    @Override
    public void upload(String fileName, byte[] fileUploaded) {
        if (fileUploaded == null || ValidatorUtil.isEmpty(fileName)) {
            return;
        }
        try {
            String path = this.getClass().getResource("../../../../../../" +
                    ConfigurationDAO.UPLOAD_DIR).getPath().replaceAll("%20", " ");            
            File serverFile = new File(path + "/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(serverFile);
            fileOutputStream.write(fileUploaded);
            fileOutputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException {
        movieService.addMovie(goodsMovieVO);
    }

    @Override
    public void deleteMovie(List<Long> goodsMovieIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        movieService.deleteMovie(goodsMovieIdList, ignoreReference);
    }

    @Override
    public void updateMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException {
        movieService.updateMovie(goodsMovieVO);
    }

    @Override
    public InitGoodsIFrameVO getInitGoodsIFrameVO(String searchByMovName, MovieCatgoryVO searchByMovCat) {
        InitGoodsIFrameVO initGoodsIFrameVO = new InitGoodsIFrameVO();
        initGoodsIFrameVO.setDirectorVOs(directorService.getDirectorList());
        initGoodsIFrameVO.setMovieCatgoryVOs(movieCatgoryService.getMovieCatgoryList());
        if (searchByMovCat == null && initGoodsIFrameVO.getMovieCatgoryVOs().size() > 0) {
            searchByMovCat = initGoodsIFrameVO.getMovieCatgoryVOs().get(0);
        }
        initGoodsIFrameVO.setGoodsMovieVOs(movieService.findGoodsMovieVOs(
                TextUtil.normalize(searchByMovName), searchByMovCat));
        return initGoodsIFrameVO;
    }

    @Override
    public List<GoodsMovieVO> getGoodsMovieVOs(String searchByMovName, MovieCatgoryVO searchByMovCat) {
        return movieService.findGoodsMovieVOs(TextUtil.normalize(searchByMovName), searchByMovCat);
    }   

    public void setDirectorService(DirectorService directorService) {
        this.directorService = directorService;
    }

    public void setMovieCatgoryService(MovieCatgoryService movieCatgoryService) {
        this.movieCatgoryService = movieCatgoryService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

}
