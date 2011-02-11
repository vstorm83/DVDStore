/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.service;

import java.util.List;
import net.homeip.dvdstore.pojo.web.vo.ChatVO;

/**
 *
 * @author VU VIET PHUONG
 */
public interface ChatService {
    /**
     * get list of chat info
     * @return list of ChatVO, never return null
     */
    List<ChatVO> getChatList();
}
