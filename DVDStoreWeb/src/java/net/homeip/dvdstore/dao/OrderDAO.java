/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.Order;

/**
 *
 * @author VU VIET PHUONG
 */
public interface OrderDAO {

    public Order createOrder();

    public void save(Order order);

    /**
     * trả về đơn hàng, thanh toán và chưa thanh toán
     * dùng để kiếm tra có thể xóa phim hay không
     * @param movId
     * @return
     */
    public List<Order> findOrderByMovieId(Long movId);

    public List<Order> findPendingOrder();

    public Order getOrderById(Long id);

    public void deleteAll(List<Order> orders);

    public List<Order> findExportCard(String userNameSearch, 
            GregorianCalendar startDateSearch, GregorianCalendar endDateSearch);
    
    public List<Long> findExportCardIdByUserId(Long userId);

    /**
     * trả về đơn hàng, thanh toán và chưa thanh toán
     * dùng để kiếm tra có thể xóa user hay không
     * @param movId
     * @return
     */
    public List<Order> findOrderByUserId(Long userId);
}
