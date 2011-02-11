/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ActorChangeEvent extends EventObject {
    private List<ActorVO> actorList;

    public ActorChangeEvent(Object source) {
        super(source);
    }

    public List<ActorVO> getActorList() {
        return actorList;
    }

    public void setActorList(List<ActorVO> actorList) {
        this.actorList = actorList;
    }
    
}
