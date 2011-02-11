/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import net.homeip.dvdstore.service.exception.EmailDuplicateException;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.UserNameDuplicateException;
import net.homeip.dvdstore.pojo.web.vo.CompactUserVO;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.web.vo.UserPrincipalVO;
import net.homeip.dvdstore.pojo.web.vo.UserVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface DSUserService {
    boolean isUserNameExists(String userName);
    boolean isEmailExists(String email);
    void register(UserVO user) throws UserNameDuplicateException,
            EmailDuplicateException, InvalidInputException;

    public DeliveryInfo getDeliveryInfo();

    public void updateDeliveryInfo(DeliveryInfo deliveryInfo) 
            throws InvalidInputException, EmailDuplicateException;

    public CompactUserVO getCompactUserVO();

    public void updateCredentials(String userName, String password, String newPassword)
            throws UserNameDuplicateException, InvalidInputException;
    public UserPrincipalVO getAuthenticatedPrincipal();
}
