/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.ActorDAO;
import net.homeip.dvdstore.pojo.Actor;
import net.homeip.dvdstore.pojo.webservice.vo.ActorVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ActorService")
public class ActorServiceImpl implements ActorService {

    private ActorDAO actorDAO;
    private Logger logger = Logger.getLogger(ActorServiceImpl.class);

    @Override
    public List<ActorVO> getActorList() {
        List<Actor> actorList = actorDAO.findActor();
        List<ActorVO> voList = new ArrayList<ActorVO>(actorList.size());
        ActorVO vo;
        for (Actor actor : actorList) {
            vo = new ActorVO();
            vo.setActorId(actor.getActorId());
            vo.setActorName(actor.getActorName());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public ActorVO getActorById(long actorId) {
        Actor actor = actorDAO.getActorById(actorId);
        if (actor == null) {
            return null;
        }
        ActorVO vo = new ActorVO();
        vo.setActorId(actor.getActorId());
        vo.setActorName(actor.getActorName());
        return vo;
    }

    @Override
    public void addActor(ActorVO actorVO)
            throws InvalidAdminInputException, DuplicateException {
        if (actorVO == null) {
            throw new IllegalArgumentException("can't add null actorVO");
        }
        logger.trace("addActor actorName=" + actorVO.getActorName());
        Actor actor = actorDAO.createActor();
        actor.populate(actorVO, actorDAO);
        actorDAO.saveActor(actor);
    }

    @Override
    public void deleteActor(List<Long> actorIdList)
            throws DBReferenceViolationException {
        if (actorIdList == null) {
            throw new IllegalArgumentException("actorIdList is null");
        }

        List<Actor> actorList = new ArrayList<Actor>(actorIdList.size());
        for (Long id : actorIdList) {
            actorList.add(actorDAO.getActorById(id));
        }
        actorDAO.deleteAll(actorList);
    }

    @Override
    public void updateActor(ActorVO actorVO)
            throws InvalidAdminInputException, DuplicateException {
        if (actorVO == null) {
            throw new IllegalArgumentException("can't update null actorVO");
        }
        Actor actor = actorDAO.getActorById(actorVO.getActorId());
        if (actor == null) {
            throw new IllegalStateException("can't update actor has not found");
        }
        actor.populate(actorVO, actorDAO);
        actorDAO.saveActor(actor);
    }

    public void setActorDAO(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }
}
