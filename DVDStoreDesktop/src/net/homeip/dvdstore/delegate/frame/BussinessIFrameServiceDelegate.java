/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.frame;

import java.util.List;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.webservice.exception.NotEnoughMovieException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface BussinessIFrameServiceDelegate {
    public List<OrderVO> getOrderVOs();

    public void deleteOrder(List<Long> orderIdList);

    public void deleteOrderItem(Long orderId, List<Long> orderItemIdList);

    public void updateOrder(OrderVO orderVO) throws InvalidInputException;

    public void addOrderItem(Long orderId, List<Long> movIds);

    public void completeOrder(List<Long> orderIds, boolean ignoreQuantity)
            throws NotEnoughMovieException;
}
