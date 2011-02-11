/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.DirectorVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class DirectorChangeEvent extends EventObject {
    private List<DirectorVO> directorList;

    public DirectorChangeEvent(Object source) {
        super(source);
    }

    public List<DirectorVO> getDirectorList() {
        return directorList;
    }

    public void setDirectorList(List<DirectorVO> directorList) {
        this.directorList = directorList;
    }
    
}
