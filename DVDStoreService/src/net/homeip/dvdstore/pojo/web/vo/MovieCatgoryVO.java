/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.web.vo;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryVO {
    private Long movCatId;
    private String movCatName;

    public Long getMovCatId() {
        return movCatId;
    }

    public void setMovCatId(Long movCatId) {
        this.movCatId = movCatId;
    }

    public String getMovCatName() {
        return movCatName;
    }

    public void setMovCatName(String movCatName) {
        this.movCatName = movCatName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovieCatgoryVO other = (MovieCatgoryVO) obj;
        if (this.movCatId != other.movCatId && (this.movCatId == null || !this.movCatId.equals(other.movCatId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + (this.movCatId != null ? this.movCatId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return movCatName==null?"":movCatName;
    }

}
