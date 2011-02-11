/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.webservice.exception.DuplicateException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ActorServiceDelegate {

    public List<ActorVO> getActorList();

    public void addActor(ActorVO actorVO)
            throws InvalidInputException, DuplicateException;

    public void updateActor(ActorVO actorVO)
            throws InvalidInputException, DuplicateException;

    public void deleteActor(List<Long> actorIdList);

}

