/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import net.homeip.dvdstore.dao.ChatDAO;
import net.homeip.dvdstore.pojo.Chat;
import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;
import net.homeip.dvdstore.webservice.exception.DBReferenceViolationException;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;
import org.apache.log4j.Logger;

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.ChatWebService")
public class ChatWebServiceImpl implements ChatWebService {

    private ChatDAO chatDAO;
    private Logger logger = Logger.getLogger(ChatWebServiceImpl.class);

    @Override
    public List<ChatVO> getChatList() {
        List<Chat> chatList = chatDAO.findChat();
        List<ChatVO> voList = new ArrayList<ChatVO>(chatList.size());
        ChatVO vo;
        for (Chat chat : chatList) {
            vo = new ChatVO();
            vo.setChatId(chat.getChatId());
            vo.setChatName(chat.getChatNick());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public ChatVO getChatById(long chatId) {
        Chat chat = chatDAO.getChatById(chatId);
        if (chat == null) {
            return null;
        }
        ChatVO vo = new ChatVO();
        vo.setChatId(chat.getChatId());
        vo.setChatName(chat.getChatNick());
        return vo;
    }

    public void addChat(ChatVO chatVO)
            throws InvalidAdminInputException, DuplicateException {
        if (chatVO == null) {
            throw new IllegalArgumentException("can't add null chatVO");
        }
        logger.trace("addChat chatName=" + chatVO.getChatName());
        Chat chat = chatDAO.createChat();
        chat.populate(chatVO, chatDAO);
        chatDAO.saveChat(chat);
    }

    public void deleteChat(List<Long> chatIdList)
            throws DBReferenceViolationException {
        if (chatIdList == null) {
            throw new IllegalArgumentException("chatIdList is null");
        }

        List<Chat> chatList = new ArrayList<Chat>(chatIdList.size());
        for (Long id : chatIdList) {
            chatList.add(chatDAO.getChatById(id));
        }
        chatDAO.deleteAll(chatList);
    }

    public void updateChat(ChatVO chatVO)
            throws InvalidAdminInputException, DuplicateException {
        if (chatVO == null) {
            throw new IllegalArgumentException("can't update null chatVO");
        }
        Chat chat = chatDAO.getChatById(chatVO.getChatId());
        if (chat == null) {
            throw new IllegalStateException("can't update chat has not found");
        }
        chat.populate(chatVO, chatDAO);
        chatDAO.saveChat(chat);
    }

    public void setChatDAO(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }
}
