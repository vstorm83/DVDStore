/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;
import net.homeip.dvdstore.dao.ActorDAO;
import net.homeip.dvdstore.dao.ConfigurationDAO;
import net.homeip.dvdstore.dao.DirectorDAO;
import net.homeip.dvdstore.dao.MovieCatgoryDAO;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.pojo.webservice.vo.GoodsMovieVO;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class Movie {

    private Long movId;
    private String movName;
    private String movPicName;
    private String movDesc;
    private MovieCatgory movCat;
    private Director director;
    private Set<Actor> actor = new HashSet<Actor>();
    private double movPrice;
    private double movSaleOff;
    private Date dateCreated;
    private int version;

    public Set<Actor> getActor() {
        return actor;
    }

    public void setActor(Set<Actor> actor) {
        this.actor = actor;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public MovieCatgory getMovCat() {
        return movCat;
    }

    public void setMovCat(MovieCatgory movCat) {
        this.movCat = movCat;
    }

    public String getMovDesc() {
        return movDesc;
    }

    public void setMovDesc(String movDesc) {
        this.movDesc = movDesc;
    }

    public Long getMovId() {
        return movId;
    }

    public void setMovId(Long movId) {
        this.movId = movId;
    }

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getMovPicName() {
        return movPicName;
    }

    public void setMovPicName(String movPicName) {
        this.movPicName = movPicName;
    }

    public double getMovPrice() {
        return movPrice;
    }

    public void setMovPrice(double movPrice) {
        this.movPrice = movPrice;
    }

    public double getMovSaleOff() {
        return movSaleOff;
    }

    public void setMovSaleOff(double movSaleOff) {
        this.movSaleOff = movSaleOff;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void addActor(Actor act) {
        actor.add(act);
    }

    public void removeActor(Actor act) {
        actor.remove(act);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.movId != other.movId && (this.movId == null || !this.movId.equals(other.movId))) {
            return false;
        }
        if ((this.movName == null) ? (other.movName != null) : !this.movName.equals(other.movName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.movId != null ? this.movId.hashCode() : 0);
        hash = 89 * hash + (this.movName != null ? this.movName.hashCode() : 0);
        return hash;
    }

    public void populate(GoodsMovieVO goodsMovieVO, MovieDAO movieDAO, MovieCatgoryDAO movieCatgoryDAO,
            DirectorDAO directorDAO, ActorDAO actorDAO, String timeZoneId)
            throws InvalidAdminInputException, DuplicateException {
        if (goodsMovieVO == null || movieDAO == null) {
            throw new IllegalArgumentException("movieDAO or goodsMovieVO is null");
        }
        validate(goodsMovieVO, movieDAO);

        this.movId = goodsMovieVO.getMovId();
        this.movName = TextUtil.normalize(goodsMovieVO.getMovName());
        this.movCat = movieCatgoryDAO.getMovieCatgoryById(goodsMovieVO.getMovCatVO().getMovCatId());
        if (goodsMovieVO.getDirectorVO() != null) {
            this.director = directorDAO.getDirectorById(goodsMovieVO.getDirectorVO().getDirectorId());
        }
        this.movPrice = goodsMovieVO.getMovPrice();
        this.movSaleOff = goodsMovieVO.getMovSaleOff();
        if (goodsMovieVO.getActorVOs() != null) {
            this.actor.clear();
            for (ActorVO actorVO : goodsMovieVO.getActorVOs()) {
                this.actor.add(actorDAO.getActorById(actorVO.getActorId()));
            }
        }
        this.dateCreated = new Date(goodsMovieVO.getDateCreated().getTime()
                - TimeZone.getTimeZone(timeZoneId).getRawOffset());
        if (goodsMovieVO.getMovPicURL() != null) {
            this.movPicName = TextUtil.getFileNameFormURL(goodsMovieVO.getMovPicURL());
        }
        if (goodsMovieVO.getMovDesc() != null) {
            this.movDesc = TextUtil.transformMovDesc(
                    goodsMovieVO.getMovDesc(), "../" + ConfigurationDAO.UPLOAD_DIR);
        }
    }

    private void validate(GoodsMovieVO goodsMovieVO, MovieDAO movieDAO) {
        if (ValidatorUtil.isEmpty(goodsMovieVO.getMovName())
                || ValidatorUtil.isInvalidLength(
                TextUtil.normalize(goodsMovieVO.getMovName()), 1, 50)) {
            throw new InvalidAdminInputException("movName");
        }
        if (goodsMovieVO.getMovCatVO() == null
                || goodsMovieVO.getMovCatVO().getMovCatId() == null) {
            throw new InvalidAdminInputException("movCatVO");
        }
        if (goodsMovieVO.getDateCreated() == null) {
            throw new InvalidAdminInputException("dateCreated");
        }

        Long dbMovieId = movieDAO.getMovieIdByMovieName(
                TextUtil.normalize(goodsMovieVO.getMovName()));
        if (dbMovieId != null) {
            if (goodsMovieVO.getMovId() == null
                    || (dbMovieId.longValue() != goodsMovieVO.getMovId().longValue())) {
                throw new DuplicateException("movName");
            }
        }
    }
}
