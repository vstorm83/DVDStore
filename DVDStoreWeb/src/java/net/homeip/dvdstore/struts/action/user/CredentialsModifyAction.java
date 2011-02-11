/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.user;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.homeip.dvdstore.service.DSUserService;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.InvalidPasswordException;
import net.homeip.dvdstore.service.exception.UserNameDuplicateException;
import net.homeip.dvdstore.struts.action.DSActionSupport;
import net.homeip.dvdstore.pojo.web.vo.CompactUserVO;

/**
 *
 * @author VU VIET PHUONG
 */
public class CredentialsModifyAction extends DSActionSupport
        implements ModelDriven<CompactUserVO>, Preparable {
    private DSUserService userService;
    private CompactUserVO compactUserVO;

    public String form() {
        return INPUT;
    }

    public String process() {
        try {
            userService.updateCredentials(compactUserVO.getUserName(), compactUserVO.getPassword(),
                    compactUserVO.getNewPassword());
        } catch (UserNameDuplicateException unEx) {
            addFieldError("userName", getText("userName.duplicate"));
        } catch (InvalidInputException inEx) {
            addFieldError(inEx.getMessage(), getText(inEx.getMessage() + "." + "invalid"));
        } catch (InvalidPasswordException passEx) {
            addFieldError("password", getText("oldPass.invalid"));
        }
        if (hasFieldErrors()) {
            return INPUT;
        }
        addActionMessage(getText("credentials.updateSuccess"));
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        compactUserVO = userService.getCompactUserVO();
    }

    @Override
    public CompactUserVO getModel() {
        return compactUserVO;
    }

    public void setUserService(DSUserService userService) {
        this.userService = userService;
    }

}