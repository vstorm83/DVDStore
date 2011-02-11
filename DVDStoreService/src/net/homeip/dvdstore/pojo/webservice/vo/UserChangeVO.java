/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.pojo.webservice.vo;

import java.io.Serializable;

/**
 *
 * @author VU VIET PHUONG
 */
public class UserChangeVO implements Serializable {
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
    
}
