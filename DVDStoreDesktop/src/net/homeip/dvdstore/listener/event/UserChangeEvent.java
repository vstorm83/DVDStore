/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.listener.event;

import java.util.EventObject;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserChangeEvent extends EventObject {
    private UserVO oldUserVO;
    private UserVO newUserVO;

    public UserVO getNewUserVO() {
        return newUserVO;
    }

    public void setNewUserVO(UserVO newUserVO) {
        this.newUserVO = newUserVO;
    }

    public UserVO getOldUserVO() {
        return oldUserVO;
    }

    public void setOldUserVO(UserVO oldUserVO) {
        this.oldUserVO = oldUserVO;
    }

    public UserChangeEvent(Object source) {
        super(source);
    }
    
}
