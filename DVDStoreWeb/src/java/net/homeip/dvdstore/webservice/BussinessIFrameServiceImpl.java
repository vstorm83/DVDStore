/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.dao.OrderDAO;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.Order;
import net.homeip.dvdstore.pojo.OrderItem;
import net.homeip.dvdstore.pojo.webservice.vo.OrderItemVO;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.service.ConfigurationService;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import net.homeip.dvdstore.webservice.exception.NotEnoughMovieException;

// </editor-fold>
/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.BussinessIFrameService")
public class BussinessIFrameServiceImpl implements BussinessIFrameService {

    private OrderDAO orderDAO;
    private MovieDAO movieDAO;
    private ConfigurationService configurationService;

    public void completeOrder(List<Long> orderIds, boolean ignoreQuantity)
            throws NotEnoughMovieException {
        List<Order> orders = getOrderListFormIdList(orderIds);
        Set<String> movNames = new HashSet<String>();
        for (Order order : orders) {
            movNames.addAll(order.complete(ignoreQuantity, movieDAO));
            orderDAO.save(order);
        }
        if (movNames.size() > 0) {
            NotEnoughMovieException exp = new NotEnoughMovieException("not enough movie");
            exp.setViolationName(new ArrayList<String>(movNames));
            throw exp;
        }
    }

    public void updateOrder(OrderVO orderVO) throws InvalidAdminInputException {
        Order order = orderDAO.getOrderById(orderVO.getOrderId());
        order.adminUpdate(orderVO);
        orderDAO.save(order);
    }

    public void deleteOrderItem(Long orderId, List<Long> orderItemIdList) {
        if (orderId == null || orderItemIdList == null) {
            throw new IllegalArgumentException("can't delete null orderId or orderItemIdList");
        }
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            throw new IllegalStateException("can't delete orderItem, order not found");
        }

        Iterator<OrderItem> orderItems = order.getOrderItem().iterator();
        OrderItem orderItem;
        while (orderItems.hasNext()) {
            orderItem = orderItems.next();
            for (Long id : orderItemIdList) {
                if (orderItem.getMovie().getMovId().longValue() == id.longValue()) {
                    orderItems.remove();
                }
            }
        }
        orderDAO.save(order);
    }

    public void addOrderItem(Long orderId, List<Long> movIds) {
        Order order = orderDAO.getOrderById(orderId);

        Movie movie;
        for (Long movId : movIds) {
            if (addIfItemExists(order.getOrderItem(), movId)) {
                continue;
            }
            //Nếu chưa có phim trong đơn hàng
            movie = movieDAO.getMovieById(movId);
            if (movie == null) {
                throw new IllegalStateException("Movie not found");
            }
            order.getOrderItem().add(makeOrderItem(movie));
        }
        orderDAO.save(order);
    }

    public void deleteOrder(List<Long> orderIdList) {
        if (orderIdList == null) {
            throw new IllegalArgumentException("can't delete null orderId");
        }
        List<Order> orders = new ArrayList<Order>(orderIdList.size());
        Order order;
        for (Long id : orderIdList) {
            order = orderDAO.getOrderById(id);
            if (order == null) {
                throw new IllegalStateException("can't delete, order not found");
            }
            orders.add(order);
        }
        orderDAO.deleteAll(orders);
    }

    public List<OrderVO> getOrderVOs() {
        List<Order> orders = orderDAO.findPendingOrder();
        List<OrderVO> orderVOs = new ArrayList<OrderVO>(orders.size());
        OrderVO orderVO;
        TimeZone timeZone = TimeZone.getTimeZone(configurationService.getTimeZoneId());
        for (Order order : orders) {
            orderVO = new OrderVO();
            orderVO.setAddress(order.getDeliveryInfo().getAddress());
            orderVO.setEmail(order.getDeliveryInfo().getEmail());
            orderVO.setFirstName(order.getDeliveryInfo().getFirstName());
            orderVO.setLastName(order.getDeliveryInfo().getLastName());
            orderVO.setOrderId(order.getOrderId());
            orderVO.setOrderItemVOs(makeOrderItemVOs(order.getOrderItem()));
            orderVO.setStartDate(new Date(order.getStartDate().getTime() + timeZone.getRawOffset()));
            orderVO.setTel(order.getDeliveryInfo().getTel());
            orderVO.setUserName(order.getUser().getUserName());
            orderVOs.add(orderVO);
        }
        return orderVOs;
    }

    private OrderItem makeOrderItem(Movie movie) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMovie(movie);
        orderItem.setMovPrice(movie.getMovPrice());
        orderItem.setMovSaleOff(movie.getMovSaleOff());
        orderItem.setQuantity(1);
        return orderItem;
    }

    private boolean addIfItemExists(Set<OrderItem> orderItems, Long movId) {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getMovie().getMovId().longValue() == movId.longValue()) {
                orderItem.setQuantity(orderItem.getQuantity() + 1);
                return true;
            }
        }
        return false;
    }

    private List<OrderItemVO> makeOrderItemVOs(Set<OrderItem> orderItems) {
        if (orderItems == null) {
            return null;
        }
        List<OrderItemVO> orderItemVOs = new ArrayList<OrderItemVO>(orderItems.size());
        OrderItemVO orderItemVO;
        for (OrderItem orderItem : orderItems) {
            orderItemVO = new OrderItemVO();
            orderItemVO.setMovCatName(orderItem.getMovie().getMovCat().getMovCatName());
            orderItemVO.setMovId(orderItem.getMovie().getMovId());
            orderItemVO.setMovName(orderItem.getMovie().getMovName());
            orderItemVO.setMovPrice(orderItem.getMovPrice());
            orderItemVO.setMovSaleOff(orderItem.getMovSaleOff());
            orderItemVO.setQuantity(orderItem.getQuantity());
            orderItemVO.setStockQuantity(movieDAO.getMovieQuantity(orderItem.getMovie().getMovId()));
            orderItemVOs.add(orderItemVO);
        }
        return orderItemVOs;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    private List<Order> getOrderListFormIdList(List<Long> orderIds) {
        List<Order> orders = new ArrayList<Order>(orderIds.size());
        Order order;
        for (Long orderId : orderIds) {
            order = orderDAO.getOrderById(orderId);
            if (order == null) {
                throw new IllegalStateException("can't complete order, order not found");
            }
            orders.add(order);
        }
        return orders;
    }
}
