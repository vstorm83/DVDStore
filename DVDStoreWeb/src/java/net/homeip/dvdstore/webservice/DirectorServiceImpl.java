/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.DirectorDAO;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.pojo.Director;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.DirectorService")
public class DirectorServiceImpl implements DirectorService {

    private DirectorDAO directorDAO;
    private MovieDAO movieDAO;
    private Logger logger = Logger.getLogger(DirectorServiceImpl.class);

    @Override
    public List<DirectorVO> getDirectorList() {
        List<Director> directorList = directorDAO.findDirector();
        List<DirectorVO> voList = new ArrayList<DirectorVO>(directorList.size());
        DirectorVO vo;
        for (Director director : directorList) {
            vo = new DirectorVO();
            vo.setDirectorId(director.getDirectorId());
            vo.setDirectorName(director.getDirectorName());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public DirectorVO getDirectorById(long directorId) {
        Director director = directorDAO.getDirectorById(directorId);
        if (director == null) {
            return null;
        }
        DirectorVO vo = new DirectorVO();
        vo.setDirectorId(director.getDirectorId());
        vo.setDirectorName(director.getDirectorName());
        return vo;
    }

    public void addDirector(DirectorVO directorVO)
            throws InvalidAdminInputException, DuplicateException {
        if (directorVO == null) {
            throw new IllegalArgumentException("can't add null directorVO");
        }
        logger.trace("addDirector directorName=" + directorVO.getDirectorName());
        Director director = directorDAO.createDirector();
        director.populate(directorVO, directorDAO);
        directorDAO.saveDirector(director);
    }

    public void deleteDirector(List<Long> directorIdList)
            throws DBReferenceViolationException {
        if (directorIdList == null) {
            throw new IllegalArgumentException("directorIdList is null");
        }

        List<Director> directorList = new ArrayList<Director>(directorIdList.size());        
        for (Long id : directorIdList) {
            resetMovie(id);
            directorList.add(directorDAO.getDirectorById(id));
        }
        directorDAO.deleteAll(directorList);
    }

    public void updateDirector(DirectorVO directorVO)
            throws InvalidAdminInputException, DuplicateException {
        if (directorVO == null) {
            throw new IllegalArgumentException("can't update null directorVO");
        }
        Director director = directorDAO.getDirectorById(directorVO.getDirectorId());
        if (director == null) {
            throw new IllegalStateException("can't update director has not found");
        }
        director.populate(directorVO, directorDAO);
        directorDAO.saveDirector(director);
    }

    public void setDirectorDAO(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    private void resetMovie(Long id) {
        for (Movie mov : movieDAO.findMovieByDirectorId(id)) {
            mov.setDirector(null);
            movieDAO.saveMovie(mov);
        }
    }
}
