/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.validator;

import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author VU VIET PHUONG
 */
public class CaptchaValidator extends FieldValidatorSupport {
    private String captchaServiceExpr;
    private String captchaIdExpr;
    
    @Override
    public void validate(Object object) throws ValidationException {
        log.trace(captchaIdExpr + " " + captchaServiceExpr);
        GenericManageableCaptchaService captchaService = obtainCaptchaService(object);
        String captchaId = obtainCaptchaId(object);

        if (captchaService == null) {
            throw new ValidationException("Không lấy được captchaService");
        }
        if (captchaId == null) {
            throw new ValidationException("Không lấy được captchaId");
        }
        if (!captchaService.validateResponseForID(captchaId, getFieldValue(getFieldName(), object))) {
            log.debug("captcha is invalid");
            addFieldError(getFieldName(), object);
        }
    }

    public void setCaptchaIdExpr(String captchaIdExpr) {
        this.captchaIdExpr = captchaIdExpr;
    }

    public void setCaptchaServiceExpr(String captchaServiceExpr) {
        this.captchaServiceExpr = captchaServiceExpr;
    }    

    private GenericManageableCaptchaService obtainCaptchaService(Object object) {
        try {
            return (GenericManageableCaptchaService) getReflectedProperty(captchaServiceExpr, object);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    private String obtainCaptchaId(Object object) {
        try {
            return (String) getReflectedProperty(captchaIdExpr, object);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    private Object getReflectedProperty(String propertyName, Object object)
            throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        return object.getClass().getMethod("get" + capitalize(propertyName)).invoke(object);
    }
    
    private String capitalize(String str) {
        str = str.trim();
        if (str.length() == 0) {
            return str;
        }
        Character firstChar = str.charAt(0);
        firstChar = Character.toUpperCase(firstChar);
        if (str.length() == 1) {
            return firstChar.toString();
        } else {
            return firstChar.toString() + str.substring(1);
        }
    }
}
