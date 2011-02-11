/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.MovieCatgoryVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieCatgoryChangeEvent extends EventObject {
    private List<MovieCatgoryVO> movCatList;

    public MovieCatgoryChangeEvent(Object source) {
        super(source);
    }

    public List<MovieCatgoryVO> getMovCatList() {
        return movCatList;
    }

    public void setMovCatList(List<MovieCatgoryVO> movCatList) {
        this.movCatList = movCatList;
    }
    
}
