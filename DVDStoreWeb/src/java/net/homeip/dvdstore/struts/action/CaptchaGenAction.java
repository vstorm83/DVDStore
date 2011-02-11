/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.struts.action;

import com.octo.captcha.service.multitype.GenericManageableCaptchaService;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;

/**
 *
 * @author VU VIET PHUONG
 */
public class CaptchaGenAction implements ServletRequestAware {
    private GenericManageableCaptchaService captchaService;
    private InputStream inputStream;
    private String captchaId;

    public String execute() throws Exception {
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        BufferedImage challenge = captchaService.getImageChallengeForID(captchaId);
        JPEGImageEncoder jpegEncoder =
                JPEGCodec.createJPEGEncoder(jpegOutputStream);        
        jpegEncoder.encode(challenge);
        inputStream = new ByteArrayInputStream(jpegOutputStream.toByteArray());
        return ActionSupport.SUCCESS;
    }

    public void setCaptchaService(GenericManageableCaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        captchaId = request.getSession(true).getId();
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }    
}
