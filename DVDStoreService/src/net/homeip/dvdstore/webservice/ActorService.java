/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface ActorService {

    List<ActorVO> getActorList();

    ActorVO getActorById(long actorId);

    public void addActor(ActorVO actorVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteActor(List<Long> actorIdList);

    public void updateActor(ActorVO actorVO)
            throws InvalidAdminInputException, DuplicateException;
}
