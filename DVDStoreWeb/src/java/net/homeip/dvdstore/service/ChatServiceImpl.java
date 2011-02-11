/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import java.util.ArrayList;
import java.util.List;
import net.homeip.dvdstore.dao.ChatDAO;
import net.homeip.dvdstore.pojo.Chat;
import net.homeip.dvdstore.pojo.web.vo.ChatVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChatServiceImpl implements ChatService {
    private ChatDAO chatDAO;
    
    @Override
    public List<ChatVO> getChatList() {
        List<Chat> chatList = chatDAO.findChat();
        List<ChatVO> voList = new ArrayList<ChatVO>(chatList.size());
        ChatVO vo;
        for (Chat chat : chatList) {
            vo = new ChatVO();
            vo.setNickName(chat.getChatNick());
            voList.add(vo);
        }
        return voList;
    }

    public void setChatDAO(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

}
