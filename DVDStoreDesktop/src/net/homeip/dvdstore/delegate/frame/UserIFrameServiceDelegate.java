/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate.frame;

import java.util.Calendar;
import java.util.List;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface UserIFrameServiceDelegate {
    public List<UserVO> getUserVOs(String userNameSearch, String firstNameSearch, String lastNameSearch,
            String addressSearch, String telSearch,
            String emailSearch, Calendar startDateSearch, Calendar endDateSearch);

    public void deleteUser(List<Long> userIds, boolean ignoreReference)
            throws DBReferenceViolationException;

    public byte[] printTradeHistory(List<Long> userIds);
}
