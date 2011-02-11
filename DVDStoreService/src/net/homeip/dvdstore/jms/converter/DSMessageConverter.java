/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.jms.converter;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import net.homeip.dvdstore.pojo.webservice.vo.OrderVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserChangeVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

/**
 *
 * @author VU VIET PHUONG
 */
public class DSMessageConverter implements MessageConverter {

    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        return ((TextMessage) message).getText();
    }

    public Message toMessage(Object message, Session session)
            throws JMSException, MessageConversionException {
        Message msg;
        if (message instanceof String) {
            msg = session.createTextMessage();
            ((TextMessage)msg).setText((String) message);
        } else if (message instanceof OrderVO ||
                message instanceof UserChangeVO ||
                message instanceof UserVO) {
            msg = session.createObjectMessage();
            ((ObjectMessage)msg).setObject((Serializable)message);
        } else {
            throw new MessageConversionException("can't convert to jms message");
        }
        return msg;
    }
}
