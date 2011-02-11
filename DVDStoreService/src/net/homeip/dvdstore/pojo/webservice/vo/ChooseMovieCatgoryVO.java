/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.util.List;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChooseMovieCatgoryVO {
    private String movCatName;
    private List<ChooseMovieVO> chooseMovieVOs ;

    public List<ChooseMovieVO> getChooseMovieVOs() {
        return chooseMovieVOs;
    }

    public void setChooseMovieVOs(List<ChooseMovieVO> chooseMovieVOs) {
        this.chooseMovieVOs = chooseMovieVOs;
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
        final ChooseMovieCatgoryVO other = (ChooseMovieCatgoryVO) obj;
        if ((this.movCatName == null) ? (other.movCatName != null) : !this.movCatName.equals(other.movCatName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.movCatName != null ? this.movCatName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return movCatName;
    }
    
}
