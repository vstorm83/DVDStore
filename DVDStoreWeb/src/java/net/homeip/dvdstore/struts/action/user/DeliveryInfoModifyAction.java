/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.action.user;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.homeip.dvdstore.service.DSUserService;
import net.homeip.dvdstore.service.exception.EmailDuplicateException;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.struts.action.DSActionSupport;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;

/**
 *
 * @author VU VIET PHUONG
 */
public class DeliveryInfoModifyAction extends DSActionSupport
        implements ModelDriven<DeliveryInfo>, Preparable {
    private DSUserService userService;
    private DeliveryInfo deliveryInfo;

    public String form() {
        return INPUT;
    }

    public String process() {
        try {
            userService.updateDeliveryInfo(deliveryInfo);
        } catch (EmailDuplicateException eEx) {
            addFieldError("email", getText("email.duplicate"));
        } catch (InvalidInputException inEx) {
            String field = inEx.getMessage().substring(inEx.getMessage().indexOf(".") + 1);
            addFieldError(field, getText(field + "." + "invalid"));
        }
        if (hasFieldErrors()) {
            return INPUT;
        }
        addActionMessage(getText("deliveryInfo.updateSuccess"));
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        deliveryInfo = userService.getDeliveryInfo();
    }    

    @Override
    public DeliveryInfo getModel() {
        return deliveryInfo;
    }

    public void setUserService(DSUserService userService) {
        this.userService = userService;
    }
    
}
