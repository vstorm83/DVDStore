/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

// <editor-fold defaultstate="collapsed" desc="import">
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.OrderDAO;
import net.homeip.dvdstore.dao.UserDAO;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import net.homeip.dvdstore.service.ConfigurationService;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import org.apache.log4j.Logger;

// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.UserIFrameService")
public class UserIFrameServiceImpl implements UserIFrameService {

    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private ConfigurationService configurationService;
    private ExportCardPanelService exportCardPanelService;
    private Logger logger = Logger.getLogger(UserIFrameServiceImpl.class);

    public byte[] printTradeHistory(List<Long> userIds) {
        if (userIds == null) {
            throw new IllegalArgumentException("can't find trade history for null user");
        }
        List<Long> exportCardIds = new ArrayList<Long>();
        for (Long userId : userIds) {
            exportCardIds.addAll(orderDAO.findExportCardIdByUserId(userId));
        }
        return exportCardPanelService.printExportCard(exportCardIds);
    }

    public List<UserVO> getUserVOs(String userNameSearch, String firstNameSearch,
            String lastNameSearch, String addressSearch, String telSearch,
            String emailSearch, Date startDateSearch, Date endDateSearch) {
        GregorianCalendar start = makeSearchDate(startDateSearch);
        GregorianCalendar end = makeSearchDate(endDateSearch);
        List<User> users = userDAO.findUser(userNameSearch, firstNameSearch,
            lastNameSearch, addressSearch, telSearch,
            emailSearch, start, end);
        return makeUserVO(users);
    }

    public void deleteUser(List<Long> userIds, boolean ignoreReference)
            throws DBReferenceViolationException {
        if (userIds == null) {
            throw new IllegalArgumentException("userIds is null");
        }
        logger.trace("deleteUser userIds.size()=" + userIds.size());
        List<User> users = new ArrayList<User>(userIds.size());
        List<String> violationNameList = new ArrayList<String>();
        User user;
        for (Long id : userIds) {
            user = userDAO.getUserById(id);
            if (!ignoreReference) {
                if (orderDAO.findOrderByUserId(id).size() > 0) {
                    violationNameList.add(user.getUserName());
                }
            }
            users.add(user);
        }
        if (violationNameList.size() > 0) {
            logger.trace("deleteUser violationNameList.size()=" + violationNameList.size());
            DBReferenceViolationException exp = new DBReferenceViolationException();
            exp.setViolationName(violationNameList);
            throw exp;
        } else {
            logger.trace("deleteUser userIds.size()=" + users.size());
            userDAO.deleteAll(users);
        }
    }

    private GregorianCalendar makeSearchDate(Date dateSearch) {
        TimeZone timeZone = TimeZone.getTimeZone(configurationService.getTimeZoneId());
        GregorianCalendar dt = new GregorianCalendar();
        dt.setTimeInMillis(dateSearch.getTime() - timeZone.getRawOffset());
        return dt;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void setExportCardPanelService(ExportCardPanelService exportCardPanelService) {
        this.exportCardPanelService = exportCardPanelService;
    }

    private List<UserVO> makeUserVO(List<User> users) {
        if (users == null) {
            return null;
        }
        List<UserVO> userVOs = new ArrayList<UserVO>(users.size());
        UserVO userVO;
        for (User user : users) {
            userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            userVO.setUserName(user.getUserName());
            userVO.setDeliveryInfo(user.getDeliveryInfo());
            userVO.setDateCreated(user.getDateCreated());
            userVOs.add(userVO);
        }
        return userVOs;
    }
}
