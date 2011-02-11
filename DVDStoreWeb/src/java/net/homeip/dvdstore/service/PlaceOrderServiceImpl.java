/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import javax.jms.Destination;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.dao.OrderDAO;
import net.homeip.dvdstore.dao.ShoppingCartDAO;
import net.homeip.dvdstore.dao.UserDAO;
import net.homeip.dvdstore.pojo.Movie;
import net.homeip.dvdstore.pojo.Order;
import net.homeip.dvdstore.pojo.OrderItem;
import net.homeip.dvdstore.pojo.ShoppingCart;
import net.homeip.dvdstore.pojo.ShoppingCartItem;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.web.vo.ShoppingCartItemVO;
import net.homeip.dvdstore.pojo.web.vo.ShoppingCartVO;
import net.homeip.dvdstore.pojo.web.vo.UserPrincipalVO;
import net.homeip.dvdstore.pojo.webservice.vo.OrderItemVO;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.ShoppingCartEmptyException;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class PlaceOrderServiceImpl implements PlaceOrderService {

    private ShoppingCartDAO shoppingCartDAO;
    private MovieDAO movieDAO;
    private OrderDAO orderDAO;
    private UserDAO userDAO;
    private JmsTemplate jmsTemplate;
    private Destination orderTopic;
    private ConfigurationService configurationService;
    private Logger logger = Logger.getLogger(PlaceOrderServiceImpl.class);

    @Override
    public String sendOrder() throws ShoppingCartEmptyException {
        Order order = saveOrder();
        sendJMSMessage(order);
        return "success";
    }

    @Override
    public void updateShoppingCart(ShoppingCartVO shoppingCartVO)
            throws InvalidInputException, ShoppingCartEmptyException {
        if (shoppingCartVO == null) {
            throw new IllegalArgumentException("shoppingCartVO is null, can't update");
        }
        logger.trace("updateShoppingCart shoppingCartVO=" + shoppingCartVO);
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCart();
        shoppingCart.updateShoppingCart(shoppingCartVO);
        shoppingCartDAO.saveShoppingCart(shoppingCart);
        logger.trace("updateShoppingCart shoppingCart=" + shoppingCart);
    }

    @Override
    public void addItem(long movId) {
        logger.trace("addItem movId=" + movId);
        Movie movie = movieDAO.getMovieById(movId);
        if (movie == null) {
            return;
        }
        ShoppingCartItem shoppingCartItem = shoppingCartDAO.makeShoppingCartItem(movie);
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCart();
        shoppingCart.addItem(shoppingCartItem);
        shoppingCartDAO.saveShoppingCart(shoppingCart);
        logger.trace("addItem movie=" + movie);
    }

    // <editor-fold defaultstate="collapsed" desc="Make">
    private Order makeOrder() throws ShoppingCartEmptyException {
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCart();
        Collection<OrderItem> orderItemList =
                makeOrderItemList(shoppingCart.getShoppingCartItem().values());

        User user = userDAO.getUserById(getUserPrincipalVO().getUserId());

        Order order = orderDAO.createOrder();
        order.populate(user, shoppingCart.getDeliveryInfo(),
                orderItemList);
        return order;
    }

    private List<ShoppingCartItemVO> makeShoppingCartItemVOList(
            Map<Long, ShoppingCartItem> shoppingCartItemList) {
        List<ShoppingCartItemVO> voList =
                new ArrayList<ShoppingCartItemVO>(shoppingCartItemList.size());
        for (ShoppingCartItem itm : shoppingCartItemList.values()) {
            ShoppingCartItemVO vo = new ShoppingCartItemVO();
            vo.setMovName(itm.getMovie().getMovName());
            vo.setMovPrice(itm.getMovie().getMovPrice());
            vo.setSaleOff(itm.getMovie().getMovSaleOff());
            vo.setQuantity(itm.getQuantity());
            voList.add(vo);
        }
        return voList;
    }

    private Collection<OrderItem> makeOrderItemList(Collection<ShoppingCartItem> items) {
        Collection<OrderItem> orderItemList = new ArrayList<OrderItem>(items.size());
        OrderItem orderItem;
        for (ShoppingCartItem itm : items) {
            if (movieDAO.getMovieById(itm.getMovie().getMovId()) == null) {
                continue;
            }
            orderItem = new OrderItem();
            orderItem.setMovie(itm.getMovie());
            orderItem.setMovPrice(itm.getMovie().getMovPrice());
            orderItem.setMovSaleOff(itm.getMovie().getMovSaleOff());
            orderItem.setQuantity(itm.getQuantity());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter">
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public void setShoppingCartDAO(ShoppingCartDAO shoppingCartDAO) {
        this.shoppingCartDAO = shoppingCartDAO;
    }

    public void setMovieDAO(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setOrderTopic(Destination orderTopic) {
        this.orderTopic = orderTopic;
    }
    // </editor-fold>

    private Order saveOrder() throws ShoppingCartEmptyException {
        Order order = makeOrder();
        orderDAO.save(order);
        shoppingCartDAO.clearShoppingCart();
        logger.trace("saveOrder sucess");
        return order;
    }

    @Override
    public int getShoppingCartItemCount() {
        return shoppingCartDAO.getShoppingCartItemCount();
    }

    @Override
    public ShoppingCartVO getShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCart();
        ShoppingCartVO shoppingCartVO = new ShoppingCartVO();
        shoppingCartVO.setDeliveryInfo(shoppingCart.getDeliveryInfo());
        shoppingCartVO.setItems(makeShoppingCartItemVOList(shoppingCart.getShoppingCartItem()));
        return shoppingCartVO;
    }

    private UserPrincipalVO getUserPrincipalVO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("user is not authenticated, can't get user principal");
        }
        return (UserPrincipalVO) authentication.getPrincipal();
    }

    private void sendJMSMessage(Order order) {
        OrderVO orderVO = makeOrderVO(order);
        jmsTemplate.convertAndSend(orderTopic, orderVO);
    }

    private OrderVO makeOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        orderVO.setAddress(order.getDeliveryInfo().getAddress());
        orderVO.setEmail(order.getDeliveryInfo().getEmail());
        orderVO.setFirstName(order.getDeliveryInfo().getFirstName());
        orderVO.setLastName(order.getDeliveryInfo().getLastName());
        orderVO.setOrderId(order.getOrderId());
        orderVO.setOrderItemVOs(makeOrderItemVOs(order.getOrderItem()));
        orderVO.setStartDate(new Date(order.getStartDate().getTime()
                + TimeZone.getTimeZone(configurationService.getTimeZoneId()).getRawOffset()));
        orderVO.setTel(order.getDeliveryInfo().getTel());
        orderVO.setUserName(order.getUser().getUserName());
        return orderVO;
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
}
