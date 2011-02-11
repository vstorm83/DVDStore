/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.dao.ActorDAO;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class Actor {
    private Long actorId;
    private String actorName;
    private int version;

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actor other = (Actor) obj;
        if (this.actorId != other.actorId && (this.actorId == null || !this.actorId.equals(other.actorId))) {
            return false;
        }
        if ((this.actorName == null) ? (other.actorName != null) : !this.actorName.equals(other.actorName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.actorId != null ? this.actorId.hashCode() : 0);
        hash = 59 * hash + (this.actorName != null ? this.actorName.hashCode() : 0);
        return hash;
    }

    public void populate(ActorVO actorVO, ActorDAO actorDAO)
            throws InvalidAdminInputException, DuplicateException {
        if (actorVO == null || actorDAO == null) {
            throw new IllegalArgumentException("actorDAO or actorVO is null");
        }
        if (ValidatorUtil.isEmpty(actorVO.getActorName()) ||
                ValidatorUtil.isInvalidLength(actorVO.getActorName(), 1, 50)) {
            throw new InvalidAdminInputException("actorName");
        }
        Long dbActorId = actorDAO.getActorIdByActorName(
                actorVO.getActorName().trim());
        if (dbActorId != null) {
            if (actorId == null ||
                    (dbActorId.longValue() != actorId.longValue())) {
                    throw new DuplicateException("actorName");
            }
        }
        this.actorName = actorVO.getActorName().trim();
    }
}
