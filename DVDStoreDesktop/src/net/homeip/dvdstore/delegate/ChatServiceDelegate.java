/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.delegate;

import java.util.List;
import net.homeip.dvdstore.delegate.exception.InvalidInputException;
import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;
import net.homeip.dvdstore.webservice.exception.DuplicateException;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ChatServiceDelegate {

    public List<ChatVO> getChatList();

    public void addChat(ChatVO chatVO)
            throws InvalidInputException, DuplicateException;

    public void updateChat(ChatVO chatVO)
            throws InvalidInputException, DuplicateException;

    public void deleteChat(List<Long> chatIdList);

}

