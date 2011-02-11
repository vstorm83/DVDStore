/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface UserIFrameService {

    public List<UserVO> getUserVOs(String userNameSearch, String firstNameSearch,
            String lastNameSearch, String addressSearch, String telSearch,
            String emailSearch, Date startDateSearch, Date endDateSearch);

    public void deleteUser(List<Long> userIds, boolean ignoreReference)
            throws DBReferenceViolationException;

    public byte[] printTradeHistory(List<Long> userIds);
}
