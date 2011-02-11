/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.dao;

import java.util.List;
import net.homeip.dvdstore.pojo.Chat;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 *
 * @author VU VIET PHUONG
 */
public class ChatDAOImpl extends HibernateDaoSupport implements ChatDAO {

    @Override
    public List<Chat> findChat() {
        return getHibernateTemplate().findByNamedQuery("findChat");
    }

    @Override
    public Chat getChatById(long chatId) {
        return (Chat)getHibernateTemplate().get(Chat.class, chatId);
    }

    public Chat createChat() {
        return new Chat();
    }

    public void deleteAll(List<Chat> chatList) {
        if (chatList == null) {
            throw new IllegalArgumentException("can't delete, chatList is null");
        }
        getHibernateTemplate().deleteAll(chatList);
    }

    public Long getChatIdByChatNick(String chatNick) {
        List<Long> chatIdList = getHibernateTemplate().findByNamedQueryAndNamedParam(
                "findChatIdByChatNick", "chatNick", chatNick);
        if (chatIdList.size() == 0) {
            return null;
        }
        return chatIdList.get(0);
    }

    public void saveChat(Chat chat) {
        if (chat == null) {
            throw new IllegalArgumentException("can't save null chat");
        }
        getHibernateTemplate().saveOrUpdate(chat);
    }
}
