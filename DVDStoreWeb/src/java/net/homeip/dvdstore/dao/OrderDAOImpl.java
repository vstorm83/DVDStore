/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.Order;
import net.homeip.dvdstore.util.TextUtil;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class OrderDAOImpl extends HibernateDaoSupport implements OrderDAO {

    @Override
    public Order createOrder() {
        return new Order();
    }

    @Override
    public void save(Order order) {
        getHibernateTemplate().saveOrUpdate(order);
        getHibernateTemplate().flush();
    }

    public List<Order> findOrderByMovieId(Long movId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam("findOrderByMovieId",
                "movId", movId);
    }

    public List<Order> findPendingOrder() {
        return getHibernateTemplate().findByNamedQuery("findPendingOrder");
    }

    public void deleteAll(List<Order> orders) {
        if (orders == null) {
            throw new IllegalArgumentException("Can't delete null orders");
        }
        getHibernateTemplate().deleteAll(orders);
    }

    public Order getOrderById(Long id) {
        return (Order) getHibernateTemplate().get(Order.class, id);
    }

    public List<Order> findExportCard(String userNameSearch,
            GregorianCalendar startDateSearch, GregorianCalendar endDateSearch) {
        makeSearchDate(startDateSearch);
        makeSearchDate(endDateSearch);
        endDateSearch.add(Calendar.DATE, 1);

        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findExportCard", new String[]{"userNameSearch", "startDateSearch", "endDateSearch"},
                new Object[]{"%" + TextUtil.normalize(userNameSearch) + "%", startDateSearch.getTime(),
                    endDateSearch.getTime()});
    }

    public List<Long> findExportCardIdByUserId(Long userId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findExportCardIdByUserId", "userId", userId);
    }   

    public List<Order> findOrderByUserId(Long userId) {
        return getHibernateTemplate().findByNamedQueryAndNamedParam("findOrderByUserId", "userId", userId);
    }

    private void makeSearchDate(GregorianCalendar dt) {
        dt.set(Calendar.HOUR, 0);
        dt.set(Calendar.MINUTE, 0);
        dt.set(Calendar.SECOND, 0);
        dt.set(Calendar.MILLISECOND, 0);
    }
}
