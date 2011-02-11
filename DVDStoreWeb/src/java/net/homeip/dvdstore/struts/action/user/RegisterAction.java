/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.user;

import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.http.HttpServletRequest;
import net.homeip.dvdstore.service.DSUserService;
import net.homeip.dvdstore.service.exception.EmailDuplicateException;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.UserNameDuplicateException;
import net.homeip.dvdstore.struts.action.DSActionSupport;
import net.homeip.dvdstore.pojo.web.vo.UserVO;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author VU VIET PHUONG
 */
public class RegisterAction extends DSActionSupport 
        implements ModelDriven<UserVO>, ServletRequestAware {
    private DSUserService userService;
    private String inputSecurityCode;
    private GenericManageableCaptchaService captchaService;
    private String captchaId;
    private UserVO user = new UserVO();

    public String form() throws Exception {
        return INPUT;
    }

    public String process() throws Exception {
        try {
            userService.register(user);
        } catch (UserNameDuplicateException unEx) {
            addFieldError("userName", getText("userName.duplicate"));
        } catch (EmailDuplicateException eEx) {
            addFieldError("deliveryInfo.email", getText("email.duplicate"));
        } catch (InvalidInputException inEx) {
            addFieldError(inEx.getMessage(), getText(inEx.getMessage() + "." + "invalid"));
        }
        if (hasFieldErrors()) {
            return INPUT;
        }
        addActionMessage("register.success");
        return SUCCESS;
    }

    @Override
    public UserVO getModel() {
        return user;
    }

    public String getInputSecurityCode() {
        return inputSecurityCode;
    }

    public void setInputSecurityCode(String inputSecurityCode) {
        this.inputSecurityCode = inputSecurityCode;
    }

    public GenericManageableCaptchaService getCaptchaService() {
        return captchaService;
    }

    public void setCaptchaService(GenericManageableCaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        captchaId = request.getSession().getId();
    }

    public void setUserService(DSUserService userService) {
        this.userService = userService;
    }
    
}
