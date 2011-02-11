/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Chat;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ChatDAO {

    public List<Chat> findChat();

    public Chat getChatById(long chatId);

    public Chat createChat();

    public void saveChat(Chat chat);

    public void deleteAll(List<Chat> chatList);

    public Long getChatIdByChatNick(String chatNick);

}
