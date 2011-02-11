/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.pojo;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import net.homeip.dvdstore.dao.UserDAO;
import net.homeip.dvdstore.service.exception.EmailDuplicateException;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.InvalidPasswordException;
import net.homeip.dvdstore.service.exception.UserNameDuplicateException;
import net.homeip.dvdstore.util.TextUtil;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.web.vo.UserVO;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

/**
 *
 * @author VU VIET PHUONG
 */
public class User {

    private Long userId;
    private String userName;
    private String password;
    private DeliveryInfo deliveryInfo;
    private Date dateCreated;
    private Integer version;

    public User() {
    }

    public void register(UserVO userVO, UserDAO userDAO, Md5PasswordEncoder pwdEncoder) 
            throws UserNameDuplicateException, EmailDuplicateException, InvalidInputException {
        if (userVO == null || userVO.getDeliveryInfo() == null || userDAO == null) {
            throw new IllegalArgumentException("user or userDAO is null");
        }
        validate(userVO, userDAO);
        populate(userVO, pwdEncoder);
        dateCreated = new Date(Calendar.getInstance().getTimeInMillis() - TimeZone.getDefault().getRawOffset());
    }

    public void updateCredentials(String un, String pwd, String newPwd,
            UserDAO userDAO, Md5PasswordEncoder pwdEncoder)
            throws UserNameDuplicateException, InvalidInputException, InvalidPasswordException {
        if (pwdEncoder == null) {
            throw new IllegalArgumentException("pwdEncoder is null");
        }
        validateCredentials(un, pwd, userDAO);
        validateNewPassword(newPwd);
        if (!checkCredentials(pwd, pwdEncoder)) {
            throw new InvalidPasswordException("password");
        }
        populateCredentials(un, newPwd, pwdEncoder);
    }

    public void updateDeliveryInfo(DeliveryInfo deliveryInfo, UserDAO userDAO) 
            throws EmailDuplicateException, InvalidInputException {
        if (deliveryInfo == null) {
            throw new IllegalArgumentException("deliveryInfo is null");
        }
        validateDeliveryInfo(deliveryInfo, userDAO);
        populateDeliveryInfo(deliveryInfo);
    }

    // <editor-fold defaultstate="collapsed" desc="Validate">
    private void validate(UserVO userVO, UserDAO userDAO) throws UserNameDuplicateException,
            EmailDuplicateException, InvalidInputException {
        validateCredentials(userVO.getUserName(), userVO.getPassword(), userDAO);

        validateDeliveryInfo(userVO.getDeliveryInfo(), userDAO);
    }

    private void validateCredentials(String un, String pwd, UserDAO userDAO)
            throws UserNameDuplicateException, InvalidInputException {
        if (ValidatorUtil.isEmpty(un)
                || ValidatorUtil.isInvalidLength(un, 1, 20)) {
            throw new InvalidInputException("userName");
        }
        if (ValidatorUtil.isEmpty(pwd, false)
                || ValidatorUtil.isInvalidLength(pwd, 5, 20, false)) {
            throw new InvalidInputException("password");
        }

        Long dbUserId = userDAO.getUserIdByUserName(un.trim());
        if (dbUserId != null) {
            if (userId == null ||
                    (dbUserId.longValue() != userId.longValue()))
            throw new UserNameDuplicateException("userName");
        }
    }

    private void validateNewPassword(String newPwd) {
        if (ValidatorUtil.isEmpty(newPwd, false)
                || ValidatorUtil.isInvalidLength(newPwd, 5, 20, false)) {
            throw new InvalidInputException("newPassword");
        }
    }

    private void validateDeliveryInfo(DeliveryInfo deliveryInfo, UserDAO userDAO)
            throws EmailDuplicateException, InvalidInputException {
        String firstName = deliveryInfo.getFirstName();
        if (ValidatorUtil.isEmpty(firstName)
                || ValidatorUtil.isInvalidLength(firstName, 1, 20)) {
            throw new InvalidInputException("deliveryInfo.firstName");
        }
        String lastName = deliveryInfo.getLastName();
        if (ValidatorUtil.isEmpty(lastName)
                || ValidatorUtil.isInvalidLength(lastName, 1, 20)) {
            throw new InvalidInputException("deliveryInfo.lastName");
        }
        String email = deliveryInfo.getEmail();
        if (ValidatorUtil.isEmpty(email)
                || ValidatorUtil.isInvalidLength(email, 1, 50)
                || ValidatorUtil.isInvalidEmail(email.trim())) {
            throw new InvalidInputException("deliveryInfo.email");
        }
        String address = deliveryInfo.getAddress();
        if (ValidatorUtil.isEmpty(address)
                || ValidatorUtil.isInvalidLength(address, 1, 100)) {
            throw new InvalidInputException("deliveryInfo.address");
        }
        String tel = deliveryInfo.getTel();
        if (ValidatorUtil.isEmpty(tel)
                || ValidatorUtil.isInvalidLength(tel, 1, 50)) {
            throw new InvalidInputException("deliveryInfo.tel");
        }

        Long dbUserId = userDAO.getUserIdByEmail(email.trim());
        if (dbUserId != null) {
            if (userId == null ||
                    (dbUserId.longValue() != userId.longValue())) {
                    throw new EmailDuplicateException("deliveryInfo.email");
            }            
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="populate">
    private void populate(UserVO user, Md5PasswordEncoder pwdEncoder) {
        populateCredentials(user.getUserName(), user.getPassword(), pwdEncoder);
        populateDeliveryInfo(user.getDeliveryInfo());
    }

    private void populateCredentials(String un, String newPwd, Md5PasswordEncoder pwdEncoder) {
        userName = un.trim();
        password = pwdEncoder.encodePassword(newPwd, userName);
    }

    private void populateDeliveryInfo(DeliveryInfo inputDeliveryInfo) {
        deliveryInfo = new DeliveryInfo();
        deliveryInfo.setFirstName(TextUtil.normalize(inputDeliveryInfo.getFirstName()));
        deliveryInfo.setLastName(TextUtil.normalize(inputDeliveryInfo.getLastName()));
        deliveryInfo.setAddress(TextUtil.normalize(inputDeliveryInfo.getAddress()));
        deliveryInfo.setEmail(TextUtil.normalize(inputDeliveryInfo.getEmail()));
        deliveryInfo.setTel(TextUtil.normalize(inputDeliveryInfo.getTel()));
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getter">
    public Date getDateCreated() {
        return dateCreated;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public String getPassword() {
        return password;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getVersion() {
        return version;
    }    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Setter">
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }// </editor-fold>

    /**
     *
     * @param pwd pass cần check
     * @param pwdEncoder Md5PasswordEncoder, salt là userName
     * @return true nếu pass đúng, false nếu sai
     */
    private boolean checkCredentials(String pwd, Md5PasswordEncoder pwdEncoder) {
        return pwdEncoder.isPasswordValid(password, pwd, userName);
    }
}
