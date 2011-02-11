/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import net.homeip.dvdstore.dao.ActorDAO;
import net.homeip.dvdstore.dao.ConfigurationDAO;
import net.homeip.dvdstore.dao.DirectorDAO;
import net.homeip.dvdstore.dao.ImportCardDAO;
import net.homeip.dvdstore.dao.MovieCatgoryDAO;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.dao.OrderDAO;
import net.homeip.dvdstore.pojo.Actor;
import net.homeip.dvdstore.pojo.Director;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.MovieCatgory;
import net.homeip.dvdstore.pojo.web.vo.CompactMovieVO;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.pojo.web.vo.MovieDetailVO;
import net.homeip.dvdstore.pojo.web.vo.MovieSearchResult;
import net.homeip.dvdstore.pojo.web.vo.NewestMovieVO;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieServiceImpl implements MovieService {

    private MovieDAO movieDAO;
    private MovieCatgoryDAO movieCatgoryDAO;
    private DirectorDAO directorDAO;
    private ActorDAO actorDAO;
    private ImportCardDAO importCardDAO;
    private OrderDAO orderDAO;
    private ConfigurationService configurationService;
    private static Logger logger = Logger.getLogger(MovieServiceImpl.class);

    @Override
    public MovieDetailVO getMovieDetailVO(Long movId) {
        logger.trace("getMovie movId=" + movId);
        Movie movie = movieDAO.getMovieById(movId);
        MovieDetailVO vo = makeMovieDetailVO(movie);
        return vo;
    }

    @Override
    public NewestMovieVO findNewestMovie() {
        Movie movie = movieDAO.getNewestMovie();
        NewestMovieVO vo = makeNewestMovieVO(movie);
        logger.trace("getNewestMovie vo=" + vo);
        return vo;
    }

    @Override
    public List<CompactMovieVO> findBestMovie(int bestMovieNum) {
        if (bestMovieNum < 0) {
            throw new IllegalArgumentException("bestMovieNum is negative");
        }
        List<Movie> movieList = movieDAO.findBestMovie(bestMovieNum);
        List<CompactMovieVO> compactMovieVOList = makeCompactMovieVOList(movieList);
        logger.trace("getBestMovieList " + compactMovieVOList.size());
        return compactMovieVOList;
    }

    @Override
    public List<CompactMovieVO> findNewMovie(int newMovieNum) {
        if (newMovieNum < 0) {
            throw new IllegalArgumentException("newMovieNum is negative");
        }
        List<Movie> movieList = movieDAO.findNewMovie(newMovieNum);
        List<CompactMovieVO> compactMovieVOList = makeCompactMovieVOList(movieList);
        logger.trace("getNewMovieList " + compactMovieVOList.size());
        return compactMovieVOList;
    }

    @Override
    public MovieSearchResult findMovie(String movName, String actName, int pgIdx, int pgSize) {
        logger.trace("movName " + movName + " pgIdx " + pgIdx + " pgSize " + pgSize);
        MovieSearchResult movieSearchResult = movieDAO.findMovie(movName, actName, pgIdx, pgSize);
        List<CompactMovieVO> compactMovieVOList = makeCompactMovieVOList(movieSearchResult.getMovieList());
        logger.trace("findMovie " + compactMovieVOList.size());

        return makeSearchResult(compactMovieVOList, movieSearchResult.getPageNum());
    }

    @Override
    public MovieSearchResult findMovieByCatId(long movCatId, int pgIdx, int pgSize) {
        logger.trace("movCatId  " + movCatId + " pgIdx " + pgIdx + " pgSize " + pgSize);
        MovieSearchResult movieSearchResult = movieDAO.findMovieByCatId(movCatId, pgIdx, pgSize);
        List<CompactMovieVO> voList = makeCompactMovieVOList(movieSearchResult.getMovieList());
        logger.trace("findMovieByCatId " + voList.size());

        return makeSearchResult(voList, movieSearchResult.getPageNum());
    }

    @Override
    public MovieSearchResult findMovieByFirstLetter(String firstLetter, int pgIdx, int pgSize) {
        logger.info("firstLetter " + firstLetter + " pgIdx " + pgIdx + " pgSize " + pgSize);
        MovieSearchResult movieSearchResult = movieDAO.findMovieByFirstLetter(firstLetter, pgIdx, pgSize);
        List<CompactMovieVO> voList = makeCompactMovieVOList(movieSearchResult.getMovieList());
        logger.trace("findMovieByFirstLetter " + voList.size());

        return makeSearchResult(voList, movieSearchResult.getPageNum());
    }

    @Override
    public List<GoodsMovieVO> findGoodsMovieVOs(String searchByMovName, MovieCatgoryVO searchByMovCat) {
        List<Movie> movies = movieDAO.findMovie(searchByMovName, searchByMovCat);
        List<GoodsMovieVO> goodsMovieVOs = new ArrayList<GoodsMovieVO>(movies.size());
        GoodsMovieVO goodsMovieVO;
        for (Movie mov : movies) {
            goodsMovieVO = makeGoodsMovieVO(mov);
            goodsMovieVOs.add(goodsMovieVO);
        }
        return goodsMovieVOs;
    }

    // <editor-fold defaultstate="collapsed" desc="Make">
    private List<CompactMovieVO> makeCompactMovieVOList(List<Movie> movieList) {
        List<CompactMovieVO> compactMovieVOList = new ArrayList<CompactMovieVO>(movieList.size());
        CompactMovieVO vo;
        for (Movie movie : movieList) {
            vo = new CompactMovieVO();
            vo.setMovId(movie.getMovId());
            vo.setMovName(movie.getMovName());
            vo.setMovPicUrl(ConfigurationDAO.APSOLUTE_UPLOAD_DIR + movie.getMovPicName());
            compactMovieVOList.add(vo);
        }
        return compactMovieVOList;
    }

    private NewestMovieVO makeNewestMovieVO(Movie movie) {
        NewestMovieVO vo = null;
        if (movie != null) {
            vo = new NewestMovieVO();
            vo.setMovId(movie.getMovId());
            vo.setMovName(movie.getMovName());
            vo.setMovPicUrl(ConfigurationDAO.APSOLUTE_UPLOAD_DIR + movie.getMovPicName());
            vo.setMovDesc(movie.getMovDesc());
        }
        return vo;
    }

    private MovieSearchResult makeSearchResult(List<CompactMovieVO> compactMovieVOList, int pageNum) {
        MovieSearchResult voResult = new MovieSearchResult();
        voResult.setMovieList(compactMovieVOList);
        voResult.setPageNum(pageNum);
        return voResult;
    }

    private MovieDetailVO makeMovieDetailVO(Movie movie) {
        MovieDetailVO vo = null;
        if (movie != null) {
            vo = new MovieDetailVO();
            vo.setMovId(movie.getMovId());
            vo.setMovName(movie.getMovName());
            vo.setMovPicUrl(ConfigurationDAO.APSOLUTE_UPLOAD_DIR + movie.getMovPicName());
            vo.setMovPrice(movie.getMovPrice());
            vo.setMovSaleOff(movie.getMovSaleOff());
            vo.setMovCat(movie.getMovCat().getMovCatName());
            vo.setMovDesc(movie.getMovDesc());
            if (movie.getDirector() != null) {
                vo.setMovDirector(movie.getDirector().getDirectorName());
            }
            if (movie.getActor() != null) {
                List<String> actorList = new ArrayList<String>(movie.getActor().size());
                for (Actor actor : movie.getActor()) {
                    actorList.add(actor.getActorName());
                }
                vo.setMovActor(actorList);
            }
        }
        return vo;
    }

    private GoodsMovieVO makeGoodsMovieVO(Movie mov) {
        if (mov == null) {
            return null;
        }
        GoodsMovieVO goodsMovieVO = new GoodsMovieVO();
        goodsMovieVO.setActorVOs(makeActorVOs(mov.getActor()));
        TimeZone timeZone = TimeZone.getTimeZone(configurationService.getTimeZoneId());
        goodsMovieVO.setDateCreated(new Date(mov.getDateCreated().getTime() + timeZone.getRawOffset()));
        goodsMovieVO.setDirectorVO(makeDirectorVO(mov.getDirector()));
        goodsMovieVO.setMovCatVO(makeMovieCatgoryVO(mov.getMovCat()));
        goodsMovieVO.setMovDesc(TextUtil.transformMovDesc(mov.getMovDesc(),
                    ConfigurationDAO.APSOLUTE_UPLOAD_DIR));
        goodsMovieVO.setMovId(mov.getMovId());
        goodsMovieVO.setMovName(mov.getMovName());
        goodsMovieVO.setMovPicURL(ConfigurationDAO.APSOLUTE_UPLOAD_DIR + mov.getMovPicName());
        goodsMovieVO.setMovPrice(mov.getMovPrice());
        goodsMovieVO.setMovQuantity(movieDAO.getMovieQuantity(mov.getMovId()));
        goodsMovieVO.setMovSaleOff(mov.getMovSaleOff());
        return goodsMovieVO;
    }

    private MovieCatgoryVO makeMovieCatgoryVO(MovieCatgory movCat) {
        if (movCat == null) {
            return null;
        }
        MovieCatgoryVO movieCatgoryVO = new MovieCatgoryVO();
        movieCatgoryVO.setMovCatId(movCat.getMovCatId());
        movieCatgoryVO.setMovCatName(movCat.getMovCatName());
        return movieCatgoryVO;
    }

    private DirectorVO makeDirectorVO(Director director) {
        if (director == null) {
            return null;
        }
        DirectorVO directorVO = new DirectorVO();
        directorVO.setDirectorId(director.getDirectorId());
        directorVO.setDirectorName(director.getDirectorName());
        return directorVO;
    }

    private List<ActorVO> makeActorVOs(Collection<Actor> actors) {
        if (actors == null) {
            return null;
        }
        List<ActorVO> actorVOs = new ArrayList<ActorVO>(actors.size());
        ActorVO actorVO;
        for (Actor actor : actors) {
            actorVO = new ActorVO();
            actorVO.setActorId(actor.getActorId());
            actorVO.setActorName(actor.getActorName());
            actorVOs.add(actorVO);
        }
        return actorVOs;
    }
    // </editor-fold>

    public void addMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException {
        if (goodsMovieVO == null) {
            throw new IllegalArgumentException("can't add null goodsMovieVO");
        }
        logger.trace("addMovie goodsMovieVO.movName=" + goodsMovieVO.getMovName());
        Movie movie = movieDAO.createMovie();
        movie.populate(goodsMovieVO, movieDAO, movieCatgoryDAO, directorDAO,
                actorDAO, configurationService.getTimeZoneId());
        movieDAO.saveMovie(movie);
    }

    public void deleteMovie(List<Long> goodsMovieIdList, boolean ignoreReference)
            throws DBReferenceViolationException {
        if (goodsMovieIdList == null) {
            throw new IllegalArgumentException("goodsMovieIdList is null");
        }
        logger.trace("deleteMovie goodsMovieIdList.size()=" + goodsMovieIdList.size());
        List<Movie> movieList = new ArrayList<Movie>(goodsMovieIdList.size());
        List<String> violationNameList = new ArrayList<String>();
        Movie movie;
        for (Long id : goodsMovieIdList) {
            movie = movieDAO.getMovieById(id);
            if (!ignoreReference) {
                if (importCardDAO.findImportCardByMovieId(id).size() > 0
                        || orderDAO.findOrderByMovieId(id).size() > 0) {
                    violationNameList.add(movie.getMovName());
                }
            }
            movieList.add(movie);
        }
        if (violationNameList.size() > 0) {
            logger.trace("deleteMovie violationNameList.size()=" + violationNameList.size());
            DBReferenceViolationException exp = new DBReferenceViolationException();
            exp.setViolationName(violationNameList);
            throw exp;
        } else {
            logger.trace("deleteMovie movieList.size()=" + movieList.size());
            movieDAO.deleteAll(movieList);
        }
    }

    public void updateMovie(GoodsMovieVO goodsMovieVO)
            throws InvalidAdminInputException, DuplicateException {
        if (goodsMovieVO == null) {
            throw new IllegalArgumentException("can't update null goodsMovieVO");
        }
        Movie movie = movieDAO.getMovieById(goodsMovieVO.getMovId());
        if (movie == null) {
            throw new IllegalStateException("can't update movie has not found");
        }
        movie.populate(goodsMovieVO, movieDAO, movieCatgoryDAO, directorDAO,
                actorDAO, configurationService.getTimeZoneId());
        movieDAO.saveMovie(movie);
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public void setImportCardDAO(ImportCardDAO importCardDAO) {
        this.importCardDAO = importCardDAO;
    }

    public void setActorDAO(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    public void setDirectorDAO(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    public void setMovieCatgoryDAO(MovieCatgoryDAO movieCatgoryDAO) {
        this.movieCatgoryDAO = movieCatgoryDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
