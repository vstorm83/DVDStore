/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorVO {
    private Long directorId;
    private String directorName;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DirectorVO other = (DirectorVO) obj;
        if (this.directorId != other.directorId && (this.directorId == null || !this.directorId.equals(other.directorId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.directorId != null ? this.directorId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return directorName==null?"":directorName;
    }
    
}
