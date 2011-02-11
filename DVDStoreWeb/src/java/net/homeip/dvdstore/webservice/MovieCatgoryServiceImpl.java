/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.MovieCatgoryDAO;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.MovieCatgoryService")
public class MovieCatgoryServiceImpl implements MovieCatgoryService {

    private MovieCatgoryDAO movieCatgoryDAO;
    private MovieDAO movieDAO;
    private Logger logger = Logger.getLogger(MovieCatgoryServiceImpl.class);

    @Override
    public List<MovieCatgoryVO> getMovieCatgoryList() {
        List<MovieCatgory> movCatList = movieCatgoryDAO.findMovieCatgory();
        List<MovieCatgoryVO> voList = new ArrayList<MovieCatgoryVO>(movCatList.size());
        MovieCatgoryVO vo;
        for (MovieCatgory movCat : movCatList) {
            vo = new MovieCatgoryVO();
            vo.setMovCatId(movCat.getMovCatId());
            vo.setMovCatName(movCat.getMovCatName());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public MovieCatgoryVO getMovieCatgoryById(long movCatId) {
        MovieCatgory movCat = movieCatgoryDAO.getMovieCatgoryById(movCatId);
        if (movCat == null) {
            return null;
        }
        MovieCatgoryVO vo = new MovieCatgoryVO();
        vo.setMovCatId(movCat.getMovCatId());
        vo.setMovCatName(movCat.getMovCatName());
        return vo;
    }

    public void addMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidAdminInputException, DuplicateException {
        if (movieCatgoryVO == null) {
            throw new IllegalArgumentException("can't add null movieCatgoryVO");
        }
        logger.trace("addMovCat movCatName=" + movieCatgoryVO.getMovCatName());
        MovieCatgory movCat = movieCatgoryDAO.createMovieCatgory();
        movCat.populate(movieCatgoryVO, movieCatgoryDAO);
        movieCatgoryDAO.saveMovieCatgory(movCat);
    }

    public void deleteMovCat(List<Long> movCatIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        if (movCatIdList == null) {
            throw new IllegalArgumentException("movCatIdList is null");
        }

        List<MovieCatgory> movCatList = new ArrayList<MovieCatgory>(movCatIdList.size());
        List<String> violationNameList = new ArrayList<String>();
        MovieCatgory movieCatgory;
        for (Long id : movCatIdList) {
            movieCatgory = movieCatgoryDAO.getMovieCatgoryById(id);
            if (movieDAO.findMovieByCatId(id, 1, 1).getMovieList().size() > 0) {
                violationNameList.add(movieCatgory.getMovCatName());
            } else {
                movCatList.add(movieCatgory);
            }
        }
        if (violationNameList.size() > 0) {
            DBReferenceViolationException exp = new DBReferenceViolationException();
            exp.setViolationName(violationNameList);
            throw exp;
        } else {
            movieCatgoryDAO.deleteAll(movCatList);
        }
    }

    public void updateMovCat(MovieCatgoryVO movieCatgoryVO)
            throws InvalidAdminInputException, DuplicateException {
        if (movieCatgoryVO == null) {
            throw new IllegalArgumentException("can't update null movieCatgoryVO");
        }
        MovieCatgory movieCatgory = movieCatgoryDAO.getMovieCatgoryById(movieCatgoryVO.getMovCatId());
        if (movieCatgory == null) {
            throw new IllegalStateException("can't update movieCatgory has not found");
        }
        movieCatgory.populate(movieCatgoryVO, movieCatgoryDAO);
        movieCatgoryDAO.saveMovieCatgory(movieCatgory);
    }    
    
    public void setMovieCatgoryDAO(MovieCatgoryDAO movieCatgoryDAO) {
        this.movieCatgoryDAO = movieCatgoryDAO;
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }
}
