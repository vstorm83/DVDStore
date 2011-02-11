/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.util.Date;
import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class GoodsMovieVO {
    private Long movId;
    private String movName;
    private MovieCatgoryVO movCatVO;
    private DirectorVO directorVO;
    private List<ActorVO> actorVOs;
    private String movPicURL;
    private String movDesc;
    private int movQuantity;
    private double movPrice;
    private double movSaleOff;
    private Date dateCreated;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public MovieCatgoryVO getMovCatVO() {
        return movCatVO;
    }

    public void setMovCatVO(MovieCatgoryVO movCatVO) {
        this.movCatVO = movCatVO;
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

    public List<ActorVO> getActorVOs() {
        return actorVOs;
    }

    public void setActorVOs(List<ActorVO> actorVOs) {
        this.actorVOs = actorVOs;
    }

    public DirectorVO getDirectorVO() {
        return directorVO;
    }

    public void setDirectorVO(DirectorVO directorVO) {
        this.directorVO = directorVO;
    }

    public String getMovDesc() {
        return movDesc;
    }

    public void setMovDesc(String movDesc) {
        this.movDesc = movDesc;
    }

    public String getMovPicURL() {
        return movPicURL;
    }

    public void setMovPicURL(String movPicURL) {
        this.movPicURL = movPicURL;
    }

    public int getMovQuantity() {
        return movQuantity;
    }

    public void setMovQuantity(int movQuantity) {
        this.movQuantity = movQuantity;
    }
    
}
