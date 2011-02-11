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
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieCatgoryVO;
import net.homeip.dvdstore.pojo.webservice.vo.ChooseMovieVO;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ChooseMovieDialogService")
public class ChooseMovieDialogServiceImpl implements ChooseMovieDialogService {
    private MovieCatgoryDAO movieCatgoryDAO;
    private MovieDAO movieDAO;
    
    public List<ChooseMovieCatgoryVO> getChooseMovieCatgoryVOs() {
        List<MovieCatgory> movieCatgories = movieCatgoryDAO.findMovieCatgory();

        List<ChooseMovieCatgoryVO> chooseMovieCatgoryVOs = 
                new ArrayList<ChooseMovieCatgoryVO>(movieCatgories.size());
        ChooseMovieCatgoryVO chooseMovieCatgoryVO;

        for (MovieCatgory movieCatgory : movieCatgories) {
            chooseMovieCatgoryVO = new ChooseMovieCatgoryVO();
            chooseMovieCatgoryVO.setMovCatName(movieCatgory.getMovCatName());
            chooseMovieCatgoryVO.setChooseMovieVOs(makeChooseMovieVOList(movieCatgory.getMovCatId()));
            chooseMovieCatgoryVOs.add(chooseMovieCatgoryVO);
        }
        return chooseMovieCatgoryVOs;
    }

    private List<ChooseMovieVO> makeChooseMovieVOList(Long movCatId) {
        List<Object[]> movIdNameList = movieDAO.findMovieIdAndNameByCatId(movCatId);
        List<ChooseMovieVO> chooseMovieVOs = new ArrayList<ChooseMovieVO>(movIdNameList.size());
        ChooseMovieVO chooseMovieVO;
        for (Object[] obj : movIdNameList) {
            chooseMovieVO = new ChooseMovieVO();
            chooseMovieVO.setMovId((Long)obj[0]);
            chooseMovieVO.setMovName((String)obj[1]);
            chooseMovieVOs.add(chooseMovieVO);
        }
        return chooseMovieVOs;
    }

    public void setMovieCatgoryDAO(MovieCatgoryDAO movieCatgoryDAO) {
        this.movieCatgoryDAO = movieCatgoryDAO;
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

}
