/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserProfitReportVO;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.util.ValidatorUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserDAOImpl extends HibernateDaoSupport implements UserDAO {

    @Override
    public User createNewUser() {
        return new User();
    }

    public void deleteAll(List<User> users) {
        if (users == null) {
            throw new IllegalArgumentException("can't delete null users");
        }
        getHibernateTemplate().deleteAll(users);
    }

    @Override
    public void saveUser(User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("newUser is null");
        }
        logger.trace("saveUser userId=" + newUser.getUserId());
        getHibernateTemplate().saveOrUpdate(newUser);
    }

    @Override
    public User getUserById(Long userId) {
        return (User) getHibernateTemplate().get(User.class, userId);
    }

    @Override
    public User getUserByUserName(String userName) {
        List<User> userList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "getUserByUserName", "userName", userName);

        return userList.size() == 0 ? null : userList.get(0);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        List<Long> userIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "getUserIdByEmail", "email", email);

        return userIdList.size() == 0 ? null : userIdList.get(0);
    }

    @Override
    public Long getUserIdByUserName(String userName) {
        List<Long> userIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "getUserIdByUserName", "userName", userName);

        return userIdList.size() == 0 ? null : userIdList.get(0);
    }

    public List<User> findUser(String userNameSearch, String firstNameSearch,
            String lastNameSearch, String addressSearch, String telSearch,
            String emailSearch, final GregorianCalendar startDateSearch, final GregorianCalendar endDateSearch) {
        userNameSearch = TextUtil.normalize(userNameSearch);
        firstNameSearch = TextUtil.normalize(firstNameSearch);
        lastNameSearch = TextUtil.normalize(lastNameSearch);
        addressSearch = TextUtil.normalize(addressSearch);
        telSearch = TextUtil.normalize(telSearch);
        emailSearch = TextUtil.normalize(emailSearch);
        
        User user = new User();
        user.setUserName(ValidatorUtil.isEmpty(userNameSearch)?null:userNameSearch);
        user.setDeliveryInfo(new DeliveryInfo(ValidatorUtil.isEmpty(firstNameSearch)?null:firstNameSearch,
                ValidatorUtil.isEmpty(lastNameSearch)?null:lastNameSearch,
                ValidatorUtil.isEmpty(addressSearch)?null:addressSearch,
                ValidatorUtil.isEmpty(telSearch)?null:telSearch,
                ValidatorUtil.isEmpty(emailSearch)?null:emailSearch));

        final Example example = Example.create(user).excludeZeroes().excludeProperty(
                "password").enableLike(MatchMode.ANYWHERE).ignoreCase();
        makeSearchDate(startDateSearch);
        makeSearchDate(endDateSearch);
        endDateSearch.add(Calendar.DATE, 1);
        
        List<User> users = getHibernateTemplate().executeFind(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                return session.createCriteria(User.class).add(example)
                        .add(Restrictions.and(
                        Restrictions.ge("dateCreated", startDateSearch.getTime()),
                        Restrictions.lt("dateCreated", endDateSearch.getTime())))
                        .list();
            }
        });
        return users;
    }

    private void makeSearchDate(GregorianCalendar dt) {
        dt.set(Calendar.HOUR, 0);
        dt.set(Calendar.MINUTE, 0);
        dt.set(Calendar.SECOND, 0);
        dt.set(Calendar.MILLISECOND, 0);
    }

    public List<UserProfitReportVO> findUserProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Connection con = session.connection();
                CallableStatement callStm = con.prepareCall("exec usp_userProfit ?, ?");
                callStm.setDate("startDate", startDate.toDate(true));
                callStm.setDate("endDate", endDate.toDate(false));

                ResultSet rs = callStm.executeQuery();
                return makeUserProfitVOs(rs);
            }

            private List<UserProfitReportVO> makeUserProfitVOs(ResultSet rs) {
                List<UserProfitReportVO> userProfitReportVOs =
                        new ArrayList<UserProfitReportVO>();
                UserProfitReportVO userProfitReportVO;
                try {
                    while (rs.next()) {
                        userProfitReportVO = new UserProfitReportVO();
                        userProfitReportVO.setUserId(rs.getLong("UserId"));
                        userProfitReportVO.setUserName(rs.getString("UserName"));
                        userProfitReportVO.setFirstName(rs.getString("FirstName"));
                        userProfitReportVO.setLastName(rs.getString("LastName"));
                        userProfitReportVO.setSoXuat(rs.getInt("UserSX"));
                        userProfitReportVO.setTongXuat(rs.getDouble("UserTX"));
                        userProfitReportVOs.add(userProfitReportVO);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return userProfitReportVOs;
            }
        });
    }
}
