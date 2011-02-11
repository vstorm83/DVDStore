/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.webservice;

import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService
public interface ChatWebService {

    List<ChatVO> getChatList();

    ChatVO getChatById(long chatId);

    public void addChat(ChatVO chatVO)
            throws InvalidAdminInputException, DuplicateException;

    public void deleteChat(List<Long> chatIdList);

    public void updateChat(ChatVO chatVO)
            throws InvalidAdminInputException, DuplicateException;
}
