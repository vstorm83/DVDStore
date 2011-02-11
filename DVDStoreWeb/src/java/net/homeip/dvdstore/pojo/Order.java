/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import net.homeip.dvdstore.dao.MovieDAO;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.webservice.vo.OrderItemVO;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.ShoppingCartEmptyException;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class Order {

    private Long orderId;
    private User user;
    private DeliveryInfo deliveryInfo;
    private Set<OrderItem> orderItem = new HashSet<OrderItem>();
    private Date startDate;
    private Date endDate;
    private int version;

    public void populate(User user, DeliveryInfo deliveryInfo,
            Collection<OrderItem> orderItemList)
            throws ShoppingCartEmptyException {
        if (user == null) {
            throw new IllegalArgumentException("user is null, can't make order");
        }
        if (orderItemList.size() == 0) {
            throw new ShoppingCartEmptyException("orderItemList is empty");
        }
        this.user = user;
        this.deliveryInfo = deliveryInfo;
        for (OrderItem item : orderItemList) {
            orderItem.add(item);
        }
        startDate = new Date(Calendar.getInstance().getTimeInMillis()
                - TimeZone.getDefault().getRawOffset());
    }

    public void adminUpdate(OrderVO orderVO)
            throws InvalidAdminInputException {
        validate(orderVO);
        deliveryInfo.setAddress(orderVO.getAddress());
        deliveryInfo.setEmail(orderVO.getEmail());
        deliveryInfo.setTel(orderVO.getTel());

        for (OrderItemVO orderItemVO : orderVO.getOrderItemVOs()) {
            for (OrderItem itm : orderItem) {
                if (itm.getMovie().getMovId().longValue() == orderItemVO.getMovId().longValue()) {
                    itm.setMovPrice(orderItemVO.getMovPrice());
                    itm.setMovSaleOff(orderItemVO.getMovSaleOff());
                    itm.setQuantity(orderItemVO.getQuantity());
                    break;
                }
            }
        }
    }

    public List<String> complete(boolean ignoreQuantity, MovieDAO movieDAO) {
        List<String> violationNames = new ArrayList<String>();
        if (!ignoreQuantity) {
            for (OrderItem itm : orderItem) {
                if (itm.getQuantity() > movieDAO.getMovieQuantity(itm.getMovie().getMovId())) {
                    violationNames.add(itm.getMovie().getMovName());
                }
            }
        }
        endDate = new Date(Calendar.getInstance().getTimeInMillis()
                - TimeZone.getDefault().getRawOffset());
        return violationNames;
    }

    public void revert() {
        endDate = null;
    }

    public void addItem(OrderItem item) {
        orderItem.add(item);
    }

    public void removeItem(OrderItem item) {
        orderItem.remove(item);
    }

    // <editor-fold defaultstate="collapsed" desc="get-set">
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Set<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }// </editor-fold>

    private void validate(OrderVO orderVO) {
        String address = orderVO.getAddress();
        if (ValidatorUtil.isEmpty(address)
                || ValidatorUtil.isInvalidLength(address, 1, 100)) {
            throw new InvalidInputException("address");
        }
        String tel = orderVO.getTel();
        if (ValidatorUtil.isEmpty(tel)
                || ValidatorUtil.isInvalidLength(tel, 1, 50)) {
            throw new InvalidInputException("tel");
        }
        String email = orderVO.getEmail();
        if (ValidatorUtil.isEmpty(email)
                || ValidatorUtil.isInvalidLength(email, 1, 50)
                || ValidatorUtil.isInvalidEmail(email.trim())) {
            throw new InvalidInputException("email");
        }
        List<OrderItemVO> orderItemVOs = orderVO.getOrderItemVOs();
        for (OrderItemVO orderItemVO : orderItemVOs) {
            if (orderItemVO.getMovPrice() < 0) {
                throw new InvalidInputException("movPrice");
            }
            if (orderItemVO.getMovSaleOff() < 0 || orderItemVO.getMovSaleOff() > 100) {
                throw new InvalidInputException("movSaleOff");
            }
            if (orderItemVO.getQuantity() <= 0) {
                throw new InvalidInputException("quantity");
            }
        }
    }
}
