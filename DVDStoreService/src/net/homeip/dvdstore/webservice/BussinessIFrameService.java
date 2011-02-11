/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.NotEnoughMovieException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface BussinessIFrameService {

    public List<OrderVO> getOrderVOs();

    public void deleteOrder(List<Long> orderIdList);

    public void deleteOrderItem(Long orderId, List<Long> orderItemIdList);

    public void updateOrder(OrderVO orderVO) throws InvalidAdminInputException;

    public void addOrderItem(Long orderId, List<Long> movIds);

    public void completeOrder(List<Long> orderIds, boolean ignoreQuantity)
            throws NotEnoughMovieException;

}
