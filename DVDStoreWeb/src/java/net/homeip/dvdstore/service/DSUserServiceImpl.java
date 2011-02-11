/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service;

// <editor-fold defaultstate="collapsed" desc="import">
import javax.jms.Destination;
import net.homeip.dvdstore.dao.UserDAO;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.service.exception.EmailDuplicateException;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.InvalidPasswordException;
import net.homeip.dvdstore.service.exception.UserNameDuplicateException;
import net.homeip.dvdstore.pojo.web.vo.CompactUserVO;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.web.vo.UserPrincipalVO;
import net.homeip.dvdstore.pojo.web.vo.UserVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserChangeVO;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
public class DSUserServiceImpl implements DSUserService, UserDetailsService {

    private UserDAO userDAO;
    private Md5PasswordEncoder passwordEncoder;
    private JmsTemplate jmsTemplate;
    private Destination userChangeTopic;
    private Destination userRegisterTopic;
    private Logger logger = Logger.getLogger(DSUserServiceImpl.class);

    @Override
    public void register(UserVO user) throws UserNameDuplicateException,
            EmailDuplicateException, InvalidInputException {
        logger.trace("register userName=" + user.getUserName());
        User newUser = userDAO.createNewUser();
        newUser.register(user, userDAO, passwordEncoder);        

        userDAO.saveUser(newUser);
        logger.trace("register userId=" + newUser.getUserId());

        //send jms message
        net.homeip.dvdstore.pojo.webservice.vo.UserVO userVO = makeUserVO(newUser);
        sendUserRegisterMessage(userVO);
    }

    @Override
    public void updateCredentials(String userName, String password, String newPassword)
            throws UserNameDuplicateException, InvalidInputException, InvalidPasswordException {
        logger.trace("updateCredentials userName=" + userName + " password=" + password
                + " newPassword=" + newPassword);
        User user = userDAO.getUserById(getAuthenticatedPrincipal().getUserId());
        net.homeip.dvdstore.pojo.webservice.vo.UserVO oldUserVO = makeUserVO(user);

        user.updateCredentials(userName, password, newPassword,
                userDAO, passwordEncoder);        

        userDAO.saveUser(user);

        //update in session
        getAuthenticatedPrincipal().setUserName(userName);

        //send jms message
        net.homeip.dvdstore.pojo.webservice.vo.UserVO newUserVO = makeUserVO(user);
        sendUserChangeMessage(oldUserVO, newUserVO);
    }

    @Override
    public void updateDeliveryInfo(DeliveryInfo deliveryInfo)
            throws InvalidInputException, EmailDuplicateException {
        logger.trace("updateDeliveryInfo deliveryInfo.email=" + deliveryInfo.getEmail());
        User user = userDAO.getUserById(getAuthenticatedPrincipal().getUserId());
        net.homeip.dvdstore.pojo.webservice.vo.UserVO oldUserVO = makeUserVO(user);

        user.updateDeliveryInfo(deliveryInfo, userDAO);        

        userDAO.saveUser(user);

        //update in session
        getAuthenticatedPrincipal().setDeliveryInfo(deliveryInfo);

        //send jms message
        net.homeip.dvdstore.pojo.webservice.vo.UserVO newUserVO = makeUserVO(user);
        sendUserChangeMessage(oldUserVO, newUserVO);
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {
        logger.trace("loadUserByUsername username=" + username);
        UserPrincipalVO userPrincipal = null;
        try {
            User user = userDAO.getUserByUserName(username);
            if (user != null) {
                userPrincipal = new UserPrincipalVO();
                userPrincipal.setDeliveryInfo(user.getDeliveryInfo());
                userPrincipal.setPassword(user.getPassword());
                userPrincipal.setUserId(user.getUserId());
                userPrincipal.setUserName(user.getUserName());
            }
        } catch (Exception ex) {
            throw new DataAccessException("Lỗi CSDL khi loadUserByUserName", ex) {
            };
        }
        if (userPrincipal == null) {
            throw new UsernameNotFoundException("Không tìm thấy user");
        }
        logger.trace("loadUserByUsername user=" + userPrincipal);
        return userPrincipal;
    }

    @Override
    public boolean isEmailExists(String email) {
        return userDAO.getUserIdByEmail(email) != null;
    }

    @Override
    public boolean isUserNameExists(String userName) {
        return userDAO.getUserIdByUserName(userName) != null;
    }

    private void sendUserChangeMessage(
            net.homeip.dvdstore.pojo.webservice.vo.UserVO oldUserVO,
            net.homeip.dvdstore.pojo.webservice.vo.UserVO newUserVO) {
        UserChangeVO userChangeVO = new UserChangeVO();
        userChangeVO.setNewUserVO(newUserVO);
        userChangeVO.setOldUserVO(oldUserVO);
        jmsTemplate.convertAndSend(userChangeTopic, userChangeVO);
    }

    private void sendUserRegisterMessage(net.homeip.dvdstore.pojo.webservice.vo.UserVO userVO) {
        jmsTemplate.convertAndSend(userRegisterTopic, userVO);
    }

    private net.homeip.dvdstore.pojo.webservice.vo.UserVO makeUserVO(User user) {
        if (user == null) {
            return null;
        }

        net.homeip.dvdstore.pojo.webservice.vo.UserVO userVO =
                new net.homeip.dvdstore.pojo.webservice.vo.UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setUserName(user.getUserName());
        userVO.setDeliveryInfo(user.getDeliveryInfo());
        userVO.setDateCreated(user.getDateCreated());
        return userVO;
    }

    @Override
    public DeliveryInfo getDeliveryInfo() {
        return getAuthenticatedPrincipal().getDeliveryInfo();
    }

    @Override
    public CompactUserVO getCompactUserVO() {
        CompactUserVO compactUser = new CompactUserVO();
        compactUser.setUserName(getAuthenticatedPrincipal().getUsername());
        return compactUser;
    }

    @Override
    public UserPrincipalVO getAuthenticatedPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("user's not authenticated yet");
        }
        return (UserPrincipalVO) authentication.getPrincipal();
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setPasswordEncoder(Md5PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setUserChangeTopic(Destination userChangeTopic) {
        this.userChangeTopic = userChangeTopic;
    }

    public void setUserRegisterTopic(Destination userRegisterTopic) {
        this.userRegisterTopic = userRegisterTopic;
    }
}
