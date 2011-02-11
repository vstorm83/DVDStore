/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo;

import net.homeip.dvdstore.dao.ChatDAO;
import net.homeip.dvdstore.pojo.webservice.vo.ChatVO;
import net.homeip.dvdstore.util.ValidatorUtil;
import net.homeip.dvdstore.webservice.exception.DuplicateException;
import net.homeip.dvdstore.webservice.exception.InvalidAdminInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class Chat {
    private Long chatId;
    private String chatNick;
    private int version;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getChatNick() {
        return chatNick;
    }

    public void setChatNick(String chatNick) {
        this.chatNick = chatNick;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void populate(ChatVO chatVO, ChatDAO chatDAO)
            throws InvalidAdminInputException, DuplicateException {
        if (chatVO == null || chatDAO == null) {
            throw new IllegalArgumentException("chatDAO or chatVO is null");
        }
        if (ValidatorUtil.isEmpty(chatVO.getChatName()) ||
                ValidatorUtil.isInvalidLength(chatVO.getChatName(), 1, 20)) {
            throw new InvalidAdminInputException("chatName");
        }
        Long dbChatId = chatDAO.getChatIdByChatNick(
                chatVO.getChatName().trim());
        if (dbChatId != null) {
            if (chatId == null ||
                    (dbChatId.longValue() != chatId.longValue())) {
                    throw new DuplicateException("chatName");
            }
        }
        this.chatNick = chatVO.getChatName().trim();
    }
}
