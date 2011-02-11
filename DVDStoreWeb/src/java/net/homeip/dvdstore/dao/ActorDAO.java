/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Actor;
import net.homeip.dvdstore.pojo.webservice.vo.ActorProfitReportVO;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ActorDAO {

    public List<Actor> findActor();

    public Actor getActorById(long actorId);

    public Actor createActor();

    public void saveActor(Actor actor);

    public void deleteAll(List<Actor> actorList);

    public Long getActorIdByActorName(String actorName);

    public List<ActorProfitReportVO> findActorProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
}
