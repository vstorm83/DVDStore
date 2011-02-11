/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.dao.MovieCatgoryDAO;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgory {
    private Long movCatId;
    private String movCatName;
    private int version;

    public Long getMovCatId() {
        return movCatId;
    }

    public String getMovCatName() {
        return movCatName;
    }

    public int getVersion() {
        return version;
    }

    public void setMovCatId(Long movCatId) {
        this.movCatId = movCatId;
    }

    public void setMovCatName(String movCatName) {
        this.movCatName = movCatName;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void populate(MovieCatgoryVO movieCatgoryVO, MovieCatgoryDAO movieCatgoryDAO)
            throws InvalidAdminInputException, DuplicateException {
        if (movieCatgoryVO == null || movieCatgoryDAO == null) {
            throw new IllegalArgumentException("movieCatgoryDAO or movieCatgoryVO is null");
        }
        if (ValidatorUtil.isEmpty(movieCatgoryVO.getMovCatName()) ||
                ValidatorUtil.isInvalidLength(movieCatgoryVO.getMovCatName(), 1, 50)) {
            throw new InvalidAdminInputException("movCatName");
        }
        Long dbMovCatId = movieCatgoryDAO.getMovCatIdByMovCatName(
                movieCatgoryVO.getMovCatName().trim());
        if (dbMovCatId != null) {
            if (movCatId == null ||
                    (dbMovCatId.longValue() != movCatId.longValue())) {
                    throw new DuplicateException("movCatName");
            }
        }
        this.movCatName = movieCatgoryVO.getMovCatName().trim();
    }
    
}
