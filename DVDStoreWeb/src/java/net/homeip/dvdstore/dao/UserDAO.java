/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.webservice.vo.DateSearchVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserProfitReportVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface UserDAO {

    public User createNewUser();

    public void saveUser(User newUser);

    public User getUserByUserName(String userName);

    public User getUserById(Long userId);

    public Long getUserIdByUserName(String userName);

    public Long getUserIdByEmail(String email);

    public List<User> findUser(String userNameSearch, String firstNameSearch, 
            String lastNameSearch, String addressSearch, String telSearch,
            String emailSearch, GregorianCalendar start, GregorianCalendar end);

    public void deleteAll(List<User> users);

    public List<UserProfitReportVO> findUserProfitVOs(
            final DateSearchVO startDate, final DateSearchVO endDate);
}
