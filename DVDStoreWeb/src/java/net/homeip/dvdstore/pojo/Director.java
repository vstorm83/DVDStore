/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.dao.DirectorDAO;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class Director {
    private Long directorId;
    private String directorName;
    private int version;

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void populate(DirectorVO directorVO, DirectorDAO directorDAO)
            throws InvalidAdminInputException, DuplicateException {
        if (directorVO == null || directorDAO == null) {
            throw new IllegalArgumentException("directorDAO or directorVO is null");
        }
        if (ValidatorUtil.isEmpty(directorVO.getDirectorName()) ||
                ValidatorUtil.isInvalidLength(directorVO.getDirectorName(), 1, 50)) {
            throw new InvalidAdminInputException("directorName");
        }
        Long dbDirectorId = directorDAO.getDirectorIdByDirectorName(
                directorVO.getDirectorName().trim());
        if (dbDirectorId != null) {
            if (directorId == null ||
                    (dbDirectorId.longValue() != directorId.longValue())) {
                    throw new DuplicateException("directorName");
            }
        }
        this.directorName = directorVO.getDirectorName().trim();
    }
}
